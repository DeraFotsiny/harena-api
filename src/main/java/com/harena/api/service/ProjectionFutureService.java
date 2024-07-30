package com.harena.api.service;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import school.hei.patrimoine.modele.EvolutionPatrimoine;
import school.hei.patrimoine.modele.FluxImpossibles;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.visualisation.xchart.GrapheurEvolutionPatrimoine;


@Service
@AllArgsConstructor
public class ProjectionFutureService {
  private static final Logger log = LoggerFactory.getLogger(ProjectionFutureService.class);
  private PatrimoineService patrimoineService;
  private GrapheurEvolutionPatrimoine grapheurEvolutionPatrimoine;


    public Set<FluxImpossibles> getPatrimoineFluxImmpossibles(
      String nomPatrimoine, String debut, String fin) {
    EvolutionPatrimoine evolutionPatrimoine = getEvolutionPatrimoine(nomPatrimoine, debut, fin);
    log.info("\n \nProjectionFutureService::getPatrimoineFluxImpossible(evolutionPatrimoine): {}", evolutionPatrimoine);
    return evolutionPatrimoine.getFluxImpossibles();
  }

  public EvolutionPatrimoine getEvolutionPatrimoine(
      String nomPatrimoine, String debut, String fin) {
    Patrimoine patrimoine = patrimoineService.getPatrimoineByNom(nomPatrimoine);
    log.info("ProjectionFutureService::getEvolutionPatrimoine(patrimoine): {}", patrimoine);
    System.out.println("patrimoine: " +patrimoine);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    LocalDate dateDebut = LocalDate.parse(debut, formatter);
    LocalDate dateFin = LocalDate.parse(fin, formatter);
    System.out.println("debut: " +dateDebut+ "\nfin : " + dateFin);
    return new EvolutionPatrimoine(nomPatrimoine, patrimoine, dateDebut, dateFin);
  }

  public File getPatrimoineGraph(String nomPatrimoine, String debut, String fin) {
    EvolutionPatrimoine evolutionPatrimoine = getEvolutionPatrimoine(nomPatrimoine, debut, fin);
    return grapheurEvolutionPatrimoine.apply(evolutionPatrimoine);
  }
}
