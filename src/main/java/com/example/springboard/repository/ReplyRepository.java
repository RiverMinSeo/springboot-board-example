package com.example.springboard.repository;

import com.example.springboard.domain.Board;
import com.example.springboard.domain.Reply;
import org.hibernate.validator.internal.properties.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
  @Query(value = "SELECT COUNT(id) FROM tb_reply WHERE tb_board_id=:boardId", nativeQuery = true)
  long countByBoard(@Param("boardId") Long boardId);
  List<Reply> findByIdGreaterThanEqualAndTbBoardId(Long id, Long tbBoardId);
}
