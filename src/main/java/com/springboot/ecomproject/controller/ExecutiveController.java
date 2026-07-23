package com.springboot.ecomproject.controller;

import com.springboot.ecomproject.dto.request.ExecutiveReqDto;
import com.springboot.ecomproject.dto.response.ExecutiveRespDto;
import com.springboot.ecomproject.enums.JobTitle;
import com.springboot.ecomproject.service.ExecutiveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/executive")
@RequiredArgsConstructor
public class ExecutiveController {

    private final ExecutiveService executiveService;

//    Executive with User
//    Body:
//    {
//        name : "",
//                jobTitle : "",
//            username : "",
//            password: ""
//    }

    @PostMapping("/add")
    public  void insert(@Valid @RequestBody ExecutiveReqDto executiveReqDto){
        executiveService.insert(executiveReqDto);
    }

    @GetMapping("/by-jobTitle")
    public List<ExecutiveRespDto> getByJobTitle(@RequestParam JobTitle jobTitle){
        return executiveService.getByJobTitle(jobTitle);
    }

}
