package com.exe.study.jblog.persistence;

import com.exe.study.jblog.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAO {

    @Autowired
    private SqlSessionTemplate mybatis;

    // UserMapper 인터페이스에 등록한 메서드이름으로 식별
    public void insertUser(User user){
        mybatis.insert("insertUser", user);
    }

    public void updateUser(User user){
        mybatis.update("updateUser", user);
    }
    public void deleteUser(User user){
        mybatis.delete("deleteUser", user);
    }
    public User getUser(User user){
        return mybatis.selectOne("getUser", user);
    }
    public List<User> getUserList(){
        return mybatis.selectList("getUserList");
    }
}
