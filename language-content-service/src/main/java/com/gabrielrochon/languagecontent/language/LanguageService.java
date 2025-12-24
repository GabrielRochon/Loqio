package com.gabrielrochon.languagecontent.language;

import org.springframework.beans.factory.annotation.Autowired;
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
	public Language addLanguage(Language language)
	{
		return languageRepository.save(language);
	}

	/**
	 * Deletes a language from the database by its ID.
	 *
	 * @param id the ID of the language to delete
	 */
	public void deleteLanguage(Long id)
	{
		languageRepository.deleteById(id);
	}
}
