package com.yelensoft.artishop_backend.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yelensoft.artishop_backend.Service.Store_service;
import com.yelensoft.artishop_backend.entities.Store;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/store")
public class Store_controller {



    @Autowired
    private Store_service storeService;

    @PostMapping("/add/{id}")
    public ResponseEntity<Store> createStore(@RequestParam("store") String storeString,
                                             @PathVariable Long id, @RequestParam("file") MultipartFile multipartFile) {
        try {
            // Convertir la chaîne JSON en objet Store
            ObjectMapper objectMapper = new ObjectMapper();
            Store store = objectMapper.readValue(storeString, Store.class);
            log.info("Store JSON converted: {}", store);

            // Appeler la méthode de création en utilisant les paramètres fournis
            Store createdStore = storeService.create(id, store, multipartFile);

            // Retourner une réponse avec le statut OK et l'objet Store créé
            return ResponseEntity.ok(createdStore);
        } catch (IOException e) {
            log.error("Error converting store JSON: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (RuntimeException e) {
            log.error("Error creating store: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

//    ----------------------------------------------------------------------------------------------
@PutMapping("/update/{id}")
public ResponseEntity<Store> updateStore(@RequestParam("store") String storeString,
                                         @PathVariable Long id, @RequestParam("file") MultipartFile multipartFile) {
    try {
        // Convertir la chaîne JSON en objet Store
        ObjectMapper objectMapper = new ObjectMapper();
        Store store = objectMapper.readValue(storeString, Store.class);
        log.info("Store JSON converted: {}", store);

        // Appeler la méthode de mise à jour en utilisant les paramètres fournis
        Store updatedStore = storeService.update(id, store, multipartFile);

        // Retourner une réponse avec le statut OK et l'objet Store mis à jour
        return ResponseEntity.ok(updatedStore);
    } catch (IOException e) {
        log.error("Error converting store JSON: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    } catch (RuntimeException e) {
        log.error("Error updating store: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    } catch (Exception e) {
        log.error("Unexpected error: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}



    //    ----------------------------------------------------------------------------------------------------
    @GetMapping("/list")
    public ResponseEntity<List<Store>> fetchAll(){
        try {
            List<Store> storeList = storeService.readStoreAll();
            return ResponseEntity.ok().body(storeList);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
//----------------------------------------------------------------------------------------------------------
    @GetMapping("/{nom}")
    public ResponseEntity<Store> fetchById(@PathVariable String nom){
        try {
            Store store = storeService.readStore(nom);
            return ResponseEntity.ok().body(store);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//    ---------------------------------------------------------------------------------------------------
    @PutMapping("/desable/{idStore}")
    public ResponseEntity<String> desableStore(@PathVariable Long idStore){
        try {
           String  store = storeService.desableStore(idStore);
            return ResponseEntity.ok().body(store);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//    --------------------------------------------------------------------------------------------------
    @GetMapping("/liker/{starNumber}/{idStore}")
    public ResponseEntity<Double> likeStore(@PathVariable int starNumber, @PathVariable Long idStore){
        try {
           double numberVote =  storeService.likeStore(starNumber,idStore);
            return  ResponseEntity.ok().body(numberVote);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//
}
