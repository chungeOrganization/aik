package com.aik.service.store;

import com.aik.assist.ErrorCodeEnum;
import com.aik.dao.StoGoodsMapper;
import com.aik.dao.StoShoppingCartMapper;
import com.aik.dao.StoUserCollectionGoodsMapper;
import com.aik.dto.response.user.GoodsDetailRespDTO;
import com.aik.dto.response.user.MerchantInfoRespDTO;
import com.aik.enums.GoodsIsRecommendEnum;
import com.aik.enums.GoodsStatusEnum;
import com.aik.exception.ApiServiceException;
import com.aik.model.StoGoods;
import com.aik.model.StoUserCollectionGoods;
import com.aik.resource.SystemResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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

    private StoUserCollectionGoodsMapper stoUserCollectionGoodsMapper;

    private StoShoppingCartMapper stoShoppingCartMapper;

    @Resource
    private SystemResource systemResource;

    @Autowired
    public void setStoGoodsMapper(StoGoodsMapper stoGoodsMapper) {
        this.stoGoodsMapper = stoGoodsMapper;
    }

    @Autowired
    public void setStoUserCollectionGoodsMapper(StoUserCollectionGoodsMapper stoUserCollectionGoodsMapper) {
        this.stoUserCollectionGoodsMapper = stoUserCollectionGoodsMapper;
    }

    @Autowired
    public void setStoShoppingCartMapper(StoShoppingCartMapper stoShoppingCartMapper) {
        this.stoShoppingCartMapper = stoShoppingCartMapper;
    }

    @Override
    public List<Map<String, Object>> getRecommendGoods(GoodsIsRecommendEnum recommendEnum) throws ApiServiceException {
        StoGoods searchSG = new StoGoods();
        searchSG.setStatus(GoodsStatusEnum.NOT_SOLD_OUT.getStatus());
        searchSG.setIsRecommend(recommendEnum.getCode());
        List<Map<String, Object>> recommendGoods = stoGoodsMapper.selectRecommendGoods(searchSG);
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

    @Override
    public GoodsDetailRespDTO getGoodsDetail(Integer goodsId, Integer userId) throws ApiServiceException {
        if (null == goodsId || null == userId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        StoGoods goods = stoGoodsMapper.selectByPrimaryKey(goodsId);
        if (null == goods) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1005004);
        }

        GoodsDetailRespDTO goodsDetail = new GoodsDetailRespDTO();
        goodsDetail.setGoodsId(goods.getId());
        goodsDetail.setGoodsName(goods.getName());
        goodsDetail.setGoodsImg(systemResource.getApiFileUri() + goods.getImage());
        goodsDetail.setGoodsPrice(goods.getPrice());
        goodsDetail.setGoodsStock(goods.getStock());

        boolean isCollection = null != stoUserCollectionGoodsMapper.selectByUserIdAndGoodsID(userId, goodsId);
        goodsDetail.setIsCollection(isCollection);

        int cartGoodsCount = stoShoppingCartMapper.selectUserShoppingCartCount(userId);
        goodsDetail.setCartGoodsCount(cartGoodsCount);

        goodsDetail.setMerchantInfo(getMerchantInfo(goodsId));

        return goodsDetail;
    }

    @Override
    public MerchantInfoRespDTO getMerchantInfo(Integer goodsId) throws ApiServiceException {
        // TODO:获取商户信息
        MerchantInfoRespDTO merchantInfo = new MerchantInfoRespDTO();
        merchantInfo.setMerchantName("华纳健康");
        merchantInfo.setGoodsNum(12);
        merchantInfo.setMerchantImg(systemResource.getApiFileUri() + "goods/merchant-hn.png");

        return merchantInfo;
    }

    @Override
    public void collectionGoods(Integer goodsId, Integer userId) throws ApiServiceException {
        if (null == goodsId || null == userId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        StoUserCollectionGoods userCollectionGoods = stoUserCollectionGoodsMapper.selectByUserIdAndGoodsID(userId, goodsId);
        if (null != userCollectionGoods) {
            return;
        }

        userCollectionGoods = new StoUserCollectionGoods();
        userCollectionGoods.setUserId(userId);
        userCollectionGoods.setGoodsId(goodsId);
        userCollectionGoods.setCreateDate(new Date());

        stoUserCollectionGoodsMapper.insertSelective(userCollectionGoods);
    }

    @Override
    public void cancelCollectionGoods(Integer goodsId, Integer userId) throws ApiServiceException {
        if (null == goodsId || null == userId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        StoUserCollectionGoods userCollectionGoods = stoUserCollectionGoodsMapper.selectByUserIdAndGoodsID(userId, goodsId);
        if (null == userCollectionGoods) {
            return;
        }

        stoUserCollectionGoodsMapper.deleteByPrimaryKey(userCollectionGoods.getId());
    }
}
