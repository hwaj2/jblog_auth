package com.exe.study.jblog;

import com.exe.study.jblog.domain.User;
import com.exe.study.jblog.persistence.UserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserDAOTest {

    @Autowired
    private UserDAO userDAO;

    @Test
    void getUserListTest(){
        User user = new User();
        user.setId(1);
        user.setUsername("돌돌");
        user.setPassword("1234");
        user.setEmail("doldol@kakao.com");

        int before = userDAO.getUserList().size();
        userDAO.insertUser(user);
        int after = userDAO.getUserList().size();

        assertEquals(before+1, after);

    }
}
