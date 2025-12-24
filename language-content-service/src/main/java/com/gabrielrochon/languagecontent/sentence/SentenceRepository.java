package com.gabrielrochon.languagecontent.sentence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Sentence entities in the PostgreSQL database.
 * Provides CRUD operations and query methods for sentences.
 * Extends JpaRepository to inherit standard database operations.
 */
@Repository
public interface SentenceRepository extends JpaRepository<Sentence, Long>
{
	// Custom query methods
	List<Sentence> findByModuleId(Long moduleId);

	List<Sentence> findByModuleLanguageId(Long languageId);
}
