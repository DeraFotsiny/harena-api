package com.harena.api.endpoint.rest.controller;

import com.harena.api.service.ProjectionFutureService;
import jakarta.ws.rs.QueryParam;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import school.hei.patrimoine.modele.FluxImpossibles;

@RestController
@AllArgsConstructor
public class ProjectionFutureController {
  private final ProjectionFutureService projectionFutureService;

  @GetMapping("/patrimoines/{nom_patrimoine}/flux-impossibles")
  public Set<FluxImpossibles> getPatrimoineFluxImpossible(
      @PathVariable("nom_patrimoine") String nomPatrimoine,
      @QueryParam("debut") String debut,
      @QueryParam("fin") String fin) {
    Set<FluxImpossibles> fluxImpossibles =
        projectionFutureService.getPatrimoineFluxImmpossibles(nomPatrimoine, debut, fin);
    return ResponseEntity.ok().body(fluxImpossibles).getBody();
  }
}
