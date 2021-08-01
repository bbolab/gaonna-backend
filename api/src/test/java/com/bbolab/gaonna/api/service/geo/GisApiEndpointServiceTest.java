package com.bbolab.gaonna.api.service.geo;

import com.bbolab.gaonna.api.AbstractContainerBaseTest;
import com.bbolab.gaonna.core.domain.region.RegionL1;
import com.bbolab.gaonna.core.repository.RegionL1Repository;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.proj4j.BasicCoordinateTransform;
import org.locationtech.proj4j.CRSFactory;
import org.locationtech.proj4j.CoordinateReferenceSystem;
import org.locationtech.proj4j.ProjCoordinate;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class GisApiEndpointServiceTest extends AbstractContainerBaseTest {

    @Autowired
    public GisDataEndpoint gisDataApiEndpoint;

    @Autowired
    public RegionL1Repository regionL1Repository;

    @Test
    public void feature() throws Exception{
        String testJson = StreamUtils.copyToString(getClass().getClassLoader().getResourceAsStream("testL1.json"), Charset.defaultCharset());

        FeatureCollection<SimpleFeatureType, SimpleFeature> featureCollections = gisDataApiEndpoint.jsonToFeatureCollection(testJson);
        try (FeatureIterator<SimpleFeature> features = featureCollections.features()) {
            while (features.hasNext()) {
                System.out.println("==================================================================");
                SimpleFeature feature = features.next();
                SimpleFeatureType featureType = feature.getFeatureType();
                String gid = (String) feature.getAttribute("GID");
                String base_year = (String) feature.getAttribute("BASE_YEAR");
                String sido_cd = (String) feature.getAttribute("SIDO_CD");
                String sido_nm = (String) feature.getAttribute("SIDO_NM");

                System.out.println("gid         = " + gid);
                System.out.println("base_year   = " + base_year);
                System.out.println("sido_cd     = " + sido_cd);
                System.out.println("sido_nm     = " + sido_nm);

                Class<?> geotype = featureType.getGeometryDescriptor().getType().getBinding();
                if(geotype.equals(MultiPolygon.class)) {
                    MultiPolygon geometry = (MultiPolygon) feature.getDefaultGeometry();
                    int numOfGeometries = geometry.getNumGeometries();
                    System.out.println("numOfGeometries     = " + numOfGeometries);

                }else if(geotype.equals(Polygon.class)) {
                }else{
                }

            }
        }
    }

    @DisplayName("[GISAPI] 시도 행정코드 request")
    @Test
    public void feature_sido() {
        // given
        Set<SimpleFeature> sdFeatures = gisDataApiEndpoint.getSdFeatures();

        // when
        List<RegionL1> regionL1s = regionL1Repository.findAll();

        // then
        assertEquals(regionL1s.size(), 17);

        regionL1s.forEach(r -> {
            assertNotNull(r.getAdmCode());
            assertNotNull(r.getName());
        });
    }

    @Test
    public void feature_sigungu() {
        gisApiEndpointService.requestSigungu();
    }

    @Test
    public void test() {
//        String[] files = {
//                "src/main/resources/geodb/CTPRVN_20210/TL_SCCO_CTPRVN.shp",
//                "src/main/resources/geodb/SIG_202101/TL_SCCO_SIG.shp",
//                "src/main/resources/geodb/EMD_202101/TL_SCCO_EMD.shp",
//                "src/main/resources/geodb/LI_202101/TL_SCCO_LI.shp"
//        };
        String[] files = {
                "src/test/resources/Z_SOP_BND_SIDO_PG/Z_SOP_BND_SIDO_PG.shp",
//                "src/main/resources/geo/SIG_202101/TL_SCCO_SIG.shp",
//                "src/main/resources/geo/EMD_202101/TL_SCCO_EMD.shp",
//                "src/main/resources/geodb/LI_202101/TL_SCCO_LI.shp"
        };

        try{
            for(String fname : files) {
                System.out.println("==================================================================================");
                File file = new File(fname);
                Map<String, Object> map = new HashMap<>();
                map.put("url", file.toURI().toURL());
                map.put("charset", "euc-kr");

                DataStore dataStore = DataStoreFinder.getDataStore(map);
                String typeName = dataStore.getTypeNames()[0];

                SimpleFeatureSource source = dataStore.getFeatureSource(typeName);

                FeatureCollection<SimpleFeatureType, SimpleFeature> collection = source.getFeatures(Filter.INCLUDE);
                try (FeatureIterator<SimpleFeature> features = collection.features()) {
                    while (features.hasNext()) {
                        SimpleFeature feature = features.next();
//                        System.out.print(feature.getID());
//                        System.out.print(" : ");
                        System.out.print(feature.getAttributes().get(1) + ", ");
                        System.out.println(feature.getAttributes().get(3));
//                    System.out.println(feature.getDefaultGeometryProperty().getValue());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        double[] data = {933650.614474257,1943286.5512082838, 970380.2826180774,1961733.2057452637};

        double[] coordnates = transCoordinate(data, "EPSG:5179", "EPSG:4326");
        for (double d : coordnates) {
            System.out.println("d = " + d);
        }
    }

    @Test
    public void test3() {
        double[] data = {132.186724, 38.809159, 124.98119, 32.50174};

        double[] coordinate = transCoordinate(data, "EPSG:4326", "EPSG:5179");
        System.out.print("{");
        for (double d : coordinate) {
            System.out.print(d + ", ");
        }
        System.out.println("}");

    }
    @Test
    public void test4() {
        double[] data = {1407017.6462277977, 2100230.0839186413, 763357.1799875839, 1393033.0219491555};


        double[] coordnates = transCoordinate(data, "EPSG:5179", "EPSG:4326");
        for (double d : coordnates) {
            System.out.println("d = " + d);
        }
    }
    public double[] transCoordinate(double[] data, String srcCrs, String dstCrs) {
        CRSFactory factory = new CRSFactory();
        CoordinateReferenceSystem src = factory.createFromName(srcCrs);
        CoordinateReferenceSystem dst = factory.createFromName(dstCrs);

        BasicCoordinateTransform transform = new BasicCoordinateTransform(src, dst);

        ProjCoordinate srcCoord = new ProjCoordinate(data[0], data[1]);
        ProjCoordinate dstCoord = new ProjCoordinate();

        transform.transform(srcCoord, dstCoord);

        ProjCoordinate srcCoord2 = new ProjCoordinate(data[2], data[3]);
        ProjCoordinate dstCoord2 = new ProjCoordinate();

        transform.transform(srcCoord2, dstCoord2);

        return new double[] {dstCoord.x, dstCoord.y, dstCoord2.x, dstCoord2.y};
    }
}