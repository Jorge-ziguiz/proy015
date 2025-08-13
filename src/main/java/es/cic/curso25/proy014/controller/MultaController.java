package es.cic.curso25.proy014.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.cic.curso25.proy014.globaException.MultaException;
import es.cic.curso25.proy014.model.Multa;
import es.cic.curso25.proy014.service.MultaService;

@RestController
@RequestMapping(path = "multa")
public class MultaController {

    @Autowired
    private MultaService multaService;

    private final static Logger LOGGER = LoggerFactory.getLogger(MultaController.class);

    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<Multa> get(@PathVariable long id) {
        Multa multa = multaService.get(Long.valueOf(id))
                .orElseThrow(() -> new MultaException("no hay multas registradas con ese ID"));
        return ResponseEntity.ok().body(multa);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping()
    public ResponseEntity<List<Multa>> getAll() {
        return ResponseEntity.ok().body(multaService.getAll());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("maltas-por-coche/{id}")
    public ResponseEntity<List<Multa>> getAllByVehiculoId(@PathVariable long id) {
        return ResponseEntity.ok().body(multaService.getAllbyVehiculoId(Long.valueOf(id)));
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping()
    public ResponseEntity<Multa> pagarMulta(@RequestBody Multa Multa) {
        throw new UnsupportedOperationException();

    }
}
