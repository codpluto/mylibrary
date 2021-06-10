package com.example.library.mapper;

import com.example.library.domain.OrderList;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderListMapper {

    //增加订单
    @Insert("INSERT INTO OrderList(orderID,user_id,createDate,consignee,phone,origPrice,practicalPrice,notes,address,good_id,publisher_id) " +
            "VALUES(#{orderID},#{user_id},#{createDate},#{consignee},#{phone},#{origPrice},#{practicalPrice},#{notes},#{address},#{good_id},#{publisher_id})")
    int saveOrderList(@Param("orderID") String orderID, @Param("user_id") int user_id, @Param("createDate") String createDate,
                      @Param("consignee") String consignee, @Param("phone") String phone, @Param("origPrice") double origPrice,
                      @Param("practicalPrice") double practicalPrice, @Param("notes") String notes, @Param("address") String address, @Param("good_id") int good_id, @Param("publisher_id") int publisher_id);

    //处理订单
    @Update("UPDATE OrderList SET state=#{state} WHERE orderID=#{orderID}")
    int dealOrderList(@Param("orderID") String orderID, @Param("state") int state);

    //修改订单信息
    @Update("UPDATE OrderList SET address=#{address},consignee=#{consignee},phone=#{phone} WHERE orderID=#{orderID}")
    int updateOrderList(@Param("address") String address, @Param("consignee") String consignee, @Param("phone") String phone);

    //确定收货日期
    @Update("UPDATE OrderList SET deliverDate=#{deliverDate} WHERE orderID=#{orderID}")
    int updateDeliverDate(@Param("deliverDate") String deliverDate, @Param("orderID") String orderID);

    //查询用户买的订单
    @Select("SELECT * FROM OrderList WHERE user_id=#{user_id}")
    List<OrderList> selectListByUser(@Param("user_id") int user_id);

    //根据订单号查订单
    @Select("SELECT * FROM OrderList WHERE orderID=#{orderID}")
    OrderList selectOrderById(@Param("orderID") String orderID);

    //取消订单
    @Delete("DELETE FROM OrderList WHERE orderID=#{orderID}")
    int deleteOrderList(@Param("orderID") String orderID);

    //修改快递单号
    @Update("UPDATE OrderList SET expressNumber=#{expressNumber} WHERE orderID=#{orderID}")
    int updateExpress(@Param("expressNumber") String expressNumber, @Param("orderID") String orderID);

    //根据good_id查询订单
    @Select("SELECT * FROM OrderList WHERE good_id=#{good_id}")
    OrderList selectOrderByGood(@Param("good_id") int good_id);

}
