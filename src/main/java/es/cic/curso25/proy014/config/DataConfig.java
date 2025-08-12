package es.cic.curso25.proy014.config;

import java.util.List;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.cic.curso25.proy014.model.Plaza;
import es.cic.curso25.proy014.service.PlazaService;

@Configuration
public class DataConfig {

    @Bean
    public ApplicationRunner llenarGaraje(PlazaService plazaService) {

        return args -> {
            int numeroPLazas = PlazaService.NUMERO_PLAZAS;

            List<Plaza> plazas = plazaService.getAll();

            if (plazas.isEmpty()) {

                for (int i = 0; i < numeroPLazas; i++) {
                    Plaza plaza = new Plaza();
                    plazaService.create(plaza);
                }
            }

        };
    }

}
