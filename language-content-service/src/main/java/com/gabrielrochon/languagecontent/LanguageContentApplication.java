package com.gabrielrochon.languagecontent;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LanguageContentApplication
{

	public static void main(String[] args)
	{
		// Load environment variables from .env file in project root before Spring Boot starts
		Dotenv dotenv = Dotenv.configure()
			.directory("../")  // Look for .env file in parent directory (project root)
			.ignoreIfMissing()
			.load();
		dotenv.entries().forEach(entry ->
		{
			if (System.getenv(entry.getKey()) == null)
			{
				System.setProperty(entry.getKey(), entry.getValue());
			}
		});

		// Run the application
		SpringApplication.run(LanguageContentApplication.class, args);
	}

	@Bean
	public CommandLineRunner clearCacheOnStartup(CacheManager cacheManager) 
	{
		return args -> 
		{
			// Clear all caches on application startup
			cacheManager.getCacheNames().forEach(cacheName -> 
			{
				cacheManager.getCache(cacheName).clear();
			});
		};
	}

}
