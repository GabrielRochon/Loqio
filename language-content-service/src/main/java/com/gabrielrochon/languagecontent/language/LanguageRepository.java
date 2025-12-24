package com.gabrielrochon.languagecontent.language;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Language entities in the PostgreSQL database.
 * Provides CRUD operations and query methods for languages.
 * Extends JpaRepository to inherit standard database operations.
 */
@Repository
public interface LanguageRepository extends JpaRepository<Language, Long>
{
	// Custom query methods can be added here if needed
}
