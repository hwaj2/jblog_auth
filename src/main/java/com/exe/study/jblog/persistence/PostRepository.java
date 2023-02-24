package com.exe.study.jblog.persistence;

import com.exe.study.jblog.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
