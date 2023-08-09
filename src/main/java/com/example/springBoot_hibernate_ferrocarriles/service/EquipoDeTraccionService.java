package com.example.springBoot_hibernate_ferrocarriles.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.springBoot_hibernate_ferrocarriles.model.EquipoDeTraccion;
import com.example.springBoot_hibernate_ferrocarriles.repository.EquipoDeTraccionRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EquipoDeTraccionService {	
	private final EquipoDeTraccionRepository equipoDeTraccionRepository;
	
	
	@Autowired
	public EquipoDeTraccionService(final EquipoDeTraccionRepository equipoDeTraccionRepository) {
		this.equipoDeTraccionRepository = equipoDeTraccionRepository;
	}
	
	public List<EquipoDeTraccion> getAllEquipoDeTraccion() throws Exception {
		log.info("Listing all traction equipment.");
		List<EquipoDeTraccion> equiposDeTraccion = new ArrayList<EquipoDeTraccion>();
		try {
			equipoDeTraccionRepository.findAll().forEach(equipoDeTraccion1->equiposDeTraccion.add(equipoDeTraccion1));
		} catch(Exception ex) {
			System.out.println("An error has occurred: " + ex.getMessage());
		} return equiposDeTraccion;		
	}
	
	public EquipoDeTraccion getEquipoDeTraccionById(Long id) throws Exception {
		log.info("Obtainig a traction equipment by ID:{}", id);	
		return equipoDeTraccionRepository.findById(id).get();
	}
	
	public EquipoDeTraccion addEquipoDeTraccion(EquipoDeTraccion equipoDeTraccion) throws Exception {
		log.info("Adding a traction equipment:{}", equipoDeTraccion);
		return equipoDeTraccionRepository.save(equipoDeTraccion);
	}
	
	public EquipoDeTraccion updateEquipoDeTraccion(Long id, EquipoDeTraccion equipoDeTraccion) throws Exception {
		log.info("Updating a traction equipment:{}", id);
		EquipoDeTraccion tractionEquipment = equipoDeTraccionRepository.getReferenceById(id);
		tractionEquipment.setKilometrajeRecorrido(equipoDeTraccion.getKilometrajeRecorrido());
		tractionEquipment.setLineaDeTrenes(equipoDeTraccion.getLineaDeTrenes());
		tractionEquipment.setPotenciaMotor(equipoDeTraccion.getPotenciaMotor());						
		EquipoDeTraccion tractionEquipmentSaved = equipoDeTraccionRepository.save(tractionEquipment);
		return tractionEquipmentSaved;
	}
	
	public void deleteEquipoDeTraccion(Long id) throws Exception {
		log.info("Deleting a traction equipment:{}", id);
		try {
			equipoDeTraccionRepository.deleteById(id);
		} catch(Exception ex) {
			System.out.println("An error has occurred: " + ex.getMessage());
		}
	}

}
