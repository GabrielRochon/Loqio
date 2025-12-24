package com.gabrielrochon.languagecontent.module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Module entities in the PostgreSQL database.
 * Provides CRUD operations and query methods for modules.
 * Extends JpaRepository to inherit standard database operations.
 */
@Repository
public interface ModuleRepository extends JpaRepository<Module, Long>
{
	// Custom query methods
	List<Module> findByLanguageId(Long languageId);
}
