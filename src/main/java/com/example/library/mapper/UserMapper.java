package com.example.library.mapper;


import com.example.library.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    //插入新用户信息
    @Insert("INSERT INTO UserInfo(UserName,Email,`Password`,Phone) VALUES(#{username},#{email},#{password},#{phone})")
    int saveUser(@Param("username") String username, @Param("password") String password, @Param("email") String email, @Param("phone") String phone);

    //根据手机号查询用户信息
    @Select("SELECT user_id,UserName,Password,Email,Phone FROM UserInfo WHERE Phone=#{phone}")
    User selectUser(@Param("phone") String phone) ;

    //修改用户信息
    @Update("UPDATE UserInfo SET UserName=#{userName},Email=#{email},Password=#{password},Phone=#{phone} WHERE user_id=#{user_id}")
    int updateUser(@Param("userName") String userName, @Param("email") String email, @Param("password") String password, @Param("phone") String phone, @Param("user_id") int user_id);


    //设置用户VIP等级
    @Update("UPDATE UserInfo SET VIP=#{vip} WHERE user_id=#{user_id}")
    int updateVip(@Param("vip") Integer vip, @Param("user_id") int user_id);

    //根据email查询用户信息
    @Select("SELECT * FROM UserInfo WHERE Email=#{email}")
    User selectUserByEmail(@Param("email") String email);

    //根据phone查询用户信息
    @Select("SELECT * FROM UserInfo WHERE Phone=#{phone}")
    User selectUserByPhone(@Param("phone") String phone);

    //根据user_id查询用户信息
    @Select("SELECT * FROM UserInfo WHERE user_id=#{user_id}")
    User selectUserById(@Param("user_id") int user_id);



}
