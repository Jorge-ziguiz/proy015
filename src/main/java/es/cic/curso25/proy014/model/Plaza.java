package es.cic.curso25.proy014.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;

@Entity
public class Plaza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @Version
    private Long version;

    @OneToMany(mappedBy = "plaza", fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.REMOVE }, orphanRemoval = true)

    @JsonManagedReference
    private List<Vehiculo> vehiculos = new ArrayList<>();

    @Column(nullable = false)
    private boolean estaLibre;

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

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void setVehiculos(List<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    public boolean isEstaLibre() {
        return estaLibre;
    }

    public void setEstaLibre(boolean estaLibre) {
        this.estaLibre = estaLibre;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        Plaza other = (Plaza) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;

        return true;
    }

}
