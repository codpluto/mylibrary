package com.example.library.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.library.domain.User;
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

    //登录
    @RequestMapping("login")
    public JsonResult login(String password, String phone){
        User user = userMapper.selectUser(phone);
        JsonResult jr = new JsonResult();
        if(user==null){
            //User u = new User(null,null,null,null,0);
            //jr.setObj(u);
            jr.setStatus(-1);   //-1，账户不存在
            return jr;
        }
        if(Objects.equals(password,user.getPassword())) {
            jr.setStatus(1);
            jr.setObj(user);
            return jr;
        }
        //User u = new User(null,null,null,null,0);
        //jr.setObj(u);
        jr.setStatus(0);        //0，密码错误
        return jr;
    }

    //注册
    @RequestMapping("register")
    public JsonResult register(String username,String password,String email,String phone){
//        log.info("username{}",username);
//        log.info("password{}",password);
//        log.info("email{}",email);
//        log.info("phone{}",phone);
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
