package com.yelensoft.artishop_backend.services;

import com.yelensoft.artishop_backend.entities.Address;
import com.yelensoft.artishop_backend.repositories.AddressRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Address addAddress(Address address){
        /*Optional<Address> addressVerif = addressRepository.findById(address.getId());
        if (addressVerif.isPresent()) throw new EntityExistsException("exist");*/
        return addressRepository.save(address);
    }

    public Address updateAddress(Address address){
        Optional<Address> addressVerif = addressRepository.findById(address.getId());
        if (addressVerif.isEmpty()) throw new EntityExistsException("invalid address");
        return addressRepository.save(address);
    }

    public void deleteAddress(Long id){
        Optional<Address> addressVerif = addressRepository.findById(id);
        if (addressVerif.isEmpty()) throw new EntityExistsException("invalid address");
        addressRepository.deleteById(id);
    }
}
