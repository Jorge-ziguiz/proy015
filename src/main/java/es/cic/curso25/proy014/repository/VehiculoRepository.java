package es.cic.curso25.proy014.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.cic.curso25.proy014.model.Vehiculo;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    @Query("SELECT v FROM Vehiculo v WHERE v.Matricula = :matricula AND v.PaisMatricula = :pais")
    Optional<Vehiculo> findByMatriculaAndPais(@Param("matricula") String matricula, @Param("pais") String pais);



    @Query("SELECT v.Estacionado FROM Vehiculo v WHERE v.Id = :id")
    boolean findByIdEstaEstacionado(@Param("id") Long id);
}
