package es.cic.curso25.proy014.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.cic.curso25.proy014.model.Multa;

public interface MultaRepository  extends JpaRepository<Multa,Long>{

    @Query("select m from Multa m where m.vehiculo.id = :id")
    List<Multa> findMultaByidVehiculo(@Param("id")Long id);


}
