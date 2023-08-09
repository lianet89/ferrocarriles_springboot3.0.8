package com.example.springBoot_hibernate_ferrocarriles.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@Table(name="CocheMotor")
@PrimaryKeyJoinColumn(name="numeroIdentificacion")
public class CocheMotor extends EquipoDeTraccion{
	
	@Schema(name = "It is air-conditioned.", example = "true")
	private boolean climatizado;
    
	public CocheMotor() {
		super();
	}

    public CocheMotor(boolean climatizado) {
		super();
		this.climatizado = climatizado;
	}
    
	public boolean isClimatizado() {
        return climatizado;
    }

    public void setClimatizado(boolean climatizado) {
        this.climatizado = climatizado;
    }

    @Override
    public int cantidadMaxCoches() {        
        if (potenciaMotor<4000){
            return 1;
        } else if (potenciaMotor>=4000) {
            return 2;
        }
        return 0;
    }
    
}
