package com.harena.api.service;

import com.harena.api.file.BucketComponent;
import com.harena.api.model.PossessionAvecType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.lang.NonNull;
import school.hei.patrimoine.modele.EvolutionPatrimoine;
import school.hei.patrimoine.modele.FluxImpossibles;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.serialisation.Serialiseur;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@SpringBootApplication
@ComponentScan(basePackages = "school.hei.patrimoine")
public class ProjectionFutureService {
    private Serialiseur<PossessionAvecType> serialiseur;
    private BucketComponent bucketComponent;

    public Set<FluxImpossibles> getPatrimoineFluxImmpossibles(String nomPatrimoine,
                                                              String debut,
                                                              String fin){

        EvolutionPatrimoine evolutionPatrimoine = getEvolutionPatrimoine(nomPatrimoine, debut, fin);
        return evolutionPatrimoine.getFluxImpossibles();
    }
    private EvolutionPatrimoine getEvolutionPatrimoine(String nomPatrimoine,
                                                       String debut,
                                                       String fin){
        // todo: prendre la fonction de Salohy getPatrimoineByNom pour prendre le patrimoine
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dateDebut = LocalDate.parse(debut, formatter);
        LocalDate dateFin = LocalDate.parse(fin, formatter);
        return new EvolutionPatrimoine(
                nomPatrimoine,
                patrimoine,
                dateDebut,
                dateFin
        );
    }
}
