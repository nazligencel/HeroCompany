package com.works.entities;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class Login {

    @Length(message = "Maximum 60", max = 60)
    @NotBlank(message = "Email Can Not Be Blank")
    @Email(message = "Email Format Error")
    private String username;

    //@Length(message = "Maximum 5 min 10",min = 5, max = 10)
    @NotBlank(message = "password can not be blank")
    private String password;
}