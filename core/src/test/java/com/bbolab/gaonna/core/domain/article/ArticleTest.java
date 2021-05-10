package com.bbolab.gaonna.core.domain.article;

import com.bbolab.gaonna.core.AbstractContainerBaseTest;
import com.bbolab.gaonna.core.domain.member.MemberArticleLike;
import com.bbolab.gaonna.core.repository.ArticleRepository;
import com.bbolab.gaonna.core.repository.MemberArticleLikeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
public class ArticleTest extends AbstractContainerBaseTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MemberArticleLikeRepository memberArticleLikeRepository;

    @BeforeEach
    void beforeEach() {
        memberArticleLikeRepository.deleteAll();
        articleRepository.deleteAll();

        Article article = Article.builder().build();
        MemberArticleLike memberArticleLike = MemberArticleLike.builder().build();
        article.addMemberArticleLike(memberArticleLike);
        memberArticleLikeRepository.save(memberArticleLike);
        articleRepository.save(article);
    }

    @DisplayName("Builder.default test")
    @Test
    void builderDefault() {
        Article article = Article.builder().build();
        assertNotNull(article.getArticleLikeMembers());
        assertNotNull(article.getComments());
    }

    @DisplayName("LikeCount increment test")
    @Test
    void likeCountIncrement() {
        Article article = articleRepository.findAll().get(0);
        assertEquals(article.getLikeCount(), 1);
    }

    @DisplayName("LikeCount decrement test")
    @Test
    void likeCountDecrement() {
        Article article = articleRepository.findAll().get(0);
        MemberArticleLike memberArticleLike = memberArticleLikeRepository.findAll().get(0);
        article.removeMemberArticleLike(memberArticleLike);
        assertEquals(article.getLikeCount(), 0);
    }
}