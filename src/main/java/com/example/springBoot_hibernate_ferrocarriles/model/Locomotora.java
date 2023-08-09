package com.example.springBoot_hibernate_ferrocarriles.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@Table(name="Locomotora")
@PrimaryKeyJoinColumn(name="numeroIdentificacion")
public class Locomotora extends EquipoDeTraccion{
	
	@Schema(name = "Manufacturer's brand.", example = "DF76")
	private  String marcaFabricante;
    
	public Locomotora() {
		super();
	}

	public Locomotora(int numeroIdentificacion, int kilometrajeRecorrido, int potenciaMotor, int lineaDeTrenes, String marcaFabricante) {
        super(numeroIdentificacion, kilometrajeRecorrido, potenciaMotor, lineaDeTrenes);
        this.marcaFabricante = marcaFabricante;
    }

    public String getMarcaFabricante() {
        return marcaFabricante;
    }

    public void setMarcaFabricante(String marcaFabricante) {
        this.marcaFabricante = marcaFabricante;
    }

    @Override
    public int cantidadMaxCoches() {
        if (marcaFabricante.equalsIgnoreCase("SD50")){
            return 10;
        } else if (marcaFabricante.equalsIgnoreCase("DF76")) {
            return 15;
        } else if (marcaFabricante.equalsIgnoreCase("MLW")) {
            return 20;
        }
        return 0;
    }


}
