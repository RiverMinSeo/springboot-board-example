package com.example.springboard.data;

import com.example.springboard.dto.BoardDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@ToString
@Getter
@Setter
public class DeleteForm implements Serializable {
  Long id;

  @NotBlank(message = "비밀번호를 입력해 주세요")
  String password;
}
