package com.yelensoft.artishop_backend;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ArtishopBackendApplication {

    public static void main(String[] args) {

//        System.setProperty( "spring.devtools.restart.enabled" , "false" );
        SpringApplication.run(ArtishopBackendApplication.class, args);
        System.out.print("le big boss lonpo");
    }

}
