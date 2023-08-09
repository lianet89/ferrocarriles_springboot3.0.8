package com.example.springBoot_hibernate_ferrocarriles.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class LocomotiveDto {
	@NotNull
	protected int numeroIdentificacion;
	
	@Positive @NotNull
    protected int kilometrajeRecorrido;
	
	@Positive @NotNull
    protected int potenciaMotor;
	
	@Positive @NotNull
    protected int lineaDeTrenes;
	
	@NotBlank @NotNull 
	private  String marcaFabricante;

	public LocomotiveDto() {
		super();
	}

	public LocomotiveDto(int numeroIdentificacion, int kilometrajeRecorrido, int potenciaMotor, int lineaDeTrenes,
			String marcaFabricante) {
		super();
		this.numeroIdentificacion = numeroIdentificacion;
		this.kilometrajeRecorrido = kilometrajeRecorrido;
		this.potenciaMotor = potenciaMotor;
		this.lineaDeTrenes = lineaDeTrenes;
		this.marcaFabricante = marcaFabricante;
	}

	public int getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(int numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	public int getKilometrajeRecorrido() {
		return kilometrajeRecorrido;
	}

	public void setKilometrajeRecorrido(int kilometrajeRecorrido) {
		this.kilometrajeRecorrido = kilometrajeRecorrido;
	}

	public int getPotenciaMotor() {
		return potenciaMotor;
	}

	public void setPotenciaMotor(int potenciaMotor) {
		this.potenciaMotor = potenciaMotor;
	}

	public int getLineaDeTrenes() {
		return lineaDeTrenes;
	}

	public void setLineaDeTrenes(int lineaDeTrenes) {
		this.lineaDeTrenes = lineaDeTrenes;
	}

	public String getMarcaFabricante() {
		return marcaFabricante;
	}

	public void setMarcaFabricante(String marcaFabricante) {
		this.marcaFabricante = marcaFabricante;
	}
	
	

}
