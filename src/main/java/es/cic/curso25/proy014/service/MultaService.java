package es.cic.curso25.proy014.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso25.proy014.repository.MultaRepository;
import es.cic.curso25.proy014.model.Multa;
import es.cic.curso25.proy014.model.Vehiculo;

@Service
@Transactional
public class MultaService {

    private final static Logger LOGGER = LoggerFactory.getLogger(MultaService.class);

    public final static int IMPORTE_POR_DIA = 5;

    @Autowired
    private MultaRepository multaRepository;

    @Transactional(readOnly = true)
    public Optional<Multa> get(Long id) {
        return multaRepository.findByid(id);
    }
    @Transactional(readOnly = true)
    public List<Multa> getAll() {
        return multaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Multa> getAllbyVehiculoid(Long id) {
        return multaRepository.findMultaByidVehiculo(id);
    }

    public double calcularImporte(Vehiculo vehiculo) {
        List<Multa> multas = vehiculo.getMultas();
        double importe = 0;
        if (!multas.isEmpty()) {
            for (Multa multa : multas) {
                if (multa.getFechaFinal() == null) {
                    multa.setFechaFinal(LocalDate.now());
                }
                int diasAcomulados = Period.between(multa.getFecha(), multa.getFechaFinal()).getDays();
                double importePorMulta = diasAcomulados * IMPORTE_POR_DIA;
                importe += importePorMulta;
                multa.setTotal(importePorMulta);
                multaRepository.save(multa);
            }
        }
        return importe;
    }

}
