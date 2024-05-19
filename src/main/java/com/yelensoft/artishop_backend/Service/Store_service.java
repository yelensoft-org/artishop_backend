package com.yelensoft.artishop_backend.Service;

import com.yelensoft.artishop_backend.Repository.Store_repository;
import com.yelensoft.artishop_backend.Repository.Users_repository;
import com.yelensoft.artishop_backend.enumClass.PersonRole;
import com.yelensoft.artishop_backend.model.Store;
import com.yelensoft.artishop_backend.model.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class Store_service {
    @Autowired
    private Store_repository store_repository;

    @Autowired
    private Users_repository users_repository;

    @Autowired
    private  FileService fileService;


    //    -----------------------------------------------------------------------------------------------
    //    methode pour creer une botique en fonction d'un artisan donner start
    public Store create(long id, Store store, MultipartFile multipartFile) throws Exception {
        try {
            Users artisantExist = users_repository.findUsersById(id);
            if (artisantExist == null || !artisantExist.getRole().equals(PersonRole.ARTISANT)) {
                throw new RuntimeException("Utilisateur inexistant ou non autorisé.");
            }

            String filePath = fileService.saveFile(multipartFile);
            // Convertir le chemin de fichier en URL
            String fileUrl = "http://localhost/artImage/" + Paths.get(filePath).getFileName().toString();

            System.out.println(filePath + "----------------------------------------");
            store.setImageUrl(fileUrl);
//            store.setUpdateDate(LocalDate.now());

            Store storeExist = store_repository.findByEmailAndName(store.getEmail(), store.getName());
            if (storeExist != null) {
                throw new RuntimeException("Le nom ou l'adresse e-mail de cette boutique existe déjà.");
            }

            store_repository.save(store);
            return store;
        } catch (RuntimeException e) {
            log.error("Runtime error: {}", e.getMessage(), e);
            throw new Exception("Une erreur est survenue lors de la création de la boutique.", e);
        }
    }

    //    ------------------------------------------------------------------------------------------------
//    methode pour appeler tout les store(boutique)
    public List<Store> readStoreAll(){
        List<Store> storeList = store_repository.findAll();
        if (storeList.isEmpty()){
            throw new RuntimeException("Il exist auccune boutique");
        }else {
            return storeList;
        }
    }
//----------------------------------------------------------------------------------------------
//    mehode pour appeler une store(boutique) en fonction d'un users(artisant)
    public Store readStore(Long idArtisan){
        return store_repository.findByUsersId(idArtisan);

    }
//    ______________________________________________________________________________________________

//  desactiver une boutique
    public String desableStore(Long id){
        Store storeExist = store_repository.findStoreById(id);
        if (storeExist !=null){
            storeExist.setDeleted(true);
        }
        return "Disable successffly!";
    }
//    ------------------------------------------------------------------------------------------------

//  methode pour calculer les nombres d'etoiles d'un store(boutique)
    public double getLikes(Long id) {
        Store storeExist = store_repository.findStoreById(id);
        if (storeExist != null) {
            double nbreVote = storeExist.getNbreVote();
            double totalValueVote = storeExist.getTotalValueVote();

            if (nbreVote > 0) {
                return totalValueVote / nbreVote;
            } else {
                // Aucun vote enregistré, renvoyer 0
                return 0.0;
            }
        }
        throw new RuntimeException("ce store n'existe pas");
    }
//------------------------------------------------------------------------------------------------------------

//    mehode pour permettre a un user de voter
    public double likeStore(int starVote, Long idStore) {
        Store storeExist = store_repository.findStoreById(idStore);

        if (storeExist != null) {
            double totalVote = storeExist.getTotalValueVote() + starVote;
            double nbreVote = storeExist.getNbreVote() + 1;

            storeExist.setNbreVote(nbreVote);
            storeExist.setTotalValueVote(totalVote);

            if (nbreVote > 0) {
                double averageVote = totalVote / nbreVote;
                store_repository.save(storeExist);
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
        Store existingStore = store_repository.findById(idStore)
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
        return store_repository.save(existingStore);
    }

//-----------------------------------------------------------------------------------------------------

}
