package com.gabrielrochon.languagecontent;

import com.gabrielrochon.languagecontent.language.Language;
import com.gabrielrochon.languagecontent.language.LanguageService;
import com.gabrielrochon.languagecontent.module.Module;
import com.gabrielrochon.languagecontent.module.ModuleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

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

    @Autowired
    private ModuleService moduleService;

    @Test
    public void testAddLanguage() {
        // Arrange
        Language newLanguage = new Language("Tagalog");

        // Act
        Language savedLanguage = languageService.addLanguage(newLanguage);

        // Assert
        assertThat(savedLanguage).isNotNull();
        assertThat(savedLanguage.getName()).isEqualTo("Tagalog");
        assertThat(savedLanguage.getId()).isNotNull();
    }

    @Test
    public void testGetAllLanguages() {
        // Arrange
        Language newLanguage1 = new Language("Klingon");
        Language newLanguage2 = new Language("French");
        languageService.addLanguage(newLanguage1);
        languageService.addLanguage(newLanguage2);

        // Act
        List<Language> languages = languageService.getAllLanguages();

        // Assert
        assertThat(languages).isNotEmpty();
        assertThat(languages.stream().anyMatch(l -> "Klingon".equals(l.getName()))).isTrue();
        assertThat(languages.stream().anyMatch(l -> "French".equals(l.getName()))).isTrue();
        assertThat(languages.stream().anyMatch(l -> "Tagalog".equals(l.getName()))).isFalse();
    }

    @Test
    public void testDeleteLanguage() {
        // Arrange
        Language newLanguage = new Language("Spanish");
        Language savedLanguage = languageService.addLanguage(newLanguage);

        // Act
        languageService.deleteLanguage(savedLanguage.getId());

        // Assert
        List<Language> languages = languageService.getAllLanguages();
        assertThat(languages.stream().noneMatch(l -> "Spanish".equals(l.getName()))).isTrue();
    }
}
