package com.harena.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import school.hei.patrimoine.modele.possession.Argent;
import school.hei.patrimoine.modele.possession.FluxArgent;
import school.hei.patrimoine.modele.possession.Materiel;
import school.hei.patrimoine.modele.possession.Possession;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PossessionAvecType {
  private String type;
  private Argent argent;
  private Materiel materiel;
  private FluxArgent fluxArgent;

  public Possession toPossession() {
    switch (type) {
      case "ARGENT":
        return argent;
      case "MATERIEL":
        return materiel;
      case "FLUXARGENT":
        return fluxArgent;
      default:
        throw new IllegalArgumentException("Invalid possession type");
    }
  }

  public static PossessionAvecType fromPossession(Possession possession) {
    PossessionAvecType possessionAvecType = new PossessionAvecType();
    if (possession instanceof Argent) {
      possessionAvecType.setType("ARGENT");
      possessionAvecType.setArgent((Argent) possession);
    } else if (possession instanceof Materiel) {
      possessionAvecType.setType("MATERIEL");
      possessionAvecType.setMateriel((Materiel) possession);
    } else if (possession instanceof FluxArgent) {
      possessionAvecType.setType("FLUXARGENT");
      possessionAvecType.setFluxArgent((FluxArgent) possession);
    } else {
      throw new IllegalArgumentException("Invalid possession type");
    }
    return possessionAvecType;
  }

  public String getNom() {
    switch (type) {
      case "ARGENT":
        return argent.getNom();
      case "MATERIEL":
        return materiel.getNom();
      case "FLUXARGENT":
        return fluxArgent.getNom();
      default:
        throw new IllegalArgumentException("Invalid possession type");
    }
  }
}
