package com.example.springBoot_hibernate_ferrocarriles.dto;

import jakarta.validation.constraints.*;

public class ItineraryDto {
	@NotNull
	private int numeroIdentificacion;
	
	@Positive @NotNull
    private int cantidadKilometros;
	
	@Positive @NotNull
    private int cantidadVagones;
	
	@NotNull @NotBlank
    private String provinciaOrigen;
	
	@NotNull @NotBlank
    private String provinciaDestino;

    public ItineraryDto() {
		super();
	}

	public ItineraryDto(int numeroIdentificacion, int cantidadKilometros, int cantidadVagones, String provinciaOrigen, String provinciaDestino) {
        this.numeroIdentificacion = numeroIdentificacion;
        this.cantidadKilometros = cantidadKilometros;
        this.cantidadVagones = cantidadVagones;
        this.provinciaOrigen = provinciaOrigen;
        this.provinciaDestino = provinciaDestino;
    }

    public int getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(int numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public int getCantidadKilometros() {
        return cantidadKilometros;
    }

    public void setCantidadKilometros(int cantidadKilometros) {
        this.cantidadKilometros = cantidadKilometros;
    }

    public int getCantidadVagones() {
        return cantidadVagones;
    }

    public void setCantidadVagones(int cantidadVagones) {
        this.cantidadVagones = cantidadVagones;
    }

    public String getProvinciaOrigen() {
        return provinciaOrigen;
    }

    public void setProvinciaOrigen(String provinciaOrigen) {
        this.provinciaOrigen = provinciaOrigen;
    }

    public String getProvinciaDestino() {
        return provinciaDestino;
    }

    public void setProvinciaDestino(String provinciaDestino) {
        this.provinciaDestino = provinciaDestino;
    }

}
