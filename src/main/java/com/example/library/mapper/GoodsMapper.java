package com.example.library.mapper;

import com.example.library.domain.Good;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsMapper {

    //添加商品信息
    @Insert("INSERT INTO GoodsInfo(ISBN,origPrice,practicalPrice,author,publishDate,press,introduction,createDate,user_id,bookName,conditions,coverUrl,expressPrice) " +
            "VALUES(#{isbn},#{origPrice},#{practicalPrice},#{author},#{publishDate},#{press},#{introduction},#{createDate},#{user_id},#{bookName},#{conditions},#{coverUrl},#{expressPrice})")
    int saveGoods(@Param("isbn") String isbn, @Param("origPrice") double origPrice, @Param("practicalPrice") double practicalPrice,
                  @Param("author") String author, @Param("publishDate") String publishDate, @Param("press") String press, @Param("introduction") String introduction,
                  @Param("createDate") String createDate, @Param("user_id") int user_id, @Param("conditions") int conditions, @Param("bookName") String bookName,
                  @Param("coverUrl") String coverUrl, @Param("expressPrice") double expressPrice);

    //修改商品信息
    @Update("UPDATE GoodsInfo SET practicalPrice=#{practicalPrice},expressPrice=#{expressPrice},conditions=#{conditions}," +
            "coverUrl=#{coverUrl},introduction=#{introduction} WHERE good_id=#{good_id}")
    int updateGoods(@Param("practicalPrice") double practicalPrice, @Param("good_id") int good_id, @Param("expressPrice") double expressPrice,
                    @Param("conditions") int conditions, @Param("coverUrl") String coverUrl, @Param("introduction") String introduction);

//    //修改商品运费
//    @Update("UPDATE GoodsInfo SET expressPrice=#{expressPrice} WHERE good_id=#{good_id}")
//    int updateExpress(@Param("expressPrice") double expressPrice, @Param("good_id") int good_id);

    //根据书名模糊查询商品
    @Select("SELECT * FROM GoodsInfo WHERE bookName LIKE CONCAT('%',#{bookName},'%')")
    List<Good> selectGoodsLike(@Param("bookName") String bookName);

    //根据id精确查询
    @Select("SELECT * FROM GoodsInfo WHERE good_id=#{good_id}")
    Good selectGood(@Param("good_id") int good_id);

    //修改商品是否已卖出
    @Update("UPDATE GoodsInfo SET isSoldOut=#{isSoldOut} WHERE good_id=#{good_id}")
    int updateSoldOut(@Param("good_id") int good_id, @Param("isSoldOut") boolean isSoldOut);

    //查询用户发布的未卖出的所有商品
    @Select("SELECT * FROM GoodsInfo WHERE user_id=#{user_id} AND isSoldOut=false")
    List<Good> selectGoodsByUser(@Param("user_id") int user_id);

    //查询用户购买的所有商品
    @Select("SELECT * FROM GoodsInfo WHERE good_id in (SELECT good_id FROM OrderList WHERE user_id=#{user_id})")
    List<Good> selectGoodsBuyed(@Param("user_id") int user_id);

    //随机获取20件商品
    @Select("SELECT * FROM GoodsInfo WHERE isSoldOut=false ORDER BY RAND() LIMIT 20")
    List<Good> selectPartGoods();

    //删除商品
    @Delete("DELETE FROM GoodsInfo WHERE good_id=#{good_id}")
    int deleteGood(@Param("good_id") int good_id);

    //修改商品品相
    @Update("UPDATE GoodsInfo SET conditions=#{conditions} WHERE good_id=#{good_id}")
    int updateConditions(@Param("good_id") int good_id, @Param("conditions") int conditions);

}
