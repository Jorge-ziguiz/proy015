package es.cic.curso25.proy014.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso25.proy014.model.Plaza;
import es.cic.curso25.proy014.repository.PlazaRepository;

@Service
@Transactional
public class PlazaService {

    @Autowired
    private PlazaRepository PlazaRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(PlazaService.class);

    public static final int NUMERO_PLAZAS = 150;

    @Transactional(readOnly = true)
    public Optional<Plaza> get(Long Id) {
        return PlazaRepository.findById(Id);
    }

    public Plaza create(Plaza plaza) {
        return PlazaRepository.saveAndFlush(plaza);
    }

    public Plaza update(Plaza plaza) {
        return PlazaRepository.saveAndFlush(plaza);
    }

    @Transactional(readOnly = true)
    public List<Plaza> getAll() {
        return PlazaRepository.findAll();
    }

}
