package com.harena.api.service;

import com.harena.api.file.BucketComponent;
import org.springframework.stereotype.Service;
import school.hei.patrimoine.serialisation.Serialiseur;

import java.io.*;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ObjectStorageService {

    private final Serialiseur<Object> serialiseur;
    private final BucketComponent bucketComponent;

    public ObjectStorageService(Serialiseur<Object> serialiseur, BucketComponent bucketComponent) {
        this.serialiseur = serialiseur;
        this.bucketComponent = bucketComponent;
    }

    public String storeObject(Object object) {
        // Sérialiser l'objet
        String serializedObject = serialiseur.serialise(object);

        // Générer une clé unique pour S3
        String bucketKey = "objects/" + UUID.randomUUID().toString();

        // Créer un fichier temporaire pour l'upload
        File tempFile = new File(System.getProperty("java.io.tmpdir"), bucketKey);
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(serializedObject);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write object to temporary file", e);
        }

        // Uploader le fichier sur S3
        bucketComponent.upload(tempFile, bucketKey);

        // Retourner la clé S3
        return bucketKey;
    }

    public Object retrieveObject(String bucketKey) {
        // Télécharger le fichier depuis S3
        File downloadedFile = bucketComponent.download(bucketKey);

        // Lire le contenu du fichier
        String serializedObject;
        try (FileReader reader = new FileReader(downloadedFile)) {
            serializedObject = new BufferedReader(reader).lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read downloaded file", e);
        }

        // Désérialiser l'objet
        return serialiseur.deserialise(serializedObject);
    }

    public String apply(Object object) {
        // Appeler storeObject pour sérialiser et stocker l'objet
        return storeObject(object);
    }
}
