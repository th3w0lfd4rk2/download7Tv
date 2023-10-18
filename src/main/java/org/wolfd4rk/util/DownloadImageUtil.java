package org.wolfd4rk.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.wolfd4rk.model.Emote;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DownloadImageUtil {

    public DownloadImageUtil(String path) {
        this.path = path;
    }

    private final String path;

    public void downloadImages(List<Emote> emotes, String extension) {

        List<Emote> downloadedEmotes = new ArrayList<>();

        for (Emote emote : emotes) {

            emote.setUrlImagen(emote.getUrlImagen().replace("png", extension));

            Request request = new Request.Builder().url(emote.getUrlImagen()).build();

            handleResponse(extension, downloadedEmotes, emote, request);
        }

        emotes.removeAll(downloadedEmotes);

    }

    private void handleResponse(String extension, List<Emote> downloadedEmotes, Emote emote, Request request) {

        try (Response response = new OkHttpClient().newCall(request).execute()) {

            if (response.isSuccessful()) {

                File directorioDestino = new File(path + "/" + extension + "/" + emote.getNombre());

                if (!directorioDestino.exists()) {
                    directorioDestino.mkdirs();
                }

                String nombreArchivo = emote.getNombre() + "." + extension;

                File archivoDestino = new File(directorioDestino, nombreArchivo);

                writeFile(downloadedEmotes, emote, response, archivoDestino);

            }

        } catch (IOException e) {

            System.out.println("Ha petao en la llamada con estos datos: ");

            System.out.println("Request: " + request);
        }
    }

    private void writeFile(List<Emote> downloadedEmotes, Emote emote, Response response, File archivoDestino) {

        int repeated = getRepeatedTimes(archivoDestino.getName());

        try (FileOutputStream fos = new FileOutputStream(archivoDestino)) {

            if (archivoDestino.exists() && repeated == 0) {
                writeFile(downloadedEmotes, emote, response, new File(archivoDestino.getParentFile(), archivoDestino.getName() + "_" + (repeated+1)));
            } else if (archivoDestino.exists()){
                writeFile(downloadedEmotes, emote, response, new File(archivoDestino.getParentFile(), archivoDestino.getName() + "_" + (repeated+1)));
            }

            //TODO arreglar esta mierda que no crea bien los emotes

            fos.write(response.body().bytes());

            System.out.println("Created emote succesfully: " + emote.getNombre());

            downloadedEmotes.add(emote);

        } catch (IOException e) {
            System.out.println("Failed to create new emote: " + emote.getNombre());
        }
    }

    private int getRepeatedTimes(String nombreArchivo) {

        if (nombreArchivo.contains("_")) {
            return Integer.parseInt(nombreArchivo.split("_")[1]);
        }

        return 0;
    }
}
