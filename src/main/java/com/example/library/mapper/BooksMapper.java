package com.example.library.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface BooksMapper {
    @Insert("INSERT INTO BookInfo VALUES(#{isbn},#{bookName},#{author},#{publishDate},#{type_id},#{price},#{press_id},#{contentIntroduction}," +
            "#{authorIntroduction},#{coverUrl},#{flag})")
    int saveBook(@Param("isbn") String isbn, @Param("bookName") String bookName, @Param("author") String author, @Param("publishDate") String publishDate, @Param("type_id") int type_id, @Param("price") double price, @Param("press_id") int press_id,
                 @Param("contentIntroduction") String contentIntroduction, @Param("authorIntroduction") String authorIntroduction, @Param("coverUrl") String coverUrl, @Param("flag") String flag);


}
