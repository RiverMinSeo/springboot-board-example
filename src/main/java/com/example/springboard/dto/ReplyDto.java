package com.example.springboard.dto;

import com.example.springboard.domain.Reply;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReplyDto {
  private Long id;
  private String author;
  private String content;
  private String password;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;

  public String getCreateDt(String format) {
    return (this.createdDate != null) ? this.createdDate.format(DateTimeFormatter.ofPattern(format)) : null;
  }

  public String getModifiedDt(String format) {
    return (this.modifiedDate != null) ? this.modifiedDate.format(DateTimeFormatter.ofPattern(format)) : null;
  }

  public String contentToHtml() {
    return this.content.replaceAll("\r\n","<br>");
  }

  public Reply toEntity() {
    return Reply.builder()
            .author(author)
            .password(password)
            .content(content)
            .build();
  }

  @Builder
  public ReplyDto(Long id, String author, String content, String password, LocalDateTime createdDate, LocalDateTime modifiedDate) {
    this.id = id;
    this.author = author;
    this.content = content;
    this.password = password;
    this.createdDate = createdDate;
    this.modifiedDate = modifiedDate;
  }
}
