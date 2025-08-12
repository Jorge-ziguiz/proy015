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

import es.cic.curso25.proy014.globaException.PlazaException;
import es.cic.curso25.proy014.model.Plaza;
import es.cic.curso25.proy014.service.PlazaService;

@RestController
@RequestMapping(path = "plaza")
public class PlazaController {

    @Autowired
    private PlazaService plazaService;
    private final static Logger LOGGER = LoggerFactory.getLogger(PlazaController.class);

    @PreAuthorize("hasRole('USER')")
    @GetMapping()
    public ResponseEntity<List<Plaza>> getAll() {
        return ResponseEntity.ok(plazaService.getAll());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<Plaza> get(@PathVariable long id) {
        Plaza plaza = plazaService.get(Long.valueOf(id))
                .orElseThrow(() -> new PlazaException("no existen plazas con ese ID"));
        return ResponseEntity.ok(plaza);
    }

    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping()
    public ResponseEntity<Plaza> update(@RequestBody Plaza plaza) {
        if (plaza.getId() == null || (plaza.getId() != null && plazaService.get(plaza.getId()) == null)) {
            LOGGER.error("no se pude actualizar una plaza sin ID o una plaza que no exista");
            throw new PlazaException("no se pude actualizar una plaza sin ID o una plaza que no exista");
        }
        return ResponseEntity.ok(plazaService.update(plaza));
    }

}
