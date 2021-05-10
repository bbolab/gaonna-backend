package com.bbolab.gaonna.core.domain.quest;

import com.bbolab.gaonna.core.AbstractContainerBaseTest;
import com.bbolab.gaonna.core.domain.article.Article;
import com.bbolab.gaonna.core.repository.QuestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
public class QuestTest extends AbstractContainerBaseTest {

    @Autowired
    private QuestRepository questRepository;

    @BeforeEach
    void beforeEach(){
        questRepository.deleteAll();
    }

    @DisplayName("hibernate-spatial Point Test")
    @Test
    void pointTest() {
        String PointWKT = "POINT (37.516455 126.721757)";
        try {
            Point point = (Point) new WKTReader().read(PointWKT);
            Quest quest = Quest.builder().price(1000).location(point).build();
            questRepository.save(quest);

            Quest quest1 = questRepository.findAll().get(0);
            assertEquals(quest1.getLocation().toString(), PointWKT);
        } catch (ParseException e) {
            fail("parse exception is not expected");
        }
    }

    @DisplayName("Builder.default test")
    @Test
    void builderDefault() {
        Quest quest = Quest.builder().build();
        assertNotNull(quest.getQuestTags());
        assertNotNull(quest.getMemberQuest());
        assertNotNull(quest.getQuestRegionL3s());
        assertNotNull(quest.getCategoryValueList());
    }

}

