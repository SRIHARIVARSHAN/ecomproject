package com.springboot.ecomproject.service;

import com.springboot.ecomproject.dto.request.ExecutiveReqDto;
import com.springboot.ecomproject.dto.response.ExecutiveRespDto;
import com.springboot.ecomproject.enums.JobTitle;
import com.springboot.ecomproject.enums.Role;
import com.springboot.ecomproject.mapper.ExecutiveMapper;
import com.springboot.ecomproject.mapper.UserMapper;
import com.springboot.ecomproject.model.Executive;
import com.springboot.ecomproject.model.User;
import com.springboot.ecomproject.repository.ExecutiveRepository;
import com.springboot.ecomproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExecutiveService {

    private final ExecutiveRepository executiveRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void insert(ExecutiveReqDto executiveReqDto) {

//        String username=executiveReqDto.username();
//        String password = executiveReqDto.password();
//        Role role=Role.EXECUTIVE;


        User user = UserMapper.convertDtoToEntity(executiveReqDto.username(), executiveReqDto.password(),Role.EXECUTIVE);

//        user.setUsername(username);
//        user.setPassword(password);
//        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user); // this user has an id

//        String name = executiveReqDto.name();
//        JobTitle jobTitle = executiveReqDto.jobTitle();
//        Executive executive = new Executive();
//        executive.setName(name);
//        executive.setJobTitle(jobTitle);
        Executive executive = ExecutiveMapper.convertDtoToEntity(executiveReqDto);
        executive.setUser(user);
        executiveRepository.save(executive);
    }

    public List<ExecutiveRespDto> getByJobTitle(JobTitle jobTitle) {
             List<Executive> list=executiveRepository.findByJobTitle(jobTitle);
             return list.stream()
                     .map(ExecutiveMapper::convertEntityToDto)
                     .toList();
    }
}
