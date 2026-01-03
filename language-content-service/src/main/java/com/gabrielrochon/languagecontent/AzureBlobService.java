package com.gabrielrochon.languagecontent;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

/**
 * Service for interacting with Azure Blob Storage.
 * Provides methods to download blobs from the configured container.
 */
@Service
public class AzureBlobService
{

	private final BlobContainerClient containerClient;

	public AzureBlobService(
		@Value("${azure.storage.account-name}") String accountName,
		@Value("${azure.storage.account-key}") String accountKey,
		@Value("${azure.storage.container-name}") String containerName)
	{

		String endpoint = String.format("https://%s.blob.core.windows.net", accountName);
		BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
			.endpoint(endpoint)
			.credential(new com.azure.storage.common.StorageSharedKeyCredential(accountName, accountKey))
			.buildClient();

		this.containerClient = blobServiceClient.getBlobContainerClient(containerName);
	}

	/**
	 * Downloads a blob as byte array.
	 *
	 * @param blobName the name of the blob to download
	 * @return byte array of the blob content
	 * @throws RuntimeException if the blob cannot be downloaded
	 */
	public byte[] downloadBlob(String blobName)
	{
		try
		{
			// Get cached Base64 string and decode to bytes
			String base64Data = downloadBlobAsBase64(blobName);
			byte[] data = Base64.getDecoder().decode(base64Data);
			System.out.println("Decoded cached blob " + blobName + " from Base64, size: " + data.length + " bytes");
			return data;
		}
		catch (Exception e)
		{
			System.err.println("Failed to decode cached blob: " + blobName + ", error: " + e.getMessage());
			throw new RuntimeException("Failed to decode cached blob: " + blobName, e);
		}
	}

	/**
	 * Downloads a blob and caches it as Base64 string for JSON serialization compatibility.
	 *
	 * @param blobName the name of the blob to download
	 * @return Base64 encoded string of the blob content
	 * @throws RuntimeException if the blob cannot be downloaded
	 */
	@Cacheable(value = "blobs", key = "#blobName")
	private String downloadBlobAsBase64(String blobName)
	{
		try
		{
			System.out.println("Attempting to download blob: " + blobName);
			BlobClient blobClient = containerClient.getBlobClient(blobName);
			System.out.println("Blob URL: " + blobClient.getBlobUrl());
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			blobClient.downloadStream(outputStream);
			byte[] data = outputStream.toByteArray();
			String base64Data = Base64.getEncoder().encodeToString(data);
			System.out.println("Downloaded and cached blob " + blobName + " as Base64, original size: " + data.length + " bytes");
			return base64Data;
		}
		catch (Exception e)
		{
			System.err.println("Failed to download blob: " + blobName + ", error: " + e.getMessage());
			throw new RuntimeException("Failed to download blob: " + blobName, e);
		}
	}

	/**
	 * Checks if a blob exists.
	 *
	 * @param blobName the name of the blob
	 * @return true if the blob exists, false otherwise
	 */
	@Cacheable(value = "blob-existence", key = "#blobName")
	public boolean blobExists(String blobName)
	{
		try
		{
			BlobClient blobClient = containerClient.getBlobClient(blobName);
			boolean exists = blobClient.exists();
			System.out.println("Blob " + blobName + " exists: " + exists);
			return exists;
		}
		catch (Exception e)
		{
			System.err.println("Error checking if blob exists " + blobName + ": " + e.getMessage());
			return false;
		}
	}

	/**
	 * Checks if the container exists.
	 *
	 * @return true if the container exists, false otherwise
	 */
	public boolean containerExists()
	{
		try
		{
			return containerClient.exists();
		}
		catch (Exception e)
		{
			System.err.println("Error checking container existence: " + e.getMessage());
			return false;
		}
	}
}
