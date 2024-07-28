package com.harena.api.service;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import school.hei.patrimoine.serialisation.Serialiseur;

@SpringBootApplication
@ComponentScan(basePackages = "school.hei.patrimoine")
public class ProjectionFutureService {
    private final Serialiseur<> serialiseur;
}
