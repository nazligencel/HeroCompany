package com.works.services;
import com.works.configs.JwtUtil;
import com.works.entities.Admin;
import com.works.entities.Customer;
import com.works.entities.Login;
import com.works.entities.Role;
import com.works.repositories.AdminRepository;
import com.works.repositories.CustomerRepository;
import com.works.utils.REnum;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;


@Service

public class LoginUserDetailService implements UserDetailsService {

    final AdminRepository adminRepository;
    final CustomerRepository customerRepository;
    final AuthenticationManager authenticationManager;
    final JwtUtil jwtUtil;
    final HttpSession session;


    public LoginUserDetailService(AdminRepository adminRepository, CustomerRepository customerRepository, @Lazy AuthenticationManager authenticationManager,
                                  JwtUtil jwtUtil, HttpSession session) {
        this.adminRepository = adminRepository;
        this.customerRepository = customerRepository;
        this.authenticationManager = authenticationManager;

        this.jwtUtil = jwtUtil;
        this.session = session;
    }


    public PasswordEncoder encoder(){
        return  new BCryptPasswordEncoder();
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> optionalAdmin=adminRepository.findByEmailEqualsIgnoreCase(username);
        Optional<Customer> optionalCustomer=customerRepository.findByEmailEqualsIgnoreCase(username);
        if (optionalAdmin.isPresent()){
            Admin a=optionalAdmin.get();

            UserDetails userDetails=new org.springframework.security.core.userdetails.User(
                    a.getEmail(),
                    a.getPassword(),
                    a.isEnabled(),
                    a.isTokenExpired(),
                    true,
                    true,
                    role(a.getRoles())
            );
            //session set attribute buraya
            return userDetails;
        }else if ( optionalCustomer.isPresent()){
            Customer c=optionalCustomer.get();

            UserDetails userDetails=new org.springframework.security.core.userdetails.User(
                   c.getEmail(),
                   c.getPassword(),
                   c.isEnabled(),
                   c.isTokenExpired(),
                   true,
                   true,
                   role(c.getRole())
            );
            session.setAttribute("customer",c);
            return userDetails;
        }else {
            throw new UsernameNotFoundException("No Such User");
        }

    }

    public Collection role(Role role ) {
        List<GrantedAuthority> ls = new ArrayList<>();

            ls.add( new SimpleGrantedAuthority( role.getName() ));

        return ls;
    }

    public ResponseEntity auth(Login login){
        Map<REnum,Object> hm=new LinkedHashMap<>();
        try {
            System.out.println("LOGIN"+login.getUsername());
            System.out.println("login.getusername"+login.getPassword());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    login.getUsername(),login.getPassword()

            ));

            System.out.println("login.getusername"+login.getUsername());
            UserDetails userDetails=loadUserByUsername(login.getUsername());
            System.out.println("login"+userDetails.getUsername()+userDetails.getPassword());
         String jwt=jwtUtil.generateToken(userDetails);
            hm.put(REnum.status,true);
            hm.put(REnum.jwt,jwt);
            return new ResponseEntity<>(hm,HttpStatus.OK);
        }
        catch (Exception e){
            hm.put(REnum.status,false);
            hm.put(REnum.error,e.getMessage());
            return new ResponseEntity(hm,HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
