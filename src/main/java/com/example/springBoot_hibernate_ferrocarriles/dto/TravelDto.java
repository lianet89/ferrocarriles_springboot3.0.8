package com.example.springBoot_hibernate_ferrocarriles.dto;

import com.example.springBoot_hibernate_ferrocarriles.model.EquipoDeTraccion;
import com.example.springBoot_hibernate_ferrocarriles.model.Itinerario;

import jakarta.validation.constraints.*;

public class TravelDto {
	@NotNull
	private int numeroViaje;
	
	@NotNull	
    Itinerario itinerario;
	
	@NotNull
	EquipoDeTraccion equipoDeTraccion;
	
	public TravelDto() {
		super();
	}

	public TravelDto(@NotNull int numeroViaje, @NotNull Itinerario itinerario,
			@NotNull EquipoDeTraccion equipoDeTraccion) {
		super();
		this.numeroViaje = numeroViaje;
		this.itinerario = itinerario;
		this.equipoDeTraccion = equipoDeTraccion;
	}

	public int getNumeroViaje() {
		return numeroViaje;
	}

	public void setNumeroViaje(int numeroViaje) {
		this.numeroViaje = numeroViaje;
	}

	public Itinerario getItinerario() {
		return itinerario;
	}

	public void setItinerario(Itinerario itinerario) {
		this.itinerario = itinerario;
	}

	public EquipoDeTraccion getEquipoDeTraccion() {
		return equipoDeTraccion;
	}

	public void setEquipoDeTraccion(EquipoDeTraccion equipoDeTraccion) {
		this.equipoDeTraccion = equipoDeTraccion;
	}
	
}
