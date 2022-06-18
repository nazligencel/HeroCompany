package com.works.services;

import com.works.entities.Admin;
import com.works.repositories.AdminRepository;
import com.works.repositories.CustomerRepository;
import com.works.utils.REnum;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.works.utils.REnum.jwt;

@Transactional
@Service

public class AdminService {


    final AdminRepository aRepo;
    final CustomerRepository cusRepo;
    final JavaMailSender mailSender;
    final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository aRepo, CustomerRepository cusRepo, JavaMailSender mailSender, PasswordEncoder passwordEncoder) {
        this.aRepo = aRepo;
        this.cusRepo = cusRepo;
        this.mailSender = mailSender;


        this.passwordEncoder = passwordEncoder;
    }


    public ResponseEntity<Map<REnum,Object>> list(){
        Map<REnum,Object> hm=new LinkedHashMap<>();
        hm.put(REnum.status,true);
        hm.put(REnum.result, aRepo.findAll());


        return new  ResponseEntity(hm, HttpStatus.OK);
    }
    public ResponseEntity<Map<String ,Object>> delete(Long  id ){
        Map<REnum,Object> hm = new LinkedHashMap<>();
        try {
            aRepo.deleteById(id);
            hm.put(REnum.status,true);
            return new  ResponseEntity(hm, HttpStatus.OK);
        }catch (Exception ex) {
            hm.put(REnum.status,false);
            hm.put(REnum.message, ex.getMessage());
            return new  ResponseEntity(hm, HttpStatus.BAD_REQUEST);
        }
    }

    public PasswordEncoder encoder(){
        return  new BCryptPasswordEncoder();
    }
    public ResponseEntity register(Admin admin) {
        Optional<Admin> optionalAdmin = aRepo.findByEmailEqualsIgnoreCase(admin.getEmail());
        Map<REnum, Object> hm = new LinkedHashMap<>();
        if (!optionalAdmin.isPresent()) {
            admin.setPassword(encoder().encode(admin.getPassword()));
            Admin a = aRepo.save(admin);
            hm.put(REnum.status, true);
            hm.put(REnum.result, a);
            return new ResponseEntity(hm, HttpStatus.OK);
        } else {
            hm.put(REnum.status, false);
            String message = "This e-mail address is registered!";
            hm.put(REnum.message, message);
            hm.put(REnum.result, jwt);
            return new ResponseEntity(hm, HttpStatus.NOT_ACCEPTABLE);
        }

    }
    public ResponseEntity changePassword(String oldPassword, String newPasword){
        Map<REnum,Object> hm=new LinkedHashMap<>();
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        Optional<Admin> optionalAdmin=aRepo.findByEmailEqualsIgnoreCase(userName);

        Admin admin=optionalAdmin.get();
        if (this.passwordEncoder.matches(oldPassword,admin.getPassword())){
            admin.setPassword(passwordEncoder.encode(newPasword));
            Admin upAdmin=aRepo.save(admin);
            hm.put(REnum.status,true);
            hm.put(REnum.result,upAdmin);
            return new ResponseEntity(hm,HttpStatus.OK);
        }else{
            hm.put(REnum.message,"Please Check Again Password ");
            hm.put(REnum.status,false);
            return new ResponseEntity(hm,HttpStatus.BAD_REQUEST);
        }

    }
    public ResponseEntity forgotPassword(String email) {
        Map<REnum, Object> hm = new LinkedHashMap();
        Optional<Admin> optionalAdmin = aRepo.findByEmailEqualsIgnoreCase(email);
        Admin admin = optionalAdmin.get();
        if (optionalAdmin.isPresent()) {
            UUID uuid = UUID.randomUUID();
            String verifyCode = uuid.toString();
            admin.setVerificationCode(uuid.toString());
            aRepo.save(admin);
            String resetPasswordLink = "http://localhost:8080/customer/resetPassword?resettoken=" + verifyCode;
            try {
                sendMessage("nazligencel82@gmail.com", "Password Reset Link", resetPasswordLink);
                hm.put(REnum.status, true);
                hm.put(REnum.result, resetPasswordLink);
                return new ResponseEntity<>(hm, HttpStatus.OK);
            } catch (Exception exception) {
                System.out.println("Mail Error" + exception);
                hm.put(REnum.status, false);
                hm.put(REnum.error, exception);
                return new ResponseEntity<>(hm, HttpStatus.BAD_REQUEST);
            }
        } else {
            hm.put(REnum.status, false);
            hm.put(REnum.status, "Invalid Customer");
            return new ResponseEntity<>(hm, HttpStatus.BAD_REQUEST);
        }

    }
    public void sendMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("nazligencel82@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);

    }

    public ResponseEntity setting( String firstName, String lastName, String email, String phone) {
        Map<REnum, Object> hm = new LinkedHashMap();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userName = auth.getName();
            System.out.println("username"+userName);
            Optional<Admin> optionalAdmin=aRepo.findByEmailEqualsIgnoreCase(userName);
            Optional<Admin> admin1 = aRepo.findByEmailEqualsIgnoreCase(email);
            if (optionalAdmin.isPresent()) {
                Admin oldAdmin = optionalAdmin.get();
                if ((oldAdmin.getEmail().equals(email)) || !admin1.isPresent()) {
                    System.out.println(oldAdmin.getEmail());
                    oldAdmin.setFirstName(firstName);
                    oldAdmin.setLastName(lastName);
                    oldAdmin.setEmail(email);
                    oldAdmin.setPhone(phone);
                    aRepo.saveAndFlush(oldAdmin);
                    hm.put(REnum.status, true);
                    hm.put(REnum.result, oldAdmin);
                    return new ResponseEntity<>(hm, HttpStatus.OK);
                } else {
                    hm.put(REnum.status, false);
                    hm.put(REnum.message, "This Email Already Registered");
                    return new ResponseEntity<>(hm, HttpStatus.BAD_REQUEST);
                }

            } else {
                hm.put(REnum.status, false);
                hm.put(REnum.message, "Invalid Customer");
                return new ResponseEntity<>(hm, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception exception) {
            hm.put(REnum.status, false);
            System.out.println(exception);
            return new ResponseEntity<>(hm, HttpStatus.BAD_REQUEST);
        }

    }

}

