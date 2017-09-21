package com.aik.service.store;

import com.aik.dao.StoGoodsMapper;
import com.aik.enums.GoodsIsRecommendEnum;
import com.aik.enums.GoodsStatusEnum;
import com.aik.exception.ApiServiceException;
import com.aik.model.StoGoods;
import com.aik.resource.SystemResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/9/8.
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    private static final Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);

    private StoGoodsMapper stoGoodsMapper;

    @Resource
    private SystemResource systemResource;

    @Autowired
    public void setStoGoodsMapper(StoGoodsMapper stoGoodsMapper) {
        this.stoGoodsMapper = stoGoodsMapper;
    }

    @Override
    public List<Map<String, Object>> getRecommendGoods() throws ApiServiceException {
        StoGoods searchSG = new StoGoods();
        searchSG.setStatus(GoodsStatusEnum.NOT_SOLD_OUT.getStatus());
        searchSG.setIsRecommend(GoodsIsRecommendEnum.RECOMMEND.getCode());
        List<Map<String, Object>> recommendGoods = stoGoodsMapper.selectGoodsBySelective(searchSG);
        for (Map<String, Object> goods : recommendGoods) {
            goods.put("image", systemResource.getApiFileUri() + goods.get("image"));
        }

        return recommendGoods;
    }

    @Override
    public List<Map<String, Object>> getGoodsWithType(Map<String, Object> params) throws ApiServiceException {
        params.put("status", GoodsStatusEnum.NOT_SOLD_OUT.getStatus());

        List<Map<String, Object>> goodsList = stoGoodsMapper.selectByParams(params);
        for (Map<String, Object> goods : goodsList) {
            goods.put("image", systemResource.getApiFileUri() + goods.get("image"));
        }

        return goodsList;
    }
}
