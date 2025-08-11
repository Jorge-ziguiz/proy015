package es.cic.curso25.proy014.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso25.proy014.repository.VehiculoRepository;
import es.cic.curso25.proy014.dto.CrearVehiculoDto;
import es.cic.curso25.proy014.globaException.PlazaException;
import es.cic.curso25.proy014.globaException.VehiculoException;
import es.cic.curso25.proy014.model.Multa;
import es.cic.curso25.proy014.model.Plaza;
import es.cic.curso25.proy014.model.Vehiculo;

@Service
@Transactional
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private PlazaService plazaService;

    @Autowired
    private MultaService multaService;

    private final static Logger LOGGER = LoggerFactory.getLogger(VehiculoService.class);

    @Transactional(readOnly = true)
    public Optional<Vehiculo> get(Long Id) {
        return vehiculoRepository.findById(Id);
    }

    @Transactional(readOnly = true)
    public Optional<Vehiculo> getByMatriculaAndPais(String matricula, String pais) {
        return vehiculoRepository.findByMatriculaAndPais(matricula, pais);
    }

    public Vehiculo create(CrearVehiculoDto vehiculo) {

        Vehiculo nuevoVehiculo = new Vehiculo();
        nuevoVehiculo.setMatricula(vehiculo.getMatricula());
        nuevoVehiculo.setPaisMatricula(vehiculo.getPaisMatricula());
        nuevoVehiculo.setTipo(vehiculo.getTipo());
        nuevoVehiculo.setPlazaOcupada(0);

        Vehiculo vehiculoConPLaza = asignarPlazaVehiculo(nuevoVehiculo);

        return vehiculoRepository.saveAndFlush(vehiculoConPLaza);
    }

    public Vehiculo update(Vehiculo vehiculo) {
        return vehiculoRepository.saveAndFlush(vehiculo);
    }

    public Vehiculo estacionarVehiculo(Vehiculo vehiculo, int plaza) {

        if (plaza > PlazaService.NUMERO_PLAZAS || plaza < 1) {
            throw new PlazaException("la plaza no exsite");

        } else if (vehiculo.getPlazaOcupada() != 0) {
            throw new VehiculoException("el vehiculo ya esta estacionado");

        } else if (!vehiculo.getPlaza().getId().equals(Long.valueOf(plaza))) {
            Vehiculo vehiculoMultado = multarVehiculo(vehiculo);
            vehiculoRepository.saveAndFlush(vehiculoMultado);
            throw new VehiculoException("el vehiculo se ha estacionado en una plaza no asignada");
        }

        Plaza plazaOcupada = plazaService.get(Long.valueOf(plaza)).orElse(null);
        if (!plazaOcupada.isEstaLibre()) {
            throw new PlazaException("la plaza esta ocupada");
        }

        plazaService.ocuparPlaza(plazaOcupada.getId(), false);
        vehiculo.setPlazaOcupada(plaza);

        return vehiculoRepository.saveAndFlush(vehiculo);
    }

    public Vehiculo sacarVehiculoDelGaraje(Vehiculo vehiculo) {
        Plaza plaza = plazaService.get(Long.valueOf(vehiculo.getPlazaOcupada())).orElse(null);
        if (vehiculo.getPlazaOcupada() == 0) {
            throw new VehiculoException("el vehiculo no esta en el garaje");
        }
        if (!vehiculo.getMultas().isEmpty()) {
            calcularImporte(vehiculo);
        }
        plazaService.ocuparPlaza(plaza.getId(), true);
        vehiculo.setPlazaOcupada(0);

        return vehiculoRepository.saveAndFlush(vehiculo);
    }

    public Vehiculo archivarVehiculo(Vehiculo vehiculo, boolean archivar) {
        vehiculo.setEstaArchivado(archivar);
        return vehiculoRepository.saveAndFlush(vehiculo);
    }

    @Transactional(readOnly = true)
    public List<Vehiculo> getAll() {
        return vehiculoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Vehiculo> getAllActivo() {
        return vehiculoRepository.findAll();
    }

    private Vehiculo asignarPlazaVehiculo(Vehiculo vehiculo) {
        List<Plaza> plazas = plazaService.getAll();
        for (int i = 0; i < plazas.size(); i++) {
            Plaza plaza = plazas.get(i);

            if (plaza.getVehiculos().size() < 5) {
                vehiculo.setPlaza(plaza);
                break;
            }
        }
        return vehiculo;
    }

    private Vehiculo multarVehiculo(Vehiculo vehiculo) {
        // le resto un rango de diez para que en los test puede comprobar los días
        LocalDate fechaMulta = LocalDate.now().minusDays((int) Math.floor(Math.random() * 9) + 1);

        Multa multa = new Multa();
        multa.setEstaPagada(false);
        multa.setFecha(fechaMulta);
        vehiculo.getMultas().add(multa);

        return vehiculo;
    }

    private double calcularImporte(Vehiculo vehiculo) {
        return multaService.calcularImporte(vehiculo);
    }

}
