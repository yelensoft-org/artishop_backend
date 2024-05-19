package com.yelensoft.artishop_backend.Service;

import com.yelensoft.artishop_backend.Repository.Store_repository;
import com.yelensoft.artishop_backend.Repository.Users_repository;
import com.yelensoft.artishop_backend.enumClass.PersonRole;
import com.yelensoft.artishop_backend.model.Cart;
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
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class Store_service {
    @Autowired
    private Store_repository store_repository;

    @Autowired
    private Users_repository users_repository;

//    -----------------------------------------------------------------------------------------------
//    methode pour creer une botique en fonction d'un artisan donner start
public Store create(long id, Store store, MultipartFile multipartFile) throws Exception {
    try {
        Users artisantExist = users_repository.findUsersById(id);
        if (artisantExist == null || !artisantExist.getRole().equals(PersonRole.ARTISANT)) {
            throw new RuntimeException("Utilisateur inexistant ou non autorisé.");
        }

        String location = "C:\\xampp\\htdocs\\artImage";
        Path rootlocation = Paths.get(location);
        if (!Files.exists(rootlocation)) {
            Files.createDirectories(rootlocation);
        }

        String nom = location + "\\" + multipartFile.getOriginalFilename();
        Path name = Paths.get(nom);
        if (Files.exists(name)) {
            Files.delete(name);
        }

        Files.copy(multipartFile.getInputStream(), rootlocation.resolve(multipartFile.getOriginalFilename()));
        store.setImageUrl("artImage/" + multipartFile.getOriginalFilename());
        store.setUpdateDate(LocalDate.now());

        Store storeExist = store_repository.findByEmailAndName(store.getEmail(), store.getName());
        if (storeExist != null) {
            throw new RuntimeException("Le nom ou l'adresse e-mail de cette boutique existe déjà.");
        }

        store_repository.save(store);
        return store;
    } catch (IOException e) {
        log.error("File operation error: {}", e.getMessage(), e);
        throw new Exception("Une erreur est survenue lors de la gestion du fichier d'image.", e);
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
public Store update(Long idStore, Store updatedStoreDetails) {
    // Recherche du magasin existant par son ID
    Store existingStore = store_repository.findById(idStore)
            .orElseThrow(() -> new NoSuchElementException("Le magasin avec l'ID " + idStore + " n'existe pas."));

    // Mise à jour des détails du magasin
    existingStore.setName(updatedStoreDetails.getName());
    existingStore.setDescription(updatedStoreDetails.getDescription());
    existingStore.setNumTel1(updatedStoreDetails.getNumTel1());
    existingStore.setNumTel2(updatedStoreDetails.getNumTel2());
    existingStore.setEmail(updatedStoreDetails.getEmail());
//    existingStore.setDeleted(updatedStoreDetails.getIs());
    existingStore.setStatus(updatedStoreDetails.getStatus());
    existingStore.setNbreVote(updatedStoreDetails.getNbreVote());
    existingStore.setTotalValueVote(updatedStoreDetails.getTotalValueVote());
    existingStore.setNbreStar(updatedStoreDetails.getNbreStar());
    existingStore.setUserAddress(updatedStoreDetails.getUserAddress());
    existingStore.setUpdateDate(LocalDate.now());

    // Enregistrement des changements dans la base de données
    return store_repository.save(existingStore);
}

//-----------------------------------------------------------------------------------------------------

}
