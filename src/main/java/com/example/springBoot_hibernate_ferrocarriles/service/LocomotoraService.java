package com.example.springBoot_hibernate_ferrocarriles.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springBoot_hibernate_ferrocarriles.exception.ResourceNotFoundException;
import com.example.springBoot_hibernate_ferrocarriles.model.Locomotora;
import com.example.springBoot_hibernate_ferrocarriles.repository.LocomotoraRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LocomotoraService {
	private final LocomotoraRepository locomotoraRepository;
	
	@Autowired
	public LocomotoraService(final LocomotoraRepository locomotoraRepository) {
		this.locomotoraRepository = locomotoraRepository;
	}
	
	public List<Locomotora> getAllLocomotora() {
		log.info("Listing all locomotives.");
		List<Locomotora> locomotives = locomotoraRepository.findAll();		 
		if(locomotives.isEmpty()) { 
			throw new ResourceNotFoundException("Not locomotives found.");
		}
		return locomotives;
	}
	
	public Locomotora getLocomotoraById(Long id) {
		log.info("Obtainig a locomotive by ID:", id);
		return locomotoraRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Not found a locomotive with id="+id));	
	}
	
	public Locomotora addLocomotora(Locomotora locomotora) {
		log.info("Adding a locomotive:{}", locomotora);
		return locomotoraRepository.save(locomotora);
	}
	
	public Locomotora updateLocomotora(Long id, Locomotora locomotora) {
		log.info("Adding a locomotive:{}", locomotora);
		locomotoraRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Not found a locomotive with id="+id));			
		return locomotoraRepository.save(locomotora);
	}
	
	public void deleteLocomotora(Long id) {
		log.info("Deleting a locomotive:{}", id);
		locomotoraRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Not found a locomotive with id="+id));
		locomotoraRepository.deleteById(id);		
	}
}
