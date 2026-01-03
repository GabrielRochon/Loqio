package com.gabrielrochon.languagecontent.language;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing languages.
 * Provides business logic for retrieving and manipulating Language data
 * from the PostgreSQL database through the LanguageRepository.
 */
@Service
public class LanguageService
{

	@Autowired
	private LanguageRepository languageRepository;

	/**
	 * Retrieves all languages from the database.
	 *
	 * @return List of all Language entities
	 */
	@Cacheable("languages")
	public List<Language> getAllLanguages()
	{
		return languageRepository.findAll();
	}

	/**
	 * Adds a new language to the database.
	 *
	 * @param language the language to add
	 * @return the saved language entity
	 */
	@CacheEvict(value = "languages", allEntries = true)
	public Language addLanguage(Language language)
	{
		return languageRepository.save(language);
	}

	/**
	 * Retrieves a language by its name.
	 *
	 * @param name the name of the language
	 * @return the Language entity
	 */
	@Cacheable(value = "languages", key = "#name")
	public Language getLanguageByName(String name)
	{
		return languageRepository.findByName(name);
	}

	/**
	 * Updates an existing language in the database.
	 *
	 * @param id the ID of the language to update
	 * @param language the updated language data
	 * @return the updated language entity
	 */
	@CacheEvict(value = "languages", allEntries = true)
	public Language updateLanguage(Long id, Language language)
	{
		Language existingLanguage = languageRepository.findById(id).orElseThrow(() -> new RuntimeException("Language not found"));
		existingLanguage.setName(language.getName());
		existingLanguage.setBackgroundImageUrl(language.getBackgroundImageUrl());
		existingLanguage.setCountryCode(language.getCountryCode());
		existingLanguage.setLanguagePresentation(language.getLanguagePresentation());
		return languageRepository.save(existingLanguage);
	}

	/**
	 * Deletes a language from the database by its ID.
	 *
	 * @param id the ID of the language to delete
	 */
	@CacheEvict(value = "languages", allEntries = true)
	public void deleteLanguage(Long id)
	{
		languageRepository.deleteById(id);
	}
}
