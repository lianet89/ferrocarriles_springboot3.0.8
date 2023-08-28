package com.example.springBoot_hibernate_ferrocarriles.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.springBoot_hibernate_ferrocarriles.cache.SpringCachingConfig;
import com.example.springBoot_hibernate_ferrocarriles.exception.ResourceNotFoundException;
import com.example.springBoot_hibernate_ferrocarriles.model.CocheMotor;
import com.example.springBoot_hibernate_ferrocarriles.repository.CocheMotorRepository;



import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CocheMotorService {	
	
	private final CocheMotorRepository cocheMotorRepository;
		
	@Autowired
	public CocheMotorService(final CocheMotorRepository cocheMotorRepositoy) {
		this.cocheMotorRepository = cocheMotorRepositoy;
	}
	
	@CachePut(value = "motorCar")
	public List<CocheMotor> getAllCocheMotor() {
		log.info("Listing all motor-car.");
		List<CocheMotor> cochesMotor = cocheMotorRepository.findAll();	
		if(cochesMotor.isEmpty()) { 
			throw new ResourceNotFoundException("Not motor-cars found.");
		}		
		return cochesMotor;	
		
	}
	
	@Cacheable(value = "motorCar")
	public CocheMotor getCocheMotorById(Long id) {
		log.info("Obtainig a motor-car by ID:{}", id);
		return cocheMotorRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Not found motor-car with id="+id));		
	}
	
	public CocheMotor addCocheMotor(CocheMotor cocheMotor) {
		log.info("Adding a motor-car:{}", cocheMotor);
		return cocheMotorRepository.save(cocheMotor);		
	}
	
	public CocheMotor updateCocheMotor(Long id, CocheMotor cocheMotor) {
		log.info("Updating a motor-car:{}", id);
		cocheMotorRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Not found motor-car with id="+id));
		return cocheMotorRepository.save(cocheMotor);
	}
	
	public void deleteCocheMotor(Long id) {
		log.info("Deleting a motor-car by ID:{}", id);
		cocheMotorRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Not found motor-car with id="+id));
		cocheMotorRepository.deleteById(id);
	}
	

}
