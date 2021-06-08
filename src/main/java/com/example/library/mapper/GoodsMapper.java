package com.example.library.mapper;

import com.example.library.domain.Good;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface GoodsMapper {

    //添加商品信息
    @Insert("INSERT INTO GoodsInfo(ISBN,origPrice,practicalPrice author,publishDate,press,introduction,createDate,isSoldOut) " +
            "VALUES(#{isbn},#{origPrice},#{practicalPrice},#{author},#{publishDate},#{press},#{introduction},#{createDate},#{user_id},#{isSoldOut})")
    int saveGoods(@Param("isbn") String isbn, @Param("origPrice") double origPrice, @Param("practicalPrice") double practicalPrice, @Param("author") String author, @Param("publishDate") String publishDate, @Param("press") String press, @Param("introduction") String introduction,
                  @Param("createDate") String createDate, @Param("user_id") int user_id, @Param("isSoldOut") boolean isSoldOut);

    //修改商品信息
    @Update("UPDATE GoodsInfo SET practicalPrice=#{practicalPrice} WHERE good_id=#{good_id}")
    int updateGoods(@Param("practicalPrice") double practicalPrice, @Param("good_id") int good_id);

    //根据书名模糊查询商品
    @Select("SELECT * FROM GoodsInfo WHERE user_id=#{user_id} AND bookName LIKE CONCAT('%',#{bookName},'%')")
    List<Good> selectGoodsLike(@Param("user_id") int user_id, @Param("bookName") String bookName);

    //根据id精确查询
    @Select("SELECT * FROM GoodsInfo WHERE good_id=#{good_id}")
    Good selectGood(@Param("good_id") int good_id);

    //修改商品是否已卖出
    @Update("UPDATE GoodsInfo SET isSoldOut=#{isSoldOut} WHERE good_id=#{good_id}")
    int updateSoldOut(@Param("good_id") int good_id, @Param("isSoldOut") boolean isSoldOut);

    //查询用户发布的所有商品
    @Select("SELECT * FROM GoodsInfo WHERE user_id=#{user_id}")
    List<Good> selectGoodsByUser(@Param("user_id") int user_id);

    //查询用户购买的所有商品
    @Select("SELECT * FROM GoodsInfo WHERE good_id in (SELECT good_id FROM OrderList WHERE user_id=#{user_id})")
    List<Good> selectGoodsBuyed(@Param("user_id") int user_id);



//    //添加采购信息
//    @Insert("INSERT INTO PurchaseList VALUES(#{purchaseID},#{createDate},#{totalPrice},#{pay},#{number},#{user_id})")
//    int savePurchase(String purchaseID,String createDate,double totalPrice,double pay,int number,int user_id);
//
//    //添加采购内容
//    @Insert("INSERT INTO PurchaseContent VALUES(#{purchaseID},#{isbn},#{number})")
//    int savePurchaseContent(@Param("purchaseID") String purchaseID, @Param("isbn") String isbn, @Param("number") int number);
//
//    //修改图书库存
//    @Update("UPDATE BooksInfo SET storage=#{storage} WHERE ISBN=#{isbn}")
//    int updateStorage(@Param("storage") int storage, @Param("isbn") String isbn);
//
//    //

}
