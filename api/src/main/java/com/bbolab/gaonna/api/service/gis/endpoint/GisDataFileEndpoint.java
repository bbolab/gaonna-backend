package com.bbolab.gaonna.api.service.gis.endpoint;

import org.springframework.stereotype.Service;

@Service
public interface GisDataFileEndpoint {
    public String getSdFilepath();

    public String getSggFilepath();

    public String getEmdFilepath();
}
