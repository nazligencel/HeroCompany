package com.works.entities;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Data
public class Admin extends Base{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Please Enter Company Information")
    @Length(message = "Please Enter Minimum 1 Maximum 50 Characters", min = 1, max = 50)
    private String companyName;

    @NotBlank(message = "Please Enter Name Information")
    @Length(message = "Please Enter Minimum 1 Maximum 50 Characters", min = 1, max = 50)
    private String firstName;

    @Length(message = "Please Enter Minimum 1 Maximum 50 Characters", min = 1, max = 50)
    private String lastName;

    @Length(message = "Please Enter Minimum 1 Maximum 60 Characters",min = 1, max = 60)
    @Email(message = "E-Mail format is Incorrect")
    @NotBlank(message = "Please Enter E-Mail Information")
    @Column(unique = true)
    private String email;

    private String phone;
    //@Pattern(message = "Password Must Consist of Letters and Numbers 0-9 ", regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\\\S+$)")
    @NotBlank(message = "Password Empty")
    private String password;

    private boolean enabled;
    private boolean tokenExpired;

    private String verificationCode;
    @ManyToOne
//    @JsonIgnore
    @JoinColumn(name = "role_Id", referencedColumnName = "id")
    private Role roles;

}


