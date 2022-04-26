package com.bbolab.gaonna.api.service.gis.endpoint;

import org.springframework.stereotype.Service;

@Service
public class GisStaticDataFileEndpoint implements GisDataFileEndpoint{
    @Override
    public String getSdFilepath() {
        return "src/main/resources/Z_SOP_BND_SIDO_PG/Z_SOP_BND_SIDO_PG.shp";
    }

    @Override
    public String getSggFilepath() {
        return "src/main/resources/Z_SOP_BND_SIGUNGU_PG 2/Z_SOP_BND_SIGUNGU_PG.shp";
    }

    @Override
    public String getEmdFilepath() {
        return "src/main/resources/Z_SOP_BND_ADM_DONG_PG 2/Z_SOP_BND_ADM_DONG_PG.shp";
    }
}
