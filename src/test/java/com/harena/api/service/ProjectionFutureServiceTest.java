package com.harena.api.service;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import school.hei.patrimoine.cas.PatrimoineEtudiantPireCas;
import school.hei.patrimoine.modele.EvolutionPatrimoine;
import school.hei.patrimoine.modele.FluxImpossibles;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.modele.Personne;
import school.hei.patrimoine.modele.possession.Argent;
import school.hei.patrimoine.modele.possession.FluxArgent;
import school.hei.patrimoine.modele.possession.Materiel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static java.time.Month.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class ProjectionFutureServiceTest {

    private static final Logger log = LoggerFactory.getLogger(ProjectionFutureServiceTest.class);
    @Mock
    private ProjectionFutureService subject;

    @Mock
    private PatrimoineService patrimoineService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void get_patrimoine_flux_immpossibles_ok() {
        var au13mai24 = "13-05-2024";
        var le31juillet = "31-07-2024";
        var patrimoine = get();

        when(patrimoineService.getPatrimoineByNom(any())).thenReturn(patrimoine);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate debut = LocalDate.parse(au13mai24, formatter);
        LocalDate fin = LocalDate.parse(le31juillet, formatter);
        var evolutionPatrimoine =  new EvolutionPatrimoine("patrimoine", patrimoine, debut, fin);
        var getPatrimoineFluxImpossibles = subject.getPatrimoineFluxImmpossibles("patrimoine", au13mai24, le31juillet);
        log.info("\n debut: {}, fin: {} \n \n patrimoine: {} \n \n evolutionPatrimoine: {} \n \n fluxImpossible: {} " +
                        "\n \n getPatrimoineFluxImpossibles: {}",
                debut, fin, patrimoine, evolutionPatrimoine,
                evolutionPatrimoine.getFluxImpossibles(), getPatrimoineFluxImpossibles);
    }

    private Patrimoine get() {
        var ilo = new Personne("Ilo");
        var au13mai24 = LocalDate.of(2024, MAY, 13);
        var financeur = new Argent("Espèces", au13mai24.minusDays(1), au13mai24, 100_000);
        var trainDeVie =
                new FluxArgent(
                        "Vie courante",
                        financeur,
                        au13mai24.minusDays(100),
                        au13mai24.plusDays(100),
                        -100_000,
                        15);

        var mac = new Materiel("MacBook Pro", au13mai24, 500_000, au13mai24.minusDays(3), -0.9);

        return new Patrimoine("Ilo (pire)", ilo, au13mai24, Set.of(financeur, trainDeVie, mac));
    }

    @Test
    void getPatrimoineGraph() {
    }
}