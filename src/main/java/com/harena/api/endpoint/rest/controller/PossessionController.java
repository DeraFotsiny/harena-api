package com.harena.api.endpoint.rest.controller;

import com.harena.api.model.PossessionAvecType;
import com.harena.api.service.PossessionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patrimoines/{nom_patrimoine}/possessions")
@AllArgsConstructor
public class PossessionController {
    private final PossessionService possessionService;

    @PutMapping
    public List<PossessionAvecType> crupdatePossession(
            @PathVariable("nom_patrimoine") String patrimoineName,
            @RequestBody List<PossessionAvecType> possessions,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "page_size") int pageSize
    ) {
        List<PossessionAvecType> crupdatePossession = possessionService.crupdatePossession(patrimoineName, possessions);
        return possessionService.paginatePossessions(crupdatePossession, page, pageSize);
    }

    @GetMapping("/{nom_possession}")
    public PossessionAvecType getPossessionByName(
            @PathVariable("nom_patrimoine") String patrimoineName,
            @PathVariable("nom_possession") String possessionName
    ) {
        return possessionService.getPossessionByName(patrimoineName, possessionName);
    }

    @GetMapping
    public List<PossessionAvecType> getPatrimoinePossessions(
            @PathVariable("nom_patrimoine") String patrimoineName,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "page_size") int pageSize
    ) {
        List<PossessionAvecType> possessions = possessionService.getPatrimoinePossessions(patrimoineName);
        return possessionService.paginatePossessions(possessions, page, pageSize);
    }

    @DeleteMapping("/{nom_possession}")
    public void deletePossessionByName(
            @PathVariable("nom_patrimoine") String patrimoineName,
            @PathVariable("nom_possession") String possessionName
    ) {
        possessionService.deletePossessionByName(patrimoineName, possessionName);
    }
}
