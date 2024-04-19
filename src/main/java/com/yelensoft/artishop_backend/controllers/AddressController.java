package com.yelensoft.artishop_backend.controllers;

import com.yelensoft.artishop_backend.configuration.ResponseHandler;
import com.yelensoft.artishop_backend.entities.Address;
import com.yelensoft.artishop_backend.services.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/create")
    public ResponseEntity<?> createAddress(@Valid @RequestBody Address address){
        return ResponseHandler.generateResponse("succes", HttpStatus.OK, addressService.addAddress(address));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAddress(@Valid @RequestBody Address address){
        return ResponseHandler.generateResponse("succes", HttpStatus.OK, addressService.updateAddress(address));
    }

    @DeleteMapping("delete/{id}")
    public void deleteAddressById(@PathVariable Long id){
        addressService.deleteAddress(id);
    }
}
