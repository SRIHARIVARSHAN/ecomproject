package com.springboot.ecomproject.service;

import com.springboot.ecomproject.dto.request.CustomerDto;
import com.springboot.ecomproject.dto.response.CustomerRespDto;
import com.springboot.ecomproject.enums.Role;
import com.springboot.ecomproject.exception.ResourceNotFoundException;
import com.springboot.ecomproject.mapper.CustomerMapper;
import com.springboot.ecomproject.model.Customer;
import com.springboot.ecomproject.model.User;
import com.springboot.ecomproject.repository.CustomerRepository;
import com.springboot.ecomproject.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;


import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private  CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private Customer customer1;
    private User user1;

    private Customer customer2;
    private User user2;

    private Customer customer3;
    private User user3;

    @BeforeEach
    public void init(){
        user1 = new User(1L,"john@gmail.com", "john@123", Role.CUSTOMER,true);
        customer1 = new Customer(1L,"John Doe","hubli",true, user1);

        user2 = new User(2L,"jane@gmail.com", "jane@123", Role.CUSTOMER,true);
        customer2 = new Customer(2L,"Jane Doe","Mysore",true, user2);

        user3 = new User(3L,"jack@gmail.com", "jack@123", Role.CUSTOMER,true);
        customer3 = new Customer(3L,"Jack Doe","Ooty",true, user3);
    }

    @Test
    public void getByIdTestPresent(){
        when(customerRepository.findById(5L)).thenReturn(Optional.of(customer1));
        CustomerRespDto customerRespDto= CustomerMapper.mapEntityToDto(customer1);

        Assertions.assertEquals(customerRespDto , customerService.getById(5));

        verify(customerRepository, times(1)).findById(5L);
    }


    @Test
    public void getByIdTestNotPresent(){
        when(customerRepository.findById(10L)).thenReturn(Optional.empty());

        Assertions.assertEquals("Customer id is invalid.",
                Assertions.assertThrows(ResourceNotFoundException.class,()->{
                    customerService.getById(10);

                }).getMessage());
    }

    @Test
    public void getAllTest(){
        int page = 0;
        int size=2;
        Pageable pageable1 =  PageRequest.of(page,size);

        Page<Customer> pageCustomer = new PageImpl<>(List.of(customer1,customer2));
        when(customerRepository.findAll(pageable1)).thenReturn(pageCustomer);

        size=3;
        Pageable pageable2 =  PageRequest.of(page,size);
        pageCustomer = new PageImpl<>(List.of(customer1,customer2,customer3));

        when(customerRepository.findAll(pageable2)).thenReturn(pageCustomer);

        Assertions.assertEquals(2 , customerService.getAll(0,2).size());
        Assertions.assertEquals(3 , customerService.getAll(0,3).size());
        Assertions.assertThrows(RuntimeException.class, ()-> customerService.getAll(0,0));

        verify(customerRepository , times(1)).findAll(pageable1);
        verify(customerRepository , times(1)).findAll(pageable2);
    }

    @Test
    public void updateTest(){
        when(customerRepository.findById(10L)).thenReturn(Optional.of(customer1));

        CustomerDto customerDto = new CustomerDto(
                "John J. Doe",
                "London",
                "",
                ""
        );
        // I am making an actual call, this must ensure that , fetchById and save method both get called exactly ONCE
        customerService.update(10L , customerDto);

        verify(customerRepository, times(1)).findById(10L);
        verify(customerRepository, times(1)).save(customer1);

    }

    @Test
    public void updateTestForInvalidCustomerId() {
// Check for invalid customer id
        when(customerRepository.findById(11L)).thenReturn(Optional.empty());
        CustomerDto customerDto = new CustomerDto(
                "John J. Doe",
                "London",
                "",
                ""
        );
        Assertions.assertEquals("Customer id Invalid",
                Assertions.assertThrows(ResourceNotFoundException.class,
                                ()->customerService.update(11L , customerDto) )
                        .getMessage()
        );

        verify(customerRepository, times(1)).findById(11L);
        verify(customerRepository, times(0)).save(customer1);
    }

    @Test
    public void addTest(){
        // Prepare the user the will be given back after userRepository.save method
        // do note: save(any(Customer.class)) this returns our prepared user 'user1' regardless of what we pass.
        user1.setPassword("encodedPass");
        when(userRepository.save(any(User.class))).thenReturn(user1);
        // Any class of Customer that i ask you to save , u must save and give me 'customer1'
        when(customerRepository.save(any(Customer.class))).thenReturn(customer1);
        // when the actual method 'customerService.add(customerDto)' will run, and it will encounter
        // passwordEncoder.encode("john@123") then , tell it to return 'encodedPass' for testing purpose
        when(passwordEncoder.encode("john@123")).thenReturn("encodedPass");

        // Preparing my Dto
        CustomerDto customerDto = new CustomerDto(
                "John J. Doe",
                "London",
                "john@gmail.com",
                "john@123"
        );

        // make the call to actual method
        customerService.add(customerDto);

        // Define the captor to give it to the repository call
        ArgumentCaptor<Customer> customerCaptor =  ArgumentCaptor.forClass(Customer.class);
        // the response of this save method will get captured in customerCaptor
        // This captor gives the saved object details , so we could check if saved info is equal to given info
        // given info comes from dto
        // saved info comes from captor

        verify(customerRepository, times(1)).save(customerCaptor.capture());

        // If the save method works fine, then we must have the name, city fields of captor (customer1)
        // equal to that to dto that we have passed.
        Assertions.assertEquals(customerDto.name(),customerCaptor.getValue().getName() );
        Assertions.assertEquals(customerDto.city(),customerCaptor.getValue().getCity() );

    }

    @Test
    public void deleteHardTest(){
        // Prepare and Mock
        when(customerRepository.findById(10L)).thenReturn(Optional.of(customer1));
        doNothing().when(customerRepository).deleteById(10L);

        // Actual Call
        customerService.deleteHard(10L);

        // Verify
        verify(customerRepository, times(1)).deleteById(10L);
    }

    @Test
    public void deleteHardTestNotFound(){
        // Prepare for Exception
        when(customerRepository.findById(11L)).thenReturn(Optional.empty());

        // Actual Call with ResourceNotFoundException
        Assertions.assertEquals("Customer id is invalid.",
                Assertions.assertThrows(ResourceNotFoundException.class, ()-> customerService.deleteHard(11L))
                        .getMessage());

        // verify : that this method is never getting called...
        verify(customerRepository, never()).deleteById(anyLong());
    }
}
