package com.yelensoft.artishop_backend.Service;

import com.yelensoft.artishop_backend.Repository.Store_repository;
import com.yelensoft.artishop_backend.Repository.Users_repository;
import com.yelensoft.artishop_backend.enumClass.PersonRole;
import com.yelensoft.artishop_backend.model.Store;
import com.yelensoft.artishop_backend.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class Store_service {
    @Autowired
    private Store_repository store_repository;

    @Autowired
    private Users_repository users_repository;

//    -----------------------------------------------------------------------------------------------
//    methode pour creer une botique en fonction d'un artisan donner start
//    public Store create(long id, Store store, MultipartFile multipartFile) {
//        Users artisantExist = users_repository.findById(id);
//        if (artisantExist != null) {
//            Users artisant = artisantExist;
//            if (PersonRole.ARTISANT.equals(artisant.getRole())) {
//
//                if (multipartFile != null) {
//                    String location = "C:\\xampp\\htdocs\\artImage";
////                    try {
//                        Path rootlocation = Paths.get(location);
//                        if (!Files.exists(rootlocation)) {
//                            Files.createDirectories(rootlocation);
//                            Files.copy(multipartFile.getInputStream(), rootlocation.resolve(multipartFile.getOriginalFilename()));
//                            store.setImageUrl("artImage/" + multipartFile.getOriginalFilename());
//                        } else {
////                            try {
//                                String nom = location + "\\" + multipartFile.getOriginalFilename();
//                                Path name = Paths.get(nom);
//                                if (!Files.exists(name)) {
//                                    Files.copy(multipartFile.getInputStream(), rootlocation.resolve(multipartFile.getOriginalFilename()));
//                                    store.setImageUrl("artImage/" + multipartFile.getOriginalFilename());
//                                } else {
//                                    Files.delete(name);
//                                    Files.copy(multipartFile.getInputStream(), rootlocation.resolve(multipartFile.getOriginalFilename()));
//                                    store.setImageUrl("artImage/" + multipartFile.getOriginalFilename());
//                                }
////                            } catch (Exception e) {
////                                throw new Exception("some error");
////                            }
//                        }
////                    } catch (Exception e) {
////                        throw new Exception(e.getMessage());
////                    }
//                }
//
//                Store storeExist = store_repository.findByEmailAndName(store.getEmail(), store.getName());
//                if (storeExist != null) {
//                    throw new RuntimeException("Le nom ou le mot de pass de cette boutique existe deja");
//                } else {
//                    return store_repository.save(store);
//                }
//
//            } else {
//                throw new RuntimeException("Seul les artisans peuvent avoir une boutique");
//            }
//
//        } else {
//            throw new RuntimeException("Vous n'est pas encore inscrit(e) en tant que artisant ");
//        }
//
//    }
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
        Store storeExist = store_repository.findById(id);
        if (storeExist !=null){
            storeExist.setDeleted(true);
        }
        return "Disable successffly!";
    }
//    ------------------------------------------------------------------------------------------------

//  methode pour calculer les nombres d'etoiles d'un store(boutique)
    public double getLikes(Long id) {
        Store storeExist = store_repository.findById(id);
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
    public void likeStore(int starVote, Long idStore){
        Store storeExist = store_repository.findById(idStore);

        if (storeExist != null){
            double totalVote = storeExist.getTotalValueVote()+starVote;
            double nbreVote = storeExist.getNbreVote()+1;

            storeExist.setNbreVote(nbreVote);
            storeExist.setTotalValueVote(totalVote);
            store_repository.save(storeExist);
        }else {
            throw new RuntimeException("store doesn't exist");
        }

    }
}
