package kz.runtime.spring.service;

import kz.runtime.spring.entity.User;
import kz.runtime.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static final long CURRENT_USER_ID = 1;

    @Autowired
    private UserRepository userRepository;

    public User getCurrentUser(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String login = authentication.getName();
        return userRepository.findUserByLogin(login).orElse(null);
    }
}
