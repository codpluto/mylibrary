package com.example.library.controller;


import com.example.library.domain.Good;
import com.example.library.mapper.GoodsMapper;
import com.example.library.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("goods")
@RestController
public class GoodsController {
    @Autowired
    private GoodsMapper goodsMapper;

    //精确查询商品信息，根据good_id
    @RequestMapping("searchGood")
    public JsonResult searchBook(int good_id){
        JsonResult jr = new JsonResult();
        Good good = goodsMapper.selectGood(good_id);
        if(good==null){
            jr.setStatus(0);        //0,该商品不存在
            return jr;
        }
        jr.setObj(good);
        jr.setStatus(1);
        return jr;
    }

    //模糊查询商品信息
    @RequestMapping("searchGoodsLike")
    public JsonResult searchGoodsLike(int user_id,String bookName){
        JsonResult jr = new JsonResult();
        List<Good> goods = goodsMapper.selectGoodsLike(user_id,bookName);
        if(goods.size()==0){
            jr.setStatus(0);        //0,没有搜寻结果
            return jr;
        }
        jr.setObj(goods);
        return jr;
    }

    //插入商品记录
    @RequestMapping("addGood")
    public JsonResult addGood(@RequestBody Good newgood){
        JsonResult jr = new JsonResult();
        int result = goodsMapper.saveGoods(newgood.getISBN(),newgood.getOrigPrice(),newgood.getPracticalPrice(),newgood.getAuthor(),
                newgood.getPublishDate(),newgood.getPress(),newgood.getIntroduction(),newgood.getCreateDate(),newgood.getUser_id(),newgood.isSoldOut());
        jr.setStatus(result);
        return jr;
    }

    //修改商品价格
    @RequestMapping("changeGood")
    public JsonResult changeGood(int good_id,double newPrice){
        JsonResult jr = new JsonResult();
        Good good = goodsMapper.selectGood(good_id);
        if(good==null){
            jr.setStatus(-1);       //-1,该商品不存在
            return jr;
        }
        int result = goodsMapper.updateGoods(newPrice,good_id);
        jr.setStatus(result);
        return jr;
    }

    //查询用户发布的所有商品
    @RequestMapping("getPublishGoods")
    public JsonResult getPublishGoods(int user_id){
        JsonResult jr = new JsonResult();
        List<Good> goods = goodsMapper.selectGoodsByUser(user_id);
        if(goods.size()==0){
            jr.setStatus(0);        //0,没发布商品
            return  jr;
        }
        jr.setStatus(1);
        jr.setObj(goods);
        return jr;
    }


    //查询用户订购的所有商品
    public JsonResult getBuyedGoods(int user_id){
        JsonResult jr = new JsonResult();
        List<Good> goods = goodsMapper.selectGoodsBuyed(user_id);
        if(goods.size()==0){
            jr.setObj(0);   //-没有订购的商品
            return jr;
        }
        jr.setStatus(1);
        jr.setObj(goods);
        return jr;
    }

    //

}
