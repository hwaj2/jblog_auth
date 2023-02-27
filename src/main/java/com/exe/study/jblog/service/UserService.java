package com.exe.study.jblog.service;


import com.exe.study.jblog.domain.RoleType;
import com.exe.study.jblog.domain.User;
import com.exe.study.jblog.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional
    public void insertUser(User user) {
        user.setRole(RoleType.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword())); //비밀번호 암호화 설정 추가
        userRepository.save(user);
    }

    @Transactional
    public User updateUser(User user){
        User findUser = userRepository.findById(user.getId()).get();
        findUser.setUsername(user.getUsername());
        findUser.setPassword(passwordEncoder.encode(user.getPassword()));
        findUser.setEmail(user.getEmail());
        return findUser;
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