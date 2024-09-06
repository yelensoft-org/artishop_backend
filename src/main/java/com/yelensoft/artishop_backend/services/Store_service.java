package com.yelensoft.artishop_backend.services;

import com.yelensoft.artishop_backend.entities.Store;
import com.yelensoft.artishop_backend.entities.Customer;
import com.yelensoft.artishop_backend.repositories.CustomerRepository;
import com.yelensoft.artishop_backend.repositories.StoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class Store_service {
    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private FileService fileService;


    //    -----------------------------------------------------------------------------------------------
    //    methode pour creer une botique en fonction d'un artisan donner start
    public Store create(long id, Store store, MultipartFile multipartFile) throws Exception {
        try {
            Optional<Customer> artisantExist = customerRepository.findById(id);
            /*if (artisantExist == null || !artisantExist.getRole().equals(PersonRole.ARTISANT)) {
                throw new RuntimeException("Utilisateur inexistant ou non autorisé.");
            }*/

            String filePath = fileService.saveFile(multipartFile);
            // Convertir le chemin de fichier en URL
            String fileUrl = "http://localhost/artImage/" + Paths.get(filePath).getFileName().toString();

            System.out.println(filePath + "----------------------------------------");
            store.setImageUrl(fileUrl);
//            store.setUpdateDate(LocalDate.now());

            Optional<Store> storeExist = storeRepository.findByEmailAndName(store.getEmail(), store.getName());
            if (storeExist != null) {
                throw new RuntimeException("Le nom ou l'adresse e-mail de cette boutique existe déjà.");
            }

            storeRepository.save(store);
            return store;
        } catch (RuntimeException e) {
            log.error("Runtime error: {}", e.getMessage(), e);
            throw new Exception("Une erreur est survenue lors de la création de la boutique.", e);
        }
    }

    //    ------------------------------------------------------------------------------------------------
//    methode pour appeler tout les store(boutique)
    public List<Store> readStoreAll(){
        List<Store> storeList = storeRepository.findAll();
        if (storeList.isEmpty()){
            throw new RuntimeException("Il exist auccune boutique");
        }else {
            return storeList;
        }
    }
//----------------------------------------------------------------------------------------------
//    mehode pour appeler une store(boutique) en fonction du nom de la boutique
    public Store readStore(String nomStore){
        return storeRepository.findByName(nomStore).orElse(new Store());

    }
//    ______________________________________________________________________________________________

//  desactiver une boutique
    public String desableStore(Long id){
        Optional<Store> storeExist = storeRepository.findById(id);
        if (storeExist.isPresent()){
            storeExist.get().setDeleted(!storeExist.get().isDeleted());
            storeRepository.save(storeExist.get());

        }
        return "Disable successffly!";
    }

//------------------------------------------------------------------------------------------------------------

//    mehode pour permettre a un user de voter
    public double likeStore(int starVote, Long idStore) {
        Optional<Store> storeExist = storeRepository.findById(idStore);

        if (storeExist.isPresent()) {
            double totalVote = storeExist.get().getTotalValueVote() + starVote;
            double nbreVote = storeExist.get().getNbreVote() + 1;

            storeExist.get().setNbreVote(nbreVote);
            storeExist.get().setTotalValueVote(totalVote);

            if (nbreVote > 0) {
                double averageVote =  totalVote / nbreVote; // Calculez la moyenne

                // Arrondissez à un chiffre après la virgule
                averageVote = Math.round(averageVote * 10.0) / 10.0;

                storeExist.get().setNbreStar(averageVote);
                storeRepository.save(storeExist.get());
                return averageVote;

            } else {
                // Aucun vote enregistré, renvoyer 0
                return 0.0;
            }
        } else {
            throw new RuntimeException("Store doesn't exist");
        }
    }
//    -----------------------------------------------------------------------------------------------
//    methode pour modifier les information d'un store(boutique)
    public Store update(Long idStore, Store updatedStoreDetails, MultipartFile multipartFile) {
        // Recherche du magasin existant par son ID
        Store existingStore = storeRepository.findById(idStore)
                .orElseThrow(() -> new NoSuchElementException("Le magasin avec l'ID " + idStore + " n'existe pas."));

        // Mise à jour des détails du magasin
        existingStore.setName(updatedStoreDetails.getName());
        existingStore.setDescription(updatedStoreDetails.getDescription());
        existingStore.setNumTel1(updatedStoreDetails.getNumTel1());
        existingStore.setEmail(updatedStoreDetails.getEmail());
        existingStore.setUserAddress(updatedStoreDetails.getUserAddress());
        existingStore.setUpdateDate(LocalDate.now());


        if (multipartFile != null && !multipartFile.isEmpty()) {
            try {
                String filePath = fileService.saveFile(multipartFile);

                String fileUrl = "http://localhost/artImage/" + Paths.get(filePath).getFileName().toString();

                System.out.println(filePath + "----------------------------------------");
                existingStore.setImageUrl(fileUrl);
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors de l'enregistrement du fichier: " + e.getMessage(), e);
            }
        }

        // Enregistrement des changements dans la base de données
        return storeRepository.save(existingStore);
    }

//-----------------------------------------------------------------------------------------------------

}
