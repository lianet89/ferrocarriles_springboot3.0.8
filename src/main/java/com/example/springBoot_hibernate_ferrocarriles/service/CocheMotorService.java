package com.example.springBoot_hibernate_ferrocarriles.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springBoot_hibernate_ferrocarriles.model.CocheMotor;
import com.example.springBoot_hibernate_ferrocarriles.repository.CocheMotorRepository;

import java.util.ArrayList;
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
	
	public List<CocheMotor> getAllCocheMotor() throws Exception {
		log.info("Listing all motor-car.");
		List<CocheMotor> cochesMotor=new ArrayList<CocheMotor>();		
		try {
			cocheMotorRepository.findAll().forEach(cocheMotor1->cochesMotor.add(cocheMotor1));
		} catch(Exception ex) {
			System.out.println("An error has occurred: " + ex.getMessage());
		} return cochesMotor;
	}
	
	public CocheMotor getCocheMotorById(Long id) throws Exception{
		log.info("Obtainig a motor-car by ID:{}", id);
		CocheMotor cocheMotor = new CocheMotor();
		try {
			cocheMotor = cocheMotorRepository.findById(id).get();
		} catch(Exception ex) {
			System.out.println("An error has occurred: " + ex.getMessage());
		} return cocheMotor;
	}
	
	public CocheMotor addCocheMotor(CocheMotor cocheMotor) throws Exception {
		log.info("Adding a motor-car:{}", cocheMotor);
		CocheMotor cocheSalvado = new CocheMotor();
		try {
			cocheSalvado = cocheMotorRepository.save(cocheMotor);
		} catch(Exception ex) {
			System.out.println("An error has occurred: " + ex.getMessage());
		} return cocheSalvado;
	}
	
	public CocheMotor updateCocheMotor(Long id, CocheMotor cocheMotor) throws Exception {
		log.info("Updating a motor-car:{}", id);
		CocheMotor cocheSalvado = new CocheMotor();
		try {
			CocheMotor motorCar = cocheMotorRepository.getReferenceById(id);
			motorCar.setClimatizado(cocheMotor.isClimatizado());
			motorCar.setKilometrajeRecorrido(cocheMotor.getKilometrajeRecorrido());
			motorCar.setLineaDeTrenes(cocheMotor.getLineaDeTrenes());
			motorCar.setPotenciaMotor(cocheMotor.getPotenciaMotor());						
			cocheSalvado = cocheMotorRepository.save(motorCar);
		} catch(Exception ex) {
			System.out.println("An error has occurred: " + ex.getMessage());
		} return cocheSalvado;
	}
	
	public void deleteCocheMotor(Long id) throws Exception {
		log.info("Deleting a motor-car by ID:{}", id);
		try {
		cocheMotorRepository.deleteById(id);
		} catch(Exception ex) {
			System.out.println("An error has occurred: " + ex.getMessage());
		}
	}
	

}
