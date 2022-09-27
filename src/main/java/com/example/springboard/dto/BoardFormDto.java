package com.example.springboard.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
public class BoardFormDto implements Serializable {
  @NotBlank(message = "제목을 입력해 주세요")
  String title;

  @NotBlank(message = "내용을 입력해 주세요")
  String content;

  @NotBlank(message = "작성자를 입력해 주세요")
  String author;

  @NotBlank(message = "비밀번호를 입력해 주세요")
  String password;

  public BoardDto toDto(){
    return BoardDto.builder()
            .title(title)
            .content(content)
            .author(author)
            .password(password).build();
  }
}
