package com.gabrielrochon.languagecontent.module;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing modules.
 * Provides business logic for retrieving and manipulating Module data
 * from the PostgreSQL database through the ModuleRepository.
 */
@Service
public class ModuleService
{

	@Autowired
	private ModuleRepository moduleRepository;

	/**
	 * Retrieves all modules for a specific language from the database.
	 *
	 * @param languageId The ID of the language
	 * @return List of Module entities for the given language
	 */
	public List<Module> getModulesByLanguageId(Long languageId)
	{
		return moduleRepository.findByLanguageId(languageId);
	}
}
