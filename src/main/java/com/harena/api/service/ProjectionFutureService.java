package com.harena.api.service;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import school.hei.patrimoine.modele.EvolutionPatrimoine;
import school.hei.patrimoine.modele.FluxImpossibles;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.visualisation.xchart.GrapheurEvolutionPatrimoine;

@SpringBootApplication
@ComponentScan(basePackages = "school.hei.patrimoine")
public class ProjectionFutureService {
  private PatrimoineService patrimoineService;
  private GrapheurEvolutionPatrimoine grapheurEvolutionPatrimoine;

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

  public File getPatrimoineGraph(String nomPatrimoine, String debut, String fin) {
    EvolutionPatrimoine evolutionPatrimoine = getEvolutionPatrimoine(nomPatrimoine, debut, fin);
    return grapheurEvolutionPatrimoine.apply(evolutionPatrimoine);
  }
}
