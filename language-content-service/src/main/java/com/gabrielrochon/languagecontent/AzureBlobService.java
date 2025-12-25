package com.gabrielrochon.languagecontent;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

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
			System.out.println("Attempting to download blob: " + blobName);
			BlobClient blobClient = containerClient.getBlobClient(blobName);
			System.out.println("Blob URL: " + blobClient.getBlobUrl());
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			blobClient.downloadStream(outputStream);
			byte[] data = outputStream.toByteArray();
			System.out.println("Downloaded blob " + blobName + " successfully, size: " + data.length + " bytes");
			return data;
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
