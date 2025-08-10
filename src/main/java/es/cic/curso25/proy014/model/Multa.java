package es.cic.curso25.proy014.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;

@Entity
public class  Multa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Version
    private Long Version;

    @ManyToOne(fetch = FetchType.LAZY,
    cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JsonBackReference
    private Vehiculo vehiculo;

    private LocalDate fecha;

    @Column(nullable = false)
    private boolean estaPagada;

    public void setId(Long id) {
        Id = id;
    }

    public void setVersion(Long version) {
        Version = version;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setEstaPagada(boolean estaPagada) {
        this.estaPagada = estaPagada;
    }

    
}
