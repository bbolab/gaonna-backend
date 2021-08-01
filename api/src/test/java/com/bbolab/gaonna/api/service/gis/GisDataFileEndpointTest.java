package com.bbolab.gaonna.api.service.gis;

import com.bbolab.gaonna.api.AbstractContainerBaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opengis.feature.simple.SimpleFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class GisDataFileEndpointTest extends AbstractContainerBaseTest {

    public static int SD_LENGTH = 17;
    public static int SGG_LENGTH = 250;
    public static int EMD_LENGTH = 3493;

    @Autowired
    public GisDataEndpoint gisDataEndpoint;

    @DisplayName("[File] 시도 Features request")
    @Test
    public void sdRequest() {
        // given & when
        Set<SimpleFeature> features = gisDataEndpoint.getSdFeatures();

        // then
        assertEquals(features.size(), SD_LENGTH);
        features.forEach(f -> {
            assertNotNull(f.getAttribute("the_geom"));
            assertNotNull(f.getAttribute("BASE_DATE"));
            assertNotNull(f.getAttribute("SIDO_CD"));
            assertNotNull(f.getAttribute("SIDO_NM"));
        });
    }

    @DisplayName("[File] 시군구 Features request")
    @Test
    public void sggRequest() {
         Set<SimpleFeature> features = gisDataEndpoint.getSggFeatures();
        assertEquals(features.size(), SGG_LENGTH);
        features.forEach(f -> {
            assertNotNull(f.getAttribute("the_geom"));
            assertNotNull(f.getAttribute("BASE_DATE"));
            assertNotNull(f.getAttribute("SIGUNGU_CD"));
            assertNotNull(f.getAttribute("SIGUNGU_NM"));
        });
    }

    @DisplayName("[File] 읍면동 Features request")
    @Test
    public void emdRequest() {
        Set<SimpleFeature> features = gisDataEndpoint.getEmdFeatures();
        assertEquals(features.size(), EMD_LENGTH);
        features.forEach(f -> {
            assertNotNull(f.getAttribute("the_geom"));
            assertNotNull(f.getAttribute("BASE_DATE"));
            assertNotNull(f.getAttribute("ADM_DR_CD"));
            assertNotNull(f.getAttribute("ADM_DR_NM"));
        });
    }
}