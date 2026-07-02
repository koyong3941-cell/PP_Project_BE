package com.kh.pp.member.model.dto;

import java.sql.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class MemberDto {
    private Long memberNo;
    @NotBlank(message = "아이디는 필수입니다.")
    @Pattern(
        regexp = "^[a-zA-Z0-9]{5,12}$",
        message = "아이디는 영문/숫자 5~12자여야 합니다."
    )
    private String memberId;
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{6,15}$",
        message = "비밀번호는 영문과 숫자를 포함한 6~15자여야 합니다."
    )
    private String memberPwd;
    @NotBlank(message = "이름은 필수입니다.")
    @Pattern(
        regexp = "^.{2,12}$",
        message = "이름은 2~12자여야 합니다."
    )
    private String memberName;
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;
    private String role;
    private Date enrollDate;
    private Date editDate;
    private String delYn;
}
