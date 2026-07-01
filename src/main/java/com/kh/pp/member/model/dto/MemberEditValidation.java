package com.kh.pp.member.model.dto;

import java.sql.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberEditValidation {
    private String memberPwd;
    @NotBlank(message = "이름은 필수입니다.")
    @Pattern(
        regexp = "^.{2,12}$",
        message = "이름은 2~12자여야 합니다."
    )
    private String memberName;
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @Pattern(
        regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
        message = "올바른 이메일 형식이 아닙니다."
    )
    private String email;
    private Date editDate;
}