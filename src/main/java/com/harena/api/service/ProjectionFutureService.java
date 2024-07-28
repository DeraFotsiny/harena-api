package com.harena.api.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import school.hei.patrimoine.modele.EvolutionPatrimoine;
import school.hei.patrimoine.modele.FluxImpossibles;
import school.hei.patrimoine.modele.Patrimoine;

@SpringBootApplication
@ComponentScan(basePackages = "school.hei.patrimoine")
public class ProjectionFutureService {
  private PatrimoineService patrimoineService;

  public Set<FluxImpossibles> getPatrimoineFluxImmpossibles(
      String nomPatrimoine, String debut, String fin) {
    EvolutionPatrimoine evolutionPatrimoine = getEvolutionPatrimoine(nomPatrimoine, debut, fin);
    return evolutionPatrimoine.getFluxImpossibles();
  }

  private EvolutionPatrimoine getEvolutionPatrimoine(
      String nomPatrimoine, String debut, String fin) {
    Patrimoine patrimoine = patrimoineService.getPatrimoineByNom(nomPatrimoine);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    LocalDate dateDebut = LocalDate.parse(debut, formatter);
    LocalDate dateFin = LocalDate.parse(fin, formatter);
    return new EvolutionPatrimoine(nomPatrimoine, patrimoine, dateDebut, dateFin);
  }
}
