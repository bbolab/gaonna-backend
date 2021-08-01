package com.bbolab.gaonna.api.service.geo;

import com.bbolab.gaonna.api.exception.GeoDataFileHandlingException;
import com.google.gson.stream.JsonReader;
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

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

//@Service
@RequiredArgsConstructor
public class GisDataApiEndpoint implements GisDataEndpoint{

    public static String apiKey = "8ab89a5abcb74258a7bfb02c457f19ff";
    public static String sdDataSet = "20171206DS00002";
    public static String sggDataSet = "20171206DS00003";
    public static String emdDataSet = "20171206DS00001";

    public static String baseUrl = "http://www.nsdi.go.kr/lxportal/zcms/nsdi/platform/openapi.html?apitype=data&resulttype=geojson&datasets=%s&bbox=%s,%s,%s,%s&authkey=%s";
    public static double[] korBoundBox = {132.1813350833317, 38.80596235082578, 123.75364991959037, 31.97440946314662};

    @Override
    public Set<SimpleFeature> getSdFeatures() {
        return requestFeatures(sdDataSet, korBoundBox);
    }

    @Override
    public Set<SimpleFeature> getSggFeatures() {
        return requestFeatures(sggDataSet, korBoundBox);
    }

    @Override
    public Set<SimpleFeature> getEmdFeatures() {
        Set<SimpleFeature> emdFeatures = new HashSet<>();

        int denominator = 1000;

        double width = (korBoundBox[0] - korBoundBox[2]) / denominator;
        double height = (korBoundBox[1] - korBoundBox[3]) / denominator;

        for(int i = 0 ; i < denominator; i++) {
            for(int j = 0 ; j < denominator; j++) {
                double[] bbox = {korBoundBox[0] - width * j, korBoundBox[1] - height * i,
                                 korBoundBox[2] - width * j, korBoundBox[3] - height * i};
                Set<SimpleFeature> features = requestFeatures(emdDataSet, bbox);
                emdFeatures.addAll(features);
            }
        }
        return emdFeatures;
    }

    public FeatureCollection<SimpleFeatureType, SimpleFeature> jsonToFeatureCollection(String jsonContent) throws IOException {
        GeometryJSON geometryJson = new GeometryJSON(15);
        FeatureJSON featureJson = new FeatureJSON(geometryJson);

        FeatureCollection featureCollection;

        try (Reader stringReader = new StringReader(jsonContent)) {
            featureCollection = featureJson.readFeatureCollection(stringReader);
        }
        return featureCollection;
    }

    private Set<SimpleFeature> requestFeatures(String dataSet, double[] bbox) {
        Set<SimpleFeature> sggFeatures = new HashSet<>();
        String geoJson = requestGeoJson(dataSet, bbox);
        try {
            FeatureCollection<SimpleFeatureType, SimpleFeature> featureCollection = jsonToFeatureCollection(geoJson);
            FeatureIterator<SimpleFeature> features = featureCollection.features();
            while (features.hasNext()) {
                sggFeatures.add(features.next());
            }
        } catch (IOException e){
            throw new GeoDataFileHandlingException("");
        }
        return sggFeatures;
    }

    public String requestGeoJson(String dataSet, double[] bound) {
        double[] bbox = transCoordinate(bound, "EPSG:4326", "EPSG:5179");
        String urlString = String.format(baseUrl,
                dataSet,
                bbox[0],
                bbox[1],
                bbox[2],
                bbox[3],
                apiKey);

        String response = null;
        try {
            StringBuilder sb = new StringBuilder();
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            JsonReader reader = new JsonReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));

            System.out.println();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;


//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> response = restTemplate.getForEntity(urlString, String.class);
//        if(response.getStatusCode() != HttpStatus.OK) {
//            throw new HttpRequestFailureException();
//        }
//        return response.getBody();
    }

    public double[] transCoordinate(double[] data, String srcCrs, String dstCrs) {
        CRSFactory factory = new CRSFactory();
        CoordinateReferenceSystem src = factory.createFromName(srcCrs);
        CoordinateReferenceSystem dst = factory.createFromName(dstCrs);

        BasicCoordinateTransform transform = new BasicCoordinateTransform(src, dst);

        ProjCoordinate srcCoordinate = new ProjCoordinate(data[0], data[1]);
        ProjCoordinate dstCoordinate = new ProjCoordinate();

        transform.transform(srcCoordinate, dstCoordinate);

        ProjCoordinate srcCoordinate2 = new ProjCoordinate(data[2], data[3]);
        ProjCoordinate dstCoordinate2 = new ProjCoordinate();

        transform.transform(srcCoordinate2, dstCoordinate2);

        return new double[]{dstCoordinate.x, dstCoordinate.y, dstCoordinate2.x, dstCoordinate2.y};
    }
}
