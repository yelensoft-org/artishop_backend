package com.yelensoft.artishop_backend.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    private final String location = "C:\\xampp\\htdocs\\artImage";

    public String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Le fichier est vide.");
        }

        // Générer un nom de fichier unique
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(location, fileName);

        // Créer le répertoire si nécessaire
        Files.createDirectories(filePath.getParent());

        // Enregistrer le fichier sur le système de fichiers
        Files.copy(file.getInputStream(), filePath);

        return filePath.toString();
    }
}