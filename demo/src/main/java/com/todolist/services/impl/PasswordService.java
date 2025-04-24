package com.todolist.services.impl;

import com.todolist.controller.config.PasswordConfig;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    private final PasswordConfig passwordConfig;


    public PasswordService(PasswordConfig passwordConfig) {
        this.passwordConfig = passwordConfig;
    }


//    public String securePassword(String password) {
//        Argon2 argon2 = getArgon2Instance();
//
//        return argon2.hash(passwordConfig.getIterations(), passwordConfig.getMemory(), passwordConfig.getParallelism() , password.toCharArray());
//
//    }

    private Argon2 getArgon2Instance() {
        Argon2Factory.Argon2Types type = Argon2Factory.Argon2Types.ARGON2d;
        if(passwordConfig.getType() == 1 ) {
            type = Argon2Factory.Argon2Types.ARGON2i;
        }
        else if(passwordConfig.getType() == 2 ) {
            type = Argon2Factory.Argon2Types.ARGON2id;
        }
        return Argon2Factory.create(type, passwordConfig.getSaltLength(), passwordConfig.getHashLength());
    }
}
