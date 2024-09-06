package com.yelensoft.artishop_backend.mappers;

import com.yelensoft.artishop_backend.dto.CustomerCreatedDto;
import com.yelensoft.artishop_backend.entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
@AllArgsConstructor
public class MapperImpl implements Mapper{
    @Override
    public CustomerCreatedDto customerToCustomerCreated(Customer customer) {
        return CustomerCreatedDto.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .numTel(customer.getNumTel())
                .build();
    }
}
