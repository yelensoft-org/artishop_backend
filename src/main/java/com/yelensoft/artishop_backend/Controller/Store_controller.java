package com.yelensoft.artishop_backend.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yelensoft.artishop_backend.Service.Store_service;
import com.yelensoft.artishop_backend.model.Store;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
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



    //modif
    @PostMapping(value = "/add1/{id}")
    public ResponseEntity<Store> createStore1(@RequestPart("store") Store store,
                                              @PathVariable Long id,
                                              @RequestParam("file") MultipartFile multipartFile) {
        try {
            System.out.println("photo :" + multipartFile.getOriginalFilename());
            // Appeler la méthode de création en utilisant les paramètres fournis
            Store createdStore = storeService.create(id, store, multipartFile);

            // Retourner une réponse avec le statut OK et l'objet Store créé
            return ResponseEntity.ok().body(createdStore);
        } catch (Exception e) {
            // En cas d'erreur, retourner une réponse avec le statut d'erreur approprié et un message d'erreur
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
    @GetMapping("/{idArtisan}")
    public ResponseEntity<Store> fetchById(@PathVariable Long idArtisan){
        try {
            Store store = storeService.readStore(idArtisan);
            return ResponseEntity.ok().body(store);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//    ---------------------------------------------------------------------------------------------------
    @GetMapping("/desable/{idStore}")
    public ResponseEntity<String> desableStore(@PathVariable Long idStore){
        try {
           String  store = storeService.desableStore(idStore);
            return ResponseEntity.ok().body(store);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//    --------------------------------------------------------------------------------------------------
    @GetMapping("/liker/{idStore}")
    public ResponseEntity<Double> likeStore(@RequestParam int starNumber, @PathVariable Long idStore){
        try {
             storeService.likeStore(starNumber,idStore);
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//    --------------------------------------------------------------------------------------------------
    @PutMapping("/update/{idStore}")
    public ResponseEntity<Store> update(@PathVariable Long idStore){
        try {
            Store  store = storeService.update(idStore);
            return ResponseEntity.ok().body(store);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
