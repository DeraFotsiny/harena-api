package com.harena.api.endpoint.rest.controller;

import com.harena.api.service.PatrimoineService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.patrimoine.modele.Patrimoine;

@RestController
public class PatrimoineController {
  private final PatrimoineService patrimoineService;

  @Autowired
  public PatrimoineController(PatrimoineService patrimoineService) {
    this.patrimoineService = patrimoineService;
  }

  @GetMapping("/patrimoines")
  public List<Patrimoine> getPatrimoines(
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize) {
    return patrimoineService.getPatrimoines(page, pageSize);
  }

  @GetMapping("/patrimoines/{nom_patrimoine}")
  public Patrimoine getPatrimoineByNom(@PathVariable String nom) {
    return patrimoineService.getPatrimoineByNom(nom);
  }

  @PutMapping("/patrimoines")
  public ResponseEntity<Map<String, List<Patrimoine>>> crupdatePatrimoines(
      @RequestBody Map<String, List<Patrimoine>> newPatrimoines) {
    newPatrimoines
        .get("data")
        .forEach(patrimoine -> patrimoineService.crupdatePatrimoine(patrimoine.nom(), patrimoine));
    Map<String, List<Patrimoine>> response = new HashMap<>();
    response.put("data", patrimoineService.getPatrimoines(0, Integer.MAX_VALUE));
    return ResponseEntity.ok(response);
  }
}
