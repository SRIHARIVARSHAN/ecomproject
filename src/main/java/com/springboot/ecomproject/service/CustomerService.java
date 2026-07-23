package com.springboot.ecomproject.service;

import com.springboot.ecomproject.dto.request.CustomerDto;
import com.springboot.ecomproject.dto.response.CustomerRespDto;
import com.springboot.ecomproject.enums.Role;
import com.springboot.ecomproject.exception.ResourceNotFoundException;
import com.springboot.ecomproject.mapper.CustomerMapper;
import com.springboot.ecomproject.mapper.UserMapper;
import com.springboot.ecomproject.model.Customer;
import com.springboot.ecomproject.model.User;
import com.springboot.ecomproject.repository.CustomerRepository;
import com.springboot.ecomproject.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public Customer add(CustomerDto customerDto) {
        //fetch user entity from dto
        User user = UserMapper.convertDtoToEntity(customerDto.username(), customerDto.password(), Role.CUSTOMER);
        //encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // save user in db
        user =userRepository.save(user);
        //convert dto to entity
        Customer customer = CustomerMapper.mapDtoToEntity(customerDto);

        // Attach user to customer
        customer.setUser(user);

        return customerRepository.save(customer);

    }

    public List<CustomerRespDto> getAll(int page, int size) {
        if(size == 0)
            throw new RuntimeException("Size has to be more than 0");

        Pageable pageable= PageRequest.of(page,size);
        List<Customer> list=customerRepository.findAll(pageable).getContent();
        return list
                .stream()
                .map(CustomerMapper::mapEntityToDto)
                .toList();
    }

    public CustomerRespDto getById(long id) {
        Customer customer=customerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Customer id is invalid."));
        return  customerMapper.mapEntityToDto(customer);

    }

    public void delete(long id) {
        Customer customer=customerRepository.fetchById(id).orElseThrow(()-> new ResourceNotFoundException("Customer id is invalid."));
        customer.setActive(false);
        customerRepository.save(customer);
    }

    public void deleteHard(long id) {
        Customer customer=customerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Customer id is invalid."));
        customerRepository.deleteById(id);

    }

    public void update(Long id, @Valid CustomerDto customerDto) {
        Customer customerDB = customerRepository.findById(id)  // This customerDb comes form the database having id.
                .orElseThrow(()-> new ResourceNotFoundException("Customer id Invalid"));

        customerDB.setName(customerDto.name());
        customerDB.setCity(customerDto.city());

        customerRepository.save(customerDB);
    }
}
