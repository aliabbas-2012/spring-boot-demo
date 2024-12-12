package com.dev.demo.customer;

import com.dev.demo.customer.dto.CustomerResponse;

public interface CustomerAPI {
    CustomerResponse findCustomerById(Long id);
}