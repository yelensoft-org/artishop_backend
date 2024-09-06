package com.yelensoft.artishop_backend.mappers;

import com.yelensoft.artishop_backend.dto.CustomerCreated;
import com.yelensoft.artishop_backend.entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
@AllArgsConstructor
public class MapperImpl implements Mapper{
    @Override
    public CustomerCreated customerToCustomerCreated(Customer customer) {
        return CustomerCreated.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .numTel(customer.getNumTel())
                .build();
    }
}
