package com.springboot.ecomproject.mapper;

import com.springboot.ecomproject.enums.Role;
import com.springboot.ecomproject.model.User;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {
    public static User convertDtoToEntity(String username, String password,Role role){
        User user=new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);

        return user;

    }
}
