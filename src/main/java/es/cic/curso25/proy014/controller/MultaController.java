package es.cic.curso25.proy014.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        throw new UnsupportedOperationException();

    }    


    @PreAuthorize("hasRole('USER')")
    @GetMapping()
    public ResponseEntity<List<Multa>> getAll() {
        throw new UnsupportedOperationException();

    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping()
    public ResponseEntity<Multa> update(@RequestBody Multa Multa) {
        throw new UnsupportedOperationException();

    }
}
