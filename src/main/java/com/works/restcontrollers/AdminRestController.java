package com.works.restcontrollers;

import com.works.entities.Admin;
import com.works.entities.Login;
import com.works.services.AdminService;
import com.works.services.LoginUserDetailService;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("admin")
public class AdminRestController {

    final AdminService aService;
    final LoginUserDetailService loginService;

    public AdminRestController(AdminService aService, LoginUserDetailService loginService) {
        this.aService = aService;

        this.loginService = loginService;
    }
    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody Admin admin){
        return aService.register(admin);
    }

    @PostMapping("/auth")
    public ResponseEntity auth(@Valid @RequestBody Login login){
        return loginService.auth(login);
    }

    @PutMapping("/setting")
    public ResponseEntity setting(
            @RequestParam  @Length(message = "firstName  Must Contain min 2 max  50 Character.", min = 2, max = 50) String firstName,
            @RequestParam @Length(message = "lastName  Must Contain min 2 max  50 Character.", min = 2, max = 50)  String lastName,
            @RequestParam @Email(message = "E-mail Format Error") String email,
            @RequestParam  @Length(message = "Phone Must Contain min 10 max  5O Character.", min = 10, max = 50) String phone ){
        return aService.setting(firstName, lastName,email,phone);
    }
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam Long aid){
        return aService.delete(aid);
    }
    @GetMapping("/list")
    public ResponseEntity list(){
        return aService.list();
    }

    @PutMapping("/changePassword")
    public ResponseEntity changePassword(@RequestParam String oldPassword, @RequestParam @NotBlank(message = "Password Can Not Be Blank")  String newPassword){
        return  aService.changePassword(oldPassword,newPassword);
    }
    @PostMapping("/forgotPassword")
    public ResponseEntity forgotPassword(@RequestParam String email) {
        return aService.forgotPassword(email);
    }



}
