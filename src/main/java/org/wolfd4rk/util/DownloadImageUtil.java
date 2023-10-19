package org.wolfd4rk.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.wolfd4rk.model.Emote;
import org.wolfd4rk.request.CurlRequest;

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

            Response response = CurlRequest.makeRequest(emote.getUrlImagen(), null, null);

            if (response != null ){
                handleResponse(extension, downloadedEmotes, emote, response.body());
            }
        }

        emotes.removeAll(downloadedEmotes);

    }

    private void handleResponse(String extension, List<Emote> downloadedEmotes, Emote emote, ResponseBody responseBody) {

        if (responseBody != null) {

            File directoryDestination = new File(path + "/" + extension + "/" + emote.getNombre());

            if (!directoryDestination.exists()) {
                directoryDestination.mkdirs();
            }

            String fileName = emote.getNombre() + "." + extension;

            File fileDestination = new File(directoryDestination, fileName);

            writeFile(downloadedEmotes, emote, responseBody, fileDestination, extension);

        }
    }

    private void writeFile(List<Emote> downloadedEmotes, Emote emote, ResponseBody response, File fileDestination, String extension) {

        String fileNameNoExtension = fileDestination.getName().replace("." + extension, "").replace("[<>:\"/\\\\|?*]", "()");

        int repeated = getRepeatedTimes(fileNameNoExtension);
        File finalDestination = fileDestination;

        while (finalDestination.exists()) {
            repeated++;
            finalDestination = new File(fileDestination.getParentFile(), setFileName(fileNameNoExtension, repeated, extension));
        }

        try (FileOutputStream fos = new FileOutputStream(finalDestination)) {
            fos.write(response.bytes());
            System.out.println("Created emote successfully: " + emote.getNombre());
            downloadedEmotes.add(emote);
        } catch (IOException e) {
            System.out.println("Failed to create new emote: " + emote.getNombre());
        }
    }

    private String setFileName(String fileNameNoExtension, int repeated, String extension) {

        return fileNameNoExtension + "_" + (repeated + 1) + "." + extension;

    }

    private int getRepeatedTimes(String nombreArchivo) {

        if (nombreArchivo.contains("_")) {
            return Integer.parseInt(nombreArchivo.split("_")[1]);
        }

        return 0;
    }
}
