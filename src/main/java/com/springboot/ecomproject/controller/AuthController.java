package com.springboot.ecomproject.controller;



import com.springboot.ecomproject.dto.request.AdminDto;
import com.springboot.ecomproject.dto.response.TokenDto;
import com.springboot.ecomproject.model.User;
import com.springboot.ecomproject.service.UserService;
import com.springboot.ecomproject.utility.JwtUtility;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtility jwtUtility;
    private Logger logger =  LoggerFactory.getLogger("AuthController.class");

    @PostMapping("/add/admin")
    public void addAdmin(@RequestBody AdminDto adminDto){
        userService.addAdmin(adminDto);
    }

    @GetMapping("/login")
    public TokenDto login(Principal principal) {
        String loggedInUsername =principal.getName();

        String token= jwtUtility.generateToken(loggedInUsername);

        logger.info("Token Generated {}", token );
        User user=userService.getUserDetils(loggedInUsername);

        logger.info("User Details fetched from DB having role: {}", user.getRole() );
        logger.info("Token Expiry {}", jwtUtility.extractExpiration(token).toString());

        return  new TokenDto(
                token,
                jwtUtility.extractExpiration(token).toString(),
                user.getRole().toString()
        );
    }
}