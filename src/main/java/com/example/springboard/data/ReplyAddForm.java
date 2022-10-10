package com.example.springboard.data;

import com.example.springboard.dto.BoardDto;
import com.example.springboard.dto.ReplyDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class ReplyAddForm implements Serializable {
  @NotBlank(message = "내용을 입력해 주세요")
  String content;

  @NotBlank(message = "작성자를 입력해 주세요")
  String author;

  @NotBlank(message = "비밀번호를 입력해 주세요")
  String password;

  public ReplyDto toDto(){
    return ReplyDto.builder()
            .content(content)
            .author(author)
            .password(password)
            .build();
  }
}
