package com.gabrielrochon.languagecontent;

import com.gabrielrochon.languagecontent.language.Language;
import com.gabrielrochon.languagecontent.language.LanguageService;
import com.gabrielrochon.languagecontent.module.Module;
import com.gabrielrochon.languagecontent.module.ModuleService;
import com.gabrielrochon.languagecontent.sentence.Sentence;
import com.gabrielrochon.languagecontent.sentence.SentenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
public class LanguageContentController
{
	@Autowired
	private LanguageService languageService;

	@Autowired
	private ModuleService moduleService;

	@Autowired
	private SentenceService sentenceService;

	@Autowired
	private AzureBlobService azureBlobService;

	@GetMapping("/")
	public String hello()
	{
		return "Hello, World!";
	}

	// Test endpoint to verify Azure Storage configuration
	@GetMapping("/test-azure")
	public String testAzureConnection()
	{
		try
		{
			// Try to check if container exists
			boolean containerExists = azureBlobService.containerExists();
			// Also check if one of the images exists
			boolean tagalogImageExists = azureBlobService.blobExists("Tagalog/background.jpg");
			return "Azure Storage connection successful. Container exists: " + containerExists + ", Tagalog image exists: " + tagalogImageExists;
		}
		catch (Exception e)
		{
			return "Azure Storage connection failed: " + e.getMessage();
		}
	}

	// Image endpoint to serve private blobs from Azure Storage
	@GetMapping("/images/**")
	public ResponseEntity<byte[]> getImage(HttpServletRequest request)
	{
		String requestURI = request.getRequestURI();
		String encodedImageName = requestURI.substring("/images/".length());
		try
		{
			String imageName = URLDecoder.decode(encodedImageName, StandardCharsets.UTF_8);
			System.out.println("Image request for: " + imageName + " (decoded from: " + encodedImageName + ")");
			byte[] imageBytes = azureBlobService.downloadBlob(imageName);
			System.out.println("Serving image " + imageName + " with " + imageBytes.length + " bytes");
			return ResponseEntity.ok()
					.body(imageBytes);
		}
		catch (Exception e)
		{
			System.err.println("Error serving image " + encodedImageName + ": " + e.getMessage());
			return ResponseEntity.notFound().build();
		}
	}

	// Languages endpoints
	@GetMapping("/languages")
	public List<Language> getAllLanguages()
	{
		return languageService.getAllLanguages();
	}

	// Clear languages cache
	@PostMapping("/languages/cache/clear")
	public String clearLanguagesCache()
	{
		languageService.clearLanguagesCache();
		return "Languages cache cleared";
	}

	@PostMapping("/languages")
	public Language addLanguage(@RequestBody Language language)
	{
		return languageService.addLanguage(language);
	}

	@PutMapping("/languages/{id}")
	public Language updateLanguage(@PathVariable Long id, @RequestBody Language language)
	{
		return languageService.updateLanguage(id, language);
	}

	@DeleteMapping("/languages/{id}")
	public void deleteLanguage(@PathVariable Long id)
	{
		languageService.deleteLanguage(id);
	}

	// Language details endpoint
	@GetMapping("/languages/{name}")
	public Language getLanguageByName(@PathVariable String name)
	{
		return languageService.getLanguageByName(name);
	}

	// Modules endpoints
	@GetMapping("/languages/{name}/modules")
	public List<Module> getModulesByLanguage(@PathVariable String name)
	{
		return moduleService.getModulesByLanguageName(name);
	}

	@PostMapping("/modules")
	public Module addModule(@RequestBody Module module)
	{
		return moduleService.addModule(module);
	}

	@DeleteMapping("/modules/{id}")
	public void deleteModule(@PathVariable Long id)
	{
		moduleService.deleteModule(id);
	}

	// Sentences endpoints
	@GetMapping("/modules/{id}/sentences")
	public List<Sentence> getSentencesByModule(@PathVariable Long id)
	{
		return sentenceService.getSentencesByModuleId(id);
	}

	@PostMapping("/sentences")
	public Sentence addSentence(@RequestBody Sentence sentence)
	{
		return sentenceService.addSentence(sentence);
	}

	@DeleteMapping("/sentences/{id}")
	public void deleteSentence(@PathVariable Long id)
	{
		sentenceService.deleteSentence(id);
	}

}
