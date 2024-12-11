package com.dev.demo.customer.service;
import com.dev.demo.customer.CustomerAPI;
import com.dev.demo.customer.dto.CustomerRequest;
import com.dev.demo.customer.mapper.CustomerMapper;
import com.dev.demo.customer.respositery.CustomerRepository;
import com.dev.demo.customer.models.Customer;
import com.dev.demo.customer.dto.CustomerResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService implements CustomerAPI {
    private final CustomerRepository repository;

    public CustomerResponse saveCustomer(CustomerRequest request) {
        Customer customer = CustomerMapper.INSTANCE.mapToCustomer(request);
        return CustomerMapper.INSTANCE.mapToCustomerResponse(repository.save(customer));
    }

    @Override
    public CustomerResponse findCustomerById(Long id) {
        Optional<Customer> customerOpt = repository.findById(id);
        if (customerOpt.isPresent()) {
            return CustomerMapper.INSTANCE.mapToCustomerResponse(customerOpt.get());
        } else throw new EntityNotFoundException("Couldn't find customer with id " + id);
    }
}