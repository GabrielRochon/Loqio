package com.gabrielrochon.languagecontent;

import com.gabrielrochon.languagecontent.language.Language;
import com.gabrielrochon.languagecontent.language.LanguageService;
import com.gabrielrochon.languagecontent.module.Module;
import com.gabrielrochon.languagecontent.module.ModuleService;
import com.gabrielrochon.languagecontent.sentence.Sentence;
import com.gabrielrochon.languagecontent.sentence.SentenceService;
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

    @Autowired
    private SentenceService sentenceService;

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
        Language newLanguage1 = new Language("Tagalog");
        Language newLanguage2 = new Language("English");
        languageService.addLanguage(newLanguage1);
        languageService.addLanguage(newLanguage2);

        // Act
        List<Language> languages = languageService.getAllLanguages();

        // Assert
        assertThat(languages).isNotEmpty();
        assertThat(languages.stream().anyMatch(l -> "Tagalog".equals(l.getName()))).isTrue();
        assertThat(languages.stream().anyMatch(l -> "English".equals(l.getName()))).isTrue();
        assertThat(languages.stream().anyMatch(l -> "Spanish".equals(l.getName()))).isFalse();
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

    @Test
    public void testGetModulesByLanguageId() {
        // Arrange
        Language newLanguage = new Language("Tagalog");
        Language savedLanguage = languageService.addLanguage(newLanguage);

        Module module1 = new Module(savedLanguage, "Basic Greetings");
        Module module2 = new Module(savedLanguage, "Food Vocabulary");
        moduleService.addModule(module1);
        moduleService.addModule(module2);

        // Act
        List<Module> modules = moduleService.getModulesByLanguageId(savedLanguage.getId());

        // Assert
        assertThat(modules).isNotEmpty();
        assertThat(modules.size()).isEqualTo(2);
        assertThat(modules.stream().anyMatch(m -> "Basic Greetings".equals(m.getName()))).isTrue();
        assertThat(modules.stream().anyMatch(m -> "Food Vocabulary".equals(m.getName()))).isTrue();
    }

    @Test
    public void testAddModule() {
        // Arrange
        Language newLanguage = new Language("Tagalog");
        Language savedLanguage = languageService.addLanguage(newLanguage);

        Module newModule = new Module(savedLanguage, "Basic Vocabulary");

        // Act
        Module savedModule = moduleService.addModule(newModule);

        // Assert
        assertThat(savedModule).isNotNull();
        assertThat(savedModule.getName()).isEqualTo("Basic Vocabulary");
        assertThat(savedModule.getId()).isNotNull();
        assertThat(savedModule.getLanguage().getName()).isEqualTo("Tagalog");
    }

    @Test
    public void testDeleteModule() {
        // Arrange
        Language newLanguage = new Language("Tagalog");
        Language savedLanguage = languageService.addLanguage(newLanguage);

        Module newModule = new Module(savedLanguage, "Basic Phrases");
        Module savedModule = moduleService.addModule(newModule);

        // Act
        moduleService.deleteModule(savedModule.getId());

        // Assert
        List<Module> modules = moduleService.getModulesByLanguageId(savedLanguage.getId());
        assertThat(modules.stream().noneMatch(m -> "Basic Phrases".equals(m.getName()))).isTrue();
    }

    @Test
    public void testAddSentence() {
        // Arrange
        Language newLanguage = new Language("Tagalog");
        Language savedLanguage = languageService.addLanguage(newLanguage);

        Module newModule = new Module(savedLanguage, "Greetings");
        Module savedModule = moduleService.addModule(newModule);

        Sentence newSentence = new Sentence(savedModule, 1, "Kumusta", "Hello", 1);

        // Act
        Sentence savedSentence = sentenceService.addSentence(newSentence);

        // Assert
        assertThat(savedSentence).isNotNull();
        assertThat(savedSentence.getLearningText()).isEqualTo("Kumusta");
        assertThat(savedSentence.getTranslationText()).isEqualTo("Hello");
        assertThat(savedSentence.getPosition()).isEqualTo(1);
        assertThat(savedSentence.getSpeaker()).isEqualTo(1);
        assertThat(savedSentence.getId()).isNotNull();
    }

    @Test
    public void testDeleteSentence() {
        // Arrange
        Language newLanguage = new Language("Tagalog");
        Language savedLanguage = languageService.addLanguage(newLanguage);

        Module newModule = new Module(savedLanguage, "Polite Expressions");
        Module savedModule = moduleService.addModule(newModule);

        Sentence newSentence = new Sentence(savedModule, 1, "Salamat", "Thank you", 1);
        Sentence savedSentence = sentenceService.addSentence(newSentence);

        // Act
        sentenceService.deleteSentence(savedSentence.getId());

        // Assert
        List<Sentence> sentences = sentenceService.getSentencesByModuleId(savedModule.getId());
        assertThat(sentences.stream().noneMatch(s -> "Salamat".equals(s.getLearningText()))).isTrue();
    }
}
