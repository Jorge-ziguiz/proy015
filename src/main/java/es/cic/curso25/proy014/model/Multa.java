package es.cic.curso25.proy014.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.version;

@Entity
public class Multa {

    @id
    @GeneratedValue(strategy = GenerationType.idENTITY)
    private Long id;

    @version
    private Long version;

    @ManyToOne(fetch = FetchType.LAZY,
    cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JsonBackReference
    private Vehiculo vehiculo;

    @Column(nullable = false)
    private LocalDate fecha;

    private LocalDate fechaFinal;

    @Column(nullable = false)
    private boolean estaPagada;

    private double Total;

    public Long getid() {
        return id;
    }

    public void setid(Long id) {
        id = id;
    }

    public Long getversion() {
        return version;
    }

    public void setversion(Long version) {
        version = version;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDate getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(LocalDate fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public boolean isEstaPagada() {
        return estaPagada;
    }

    public void setEstaPagada(boolean estaPagada) {
        this.estaPagada = estaPagada;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    

}
