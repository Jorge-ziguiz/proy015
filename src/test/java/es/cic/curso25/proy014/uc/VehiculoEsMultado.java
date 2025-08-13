package es.cic.curso25.proy014.uc;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic.curso25.proy014.dto.CrearVehiculoDto;
import es.cic.curso25.proy014.model.Plaza;
import es.cic.curso25.proy014.model.Vehiculo;
import es.cic.curso25.proy014.service.VehiculoService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@SpringBootTest
@AutoConfigureMockMvc
public class VehiculoEsMultado {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VehiculoService vehiculoService;

    private Vehiculo vehiculo;

    private CrearVehiculoDto vehiculoDto;

    @BeforeEach
    void setUp() {
        vehiculo = new Vehiculo();
        vehiculo.setMatricula("ABC123");
        vehiculo.setTipo("turismo");
        vehiculo.setPaisMatricula("spain");

        vehiculoDto = new CrearVehiculoDto();
        vehiculoDto.setMatricula("ABC123");
        vehiculoDto.setTipo("turismo");
        vehiculoDto.setPaisMatricula("spain");
    }

    @Test
    @Transactional
    void testEstacionarVehiculoEnPlazaIncorrecta() throws Exception {
        Vehiculo resultado = vehiculoService.create(vehiculoDto);

        if (!resultado.getPlaza().isEstaLibre()) {
            Plaza update = resultado.getPlaza();
            update.setEstaLibre(true);
            vehiculo.setPlazaOcupada(0);
            vehiculo.setPlaza(update);
            vehiculoService.update(resultado);
        }

        int plazaCorrecta = (int) resultado.getPlaza().getId().longValue();
        int PlazaIncorrecta = (plazaCorrecta > 0) ? plazaCorrecta + 1 : plazaCorrecta - 1;

        String errorEstacionar = mockMvc.perform(put("/vehiculo/estacionar/" + resultado.getId())
                .param("plaza", String.valueOf(PlazaIncorrecta))
                .with(httpBasic("user", "#|@5{31./&}(.-")).with(csrf()))
                .andExpect(status().is4xxClientError())
                .andDo(print()).andReturn().getResponse().getContentAsString();

        assertTrue(errorEstacionar.contains("el vehiculo se ha estacionado en una plaza no asignada"));
    }

}
