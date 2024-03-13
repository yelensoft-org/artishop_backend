package com.yelensoft.artishop_backend.Controller;

import com.yelensoft.artishop_backend.Service.Store_service;
import com.yelensoft.artishop_backend.model.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/store")
public class Store_controller {
    @Autowired
    private Store_service storeService;

//    @PostMapping("/add")
//    public ResponseEntity<Store> createStore()
}
