package com.bbolab.gaonna.core.domain.quest;

import com.bbolab.gaonna.core.repository.QuestRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles(value = "local")
@SpringBootTest
class QuestTest {

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
            Assertions.assertEquals(quest1.getLocation().toString(), PointWKT);
        } catch (ParseException e) {
            Assertions.fail("parse exception is not expected");
        }
    }
}