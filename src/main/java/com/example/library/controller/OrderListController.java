package com.example.library.controller;

import com.example.library.domain.OrderList;
import com.example.library.mapper.GoodsMapper;
import com.example.library.mapper.OrderListMapper;
import com.example.library.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("orderList")
@RestController
public class OrderListController {

    @Autowired
    private OrderListMapper orderListMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    //增加订单
    @RequestMapping("addOrderList")
    public JsonResult addOrderList(@RequestBody OrderList orderList){
        JsonResult jr = new JsonResult();
        //调用数据库insert语句
        int result = orderListMapper.saveOrderList(orderList.getOrderID(),orderList.getUser_id(),orderList.getCreateDate(),
                orderList.getConsignee(),orderList.getPhone(),orderList.getOrigPrice(),orderList.getPracticalPrice(),
                orderList.getNotes(),orderList.getAddress(),orderList.getGood_id(),orderList.getPublisher_id());
        //result=0，插入操作失败，设置状态码0
        if(result==0){
            jr.setStatus(0);        //0,增加订单失败
            return jr;
        }
        jr.setStatus(1);
        //商品卖出后修改商品状态，并下架商品
        goodsMapper.updateSoldOut(orderList.getGood_id(),true);
        return jr;
    }

    //修改订单交易状态
    @RequestMapping("changeState")
    public JsonResult changeState(int state,String orderID){
        JsonResult jr = new JsonResult();
        //修改订单状态，0已付款，1已发货，2交易完成
        int result = orderListMapper.dealOrderList(orderID,state);
        jr.setStatus(result);
        return jr;
    }

    //取消订单
    @RequestMapping("cancelList")
    public JsonResult cancelList(String orderID){
        JsonResult jr = new JsonResult();
        OrderList orderList = orderListMapper.selectOrderById(orderID);

        int result = orderListMapper.deleteOrderList(orderID);
        jr.setStatus(result);
        if(result==1){
            goodsMapper.updateSoldOut(orderList.getGood_id(),false);
        }
        return jr;
    }

    //查询用户所有订单
    @RequestMapping("searchUserList")
    public JsonResult searchUserList(int user_id){
        JsonResult jr = new JsonResult();
        List<OrderList> orderLists = orderListMapper.selectListByUser(user_id);
        if(orderLists.size()==0){
            jr.setStatus(0);
            return jr;
        }
        jr.setObj(orderLists);
        jr.setStatus(1);
        return jr;
    }

    //增加（修改）快递单号
    @RequestMapping("setExpress")
    public JsonResult setExpress(String expressNumber,String orderID){
        JsonResult jr = new JsonResult();
        //调用数据库Update语句
        int result = orderListMapper.updateExpress(expressNumber,orderID);
        jr.setStatus(result);
        return jr;
    }

    //根据good_id查询订单
    @RequestMapping("searchOrderByGood")
    public JsonResult searchOrderByGood(int good_id){
        JsonResult jr = new JsonResult();
        OrderList orderList = orderListMapper.selectOrderByGood(good_id);
        if(orderList==null){
            jr.setStatus(0);
            return jr;
        }
        jr.setStatus(1);
        jr.setObj(orderList);
        return jr;
    }

    //根据订单号查询订单信息
    @RequestMapping("searchOrderById")
    public JsonResult searchOrderById(String orderID){
        JsonResult jr = new JsonResult();
        OrderList orderList = orderListMapper.selectOrderById(orderID);
        if(orderList==null){
            jr.setStatus(0);        //0，该订单不存在
            return jr;
        }
        jr.setStatus(1);
        jr.setObj(orderList);
        return jr;
    }

    //查询publisher的订单
    @RequestMapping("searchPublishOrder")
    public JsonResult searchPublishOrder(int publisher_id){
        JsonResult jr = new JsonResult();
        List<OrderList> orderLists = orderListMapper.selectOrderByPublisher(publisher_id);
        if(orderLists.size()==0){
            jr.setStatus(0);        //0，没有订单
            return jr;
        }
        jr.setStatus(1);
        jr.setObj(orderLists);
        return jr;
    }
}
