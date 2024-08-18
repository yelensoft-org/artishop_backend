package com.yelensoft.artishop_backend.controllers;

import com.yelensoft.artishop_backend.services.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/listPerPage/{idUser}")
    public ResponseEntity<?> getAllStorePerPage(@PathVariable Long idUser, @RequestParam("page") int page, @RequestParam("size") int size){
        return storeService.getAllStorePerPage(idUser, page, size);
    }
}
