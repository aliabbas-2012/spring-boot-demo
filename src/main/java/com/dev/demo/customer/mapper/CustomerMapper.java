package com.dev.demo.customer.mapper;
import com.dev.demo.customer.dto.CustomerRequest;
import com.dev.demo.customer.dto.CustomerResponse;
import com.dev.demo.customer.models.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    Customer mapToCustomer(CustomerRequest request);

    CustomerResponse mapToCustomerResponse(Customer customer);

}
