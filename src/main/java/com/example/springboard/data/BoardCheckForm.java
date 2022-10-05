package com.example.springboard.data;

import com.example.springboard.dto.BoardDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Getter
@Setter
@ToString
public class BoardCheckForm {
    @NotNull
    Long boardId;

    @NotBlank(message = "비밀번호를 입력해 주세요.")
    @Pattern(regexp = "^\\d{4}$", message = "비밀번호는 숫자 4자리를 입력해 주세요.")
    String password;

    public BoardDto toDto(){
        return BoardDto.builder().id(boardId).password(password).build();
    }
}
