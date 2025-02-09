package com.coder.rental.security;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

@Configuration
@Data
public class PasswordConfig {

    @Value("${encoder.ctype.strength}")
    private int strength;

    @Value("${encoder.ctype.secret}")
    private String secret;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        SecureRandom secureRandom=new SecureRandom(secret.getBytes());
        return new BCryptPasswordEncoder(strength,secureRandom);
    }

//    public static void main(String[] args) {
//        BCryptPasswordEncoder passwordEncoder=new PasswordConfig().passwordEncoder();
//        System.out.println(passwordEncoder.encode("a"));
//
//        System.out.println(passwordEncoder.matches("admin","$2a$06$0c/ndr5FZ87vzmlIVJdUd.UyQKp296WpR60wwywVi1gYHrvSiyAZW"));
//    }
}