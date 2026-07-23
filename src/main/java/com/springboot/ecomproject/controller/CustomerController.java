package com.springboot.ecomproject.controller;

import com.springboot.ecomproject.dto.request.CustomerDto;
import com.springboot.ecomproject.dto.response.CustomerRespDto;
import com.springboot.ecomproject.model.Customer;
import com.springboot.ecomproject.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer/")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    /*
     Body: {
     "name" : "harry potter",
     "city" : "london"
     }
     */
    @PostMapping("/add")
    public Customer add(@Valid @RequestBody CustomerDto  dto){
        return customerService.add(dto);

    }

    @GetMapping("/get-all")
    public  List<CustomerRespDto> getAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                         @RequestParam(required = false, defaultValue = "20") Integer size){
        return customerService.getAll(page,size);

    }

    @GetMapping("/get-one/{id}")
    public CustomerRespDto getById(@PathVariable int id){
        return customerService.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    public  void delete(@PathVariable long id){
        customerService.delete(id);
    }
    @DeleteMapping("/delete-hard/{id}")
    public  void deleteHard(@PathVariable long id){
        customerService.deleteHard(id);
    }


    @PutMapping("update/{id}")
    public  void update(@PathVariable Long id,
                        @Valid @RequestBody CustomerDto customerDto){
        customerService.update(id,customerDto);

    }


}
