package com.example.springBoot_hibernate_ferrocarriles.service;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.springBoot_hibernate_ferrocarriles.exception.ResourceNotFoundException;
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
	
	public List<EquipoDeTraccion> getAllEquipoDeTraccion() {
		log.info("Listing all traction equipment.");
		List<EquipoDeTraccion> equiposDeTraccion = equipoDeTraccionRepository.findAll();		
		if(equiposDeTraccion.isEmpty()) { 
			throw new ResourceNotFoundException("Not traction equipments found.");
		}
		return equiposDeTraccion;		
	}
	
	public EquipoDeTraccion getEquipoDeTraccionById(Long id) {
		log.info("Obtainig a traction equipment by ID:{}", id);	
		return equipoDeTraccionRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Not found a traction equipment with id="+id));
	}
	
	public EquipoDeTraccion addEquipoDeTraccion(EquipoDeTraccion equipoDeTraccion) {
		log.info("Adding a traction equipment:{}", equipoDeTraccion);
		return equipoDeTraccionRepository.save(equipoDeTraccion);
	}
	
	public EquipoDeTraccion updateEquipoDeTraccion(Long id, EquipoDeTraccion equipoDeTraccion) {
		log.info("Updating a traction equipment:{}", id);
		equipoDeTraccionRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Not found a traction equipment with id="+id));
		return equipoDeTraccionRepository.save(equipoDeTraccion);
	}
	
	public void deleteEquipoDeTraccion(Long id) {
		log.info("Deleting a traction equipment:{}", id);
		equipoDeTraccionRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Not found a traction equipment with id="+id));
		equipoDeTraccionRepository.deleteById(id);	
	}

}
