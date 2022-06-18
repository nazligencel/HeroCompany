package com.works.configs;
import com.works.services.LoginUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    final JwtFilter jwtFilter;
    final LoginUserDetailService loginUserDetailService;

    public SecurityConfig(JwtFilter jwtFilter, LoginUserDetailService loginUserDetailService) {
        this.jwtFilter = jwtFilter;

        this.loginUserDetailService = loginUserDetailService;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

       http
               .authorizeHttpRequests()
                .antMatchers("/admin/register","login","customer/register").permitAll()
              .antMatchers("/customer/settings").hasRole("customer")
              .antMatchers("/admin/settings").hasRole("admin")
               .antMatchers("/order/add","/basket/save","/customer/changePassword","/customer/resetPassword","/customer/forgotPassword").hasRole("customer")
               .antMatchers("/admin/changePassword","/admin/resetPassword","/admin/forgotPassword").hasRole("admin")
                .antMatchers("/categories/save","/categories/delete","/categories/update","/customer/delete").hasRole("admin")
                .antMatchers("/product/save","/product/delete","/product/update","/customer/delete").hasRole("admin")
                .antMatchers("/categories/list","/product/list").hasAnyRole("admin","customer")

                .and()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
         http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(loginUserDetailService).passwordEncoder(loginUserDetailService.encoder());
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}
