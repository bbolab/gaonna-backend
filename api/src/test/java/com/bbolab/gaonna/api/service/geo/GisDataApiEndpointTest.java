package com.bbolab.gaonna.api.service.geo;

import com.bbolab.gaonna.api.AbstractContainerBaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opengis.feature.simple.SimpleFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class GisDataApiEndpointTest extends AbstractContainerBaseTest {

    @Autowired
    public GisDataEndpoint gisDataEndpoint;

    @DisplayName("[API] 시도 Features request")
    @Test
    public void sdRequest() {
        // given & when
        Set<SimpleFeature> features = gisDataEndpoint.getSdFeatures();

        // then
        assertEquals(features.size(), 17);
        features.forEach(f -> {
            assertNotNull(f.getAttribute("GID"));
            assertNotNull(f.getAttribute("BASE_YEAR"));
            assertNotNull(f.getAttribute("SIDO_CD"));
            assertNotNull(f.getAttribute("SIDO_NM"));
        });
    }

    @DisplayName("[API] 시군구 Features request")
    @Test
    public void sggRequest() {
        Set<SimpleFeature> features = gisDataEndpoint.getSggFeatures();
        assertEquals(features.size(), 228);
        features.forEach(f -> {
            assertNotNull(f.getAttribute("GID"));
            assertNotNull(f.getAttribute("BASE_YEAR"));
            assertNotNull(f.getAttribute("SIGUNGU_CD"));
            assertNotNull(f.getAttribute("SGG_NM"));
        });
    }

    @DisplayName("[API] 읍면동 Features request")
    @Test
    public void emdRequest() {
        Set<SimpleFeature> features = gisDataEndpoint.getEmdFeatures();
        features.forEach(f -> {
            assertNotNull(f.getAttribute("GID"));
            assertNotNull(f.getAttribute("BASE_YEAR"));
            assertNotNull(f.getAttribute("ADM_CD"));
            assertNotNull(f.getAttribute("ADM_NM"));
        });
    }

}