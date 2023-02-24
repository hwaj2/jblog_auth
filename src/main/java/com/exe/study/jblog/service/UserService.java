package com.exe.study.jblog.service;


import com.exe.study.jblog.domain.RoleType;
import com.exe.study.jblog.domain.User;
import com.exe.study.jblog.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void insertUser(User user) {
        user.setRole(RoleType.USER);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUserName(String username) {
        User findUser = userRepository.findByUsername(username).orElseGet(()->{
            return new User();
        });
        return findUser;
    }


    @Transactional(readOnly = true)
    public User getUser(int id) {
        User findUser = userRepository.findById(id).orElseGet(()->{
            return new User();
        });
        return findUser;
    }


}