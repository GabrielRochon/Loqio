package com.gabrielrochon.languagecontent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing vocabulary items (Items).
 * Provides business logic for retrieving and manipulating Item data
 * from the PostgreSQL database through the ItemRepository.
 */
@Service
public class ItemService
{

	@Autowired
	private ItemRepository itemRepository;

	/**
	 * Retrieves all vocabulary items from the database.
	 *
	 * @return List of all Item entities containing Tagalog-English translations
	 */
	public List<Item> getAllItems()
	{
		return itemRepository.findAll();
	}
}
