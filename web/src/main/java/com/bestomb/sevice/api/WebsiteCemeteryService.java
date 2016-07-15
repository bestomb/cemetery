package com.bestomb.sevice.api;

import com.bestomb.common.exception.EqianyuanException;
import com.bestomb.common.request.cemetery.CemeteryByEditRequest;
import com.bestomb.service.ICemeteryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 网站陵园业务调用类
 * Created by jason on 2016-07-15.
 */
@Service
public class WebsiteCemeteryService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private ICemeteryService cemeteryService;

    /**
     * 创建陵园
     *
     * @param cemeteryByEditRequest
     */
    public void create(CemeteryByEditRequest cemeteryByEditRequest) throws EqianyuanException {
        cemeteryService.add(cemeteryByEditRequest);
    }
}
