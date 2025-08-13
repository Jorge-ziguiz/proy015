package es.cic.curso25.proy014.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic.curso25.proy014.dto.CrearVehiculoDto;
import es.cic.curso25.proy014.model.Plaza;
import es.cic.curso25.proy014.model.Vehiculo;
import es.cic.curso25.proy014.service.VehiculoService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class VehiculoControllerIntegralTest {

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

        void testArchivarElVehiculo() throws Exception {

                Vehiculo resultado = vehiculoService.create(vehiculoDto);

                String jsonResultado = mockMvc
                                .perform(put("/vehiculo/archivar/" + resultado.getId())
                                                .with(httpBasic("user", "#|@5{31./&}(.-")).with(csrf()))
                                .andDo(print())
                                .andExpect(status().is2xxSuccessful())
                                .andReturn().getResponse().getContentAsString();

                Vehiculo resultadoArchivar = objectMapper.readValue(jsonResultado, Vehiculo.class);
                assertEquals(resultadoArchivar.isEstaArchivado(), true);
        }

        @Test
        void testCreate() throws Exception {

                String jsonRequest = objectMapper.writeValueAsString(vehiculoDto);

                String jsonResultado = mockMvc
                                .perform(post("/vehiculo").contentType(MediaType.APPLICATION_JSON).content(jsonRequest)
                                                .with(httpBasic("user", "#|@5{31./&}(.-")).with(csrf()))
                                .andDo(print())
                                .andExpect(status().is2xxSuccessful())
                                .andReturn().getResponse().getContentAsString();
                Vehiculo resultado = objectMapper.readValue(jsonResultado, Vehiculo.class);

                Vehiculo getVehiculo = vehiculoService.get(Long.valueOf(resultado.getId())).orElse(null);

                assertTrue(getVehiculo.getPlaza().getId() != null);

                assertTrue(vehiculoService.get(Long.valueOf(resultado.getId())) != null);

        }

        @Test
        void testDesArchivarElVehiculo() throws Exception {
                Vehiculo resultado = vehiculoService.create(vehiculoDto);
                vehiculoService.archivarVehiculo(resultado, true);

                String jsonResultado = mockMvc
                                .perform(put("/vehiculo/des-archivar/" + resultado.getId())
                                                .with(httpBasic("user", "#|@5{31./&}(.-")).with(csrf()))
                                .andExpect(status().is2xxSuccessful())
                                .andDo(print()).andReturn().getResponse().getContentAsString();

                Vehiculo resultadoArchivar = objectMapper.readValue(jsonResultado, Vehiculo.class);
                assertEquals(resultadoArchivar.isEstaArchivado(), false);

        }

        @Test
        @Transactional
        void testEstacionarVehiculo() throws Exception {
                Vehiculo resultado = vehiculoService.create(vehiculoDto);

                if (!resultado.getPlaza().isEstaLibre()) {
                        Plaza update = resultado.getPlaza();
                        update.setEstaLibre(true);
                        vehiculo.setPlazaOcupada(0);
                        vehiculo.setPlaza(update);
                        vehiculoService.update(resultado);
                }

                String jsonResultado = mockMvc.perform(put("/vehiculo/estacionar/" + resultado.getId())
                                .param("plaza", String.valueOf(resultado.getPlaza().getId()))
                                .with(httpBasic("user", "#|@5{31./&}(.-")).with(csrf()))
                                .andExpect(status().is2xxSuccessful())
                                .andDo(print()).andReturn().getResponse().getContentAsString();

                Vehiculo resultadoEstacionar = objectMapper.readValue(jsonResultado, Vehiculo.class);

                assertTrue(resultadoEstacionar.getPlazaOcupada() != 0);

        }

        @Test
        void testGet() throws Exception {
                Vehiculo resultado = vehiculoService.create(vehiculoDto);

                String jsonResultado = mockMvc
                                .perform(get("/vehiculo/" + resultado.getId())
                                                .with(httpBasic("user", "#|@5{31./&}(.-")).with(csrf()))
                                .andExpect(status().is2xxSuccessful())
                                .andDo(print()).andReturn().getResponse().getContentAsString();

                Vehiculo vehiculoResultado = objectMapper.readValue(jsonResultado, Vehiculo.class);

                assertEquals(vehiculoResultado.getMatricula(), vehiculoDto.getMatricula());
                assertEquals(vehiculoResultado.getPaisMatricula(), vehiculoDto.getPaisMatricula());
        }

        @Test
        void testGetAll() throws Exception {

                vehiculoService.create(vehiculoDto);

                String jsonResultado = mockMvc
                                .perform(get("/vehiculo")
                                                .with(httpBasic("user", "#|@5{31./&}(.-")).with(csrf()))
                                .andExpect(status().is2xxSuccessful())
                                .andDo(print()).andReturn().getResponse().getContentAsString();

                List<Vehiculo> vehiculosResultado = objectMapper.readValue(jsonResultado,
                                new TypeReference<List<Vehiculo>>() {
                                });

                assertTrue(!vehiculosResultado.isEmpty());
        }

        @Test
        void testSacarVehiculoDelGaraje() throws Exception {
                Vehiculo resultado = vehiculoService.create(vehiculoDto);

                Vehiculo vehiculoBaseDatos = vehiculoService.get(Long.valueOf(resultado.getId())).orElseGet(null);

                Plaza plaza = vehiculoBaseDatos.getPlaza();

                vehiculoService.estacionarVehiculo(resultado, (int) plaza.getId().longValue());

                String jsonResultado = mockMvc
                                .perform(put("/vehiculo/sacar-del-garaje/" + resultado.getId())
                                                .with(httpBasic("user", "#|@5{31./&}(.-")).with(csrf()))
                                .andExpect(status().is2xxSuccessful())
                                .andDo(print()).andReturn().getResponse().getContentAsString();

                Vehiculo vehiculoFueraGaraje = objectMapper.readValue(jsonResultado, Vehiculo.class);
                assertEquals(vehiculoFueraGaraje.getPlazaOcupada(), 0);

        }

        @Test
        void testUpdate() throws Exception {
                Vehiculo resultado = vehiculoService.create(vehiculoDto);
                resultado.setTipo("moto");

                String jsonRequest = objectMapper.writeValueAsString(resultado);
                String jsonResultado = mockMvc
                                .perform(put("/vehiculo").contentType(MediaType.APPLICATION_JSON).content(jsonRequest)
                                                .with(httpBasic("admin", "#~@A41#s#ds@(.-"))
                                                .with(csrf()))
                                .andExpect(status().is2xxSuccessful())
                                .andDo(print()).andReturn().getResponse().getContentAsString();

                Vehiculo resultadoUpdate = objectMapper.readValue(jsonResultado, Vehiculo.class);

                assertEquals(resultadoUpdate.getTipo(), "moto");

        }
}
