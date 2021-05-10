package com.bbolab.gaonna.core.domain.article;

import com.bbolab.gaonna.core.AbstractContainerBaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
public class ArticleTest extends AbstractContainerBaseTest {

    @DisplayName("Builder.default test")
    @Test
    void builderDefault() {
        Article article = Article.builder().build();
        assertNotNull(article.getArticleLikeMembers());
        assertNotNull(article.getComments());
    }
}