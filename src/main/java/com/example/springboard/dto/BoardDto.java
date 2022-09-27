package com.example.springboard.dto;

import com.example.springboard.domain.Board;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString   // 객체가 가지고 있는 정보나 값들을 문자열로 만들어 리턴하는 메서드
@NoArgsConstructor
public class BoardDto {
    private Long id;
    private String author;
    private String password;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public String getCreateDt(String format) {
        return (this.createdDate != null) ? this.createdDate.format(DateTimeFormatter.ofPattern(format)) : null;
    }

    public String getModifiedDt(String format) {
        return (this.modifiedDate != null) ? this.modifiedDate.format(DateTimeFormatter.ofPattern(format)) : null;
    }

    public Board toEntity(){
        Board board = Board.builder()
                .author(author)
                .password(password)
                .title(title)
                .content(content)
                .build();
        return board;
    }
    @Builder
    public BoardDto(Long id, String author, String password, String title, String content, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.author = author;
        this.password = password;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
