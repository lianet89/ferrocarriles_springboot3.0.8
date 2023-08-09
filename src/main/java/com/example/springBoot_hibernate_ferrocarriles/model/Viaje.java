package com.example.springBoot_hibernate_ferrocarriles.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name="Viaje")
public class Viaje {
	
	@Schema(name = "Identifier number.", example = "1")
	@Id	
	private int numeroViaje;
	
	@Schema(name = "Itinerary.", example = "Havana-Matance")
	@OneToOne (cascade = CascadeType.ALL)
	@JoinColumn(name="itinerario", referencedColumnName="numeroIdentificacion")
    Itinerario itinerario;
	
	@Schema(name = "Traction equipment.")
	@OneToOne
	@JoinColumn(name="equipoDeTraccion", referencedColumnName="numeroIdentificacion")
	EquipoDeTraccion equipoDeTraccion;
	
	
    public Viaje() {
		super();
	}

	public Viaje(int numeroViaje, Itinerario itinerario, EquipoDeTraccion equipoDeTraccion) {
        this.numeroViaje = numeroViaje;
        this.itinerario = itinerario;
        this.equipoDeTraccion = equipoDeTraccion;
    }
	
    public Itinerario getItinerario() {
        return itinerario;
    }

    public void setItinerario(Itinerario itinerario) {
        this.itinerario = itinerario;
    }

	public int getNumeroViaje() {
		return numeroViaje;
	}

	public void setNumeroViaje(int numeroViaje) {
		this.numeroViaje = numeroViaje;
	}

	public EquipoDeTraccion getEquipoDeTraccion() {
		return equipoDeTraccion;
	}

	public void setEquipoDeTraccion(EquipoDeTraccion equipoDeTraccion) {
		this.equipoDeTraccion = equipoDeTraccion;
	}
	
	
}
