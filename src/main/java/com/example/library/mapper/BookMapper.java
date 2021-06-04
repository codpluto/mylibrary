package com.example.library.mapper;

import com.example.library.domain.Book;
import com.example.library.utils.Author2Number;
import com.example.library.utils.Press2Number;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BookMapper {
    @Insert("INSERT INTO BookInfo VALUES(#{isbn},#{bookName},#{coverUrl},#{notes},#{lender},#{isLentOut}," +
            "#{buyFrom},#{buyDate},#{price},#{author},#{translator},#{press},#{publicationDate},#{totalPages},#{readProgress}," +
            "#{contentIntroduction},#{authorIntroduction},#{user_id},#{shelf_id})")
    int saveBook(@Param("isbn") String isbn, @Param("bookName") String bookName, @Param("coverUrl") String coverUrl,
                 @Param("notes") String notes, @Param("lender") String lender, @Param("isLentOut") boolean isLentOut,
                 @Param("buyFrom") String buyFrom, @Param("buyDate") String buyDate, @Param("price") double price,
                 @Param("author") String author, @Param("translator") String translator, @Param("press") String press, @Param("publicationDate") String publicationDate,
                 @Param("totalPages") int totalPages, @Param("readProgress") int readProgress, @Param("contentIntroduction") String contentIntroduction,
                 @Param("authorIntroduction") String authorIntroduction, @Param("user_id") int user_id, @Param("shelf_id") Integer shelf_id);



    @Select("SELECT * FROM BookInfo WHERE ISBN=#{isbn} AND user_id=#{user_id}")
    Book selectBook(@Param("isbn") String isbn, @Param("user_id") int user_id);

    @Select("SELECT * FROM BookInfo WHERE user_id=#{user_id} AND BookName LIKE CONCAT('%',#{bookName},'%')")
    List<Book> selectBookLike(@Param("bookName") String bookName, @Param("user_id") int user_id);

    @Update("UPDATE BookInfo SET ISBN=#{isbn},bookName=#{bookName},coverURL=#{coverUrl},notes=#{notes}," +
            "lender=#{lender},isLentOut=#{isLentOut},buyFrom=#{buyFrom},buyDate=#{buyDate},price=#{price},author=#{author}," +
            "translator=#{translator},press=#{press},publicationDate=#{publicationDate},totalPages=#{totalPages}," +
            "readProgress=#{readProgress},contentIntroduction=#{contentIntroduction},authorIntroduction=#{authorIntroduction}," +
            "shelf_id=#{shelf_id} WHERE user_id=#{user_id} AND isbn=#{isbn}")
    int updateBook(@Param("isbn") String isbn, @Param("bookName") String bookName, @Param("coverUrl") String coverUrl,
                   @Param("notes") String notes, @Param("lender") String lender,
                   @Param("isLentOut") boolean isLentOut, @Param("buyFrom") String buyFrom, @Param("buyDate") String buyDate,
                   @Param("price") double price, @Param("author") String author, @Param("translator") String translator,
                   @Param("press") String press, @Param("publicationDate") String publicationDate, @Param("totalPages") int totalPages,
                   @Param("readProgress") int readProgress, @Param("contentIntroduction") String contentIntroduction,
                   @Param("authorIntroduction") String authorIntroduction, @Param("user_id") int user_id, @Param("shelf_id") Integer shelf_id);

    @Delete("DELETE FROM BookInfo WHERE user_id=#{user_id} AND isbn=#{isbn}")
    int deleteBook(@Param("user_id") int user_id, @Param("isbn") String isbn);

    @Select("SELECT * FROM BookInfo WHERE user_id=#{user_id}")
    List<Book> selectAllBook(@Param("user_id") int user_id);

    @Select("SELECT COUNT(press) FROM (SELECT DISTINCT press FROM BookInfo WHERE user_id=#{user_id})a")
    int countAllPress(@Param("user_id") int user_id);

    @Select("SELECT COUNT(author) FROM (SELECT DISTINCT author FROM BookInfo WHERE user_id=#{user_id})a")
    int countAllAuthor(@Param("user_id") int user_id);

    @Select("SELECT press,COUNT(*) AS 'number' FROM BookInfo WHERE user_id=#{user_id} GROUP BY press")
    List<Press2Number> countBookByPress(@Param("user_id") int user_id);

    @Select("SELECT author,COUNT(*) AS 'number' FROM BookInfo WHERE user_id=#{user_id} GROUP BY author")
    List<Author2Number> countBookByAuthor(@Param("user_id") int user_id);

    @Select("SELECT COUNT(isbn) FROM BookInfo WHERE user_id=#{user_id}")
    int countBookByUser(@Param("user_id") int user_id);
}
