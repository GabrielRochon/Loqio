package com.gabrielrochon.languagecontent.module;

import com.gabrielrochon.languagecontent.language.Language;
import com.gabrielrochon.languagecontent.language.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

	@Autowired
	private LanguageService languageService;

	/**
	 * Retrieves all modules for a specific language from the database.
	 *
	 * @param languageId The ID of the language
	 * @return List of Module entities for the given language
	 */
	@Cacheable(value = "modules", key = "#languageId")
	public List<Module> getModulesByLanguageId(Long languageId)
	{
		return moduleRepository.findByLanguageId(languageId);
	}

	/**
	 * Retrieves all modules for a specific language by name.
	 *
	 * @param languageName The name of the language
	 * @return List of Module entities for the given language
	 */
	@Cacheable(value = "modules", key = "#languageName")
	public List<Module> getModulesByLanguageName(String languageName)
	{
		Language language = languageService.getLanguageByName(languageName);
		if (language == null)
		{
			return List.of(); // Return empty list if language not found
		}
		return moduleRepository.findByLanguageId(language.getId());
	}

	/**
	 * Adds a new module to the database.
	 *
	 * @param module the module to add
	 * @return the saved module entity
	 */
	@CacheEvict(value = "modules", allEntries = true)
	public Module addModule(Module module)
	{
		return moduleRepository.save(module);
	}

	/**
	 * Deletes a module from the database by its ID.
	 *
	 * @param id the ID of the module to delete
	 */
	@CacheEvict(value = "modules", allEntries = true)
	public void deleteModule(Long id)
	{
		moduleRepository.deleteById(id);
	}
}
