package com.bbolab.gaonna.api.service.geo;

import com.bbolab.gaonna.api.AbstractContainerBaseTest;
import com.bbolab.gaonna.api.exception.GeoDataFileHandlingException;
import com.bbolab.gaonna.core.domain.region.RegionL1;
import com.bbolab.gaonna.core.domain.region.RegionL2;
import com.bbolab.gaonna.core.domain.region.RegionL3;
import com.bbolab.gaonna.core.repository.RegionL1Repository;
import com.bbolab.gaonna.core.repository.RegionL2Repository;
import com.bbolab.gaonna.core.repository.RegionL3Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class GisDataUpdateServiceTest extends AbstractContainerBaseTest {

    @Autowired
    GisDataUpdateService gisDataUpdateService;
    @Autowired
    RegionL1Repository regionL1Repository;
    @Autowired
    RegionL2Repository regionL2Repository;
    @Autowired
    RegionL3Repository regionL3Repository;

    @BeforeEach
    void initTable() {
        regionL1Repository.deleteAll();
        regionL2Repository.deleteAll();
        regionL3Repository.deleteAll();
    }

    @DisplayName("[GIS] 시도 업데이트 요청")
    @Test
    void updateSido() {
        // given
        gisDataUpdateService.updateSido();

        // when
        List<RegionL1> regionL1s = regionL1Repository.findAll();

        // then
        assertEquals(regionL1s.size(), GisDataFileEndpointTest.SD_LENGTH);
        for (RegionL1 region : regionL1s) {
            assertNotNull(region.getName());
            assertNotNull(region.getAdmCode());
            assertNotNull(region.getVersion());
        }
    }

    @DisplayName("[GIS] 시군구 업데이트 요청 - 성공(시도 업데이트가 먼저 수행)")
    @Test
    void updateSgg() {
        // given
        gisDataUpdateService.updateSido();
        gisDataUpdateService.updateSgg();

        // when
        List<RegionL2> regionL2s = regionL2Repository.findAll();

        // then
        assertEquals(regionL2s.size(), GisDataFileEndpointTest.SGG_LENGTH);
        regionL2s.forEach(region -> {
            assertNotNull(region.getName());
            assertNotNull(region.getAdmCode());
            assertNotNull(region.getVersion());

            RegionL1 regionL1 = region.getRegionL1();
            assertNotNull(regionL1);
            assertTrue(regionL1.getRegionL2s().contains(region));
        });
    }

    @DisplayName("[GIS] 시군구 업데이트 요청 - 실패(시도 업데이트가 먼저 수행되지 않았을 때)")
    @Test
    void updateSgg_fail() {
        // given
        assertThrows(GeoDataFileHandlingException.class, () -> gisDataUpdateService.updateSgg());
    }

    @DisplayName("[GIS] 읍면동 업데이트 요청 - 성공(시도, 시군구 업데이트가 먼저 수행되었을 때)")
    @Test
    void updateEmd() {
        // given
        gisDataUpdateService.updateSido();
        gisDataUpdateService.updateSgg();
        gisDataUpdateService.updateEmd();

        // when
        List<RegionL3> regionL3s = regionL3Repository.findAll();

        // then
        assertEquals(regionL3s.size(), GisDataFileEndpointTest.EMD_LENGTH);
        for (RegionL3 region : regionL3s) {
            assertNotNull(region.getName());
            assertNotNull(region.getAdmCode());
            assertNotNull(region.getVersion());
            assertNotNull(region.getGeoPolygon());

            RegionL2 regionL2 = region.getRegionL2();
            assertNotNull(regionL2);
            assertTrue(regionL2.getRegionL3s().contains(region));
//            List<RegionL2> regionL2s = region.getRegionL2s();
//            assertNotEquals(regionL2s.size(), 0);
//            for (RegionL2 l2 : regionL2s) {
//                assertEquals(l2.getRegionL3().getId(), region.getId());
//            }
        }
    }

    @DisplayName("[GIS] 읍면동 업데이트 요청 - 실패(시도, 시군구 업데이트가 먼저 수행되지 않았을 때)")
    @Test
    void updateEmd_fail() {
        // given
        assertThrows(GeoDataFileHandlingException.class, () -> gisDataUpdateService.updateEmd());
    }
}