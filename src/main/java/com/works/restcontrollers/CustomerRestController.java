package com.works.restcontrollers;
import com.works.entities.Customer;
import com.works.entities.Login;
import com.works.services.CustomerService;
import com.works.services.LoginUserDetailService;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("customer")
public class CustomerRestController {
    final CustomerService cusService;
    final LoginUserDetailService loginService;

    public CustomerRestController(CustomerService cusService, LoginUserDetailService loginService) {
        this.cusService = cusService;
        this.loginService = loginService;
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody Customer cus) {
        return cusService.register(cus);
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
        return cusService.setting(firstName, lastName,email,phone);
    }
    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam Long id){
        return cusService.delete(id);
    }
    @GetMapping("/list")
    public ResponseEntity list(){
        return cusService.list();
    }

    @PutMapping("/changePassword")
    public ResponseEntity changePassword(@RequestParam String oldPassword, @RequestParam @NotBlank(message = "Password Can Not Be Blank")  String newPassword){
        return  cusService.changePassword(oldPassword,newPassword);
    }
    @PostMapping("/forgotPassword")
    public ResponseEntity forgotPassword(@RequestParam String email) {
        return cusService.forgotPassword(email);
    }

}