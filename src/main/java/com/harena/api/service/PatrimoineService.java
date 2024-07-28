package com.harena.api.service;

import com.harena.api.file.BucketComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.serialisation.Serialiseur;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PatrimoineService {
    private final Serialiseur<Patrimoine> serialiseur;
    private final BucketComponent bucketComponent;
    private final Map<String, Patrimoine> dataStore = new HashMap<>();

    @Autowired
    public PatrimoineService(Serialiseur<Patrimoine> serialiseur, BucketComponent bucketComponent) {
        this.serialiseur = serialiseur;
        this.bucketComponent = bucketComponent;
    }

    public List<Patrimoine> getPatrimoines(int page, int pageSize) {
        return dataStore.values().stream()
                .skip((long) page * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Patrimoine getPatrimoineByNom(String nom) {
        return dataStore.values().stream()
                .filter(patrimoine -> patrimoine.nom().equals(nom))
                .findFirst()
                .orElse(null);
    }

    public void crupdatePatrimoines(List<Patrimoine> newPatrimoines) {
        for (Patrimoine patrimoine : newPatrimoines) {
            storePatrimoine(patrimoine);
        }
    }

    private void storePatrimoine(Patrimoine object) {
        String serializedObject = serialiseur.serialise(object);
        String bucketKey = "objects/" + UUID.randomUUID().toString();
        File tempFile = new File(System.getProperty("java.io.tmpdir"), bucketKey);

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(serializedObject);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write object to temporary file", e);
        }
        bucketComponent.upload(tempFile, bucketKey);
        dataStore.put(bucketKey, object);
    }
    private Patrimoine retrievePatrimoine(String bucketKey) {
        File downloadedFile = bucketComponent.download(bucketKey);
        String serializedObject;

        try (FileReader reader = new FileReader(downloadedFile)) {
            serializedObject = new BufferedReader(reader).lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read downloaded file", e);
        }
        return serialiseur.deserialise(serializedObject);
    }
}
