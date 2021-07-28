package com.bbolab.gaonna.api.service.geo;

import org.opengis.feature.simple.SimpleFeature;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface GisDataEndpoint {
    public Set<SimpleFeature> getSdFeatures();
    public Set<SimpleFeature> getSggFeatures();
    public Set<SimpleFeature> getEmdFeatures();
}
