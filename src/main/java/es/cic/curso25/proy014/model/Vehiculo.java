package es.cic.curso25.proy014.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;


@Entity
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @Column(nullable = false)
    private String Matricula;
    
    @Column(nullable = false)
    private String PaisMatricula;

    private String Tipo;


    @Column(nullable = false)
    private boolean estaArchivado;

    private int PlazaOcupada; 

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.MERGE, CascadeType.REFRESH })
    @JsonBackReference
    private Plaza plaza;

    @OneToMany(mappedBy = "vehiculo", fetch = FetchType.LAZY, orphanRemoval = true, cascade = { CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH })
    @JsonManagedReference
    private List<Multa> multas = new ArrayList<>();

    public Long getid() {
        return id;
    }

    public void setid(Long id) {
        this.id = id;

    }

    public Long getversion() {
        return version;
    }

    public void setversion(Long version) {
          this.version = version;
    }

    public String getMatricula() {
        return Matricula;
    }

    public void setMatricula(String matricula) {
        Matricula = matricula;
    }

    public String getPaisMatricula() {
        return PaisMatricula;
    }

    public void setPaisMatricula(String paisMatricula) {
        PaisMatricula = paisMatricula;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public boolean isEstaArchivado() {
        return estaArchivado;
    }

    public void setEstaArchivado(boolean estaArchivado) {
        this.estaArchivado = estaArchivado;
    }

    public int getPlazaOcupada() {
        return PlazaOcupada;
    }

    public void setPlazaOcupada(int plazaOcupada) {
        PlazaOcupada = plazaOcupada;
    }

    public Plaza getPlaza() {
        return plaza;
    }

    public void setPlaza(Plaza plaza) {
        this.plaza = plaza;
    }

    public List<Multa> getMultas() {
        return multas;
    }

    public void setMultas(List<Multa> multas) {
        this.multas = multas;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((Matricula == null) ? 0 : Matricula.hashCode());
        result = prime * result + ((PaisMatricula == null) ? 0 : PaisMatricula.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vehiculo other = (Vehiculo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (Matricula == null) {
            if (other.Matricula != null)
                return false;
        } else if (!Matricula.equals(other.Matricula))
            return false;
        if (PaisMatricula == null) {
            if (other.PaisMatricula != null)
                return false;
        } else if (!PaisMatricula.equals(other.PaisMatricula))
            return false;
        return true;
    }

    

}
