package com.yelensoft.artishop_backend.mappers;

import com.yelensoft.artishop_backend.dto.CustomerCreated;
import com.yelensoft.artishop_backend.entities.Customer;

public interface Mapper {

    CustomerCreated customerToCustomerCreated(Customer customer);
}
