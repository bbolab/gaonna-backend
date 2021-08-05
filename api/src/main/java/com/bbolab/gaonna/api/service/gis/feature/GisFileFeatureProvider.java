package com.bbolab.gaonna.api.service.gis.feature;

import com.bbolab.gaonna.api.service.gis.endpoint.GisDataFileEndpoint;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class GisFileFeatureProvider implements GisFeatureProvider {

    private final GisDataFileEndpoint gisDataFileEndpoint;

    @Override
    public Set<SimpleFeature> getSdFeatures() {
        String filepath = gisDataFileEndpoint.getSdFilepath();
        return getFeaturesFromFile(filepath);
    }

    @Override
    public Set<SimpleFeature> getSggFeatures() {
        String filepath = gisDataFileEndpoint.getSggFilepath();
        return getFeaturesFromFile(filepath);
    }

    @Override
    public Set<SimpleFeature> getEmdFeatures() {
        String filepath = gisDataFileEndpoint.getEmdFilepath();
        return getFeaturesFromFile(filepath);
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
        } finally {
            if (features != null) features.close();
            if (dataStore != null) dataStore.dispose();
        }
        return result;
    }
}
