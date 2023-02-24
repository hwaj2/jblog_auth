package com.exe.study.jblog.persistence;

import com.exe.study.jblog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // SELECT * FROM user WHERE username = ?1;
    Optional<User> findByUsername(String username); // find + (엔티티명) + By + 변수명
}
