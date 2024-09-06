package com.yelensoft.artishop_backend.mappers;

import com.yelensoft.artishop_backend.dto.CustomerCreatedDto;
import com.yelensoft.artishop_backend.entities.Customer;

public interface Mapper {

    CustomerCreatedDto customerToCustomerCreated(Customer customer);
}
