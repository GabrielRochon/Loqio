package com.gabrielrochon.languagecontent;

import com.gabrielrochon.languagecontent.language.Language;
import com.gabrielrochon.languagecontent.language.LanguageService;
import com.gabrielrochon.languagecontent.module.Module;
import com.gabrielrochon.languagecontent.module.ModuleService;
import com.gabrielrochon.languagecontent.sentence.Sentence;
import com.gabrielrochon.languagecontent.sentence.SentenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

	@GetMapping("/")
	public String hello()
	{
		return "Hello, World!";
	}

	// Languages endpoints
	@GetMapping("/languages")
	public List<Language> getAllLanguages()
	{
		return languageService.getAllLanguages();
	}

	@PostMapping("/languages")
	public Language addLanguage(@RequestBody Language language)
	{
		return languageService.addLanguage(language);
	}

	@DeleteMapping("/languages/{id}")
	public void deleteLanguage(@PathVariable Long id)
	{
		languageService.deleteLanguage(id);
	}

	// Modules endpoints
	@GetMapping("/languages/{id}")
	public List<Module> getModulesByLanguage(@PathVariable Long id)
	{
		return moduleService.getModulesByLanguageId(id);
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
	
}
