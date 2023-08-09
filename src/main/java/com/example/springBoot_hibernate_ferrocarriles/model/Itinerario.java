package com.example.springBoot_hibernate_ferrocarriles.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Itinerario")
public class Itinerario {
	
	@Schema(name = "Identifier number.", example = "1")
	@Id	
	private int numeroIdentificacion;
	
	@Schema(name = "Amount of kilometers.", example = "100")
    private int cantidadKilometros;
	
	@Schema(name = "Amount of wagons.", example = "10")
    private int cantidadVagones;
	
	@Schema(name = "Province origin.", example = "Havana")
    private String provinciaOrigen;
	
	@Schema(name = "Province destiny.", example = "Matance")
    private String provinciaDestino;

      
    public Itinerario() {
		super();
	}

	public Itinerario(int numeroIdentificacion, int cantidadKilometros, int cantidadVagones, String provinciaOrigen, String provinciaDestino) {
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
