package es.cic.curso25.proy014.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.cic.curso25.proy014.dto.CrearVehiculoDto;
import es.cic.curso25.proy014.globaexception.VehiculoException;
import es.cic.curso25.proy014.model.Vehiculo;
import es.cic.curso25.proy014.service.VehiculoService;

@RestController
@RequestMapping(path = "vehiculo")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    private static final  Logger LOGGER = LoggerFactory.getLogger(VehiculoController.class);

    @PreAuthorize("hasRole('USER')")
    @PostMapping()
    public ResponseEntity<Vehiculo> create(@RequestBody CrearVehiculoDto vehiculo) {

        if (vehiculoService.getByMatriculaAndPais(vehiculo.getMatricula(), vehiculo.getPaisMatricula()).isPresent()) {
            LOGGER.error("se intento insertar un registro que existe");
            throw new VehiculoException("no se puede crear un vehicula que ya existe");
        }
        Vehiculo resultado = vehiculoService.create(vehiculo);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<Vehiculo> get(@PathVariable long id) {
        Vehiculo vehiculo = vehiculoService.get(Long.valueOf(id))
                .orElseThrow(() -> new VehiculoException("el vehiculo no existe"));
        return ResponseEntity.ok().body(vehiculo);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping()
    public ResponseEntity<List<Vehiculo>> getAll() {
        return ResponseEntity.ok().body(vehiculoService.getAll());
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("estacionar/{id}")
    public ResponseEntity<Vehiculo> estacionarVehiculo(@PathVariable long id, @RequestParam int plaza) {
        Vehiculo vehiculo = vehiculoService.get(Long.valueOf(id))
                .orElseThrow(() -> new VehiculoException("no se puede estacionar un vehiculo que no este registrado"));
        Vehiculo vehiculoResultado = vehiculoService.estacionarVehiculo(vehiculo, plaza);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(vehiculoResultado);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("sacar-del-garaje/{id}")
    public ResponseEntity<Vehiculo> sacarVehiculoDelGaraje(@PathVariable long id) {
        Vehiculo vehiculo = vehiculoService.get(Long.valueOf(id))
                .orElseThrow(
                        () -> new VehiculoException("no se puede sacar del garaje un vehiculo que no este registrado"));
        Vehiculo vehiculoResultado = vehiculoService.sacarVehiculoDelGaraje(vehiculo);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(vehiculoResultado);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("archivar/{id}")
    public ResponseEntity<Vehiculo> archivarElVehiculo(@PathVariable long id) {
        Vehiculo vehiculo = vehiculoService.get(Long.valueOf(id))
                .orElseThrow(
                        () -> new VehiculoException("no se puede arhivar un vehiculo no registrado"));
        Vehiculo vehiculoResultado = vehiculoService.archivarVehiculo(vehiculo, true);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(vehiculoResultado);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("des-archivar/{id}")
    public ResponseEntity<Vehiculo> desArchivarElVehiculo(@PathVariable long id) {
        Vehiculo vehiculo = vehiculoService.get(Long.valueOf(id))
                .orElseThrow(
                        () -> new VehiculoException("no se puede des-archivar un vehiculo no registrado"));
        Vehiculo vehiculoResultado = vehiculoService.archivarVehiculo(vehiculo, false);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(vehiculoResultado);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping()
    public ResponseEntity<Vehiculo> update(@RequestBody Vehiculo vehiculo) {
        if (vehiculoService.get(Long.valueOf(vehiculo.getid())) == null) {
            throw new VehiculoException("no se puede actualizar un vehiculo no registrado");
        }
        Vehiculo vehiculoUpdate = vehiculoService.update(vehiculo);
        return ResponseEntity.ok().body(vehiculoUpdate);
    }
}
