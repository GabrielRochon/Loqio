package com.gabrielrochon.loqio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController
{
	@Autowired
	private ItemService itemService;

	@GetMapping("/")
	public String home()
	{
		return "Hello, World!";
	}

	@GetMapping("/items")
	public List<Item> getAllItems()
	{
		return itemService.getAllItems();
	}

}
