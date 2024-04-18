package com.yelensoft.artishop_backend.service;

import com.yelensoft.artishop_backend.model.Address;
import com.yelensoft.artishop_backend.repository.AddressRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Address addAddress(Address address){
        Optional<Address> addressVerif = addressRepository.findById(address.getId());
        if (addressVerif.isPresent()) throw new EntityExistsException("exist");
        return addressRepository.save(address);
    }
}
