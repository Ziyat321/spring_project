package kz.runtime.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.authorizeHttpRequests(authorizationConfigurer -> {
//           authorizationConfigurer.requestMatchers("/category_choice").authenticated();
//           authorizationConfigurer.requestMatchers("/category_choice").hasRole("USER");
            authorizationConfigurer.requestMatchers("/category_choice").hasRole("ADMIN"); // создать товар
            authorizationConfigurer.requestMatchers("/create_product").hasRole("ADMIN"); // создать товар
            authorizationConfigurer.requestMatchers("/products/cart/**").authenticated(); // корзина
            authorizationConfigurer.requestMatchers("/products/show_order").authenticated(); // содержимое заказа
            authorizationConfigurer.requestMatchers("/products/change").hasRole("ADMIN"); // редактировать товар
            authorizationConfigurer.requestMatchers("/products/add_to_cart").authenticated(); // добавить в корзину

           authorizationConfigurer.anyRequest().permitAll();
        });

        httpSecurity.formLogin(formLoginConfigurer -> {
            formLoginConfigurer.defaultSuccessUrl("/products");
        });

        httpSecurity.formLogin(formLoginConfigurer ->{

        });

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }
}
