package com.example.library.controller;

import com.alibaba.fastjson.JSON;
import com.example.library.domain.Book;
import com.example.library.mapper.BookMapper;
import com.example.library.service.BookService;
import com.example.library.utils.Author2Number;
import com.example.library.utils.JsonResult;
import com.example.library.utils.Press2Number;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RequestMapping("book")
@RestController
public class BookController {

    @Autowired
    private BookMapper bookMapper;

//    @RequestMapping("addBook")
//    public int addBook(String isbn,String bookName,String coverUrl,String bookShelf,String notes,String lender,
//                          boolean isLentOut,String buyFrom,String buyDate,double price,String author,String translator,
//                          String press,String publicationDate,int totalPages,int readProgress,String contentIntroduction,
//                          String authorIntroduction,String phone){
//        Book book = bookMapper.selectBook(isbn,phone);
//        if(book==null){
//            return -1;
//        }
//
//        int resultCount = bookMapper.saveBook(isbn,bookName,coverUrl,bookShelf,notes,lender,isLentOut,buyFrom,buyDate,price,
//                publicationDate,totalPages,readProgress,contentIntroduction,authorIntroduction,phone);
//        return resultCount;
//    }

    //模糊查询书籍
    @RequestMapping("searchBookLike")
    public JsonResult searchBookLike(int user_id, String bookName){
        log.info("user_id:{}",user_id);
        log.info("bookName:{}",bookName);
        List<Book> book = bookMapper.selectBookLike(bookName,user_id);
        JsonResult jr = new JsonResult();
        jr.setObj(book);
        if(book.size()==0){
            jr.setStatus(0);        //0,未查到
            return jr;
        }
        jr.setStatus(1);
        return jr;
    }

    //精确查询书籍
    @RequestMapping("searchBook")
    public JsonResult searchBook(int user_id,String isbn){
        Book book = bookMapper.selectBook(isbn,user_id);

        JsonResult jr = new JsonResult();
        jr.setObj(book);
        System.out.println(jr.getObj());
        if(book==null){
            jr.setStatus(0);
            return jr;
        }
        jr.setStatus(1);
        return jr;
    }


    //添加书籍
    @RequestMapping("addBook")
    @ResponseBody
    public JsonResult addBook(@RequestBody Book newbook) {
//        if(newbook.getShelf_id()==0)
//            newbook.setShelf_id(null);
        log.info("newbook:{}",newbook);

        Book book = bookMapper.selectBook(newbook.getIsbn(),newbook.getUser_id());
        JsonResult jr = new JsonResult();
        if(book!=null){
            jr.setStatus(-1);
            return jr;
        }
        //String result = BookService.getDataByIsbn(isbn);

        //jr.setObj(JSON.toJSON(result));
        int resultCount = bookMapper.saveBook(newbook.getIsbn(),newbook.getBookName(),newbook.getCoverUrl(),newbook.getNotes(),
                newbook.getLender(),newbook.isLentOut(),newbook.getBuyFrom(),newbook.getBuyDate(),newbook.getPrice(),
                newbook.getAuthor(),newbook.getTranslator(),newbook.getPress(),newbook.getPublicationDate(),newbook.getTotalPages(),newbook.getReadProgress(),
                newbook.getContentIntroduction(),newbook.getAuthorIntroduction(),newbook.getUser_id(),newbook.getShelf_id());
        jr.setStatus(resultCount);
        return jr;
    }

    //更新书籍
    @RequestMapping("updateBook")
    @ResponseBody
    public JsonResult changeBook(@RequestBody Book book){
//            String isbn,String bookName,String coverUrl,String bookShelf,String notes,String lender,
//                          boolean isLentOut,String buyFrom,String buyDate,double price,String author,String translator,
//                          String press,String publicationDate,int totalPages,int readProgress,String contentIntroduction,
//                          String authorIntroduction,String user_id){

        int resultCount = bookMapper.updateBook(book.getIsbn(),book.getBookName(),book.getCoverUrl(),book.getNotes(),book.getLender(),
                book.isLentOut(),book.getBuyFrom(),book.getBuyDate(),book.getPrice(),book.getAuthor(),book.getTranslator(),book.getPress(),
                book.getPublicationDate(),book.getTotalPages(),book.getReadProgress(),book.getContentIntroduction(),book.getAuthorIntroduction(),
                book.getUser_id(),book.getShelf_id());
        //int resultCount = bookMapper.updateBook(isbn,bookName,coverUrl,bookShelf,notes,lender,isLentOut,buyFrom,buyDate,
        //        price,publicationDate,totalPages,readProgress,contentIntroduction,authorIntroduction,user_id);
        JsonResult jr = new JsonResult();
        jr.setStatus(resultCount);
        return jr;
    }

    //删除书籍
    @RequestMapping("deleteBook")
    public JsonResult dropBook(@RequestBody Book book){
        int resultCount = bookMapper.deleteBook(book.getUser_id(),book.getIsbn());
        JsonResult jr = new JsonResult();
        jr.setStatus(resultCount);
        return jr;
    }

    //获取用户全部图书信息
    @RequestMapping("getAllBook")
    public JsonResult getAllBookByUserid(int user_id){
        JsonResult jr = new JsonResult();
        List<Book> books = bookMapper.selectAllBook(user_id);
        jr.setObj(books);
        if(books.size()==0){
            jr.setStatus(0);        //0,没有图书
            return jr;
        }
        jr.setStatus(1);
        return jr;
    }

    //查询出版社数量
    @RequestMapping("countPress")
    public JsonResult countPress(int user_id){
        JsonResult jr = new JsonResult();
        int count = bookMapper.countAllPress(user_id);
        jr.setObj(count);
//        if(count==0){
//            jr.setStatus(0);
//            return jr;
//        }
        jr.setStatus(1);
        return jr;
    }


    //查询作者数量
    @RequestMapping("countAuthor")
    public JsonResult coutAuthor(int user_id){
        JsonResult jr = new JsonResult();
        int count = bookMapper.countAllAuthor(user_id);
        jr.setObj(count);
        jr.setStatus(1);
        return jr;
    }

    //添加图书
//    @RequestMapping("addBook")
//    public JsonResult addBook(String isbn,int user_id) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
//        JsonResult jr = new JsonResult();
//        String data = BookService.getDataByIsbn(isbn);
//        log.info(data);
//        //JSON jsdata = (JSON) JSON.toJSON(data);
//        jr.setObj(data);
//        return jr;
//    }
    //调用查书API
    @RequestMapping("getBookData")
    public JsonResult getBookData(String isbn) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        JsonResult jr = new JsonResult();
        jr.setStatus(-1);
        String data = BookService.getDataByIsbn(isbn);
        System.out.println(JSON.parse(data));
        if(data!=null)
            jr.setStatus(1);
        //System.out.println(data);
        jr.setObj(JSON.parse(data));
        return jr;
    }

    //出版社:图书本书
    @RequestMapping("countBooksByPress")
    public JsonResult countBookByPress(int user_id){
        JsonResult jr = new JsonResult();
        List<Press2Number> press2Numbers = bookMapper.countBookByPress(user_id);
        Map<String,Integer> map = new HashMap<String,Integer>();
        for(int i=0;i<press2Numbers.size();i++){
            if(press2Numbers.get(i).getPress()==null)
                press2Numbers.get(i).setPress("default");
            map.put(press2Numbers.get(i).getPress(),press2Numbers.get(i).getNumber());
        }
        jr.setObj(map);
        if(press2Numbers.size()==0){
            jr.setStatus(0);
            return jr;
        }
        jr.setStatus(1);
        return jr;
    }

    //作者:图书本书
    @RequestMapping("countBooksByAuthor")
    public JsonResult countBookByAuthor(int user_id){
        JsonResult jr = new JsonResult();
        List<Author2Number> author2Numbers = bookMapper.countBookByAuthor(user_id);
        Map<String,Integer> map = new HashMap<String,Integer>();
        for(int i=0;i<author2Numbers.size();i++){
            if(author2Numbers.get(i).getAuthor()==null)
                author2Numbers.get(i).setAuthor("default");
            map.put(author2Numbers.get(i).getAuthor(),author2Numbers.get(i).getNumber());
        }
        jr.setObj(map);
        if(author2Numbers.size()==0){
            jr.setStatus(0);
            return jr;
        }
        jr.setStatus(1);
        return jr;
    }

    @RequestMapping("countBooksByUser")
    public JsonResult countBooksByUser(int user_id){
        JsonResult jr = new JsonResult();
        int number = bookMapper.countBookByUser(user_id);
        Map<String,Integer> map = new HashMap<String,Integer>();
        map.put("number",number);
        jr.setObj(map);
        jr.setStatus(1);
        return jr;
    }

}
