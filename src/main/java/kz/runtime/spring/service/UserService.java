package kz.runtime.spring.service;

import kz.runtime.spring.entity.User;
import kz.runtime.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final long CURRENT_USER_ID = 1;

    @Autowired
    private UserRepository userRepository;

    public User getCurrentUser(){
        return userRepository.findById(CURRENT_USER_ID).orElseThrow();
    }
}
