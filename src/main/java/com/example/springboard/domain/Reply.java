package com.example.springboard.domain;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "tb_reply")
@EntityListeners(value = {AuditingEntityListener.class})
public class Reply extends  Base{
  @Column(name="author", length = 10, nullable = false)
  private String author;

  @Column(name="password", length = 10, nullable = false)
  private String password;

  @Column(name="content", columnDefinition = "TEXT", nullable = false)
  private String content;

  @Column(name = "tb_board_id")
  private Long tbBoardId;

  @Builder
  public Reply(String author, String password, String content, Long tbBoardId) {
    this.author = author;
    this.password = password;
    this.content = content;
    this.tbBoardId = tbBoardId;
  }

  public void update(String content) {
    this.content = content;
  }
}
