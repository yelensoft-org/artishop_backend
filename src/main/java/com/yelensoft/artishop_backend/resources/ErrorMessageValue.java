package com.yelensoft.artishop_backend.resources;

import lombok.NoArgsConstructor;

public class ErrorMessageValue {
    public static final String USER_NOT_FOUND = "404: Cet utilisateur n'existe pas";
    public static final String ROLE_NOT_FOUND = "404: Le role specifier est introuvable";
    public static final String ACCESS_FORBIDDEN = "403: Vous n'ête pas autoriser à effectuer cette action";
    public static final String BAD_REQUEST = "400: Erreur d'execution de la requête";
}
