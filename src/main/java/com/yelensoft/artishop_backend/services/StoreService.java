package com.yelensoft.artishop_backend.services;

import com.yelensoft.artishop_backend.configuration.ResponseHandler;
import com.yelensoft.artishop_backend.dto.StorePresentationDto;
import com.yelensoft.artishop_backend.entities.Follow;
import com.yelensoft.artishop_backend.entities.Store;
import com.yelensoft.artishop_backend.entities.UserApp;
import com.yelensoft.artishop_backend.exceptions.BadRequestException;
import com.yelensoft.artishop_backend.exceptions.NotFoundException;
import com.yelensoft.artishop_backend.repositories.FollowRepository;
import com.yelensoft.artishop_backend.repositories.StoreRepository;
import com.yelensoft.artishop_backend.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final UsersRepository usersRepository;
    private final FollowRepository followRepository;

    public Store getStoreById(Long storeId) {
        return null;
    }

    public ResponseEntity<?> getAllStorePerPage(Long idUser, int page, int size){
        try {
            UserApp userApp = usersRepository.findById(idUser).orElseThrow(()-> new NotFoundException("utilisateur invalide"));
            Pageable pageable = PageRequest.of(page, size);
            Page<Store> storePage = storeRepository.findAll(pageable);
            List<Store> storeList = storePage.getContent();
            List<StorePresentationDto> presentationDtos = storeList.stream().map(store -> {
                Follow follow = followRepository.findByUserAppIdAndStoreId(idUser,store.getId());
                return new StorePresentationDto(
                        store.getId(),
                        store.getName(),
                        store.getImageUrl(),
                        store.getUserApp().getFullName(),
                        follow != null,
                        followRepository.findByStoreId(store.getId()).size()
                );
            }).toList();
            return ResponseHandler.generateResponse("success", HttpStatus.OK,presentationDtos);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
}
