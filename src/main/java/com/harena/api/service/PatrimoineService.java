package com.harena.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harena.api.file.BucketComponent;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.serialisation.Serialiseur;

@Service
public class PatrimoineService {
  private final Serialiseur<Patrimoine> serialiseur;
  private final BucketComponent bucketComponent;
  private final Map<String, Patrimoine> dataStore = new HashMap<>();
  private final ObjectMapper objectMapper = new ObjectMapper();

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

  public void crupdatePatrimoine(String nom, Patrimoine patrimoine) {
    storePatrimoine(patrimoine);
  }

  private void storePatrimoine(Patrimoine patrimoine) {
    String bucketKey = "objects/" + patrimoine.nom() + ".json";
    File tempFile = new File(System.getProperty("java.io.tmpdir"), bucketKey);

    try {
      objectMapper.writeValue(tempFile, patrimoine);
      bucketComponent.upload(tempFile, bucketKey);
      dataStore.put(bucketKey, patrimoine);
    } catch (IOException e) {
      throw new RuntimeException("Failed to write object to temporary file", e);
    } finally {
      if (tempFile.exists() && !tempFile.delete()) {
        System.err.println("Failed to delete temporary file: " + tempFile.getAbsolutePath());
      }
    }
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
