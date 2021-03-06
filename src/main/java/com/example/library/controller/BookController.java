package com.example.library.controller;

import com.alibaba.fastjson.JSON;
import com.example.library.domain.Book;
import com.example.library.mapper.BookMapper;
import com.example.library.mapper.ShelfMapper;
import com.example.library.service.BookService;
import com.example.library.utils.Author2Number;
import com.example.library.utils.JsonResult;
import com.example.library.utils.Press2Number;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;
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

    @Autowired
    private ShelfMapper shelfMapper;

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
//        log.info("user_id:{}",user_id);
//        log.info("bookName:{}",bookName);
        JsonResult jr = new JsonResult();
        if(bookName.length()==0){
            jr.setStatus(0);
            return jr;
        }
        List<Book> book = bookMapper.selectBookLike(bookName,user_id);
        for(int i=0;i<book.size();i++){
            if(book.get(i).getShelf_id()!=null)
                book.get(i).setShelfName(shelfMapper.getShelfNameById(book.get(i).getShelf_id()));
        }
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
//        log.info("user_id:{}",user_id);
//        log.info("isbn:{}",isbn);
        Book book = bookMapper.selectBook(isbn,user_id);
        JsonResult jr = new JsonResult();
        if(book==null){
            jr.setStatus(0);
            return jr;
        }

        if(book.getShelf_id()!=null)
            book.setShelfName(shelfMapper.getShelfNameById(book.getShelf_id()));
        jr.setObj(book);
        //System.out.println(jr.getObj());

        jr.setStatus(1);
        return jr;
    }


    //添加书籍
    @RequestMapping("addBook")
    public JsonResult addBook(@RequestBody Book newbook) {
        JsonResult jr = new JsonResult();
        //如果添加时设置书架了，修改该书架藏书数量
        if(newbook.getShelf_id()!=null){
            shelfMapper.updateCountOfBooks_plus(newbook.getShelf_id());
        }
        //调用insert语句插入记录
        int resultCount = bookMapper.saveBook(newbook.getIsbn(),newbook.getBookName(),newbook.getCoverUrl(),newbook.getNotes(),
                newbook.getLender(),newbook.isLentOut(),newbook.getBuyFrom(),newbook.getBuyDate(),newbook.getPrice(),
                newbook.getAuthor(),newbook.getTranslator(),newbook.getPress(),newbook.getPublicationDate(),newbook.getTotalPages(),newbook.getReadProgress(),
                newbook.getContentIntroduction(),newbook.getAuthorIntroduction(),newbook.getUser_id(),newbook.getShelf_id());
        jr.setStatus(resultCount);
        return jr;
    }

    //更新书籍
    @RequestMapping("updateBook")
    public JsonResult changeBook(@RequestBody Book book){
        Book oriBook = bookMapper.selectBook(book.getIsbn(),book.getUser_id());
        Integer shelf_id = oriBook.getShelf_id();
        if(shelf_id!=null){
            shelfMapper.updateCountOfBook_minus(shelf_id);
        }
        if(book.getShelf_id()!=null){
            shelfMapper.updateCountOfBooks_plus(book.getShelf_id());
        }

        int resultCount = bookMapper.updateBook(book.getIsbn(),book.getBookName(),book.getCoverUrl(),book.getNotes(),book.getLender(),
                book.isLentOut(),book.getBuyFrom(),book.getBuyDate(),book.getPrice(),book.getAuthor(),book.getTranslator(),book.getPress(),
                book.getPublicationDate(),book.getTotalPages(),book.getReadProgress(),book.getContentIntroduction(),book.getAuthorIntroduction(),
                book.getUser_id(),book.getShelf_id());

        JsonResult jr = new JsonResult();
        jr.setStatus(resultCount);
        return jr;
    }

    //删除书籍
    @RequestMapping("deleteBook")
    public JsonResult dropBook(int user_id,String isbn){
        JsonResult jr = new JsonResult();

        log.info("user_id:{}",user_id);
        log.info("isbn:{}",isbn);

        Book book = bookMapper.selectBook(isbn,user_id);
        if(book==null){
            jr.setStatus(0);    //没有该书
            return jr;
        }
        Integer shelf_id = book.getShelf_id();
        if(shelf_id!=null){
            shelfMapper.updateCountOfBook_minus(shelf_id);
        }
        int result = bookMapper.deleteBook(user_id,isbn);
        jr.setStatus(result);
        return jr;
    }



    //获取用户全部图书信息
    @RequestMapping("getAllBook")
    public JsonResult getAllBookByUserid(int user_id){
        JsonResult jr = new JsonResult();
        List<Book> books = bookMapper.selectAllBook(user_id);
        for(int i=0;i<books.size();i++){
            if(books.get(i).getShelf_id()!=null)
                books.get(i).setShelfName(shelfMapper.getShelfNameById(books.get(i).getShelf_id()));
        }
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

    //查询每个用户的藏书数量
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
