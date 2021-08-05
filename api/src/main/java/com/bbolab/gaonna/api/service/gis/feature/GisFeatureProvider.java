package com.bbolab.gaonna.api.service.gis.feature;

import org.opengis.feature.simple.SimpleFeature;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface GisFeatureProvider {
    public Set<SimpleFeature> getSdFeatures();
    public Set<SimpleFeature> getSggFeatures();
    public Set<SimpleFeature> getEmdFeatures();
}
