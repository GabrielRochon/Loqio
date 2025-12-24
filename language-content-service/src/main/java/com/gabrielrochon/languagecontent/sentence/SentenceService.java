package com.gabrielrochon.languagecontent.sentence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing sentences.
 * Provides business logic for retrieving and manipulating Sentence data
 * from the PostgreSQL database through the SentenceRepository.
 */
@Service
public class SentenceService
{

	@Autowired
	private SentenceRepository sentenceRepository;

	/**
	 * Retrieves all sentences for a specific module from the database.
	 *
	 * @param moduleId The ID of the module
	 * @return List of SentenceResponse DTOs for the given module
	 */
	public List<SentenceResponse> getSentencesByModuleId(Long moduleId)
	{
		return sentenceRepository.findByModuleId(moduleId).stream()
				.map(this::mapToResponse)
				.collect(Collectors.toList());
	}

	/**
	 * Retrieves all sentences for a specific language from the database.
	 *
	 * @param languageId The ID of the language
	 * @return List of SentenceResponse DTOs for the given language
	 */
	public List<SentenceResponse> getSentencesByLanguageId(Long languageId)
	{
		return sentenceRepository.findByModuleLanguageId(languageId).stream()
				.map(this::mapToResponse)
				.collect(Collectors.toList());
	}
	
}
