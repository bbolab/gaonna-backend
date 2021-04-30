package com.bbolab.gaonna.core.domain.article;

import com.bbolab.gaonna.core.repository.ArticleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@ActiveProfiles("local")
@Transactional
@SpringBootTest
@EnableJpaRepositories
@ContextConfiguration
@AutoConfigureMockMvc
class ArticleTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    void test() throws Exception {
    }
}