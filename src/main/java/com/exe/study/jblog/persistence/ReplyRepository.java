package com.exe.study.jblog.persistence;

import com.exe.study.jblog.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
}
