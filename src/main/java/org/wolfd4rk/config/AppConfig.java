package org.wolfd4rk.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class AppConfig {

    public AppConfig() {

        File archivo = null;
        String bearer = "";

        try {
            URL url = AppConfig.class.getResource("application.properties");

            if (url != null) {
                archivo = Paths.get(url.toURI()).toFile();
            } else {
                throw new NullPointerException("url to properties is null");
            }
        } catch (URISyntaxException | NullPointerException e) {
            System.out.println("There was a problem retrieving application.properties");
        }

        if (archivo != null) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(archivo))) {

                String linea;

                while ((linea = bufferedReader.readLine()) != null) {

                    if (linea.contains("bearer")) {

                        String[] partes = linea.split("=");
                        if (partes.length == 2) {

                            bearer = partes[1].trim();

                            System.out.println("Token de autenticación: " + bearer);

                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error accesing application properties");
            }
        }

        this.bearer = bearer;
    }

    private final String bearer;

    public String getBearer() {
        return bearer;
    }

}
