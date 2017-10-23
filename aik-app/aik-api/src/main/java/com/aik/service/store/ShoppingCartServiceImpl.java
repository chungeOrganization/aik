package com.aik.service.store;

import com.aik.assist.ErrorCodeEnum;
import com.aik.dao.StoShoppingCartMapper;
import com.aik.dto.UpdateShoppingCartDTO;
import com.aik.dto.UpdateShoppingCartDTO.*;
import com.aik.dto.request.user.ShoppingCartAddGoodsReqDTO;
import com.aik.exception.ApiServiceException;
import com.aik.model.StoShoppingCart;
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
 * Created by as on 2017/9/3.
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);

    private StoShoppingCartMapper stoShoppingCartMapper;

    @Resource
    private SystemResource systemResource;

    @Autowired
    public void setStoShoppingCartMapper(StoShoppingCartMapper stoShoppingCartMapper) {
        this.stoShoppingCartMapper = stoShoppingCartMapper;
    }

    @Override
    public List<Map<String, Object>> getUserShoppingCart(Integer userId) throws ApiServiceException {
        if (null == userId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        List<Map<String, Object>> shoppingCarts = stoShoppingCartMapper.selectUserShoppingCart(userId);
        for (Map<String, Object> shoppingCart : shoppingCarts) {
            String goodsImg = shoppingCart.get("goodsImg").toString();
            shoppingCart.put("goodsImg", systemResource.getApiFileUri() + goodsImg);
        }

        return shoppingCarts;
    }

    @Override
    public void updateShoppingCart(UpdateShoppingCartDTO updateShoppingCartDTO) throws ApiServiceException {
        if (null == updateShoppingCartDTO) {
            return;
        }

        List<Integer> deleteScIds = updateShoppingCartDTO.getDeleteScIds();
        if (null != deleteScIds && deleteScIds.size() > 0) {
            stoShoppingCartMapper.deleteByPrimaryKeyBatch(deleteScIds);
        }

        List<UpdateShoppingCart> updateShoppingCarts = updateShoppingCartDTO.getUpdateScList();
        if (null != updateShoppingCarts && updateShoppingCarts.size() > 0) {
            for (UpdateShoppingCart updateShoppingCart : updateShoppingCarts) {
                StoShoppingCart shoppingCart = new StoShoppingCart();
                shoppingCart.setId(updateShoppingCart.getScId());
                shoppingCart.setNumber(updateShoppingCart.getNumber());
                shoppingCart.setUpdateDate(new Date());

                stoShoppingCartMapper.updateByPrimaryKeySelective(shoppingCart);
            }
        }
    }

    @Override
    public void shoppingCartAddGoods(ShoppingCartAddGoodsReqDTO reqDTO) throws ApiServiceException {
        if (null == reqDTO) {
            return;
        }

        StoShoppingCart shoppingCart = new StoShoppingCart();
        shoppingCart.setUserId(reqDTO.getUserId());
        shoppingCart.setGoodsId(reqDTO.getGoodsId());
        shoppingCart.setNumber(reqDTO.getGoodsNumber());
        shoppingCart.setCreateDate(new Date());

        stoShoppingCartMapper.insertSelective(shoppingCart);
    }
}
