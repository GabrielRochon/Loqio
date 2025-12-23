package com.gabrielrochon.loqio;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoqioApplication
{

	public static void main(String[] args)
	{
		// Load environment variables from .env file before Spring Boot starts
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		dotenv.entries().forEach(entry ->
		{
			if (System.getenv(entry.getKey()) == null)
			{
				System.setProperty(entry.getKey(), entry.getValue());
			}
		});

		// Run the application
		SpringApplication.run(LoqioApplication.class, args);
	}

}
