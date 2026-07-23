package com.springboot.ecomproject.service;



import com.springboot.ecomproject.dto.request.AdminDto;
import com.springboot.ecomproject.enums.Role;
import com.springboot.ecomproject.exception.InvalidCredentialsException;
import com.springboot.ecomproject.model.User;
import com.springboot.ecomproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void addAdmin(AdminDto adminDto) {
        // Prepare the user
        User user = new User();
        user.setUsername(adminDto.username());
        user.setPassword(passwordEncoder.encode(adminDto.password()));
        user.setRole(Role.ADMIN);
        user.setActivated(true);

        // save user in DB
        userRepository.save(user);
    }


    public User getUserDetils(String loggedInUsername) {
        return  userRepository.loadUserByUsername(loggedInUsername)
                .orElseThrow(()-> new InvalidCredentialsException("Login Denied"));
    }
}