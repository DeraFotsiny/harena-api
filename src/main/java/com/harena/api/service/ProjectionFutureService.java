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

import java.util.Set;

@SpringBootApplication
@ComponentScan(basePackages = "school.hei.patrimoine")
public class ProjectionFutureService {
    private Serialiseur<PossessionAvecType> serialiseur;
    private BucketComponent bucketComponent;

    public Set<FluxImpossibles> getPatrimoineFluxImmpossibles(@NonNull String nomPatrimoine,
                                                              @NonNull String debut,
                                                              @NonNull String fin){

        // todo: prendre la fonction de Salohy getPatrimoineByNom pour prendre le patrimoine
        EvolutionPatrimoine evolutionPatrimoine = new EvolutionPatrimoine(
                nomPatrimoine,
                patrimoine,
                debut,
                fin
        );
        return evolutionPatrimoine.getFluxImpossibles();
    }
}
