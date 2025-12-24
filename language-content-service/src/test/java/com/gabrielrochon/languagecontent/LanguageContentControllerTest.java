package com.gabrielrochon.languagecontent;

import com.gabrielrochon.languagecontent.language.Language;
import com.gabrielrochon.languagecontent.language.LanguageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
})
public class LanguageContentControllerTest {

    @Autowired
    private LanguageService languageService;

    @Test
    public void testAddLanguage() {
        // Arrange
        Language newLanguage = new Language("Test Language");

        // Act
        Language savedLanguage = languageService.addLanguage(newLanguage);

        // Assert
        assertThat(savedLanguage).isNotNull();
        assertThat(savedLanguage.getName()).isEqualTo("Test Language");
        assertThat(savedLanguage.getId()).isNotNull();
    }
}
