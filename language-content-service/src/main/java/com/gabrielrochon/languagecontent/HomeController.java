package com.gabrielrochon.languagecontent;

import com.gabrielrochon.languagecontent.language.Language;
import com.gabrielrochon.languagecontent.language.LanguageService;
import com.gabrielrochon.languagecontent.module.Module;
import com.gabrielrochon.languagecontent.module.ModuleService;
import com.gabrielrochon.languagecontent.sentence.Sentence;
import com.gabrielrochon.languagecontent.sentence.SentenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController
{
	@Autowired
	private LanguageService languageService;

	@Autowired
	private ModuleService moduleService;

	@Autowired
	private SentenceService sentenceService;

	@GetMapping("/")
	public String home()
	{
		return "Hello, World!";
	}

	@GetMapping("/languages")
	public List<Language> getAllLanguages()
	{
		return languageService.getAllLanguages();
	}

	@GetMapping("/languages/{id}")
	public List<Module> getModulesByLanguage(@PathVariable Long id)
	{
		return moduleService.getModulesByLanguageId(id);
	}

	@GetMapping("/modules/{id}/sentences")
	public List<Sentence> getSentencesByModule(@PathVariable Long id)
	{
		return sentenceService.getSentencesByModuleId(id);
	}

}
