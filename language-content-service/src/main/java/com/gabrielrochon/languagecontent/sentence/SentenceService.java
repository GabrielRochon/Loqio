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
	 * @return List of Sentence entities for the given module
	 */
	public List<Sentence> getSentencesByModuleId(Long moduleId)
	{
		return sentenceRepository.findByModuleId(moduleId);
	}

	/**
	 * Retrieves all sentences for a specific language from the database.
	 *
	 * @param languageId The ID of the language
	 * @return List of Sentence entities for the given language
	 */
	public List<Sentence> getSentencesByLanguageId(Long languageId)
	{
		return sentenceRepository.findByModuleLanguageId(languageId);
	}

	/**
	 * Adds a new sentence to the database.
	 *
	 * @param sentence the sentence to add
	 * @return the saved sentence entity
	 */
	public Sentence addSentence(Sentence sentence)
	{
		return sentenceRepository.save(sentence);
	}

	/**
	 * Deletes a sentence from the database by its ID.
	 *
	 * @param id the ID of the sentence to delete
	 */
	public void deleteSentence(Long id)
	{
		sentenceRepository.deleteById(id);
	}

}
