package com.harena.api.endpoint.rest.controller;

import com.harena.api.model.exception.InternalServerException;
import com.harena.api.model.exception.TooManyRequestsException;
import com.harena.api.service.ProjectionFutureService;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.QueryParam;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import school.hei.patrimoine.modele.FluxImpossibles;

import java.util.Set;

@RestController
@AllArgsConstructor
public class ProjectionFutureController {
    private final ProjectionFutureService projectionFutureService;

    @GetMapping("/patrimoines/{nom_patrimoine}/flux-impossibles")
    public ResponseEntity<?> getPatrimoineFluxImpossible(@PathVariable("nom_patrimoine") String nomPatrimoine,
                                                            @QueryParam("debut") String debut,
                                                            @QueryParam("fin") String fin){
        try {
            Set<FluxImpossibles> fluxImpossibles = projectionFutureService.getPatrimoineFluxImmpossibles(nomPatrimoine, debut, fin);
            return ResponseEntity.ok().body(fluxImpossibles);
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(new com.harena.api.model.exception.BadRequestException());
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(new com.harena.api.model.exception.NotFoundException());
        } catch (TooManyRequestsException e) {
            return ResponseEntity.status(429).body(new TooManyRequestsException());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new InternalServerException());
        }
    }
}
