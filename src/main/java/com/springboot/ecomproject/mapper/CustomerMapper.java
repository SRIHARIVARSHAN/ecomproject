package com.springboot.ecomproject.mapper;

import com.springboot.ecomproject.dto.request.CustomerDto;
import com.springboot.ecomproject.dto.response.CustomerRespDto;
import com.springboot.ecomproject.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public static Customer mapDtoToEntity(CustomerDto dto) {
        Customer customer= new Customer();
        customer.setName(dto.name());
        customer.setCity(dto.city());
        return  customer;
    }

    public static CustomerRespDto mapEntityToDto(Customer customer) {
        CustomerRespDto dto=new CustomerRespDto(
                customer.getName(),
                customer.getCity()
        );
        return dto;
    }


}
