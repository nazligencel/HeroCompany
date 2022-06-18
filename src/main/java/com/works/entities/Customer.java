package com.works.entities;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Entity
@Data
public class Customer extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @NotBlank(message = "Please Enter Name Information")
    @Length(message = "Please Enter Minimum 1 Maximum 50 Characters", min = 1, max = 50)
    private String customerName;

    @NotBlank(message = "Please Enter Surname Information")
    @Length(message = "Please Enter Minimum 1 Maximum 50 Characters", min = 1, max = 50)
    private String customerSurname;


    @Email(message = "E-Mail format is Incorrect")
    @NotBlank(message = "Please Enter E-Mail Information")
    @Column(unique = true)
    private String email;


    //@Pattern(message = "Password Must Consist of Letters and Numbers 0-9 ", regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\\\S+$)")
    @NotBlank(message = "Password Empty")
    private String password;

    @Length(message = "Please Enter Minimum 5 Maximum 50", min = 5, max = 50)
    private String phone;
    private boolean enabled;
    private boolean tokenExpired;

    private String verificationCode;

    @ManyToOne
    //@JsonIgnore
    @JoinColumn(name = "role_Id",referencedColumnName = "id")
    private Role role;

}
