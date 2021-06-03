package com.example.library.controller;


import com.example.library.domain.Book;
import com.example.library.domain.Shelf;
import com.example.library.mapper.ShelfMapper;
import com.example.library.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("shelf")
@RestController
public class ShelfController {

    @Autowired
    private ShelfMapper shelfMapper;

    @RequestMapping("getShelf")
    public JsonResult getShelf(int user_id){
        JsonResult jr = new JsonResult();
        List<Shelf> shelves = shelfMapper.selectShelf(user_id);
        jr.setObj(shelves);
        if(shelves.size()==0){
            jr.setStatus(0);        //0,无书架
            return jr;
        }
        jr.setStatus(1);
        return jr;
    }

    @RequestMapping("getBookInShelf")
    public JsonResult getBookInShelf(int user_id,String shelfName){
        JsonResult jr = new JsonResult();
        log.info("user_id:{}",user_id);
        log.info("shelfName:{}",shelfName);
        List<Book> books = shelfMapper.selectBookInShelf(user_id,shelfName);
        jr.setObj(books);
        if(books.size()==0){
            jr.setStatus(0);
            return jr;
        }
        jr.setStatus(1);
        return jr;
    }

    @RequestMapping("getAllNumber")
    public JsonResult getAllNumber(int user_id){
        JsonResult jr = new JsonResult();
        List<Shelf> shelves = shelfMapper.selectShelf(user_id);
//        for(int i=0;i<shelves.size();i++){
//            System.out.println(shelves.get(i).);
//        }
        Map<String,Integer> map = new HashMap<String,Integer>();
        for(int i=0;i<shelves.size();i++){
            map.put(shelves.get(i).getShelfName(),shelves.get(i).getCountOfBooks());
        }
        jr.setObj(map);
        log.info("shelves:{}",map);
        if(map.size()==0){
            jr.setStatus(0);
            return jr;
        }
        jr.setStatus(1);
        return jr;
    }

    //修改书柜名
    @RequestMapping("updateShelfName")
    public JsonResult changeShelfName(int user_id,String old_shelfName,String new_shelfName){
        JsonResult jr = new JsonResult();
        int resultCount = shelfMapper.updateShelfName(user_id,old_shelfName,new_shelfName);
        jr.setStatus(resultCount);
        return jr;
    }

    //添加书柜
    @RequestMapping("addShelf")
    public JsonResult addShelf(@RequestBody Shelf newshelf){
        log.info("newshelf:{}",newshelf);
        JsonResult jr = new JsonResult();
        Shelf shelf = shelfMapper.findShelf(newshelf.getShelf_id(),newshelf.getShelfName());
        if(shelf!=null){
            jr.setStatus(0);
            return jr;
        }
        int resultCount = shelfMapper.insertShelf(newshelf.getShelfName(),newshelf.getCountOfBooks(),newshelf.getUser_id());
        if(resultCount==1){
            jr.setStatus(1);
            return jr;
        }
        jr.setStatus(-1);
        return jr;
    }

    //删除书柜
    @RequestMapping("deleteShelf")
    public JsonResult dropShelf(int user_id,String shelfName){
        JsonResult jr = new JsonResult();
        int resultCount = shelfMapper.deleteShelf(user_id,shelfName);
        jr.setStatus(resultCount);
        return jr;
    }

}
