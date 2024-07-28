package com.harena.api.service;

import com.harena.api.file.BucketComponent;
import com.harena.api.model.PossessionAvecType;
import com.harena.api.model.exception.ResourceNotFoundException;
import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.patrimoine.modele.possession.Possession;
import school.hei.patrimoine.serialisation.Serialiseur;

@Service
@AllArgsConstructor
public class PossessionService {
  private final Serialiseur<List<Possession>> serialiseur;
  private final BucketComponent bucketComponent;

  public List<PossessionAvecType> crupdatePossession(
      String patrimoineName, List<PossessionAvecType> possessions) {
    List<Possession> possessionList =
        possessions.stream().map(PossessionAvecType::toPossession).collect(Collectors.toList());
    String serializedObject = serialiseur.serialise(possessionList);

    String bucketKey = "patrimoines/" + patrimoineName + "/possessions.json";

    File tempFile = new File(System.getProperty("java.io.tmpdir"), bucketKey);
    try (FileWriter writer = new FileWriter(tempFile)) {
      writer.write(serializedObject);
    } catch (IOException e) {
      throw new RuntimeException("Failed to write possession to temporary file", e);
    }

    bucketComponent.upload(tempFile, bucketKey);

    return possessions;
  }

  public PossessionAvecType getPossessionByName(String patrimoineName, String possessionName) {
    List<PossessionAvecType> possessions = getPatrimoinePossessions(patrimoineName);

    return possessions.stream()
        .filter(p -> p.getNom().equals(possessionName))
        .findFirst()
        .orElseThrow(ResourceNotFoundException::new);
  }

  public List<PossessionAvecType> getPatrimoinePossessions(String patrimoineName) {
    String bucketKey = "patrimoines/" + patrimoineName + "/possessions.json";
    File downloadedFile = bucketComponent.download(bucketKey);

    String serializedObject;
    try (FileReader reader = new FileReader(downloadedFile)) {
      serializedObject = new BufferedReader(reader).lines().collect(Collectors.joining("\n"));
    } catch (IOException e) {
      throw new RuntimeException("Failed to read downloaded file", e);
    }

    List<Possession> possessions = serialiseur.deserialise(serializedObject);
    return possessions.stream()
        .map(PossessionAvecType::fromPossession)
        .collect(Collectors.toList());
  }

  public void deletePossessionByName(String patrimoineName, String possessionName) {
    List<PossessionAvecType> possessions = getPatrimoinePossessions(patrimoineName);
    possessions =
        possessions.stream()
            .filter(possession -> !possession.getNom().equals(possessionName))
            .collect(Collectors.toList());

    crupdatePossession(patrimoineName, possessions);
  }

  public List<PossessionAvecType> paginatePossessions(
      List<PossessionAvecType> possessions, int page, int pageSize) {
    int fromIndex = (page - 1) * pageSize;
    if (fromIndex >= possessions.size()) {
      return Collections.emptyList();
    }
    int toIndex = Math.min(fromIndex + pageSize, possessions.size());
    return possessions.subList(fromIndex, toIndex);
  }
}
