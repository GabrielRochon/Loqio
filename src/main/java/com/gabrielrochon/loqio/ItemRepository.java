package com.gabrielrochon.loqio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Item entities in the PostgreSQL database.
 * Provides CRUD operations and query methods for vocabulary items.
 * Extends JpaRepository to inherit standard database operations.
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long>
{
	// Custom query methods can be added here if needed
}
