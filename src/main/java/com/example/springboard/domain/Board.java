package com.example.springboard.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "tb_board")
@EntityListeners(value = {AuditingEntityListener.class})
public class Board extends Base {
    @Column(name="author", length = 10, nullable = false)
    private String author;

    @Column(name="password", length = 10, nullable = false)
    private String password;

    @Column(name="title", length = 100, nullable = false)
    private String title;

    @Column(name="content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Builder
    public Board(String author, String password, String title, String content) {
        this.author = author;
        this.password = password;
        this.title = title;
        this.content = content;
    }
}
