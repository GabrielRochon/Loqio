package com.gabrielrochon.languagecontent;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

}
