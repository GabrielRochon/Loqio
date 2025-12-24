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
}
