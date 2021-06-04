package com.example.library.mapper;

import com.example.library.domain.Book;
import com.example.library.domain.Shelf;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ShelfMapper {

    @Select("SELECT * FROM ShelfInfo WHERE user_id=#{user_id}")
    List<Shelf> selectShelf(@Param("user_id") int user_id);

    @Select("SELECT * FROM BookInfo WHERE user_id=#{user_id} AND shelf_id in (SELECT shelf_id FROM ShelfInfo WHERE ShelfName=#{shelfName} AND user_id=#{user_id})")
    List<Book> selectBookInShelf(@Param("user_id") int user_id, @Param("shelfName") String shelfName);

    @Select("SELECT ShelfName,CountOfBooks FROM ShelfInfo WHERE user_id=#{user_id}")
    List<Shelf> getBookNumber(@Param("user_id") int user_id);

    @Update("UPDATE ShelfInfo SET ShelfName=#{new_shelfName} WHERE user_id=#{user_id} AND ShelfName=#{old_shelfName}")
    int updateShelfName(@Param("user_id") int user_id, @Param("old_shelfName") String old_shelfName, @Param("new_shelfName") String new_shelfName);

    @Insert("INSERT INTO ShelfInfo(ShelfName,CountOfBooks,user_id) VALUES(#{shelfName},#{number},#{user_id})")
    int insertShelf(@Param("shelfName") String shelfName, @Param("number") int number, @Param("user_id") int user_id);

    @Select("SELECT shelf_id FROM ShelfInfo WHERE user_id=#{user_id} AND ShelfName=#{shelfName}")
    Shelf findShelf(@Param("user_id") int user_id, @Param("shelfName") String shelfName);

    @Delete("DELETE FROM ShelfInfo WHERE user_id=#{user_id} AND ShelfName=#{shelfName}")
    int deleteShelf(@Param("user_id") int user_id, @Param("shelfName") String shelfName);

    @Select("SELECT shelfName FROM ShelfInfo WHERE shelf_id=#{shelf_id}")
    String getShelfNameById(@Param("shelf_id") int shelf_id);

}
