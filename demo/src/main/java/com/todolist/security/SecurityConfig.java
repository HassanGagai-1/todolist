//package com.todolist.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
//// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//// import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public Argon2PasswordEncoder passwordEncoder() {
//        // saltLength, hashLength, parallelism, memory, iterations
//        return new Argon2PasswordEncoder(
//                /* saltLength */   16,
//                /* hashLength */   32,
//                /* parallelism */  1,
//                /* memory */       65536,
//                /* iterations */   3
//        );
//    }
//
//    // If you prefer BCrypt instead, comment out the above and use:
//    //
//    // @Bean
//    // public PasswordEncoder passwordEncoder() {
//    //   return new BCryptPasswordEncoder(10);
//    // }
//}
//
