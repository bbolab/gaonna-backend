package com.bbolab.gaonna.api.service.geo;

import com.bbolab.gaonna.api.exception.HttpRequestFailureException;
import com.bbolab.gaonna.core.domain.region.RegionL1;
import com.bbolab.gaonna.core.domain.region.RegionL2;
import com.bbolab.gaonna.core.repository.RegionL1Repository;
import com.bbolab.gaonna.core.repository.RegionL2Repository;
import lombok.RequiredArgsConstructor;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.geojson.feature.FeatureJSON;
import org.geotools.geojson.geom.GeometryJSON;
import org.locationtech.proj4j.BasicCoordinateTransform;
import org.locationtech.proj4j.CRSFactory;
import org.locationtech.proj4j.CoordinateReferenceSystem;
import org.locationtech.proj4j.ProjCoordinate;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GisApiEndpointService {

    private final RegionL1Repository regionL1Repository;
    private final RegionL2Repository regionL2Repository;

    // TODO : Credentials should be injected from profiles
    public static String apiKey = "8ab89a5abcb74258a7bfb02c457f19ff";
    public static String sdDataSet = "20171206DS00002";
    public static String sggDataSet = "20171206DS00003";
    public static String emdDataSet = "20171206DS00001";
    public static String baseUrl = "http://www.nsdi.go.kr/lxportal/zcms/nsdi/platform/openapi.html?apitype=data&resulttype=geojson&datasets=%s&bbox=%s,%s,%s,%s&authkey=%s";
    public static double[] boundBox = {132.1813350833317, 38.80596235082578, 123.75364991959037, 31.97440946314662};


    // TODO : 시도
    @Transactional
    public String requestSido() {
        String geoJson = requestGeoJson(sdDataSet, boundBox);

        try {
            FeatureCollection<SimpleFeatureType, SimpleFeature> featureCollection = jsonToFeatureCollection(geoJson);
            FeatureIterator<SimpleFeature> features = featureCollection.features();
            while (features.hasNext()) {
                // TODO : create RegionL1 instance with feature
                SimpleFeature feature = features.next();

                String sidoCd = (String) feature.getAttribute("SIDO_CD");
                String sidoNm = (String) feature.getAttribute("SIDO_NM");
                String baseyear = (String) feature.getAttribute("BASE_YEAR");


                RegionL1 region = regionL1Repository.findByAdmCode(sidoCd).orElseGet(RegionL1::new);
                region.setAdmCode(sidoCd);
                region.setName(sidoNm);
                region.setVersion(baseyear);


                regionL1Repository.save(region);
            }
        } catch (IOException e) {
        }

        return "";
    }

    @Transactional
    public String requestSigungu() {
        try {
            String testJson = StreamUtils.copyToString(getClass().getClassLoader().getResourceAsStream("testL2.json"), Charset.defaultCharset());
            FeatureCollection<SimpleFeatureType, SimpleFeature> featureCollection = jsonToFeatureCollection(testJson);
            try (FeatureIterator<SimpleFeature> features = featureCollection.features()) {
                while (features.hasNext()) {
                    SimpleFeature feature = features.next();
                    RegionL2 regionL2 = RegionL2.builder()
                            .admCode((String) feature.getAttribute("SIGUNGU_CD"))
                            .name((String) feature.getAttribute("SGG_NM"))
                            .build();
                    regionL2Repository.save(regionL2);
                    String sidoCd = regionL2.getAdmCode().substring(0, 2);

                    Optional<RegionL1> regionL1 = regionL1Repository.findByAdmCode(sidoCd);
                    regionL1.ifPresentOrElse(
                         l1 -> {
                             System.out.println("regionL2 = " + regionL2.getName());
                             System.out.println("l1.getName() = " + l1.getName());
                         },
                        () -> {
                            System.out.println("regionL2 = " + regionL2.getName());
                            System.out.println("No L1 region");
                        }
                    );

                }
            }
        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return "";
    }




    // TODO : 시군구

    // TODO : 읍면동





}
