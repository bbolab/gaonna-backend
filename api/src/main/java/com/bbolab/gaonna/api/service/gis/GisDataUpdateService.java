package com.bbolab.gaonna.api.service.gis;

import com.bbolab.gaonna.api.exception.GeoDataFileHandlingException;
import com.bbolab.gaonna.api.service.gis.feature.GisFeatureProvider;
import com.bbolab.gaonna.core.domain.region.RegionL1;
import com.bbolab.gaonna.core.domain.region.RegionL2;
import com.bbolab.gaonna.core.domain.region.RegionL3;
import com.bbolab.gaonna.core.repository.RegionL1Repository;
import com.bbolab.gaonna.core.repository.RegionL2Repository;
import com.bbolab.gaonna.core.repository.RegionL3Repository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.MultiPolygon;
import org.opengis.feature.simple.SimpleFeature;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GisDataUpdateService {

    private final GisFeatureProvider gisFeatureProvider;
    private final RegionL1Repository regionL1Repository;
    private final RegionL2Repository regionL2Repository;
    private final RegionL3Repository regionL3Repository;

    @Transactional
    public void updateSido() {
        Set<SimpleFeature> features = gisFeatureProvider.getSdFeatures();
        for(SimpleFeature feature : features) {
            String admCode = (String) feature.getAttribute("SIDO_CD");
            String name = (String) feature.getAttribute("SIDO_NM");
            String date = (String) feature.getAttribute("BASE_DATE");

            RegionL1 regionL1 = regionL1Repository.findByAdmCode(admCode).orElseGet(RegionL1::new);
            regionL1.setAdmCode(admCode);
            regionL1.setName(name);
            regionL1.setVersion(date);
            regionL1Repository.save(regionL1);
        }
    }

    @Transactional
    public void updateSgg() {
        Set<SimpleFeature> features = gisFeatureProvider.getSggFeatures();
        for (SimpleFeature feature : features) {
            String admCode = (String) feature.getAttribute("SIGUNGU_CD");
            String name = (String) feature.getAttribute("SIGUNGU_NM");
            String date = (String) feature.getAttribute("BASE_DATE");

            RegionL2 regionL2 = regionL2Repository.findByAdmCode(admCode).orElseGet(RegionL2::new);
            regionL2.setAdmCode(admCode);
            regionL2.setName(name);
            regionL2.setVersion(date);

            String admL1Code = admCode.substring(0, 2);
            RegionL1 regionL1 = regionL1Repository.findByAdmCode(admL1Code).orElseThrow(() -> {
                String msg = String.format("Invalid RegionL2 adm code [%s], there is no RegionL1 adm code [%s]", admCode, admL1Code);
                return new GeoDataFileHandlingException(msg);
            });

            regionL1.addRegionL2(regionL2);
            regionL2Repository.save(regionL2);
        }
    }

    @Transactional
    public void updateEmd() {
        Set<SimpleFeature> features = gisFeatureProvider.getEmdFeatures();
        for (SimpleFeature feature : features) {
            String admCode = (String) feature.getAttribute("ADM_DR_CD");
            String name = (String) feature.getAttribute("ADM_DR_NM");
            String date = (String) feature.getAttribute("BASE_DATE");

            RegionL3 regionL3 = regionL3Repository.findByAdmCode(admCode).orElseGet(RegionL3::new);
            regionL3.setAdmCode(admCode);
            regionL3.setName(name);
            regionL3.setVersion(date);
            regionL3.setGeoPolygon((MultiPolygon) feature.getAttribute("the_geom"));

            String admL2Code = admCode.substring(0, 5);
            RegionL2 regionL2 = regionL2Repository.findByAdmCode(admL2Code).orElseThrow(() -> {
                String msg = String.format("Invalid RegionL3 adm code [%s], there is no RegionL2 adm code [%s]", admCode, admL2Code);
                return new GeoDataFileHandlingException(msg);
            });

            regionL2.addRegionL3(regionL3);
            regionL3Repository.save(regionL3);
        }
    }
}
