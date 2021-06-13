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
    public JsonResult searchGood(int good_id){
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
    public JsonResult searchGoodsLike(String bookName){
        JsonResult jr = new JsonResult();
        List<Good> goods = goodsMapper.selectGoodsLike(bookName);
        if(goods.size()==0){
            jr.setStatus(0);        //0,没有搜寻结果
            return jr;
        }
        jr.setObj(goods);
        jr.setStatus(1);
        return jr;
    }

    //插入商品记录
    @RequestMapping("addGood")
    public JsonResult addGood(@RequestBody Good newgood){
        JsonResult jr = new JsonResult();

        log.info("conditions:{}",newgood.getConditions());

        int result = goodsMapper.saveGoods(newgood.getISBN(),newgood.getOrigPrice(),newgood.getPracticalPrice(),newgood.getAuthor(),
                newgood.getPublishDate(),newgood.getPress(),newgood.getIntroduction(),newgood.getCreateDate(),newgood.getUser_id(),
                newgood.getConditions(),newgood.getBookName(),newgood.getCoverUrl(),newgood.getExpressPrice());
        jr.setStatus(result);
        return jr;
    }

    //修改商品价格
    @RequestMapping("changePrice")
    public JsonResult changePrice(int good_id,double newPrice,double expressPrice){
        JsonResult jr = new JsonResult();
        Good good = goodsMapper.selectGood(good_id);
        if(good==null){
            jr.setStatus(-1);       //-1,该商品不存在
            return jr;
        }
        if(good.isSoldOut()==true){
            jr.setStatus(-2);
            return jr;
        }
        int result = goodsMapper.updateGoods(newPrice,good_id,expressPrice);
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

    //每次随机加载20件商品
    @RequestMapping("loadGoods")
    public JsonResult loadGoods(){
        JsonResult jr = new JsonResult();
        List<Good> goods = goodsMapper.selectPartGoods();
        if(goods.size()==0){
            jr.setStatus(0);        //0,商店为空
            return jr;
        }
        jr.setStatus(1);
        jr.setObj(goods);
        return jr;
    }


    //删除商品(仅限没卖出的)
    @RequestMapping("deleteGoods")
    public JsonResult deleteGoods(int good_id){
        JsonResult jr = new JsonResult();
        Good good = goodsMapper.selectGood(good_id);
        if(good!=null&&good.isSoldOut()==true){
            jr.setStatus(-1);       //-1,商品已卖出，禁止删除
            return jr;
        }
        int result = goodsMapper.deleteGood(good_id);
        jr.setStatus(result);
        return jr;
    }

    //修改商品品相
    @RequestMapping("changeConditions")
    public JsonResult changeConditions(int good_id,int conditions){
        JsonResult jr = new JsonResult();
        int result = goodsMapper.updateConditions(good_id,conditions);
        jr.setStatus(result);
        return jr;
    }


//    //查询用户订购的所有商品
//    public JsonResult getBuyedGoods(int user_id){
//        JsonResult jr = new JsonResult();
//        List<Good> goods = goodsMapper.selectGoodsBuyed(user_id);
//        if(goods.size()==0){
//            jr.setObj(0);   //-没有订购的商品
//            return jr;
//        }
//        jr.setStatus(1);
//        jr.setObj(goods);
//        return jr;
//    }


}
