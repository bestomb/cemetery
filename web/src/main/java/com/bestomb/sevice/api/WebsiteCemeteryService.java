package com.bestomb.sevice.api;

import com.bestomb.common.request.cemetery.CemeteryByEditRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * 网站陵园业务调用类
 * Created by jason on 2016-07-15.
 */
@Service
public class WebsiteCemeteryService {

    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * 创建陵园
     *
     * @param cemeteryByEditRequest
     */
    public void create(CemeteryByEditRequest cemeteryByEditRequest) {

    }
}
