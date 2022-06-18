package com.works.restcontrollers;

import com.works.entities.Login;
import com.works.services.LoginUserDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController

public class LoginUserDetailRestController {
    final LoginUserDetailService loginUserDetailService;

    public LoginUserDetailRestController(LoginUserDetailService loginUserDetailService) {
        this.loginUserDetailService = loginUserDetailService;
    }
    @PostMapping("/login")
    public ResponseEntity auth(@Valid @RequestBody Login login){
        return loginUserDetailService.auth(login) ;
    }
}
