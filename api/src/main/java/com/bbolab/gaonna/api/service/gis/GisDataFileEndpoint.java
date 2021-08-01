package com.bbolab.gaonna.api.service.gis;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class GisDataFileEndpoint implements GisDataEndpoint {

    @Override
    public Set<SimpleFeature> getSdFeatures() {
        String fileName = "src/main/resources/Z_SOP_BND_SIDO_PG/Z_SOP_BND_SIDO_PG.shp";
        return getFeaturesFromFile(fileName);
    }

    @Override
    public Set<SimpleFeature> getSggFeatures() {
        String fileName = "src/main/resources/Z_SOP_BND_SIGUNGU_PG 2/Z_SOP_BND_SIGUNGU_PG.shp";
        return getFeaturesFromFile(fileName);
    }

    @Override
    public Set<SimpleFeature> getEmdFeatures() {
        String fileName = "src/main/resources/Z_SOP_BND_ADM_DONG_PG 2/Z_SOP_BND_ADM_DONG_PG.shp";
        return getFeaturesFromFile(fileName);
    }

    public Set<SimpleFeature> getFeaturesFromFile(String fileName) {
        Set<SimpleFeature> result = new HashSet<>();
        FeatureIterator<SimpleFeature> features = null;
        DataStore dataStore = null;
        try {
            File file = new File(fileName);
            Map<String, Object> map = new HashMap<>();
            map.put("url", file.toURI().toURL());
            map.put("charset", "euc-kr");

            dataStore = DataStoreFinder.getDataStore(map);
            String typeName = dataStore.getTypeNames()[0];

            SimpleFeatureSource source = dataStore.getFeatureSource(typeName);

            FeatureCollection<SimpleFeatureType, SimpleFeature> collection = source.getFeatures(Filter.INCLUDE);
            features = collection.features();
            while (features.hasNext()) {
                SimpleFeature feature = features.next();
                result.add(feature);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(features != null) features.close();
            if(dataStore != null) dataStore.dispose();
        }
        return result;
    }
}
