package com.example.library.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.library.domain.Shelf;
import com.example.library.domain.User;
import com.example.library.mapper.BookMapper;
import com.example.library.mapper.ShelfMapper;
import com.example.library.mapper.UserMapper;
import com.example.library.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSON;
import java.util.Objects;

@Slf4j
@RequestMapping("user")
@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private ShelfMapper shelfMapper;

    //登录
    @RequestMapping("login")
    public JsonResult login(String password, String phone){
        //先查询该用户是否存在
        User user = userMapper.selectUser(phone);
        JsonResult jr = new JsonResult();
        //数据库查询结果为空，用户不存在，设置状态码-1
        if(user==null){
            jr.setStatus(-1);   //-1，账户不存在
            return jr;
        }
        //比较密码是否正确，正确，登录成功并返回该用户信息
        if(Objects.equals(password,user.getPassword())) {
            jr.setStatus(1);
            jr.setObj(user);
            return jr;
        }
        //密码错误，登录失败，设置状态码0
        jr.setStatus(0);        //0，密码错误
        return jr;
    }

    //注册
    @RequestMapping("register")
    public JsonResult register(String username,String password,String email,String phone){
//        log.info("username:{}",username);
//        log.info("password:{}",password);
//        log.info("email:{}",email);
//        log.info("phone:{}",phone);
        JsonResult jr = new JsonResult();
        if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password)||
                StringUtils.isEmpty(email)||StringUtils.isEmpty(phone)) {
            jr.setStatus(-2);       //-2,输入缺失
            return jr;
        }
        User user = userMapper.selectUser(phone);
        if(user != null){
            jr.setStatus(0);         //0,用户已注册
            return jr;
        }

        int resultCount=userMapper.saveUser(username,password,email,phone);
        if(resultCount == 0){
            jr.setStatus(-1);    //-1，数据库插入异常
            return jr;
        }
        jr.setStatus(1);
        User user1 = userMapper.selectUser(phone);
        shelfMapper.insertShelf("我的书架",1,user1.getUser_id());
        Shelf shelf = shelfMapper.findShelf(user1.getUser_id(),"我的书架");

        bookMapper.saveBook("9787569377941","云雀叫了一整天","https://img3.doubanio.com/view/subject/l/public/s25948080.jpg",
                "精装版",null,false,"京东","2020-06-21",30.0,"木心",null,
                "广西师范大学出版社","2016-04-01",245,0,"云雀叫了一整天》是由广西师范大学出版社出版的图书，" +
                        "作者是木心。该书由第一辑（诗歌）与第二辑（短句）组成，收入了《火车中的情诗》《女优的肖像》《伏尔加》等一百余首诗篇，逾百行木心式的精彩箴言。",
                "木心（1927年2月14日—2011年12月21日），本名孙璞，字仰中，号牧心，笔名木心。中国当代作家、画家。1927年出生于浙江省嘉兴市桐乡乌镇东栅。毕业于上海美术专科学校。" +
                        "2011年12月21日3时逝世于故乡乌镇，享年84岁。",user1.getUser_id(),shelf.getShelf_id());
        return jr;
    }

    //修改用户信息
    @RequestMapping("changeUserInfo")
    public JsonResult changeUserInfo(String username,String email,String password,String phone,int user_id){
        JsonResult jr = new JsonResult();
        User oldUser = userMapper.selectUserById(user_id);
        if(oldUser==null){
            jr.setStatus(-3);       //-3,user不存在
            return jr;
        }
        if(!Objects.equals(oldUser.getPhone(),phone)){
            User user = userMapper.selectUserByPhone(phone);
            if(user!=null){
                jr.setStatus(-1);       //-1,手机号已注册
                return jr;
            }
        }
        if(!Objects.equals(oldUser.getEmail(),email)){
            User user = userMapper.selectUserByEmail(email);
            if(user!=null){
                jr.setStatus(-2);       //-2,邮箱已注册
                return jr;
            }
        }
        int result = userMapper.updateUser(username,email,password,phone,user_id);
        jr.setStatus(result);
        return jr;
    }

    //根据user_id查询用户信息
    @RequestMapping("getUserById")
    public JsonResult getUserById(int user_id){
        JsonResult jr = new JsonResult();
        User user = userMapper.selectUserById(user_id);
        if(user==null){
            jr.setStatus(0);
            return jr;
        }
        jr.setStatus(1);
        jr.setObj(user);
        return jr;
    }

    //设置vip等级
    @RequestMapping("setVip")
    public JsonResult setVip(Integer vip,int user_id){
        JsonResult jr = new JsonResult();
        int result = userMapper.updateVip(vip,user_id);
        jr.setStatus(result);
        return jr;
    }


}
