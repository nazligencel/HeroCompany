package com.works.services;
import com.works.entities.Admin;
import com.works.entities.Customer;
import com.works.repositories.CustomerRepository;
import com.works.utils.REnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.works.utils.REnum.jwt;

@Service

public class CustomerService {
    final CustomerRepository cusRepo;
    final JavaMailSender mailSender;
    final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository cusRepo, JavaMailSender mailSender, PasswordEncoder passwordEncoder) {
        this.cusRepo = cusRepo;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<Map<REnum,Object>> list(){
        Map<REnum,Object> hm=new LinkedHashMap<>();
        hm.put(REnum.status,true);
        hm.put(REnum.result, cusRepo.findAll());


        return new  ResponseEntity(hm, HttpStatus.OK);
    }

    public ResponseEntity<Map<String ,Object>> delete(Long  id ){
        Map<REnum,Object> hm = new LinkedHashMap<>();
        try {
            cusRepo.deleteById(id);
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
    public ResponseEntity register(Customer customer){
        Optional<Customer> optionalCustomer=cusRepo.findByEmailEqualsIgnoreCase(customer.getEmail());
        Map<REnum,Object> hm=new LinkedHashMap<>();
        if (!optionalCustomer.isPresent()){
            customer.setPassword(encoder().encode(customer.getPassword()));
            Customer cus=cusRepo.save(customer);
            hm.put(REnum.status,true);
            hm.put(REnum.result,cus);
            return new ResponseEntity(hm, HttpStatus.OK);
        }else {
            hm.put(REnum.status,false);
            String message="This e-mail Address is Registered!";
            hm.put(REnum.message,message);
            hm.put(REnum.result,jwt);
            return new ResponseEntity(hm,HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public ResponseEntity setting( String firstName, String lastName, String email, String phone) {
        Map<REnum, Object> hm = new LinkedHashMap();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userName = auth.getName();
            System.out.println("username"+userName);
            Optional<Customer> optionalCustomer=cusRepo.findByEmailEqualsIgnoreCase(userName);
            Optional<Customer> customer = cusRepo.findByEmailEqualsIgnoreCase(email);
            if (optionalCustomer.isPresent()) {
                Customer oldCustomer = optionalCustomer.get();
                if ((oldCustomer.getEmail().equals(email)) || !customer.isPresent()) {
                    System.out.println(oldCustomer.getEmail());
                    oldCustomer.setCustomerName(firstName);
                    oldCustomer.setCustomerSurname(lastName);
                    oldCustomer.setEmail(email);
                    oldCustomer.setPhone(phone);
                    cusRepo.saveAndFlush(oldCustomer);
                    hm.put(REnum.status, true);
                    hm.put(REnum.result, oldCustomer);
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
    public ResponseEntity changePassword(String oldPassword, String newPasword){
        Map<REnum,Object> hm=new LinkedHashMap<>();
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        Optional<Customer> optionalCustomer=cusRepo.findByEmailEqualsIgnoreCase(userName);

        Customer customer=optionalCustomer.get();
        if (this.passwordEncoder.matches(oldPassword,customer.getPassword())){
            customer.setPassword(passwordEncoder.encode(newPasword));
            Customer customer1=cusRepo.save(customer);
            hm.put(REnum.status,true);
            hm.put(REnum.result,customer1);
            return new ResponseEntity(hm,HttpStatus.OK);
        }else{
            hm.put(REnum.message,"Please Check Again Password ");
            hm.put(REnum.status,false);
            return new ResponseEntity(hm,HttpStatus.BAD_REQUEST);
        }

    }
    public ResponseEntity forgotPassword(String email) {
        Map<REnum, Object> hm = new LinkedHashMap();
        Optional<Customer> optionalCustomer = cusRepo.findByEmailEqualsIgnoreCase(email);
        Customer customer = optionalCustomer.get();
        if (optionalCustomer.isPresent()) {
            UUID uuid = UUID.randomUUID();
            String verifyCode = uuid.toString();
            customer.setVerificationCode(uuid.toString());
            cusRepo.save(customer);
            String resetPasswordLink = "http://localhost:8080/customer/resetPassword?resettoken=" + verifyCode;
            try {
                sendMessage("nazligencel82@gmail.com", "Password Reset Link", resetPasswordLink);
                hm.put(REnum.status,true);
                hm.put(REnum.result, resetPasswordLink);
                return new ResponseEntity<>(hm, HttpStatus.OK);
            } catch (Exception exception) {
                System.out.println("Mail Error" + exception);
                hm.put(REnum.status, false);
                hm.put(REnum.error, exception);
                return new ResponseEntity<>(hm, HttpStatus.BAD_REQUEST);
            }
        } else {
            hm.put(REnum.status,false);
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

}
