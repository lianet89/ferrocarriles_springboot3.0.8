package com.example.springBoot_hibernate_ferrocarriles.dto;

import jakarta.validation.constraints.*;

public class MotorCarDto {
	@NotNull
	protected int numeroIdentificacion;
	
	@Positive @NotNull
    protected int kilometrajeRecorrido;
	
	@Positive @NotNull
    protected int potenciaMotor;
	
	@Positive @NotNull
    protected int lineaDeTrenes;
	
	@NotNull
	private boolean climatizado;

	public MotorCarDto() {
		super();
	}
		
	public MotorCarDto(int numeroIdentificacion, int kilometrajeRecorrido, int potenciaMotor, int lineaDeTrenes,
			boolean climatizado) {
		super();
		this.numeroIdentificacion = numeroIdentificacion;
		this.kilometrajeRecorrido = kilometrajeRecorrido;
		this.potenciaMotor = potenciaMotor;
		this.lineaDeTrenes = lineaDeTrenes;
		this.climatizado = climatizado;
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

	public boolean isClimatizado() {
		return climatizado;
	}

	public void setClimatizado(boolean climatizado) {
		this.climatizado = climatizado;
	}
    
}
