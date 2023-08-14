package com.example.springBoot_hibernate_ferrocarriles.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.springBoot_hibernate_ferrocarriles.exception.ResourceNotFoundException;
import com.example.springBoot_hibernate_ferrocarriles.model.EquipoDeTraccion;
import com.example.springBoot_hibernate_ferrocarriles.model.Itinerario;
import com.example.springBoot_hibernate_ferrocarriles.model.Locomotora;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReportesService {
	private final EquipoDeTraccionService equipoDeTraccionService;
	private final ItinerarioService itinerarioService;
	private final LocomotoraService locomotoraService;

	@Autowired
	public ReportesService(final EquipoDeTraccionService equipoDeTraccionService, final ItinerarioService itinerarioService,
			final LocomotoraService locomotoraService) {
		this.equipoDeTraccionService = equipoDeTraccionService;
		this.itinerarioService = itinerarioService;
		this.locomotoraService = locomotoraService;
	}
	
	@Cacheable(value = "tractionEquipmentsForItinerary")
	public List<EquipoDeTraccion> equiposAptosParaItinerario(int id) {
		log.info("Listing the traction equipments that can cover the itinerary with ID:{}", id);
		List<EquipoDeTraccion> equiposAptosParaItinerario = new ArrayList<>();
		
		if(equipoDeTraccionService.getAllEquipoDeTraccion().isEmpty()) { 
			throw new ResourceNotFoundException("Not traction equipments found.");
		}		
		for(EquipoDeTraccion equipo:equipoDeTraccionService.getAllEquipoDeTraccion()) {
			if(equipo.cantidadMaxCoches()>=itinerarioService.getItinerarioById((long) id).getCantidadVagones() && equipo.getLineaDeTrenes()==1) {
					equiposAptosParaItinerario.add(equipo);
			}
		}
		return equiposAptosParaItinerario;
	}
	
	@Cacheable(value = "locomotivesOfBrand")
	public List<Locomotora> locomotorasDeMarca(String marca) {
		log.info("Listing the locomotives that are of the brand:{}", marca);
		List<Locomotora> locomotorasDeMarca = new ArrayList<>();
		
		if(locomotoraService.getAllLocomotora().isEmpty()) { 
			throw new ResourceNotFoundException("Not locomotives found.");
		}
		for(Locomotora locomotora:locomotoraService.getAllLocomotora()) {
			if(locomotora.getMarcaFabricante().equals(marca)) {
				locomotorasDeMarca.add(locomotora);
			}			
		}
		Comparator<Locomotora> porKilometraje = Comparator.comparing(Locomotora::getKilometrajeRecorrido);
        Collections.sort(locomotorasDeMarca, porKilometraje);
        Collections.reverse(locomotorasDeMarca);	
        return locomotorasDeMarca;
	}
		
	public void findDestinations(List<Itinerario> listaItinerarios, List<String> listaAuxiliar, int cantidadTemporal, int i) {
		log.info("Listing the itineraries that have the same destination of the itinerary:{}", i);
		
		try {
			for(int j = listaItinerarios.size()-1; j>i; j--) {
				if(listaItinerarios.get(i).getProvinciaOrigen().equals(listaItinerarios.get(j).getProvinciaOrigen()) &&
						listaItinerarios.get(i).getProvinciaDestino().equals(listaItinerarios.get(j).getProvinciaDestino())) {
					listaItinerarios.remove(j);
				}else if(listaItinerarios.get(i).getProvinciaOrigen().equals(listaItinerarios.get(j).getProvinciaOrigen()) &&
						   !listaItinerarios.get(i).getProvinciaDestino().equals(listaItinerarios.get(j).getProvinciaDestino()) &&
						   !listaAuxiliar.contains(listaItinerarios.get(j).getProvinciaDestino())){				
					listaAuxiliar.add(listaItinerarios.get(j).getProvinciaDestino());
					cantidadTemporal++;
					listaItinerarios.remove(j);
				}
			}
		} catch(Exception ex) {
			System.out.println("An error has occurred: " + ex.getMessage());
		}
	}
	
	@Cacheable(value = "originMoreDestinations")
	public String originMoreDestinations() {
		log.info("Obtaining the origin province that have more destinations.");
		
		List<String> listaAuxiliar = new ArrayList<>();
		int cantidadTemporal = 0;
		int cantidadFinal = 0;		
		String origenTemporal = "";
		String origenGanador = "";
		
		List<Itinerario> listaItinerarios = itinerarioService.getAllItinerarios();			
		if(listaItinerarios.isEmpty()) {
			throw new ResourceNotFoundException("Not locomotives found.");
		}else {			
			for(int i = 0; i<listaItinerarios.size(); i++) {
				origenTemporal = listaItinerarios.get(i).getProvinciaOrigen();
				cantidadTemporal = 1;
				findDestinations(listaItinerarios, listaAuxiliar, cantidadTemporal, i);						
				if(cantidadTemporal>cantidadFinal) {
					origenGanador = origenTemporal;
					cantidadFinal = cantidadTemporal;
				}
			}
		}
		return origenGanador;
	}
	
	public String findCombination(Itinerario temporalItinerary, List<Itinerario> itineraryList, Itinerario itineraryToDelete, int id) {
		String combination = "";
		for(int i = 0; i<itineraryList.size(); i++) {
			temporalItinerary = itineraryList.get(i);
			if(temporalItinerary.getNumeroIdentificacion() != id &&
					itineraryToDelete.getProvinciaOrigen().equals(temporalItinerary.getProvinciaOrigen()) &&
					itineraryToDelete.getProvinciaDestino().equals(temporalItinerary.getProvinciaDestino())){
				combination = temporalItinerary.getProvinciaOrigen() + " - " + temporalItinerary.getProvinciaDestino() + 
						" ( " + temporalItinerary.getNumeroIdentificacion() + " ) ";
				break;
			}else if(temporalItinerary.getNumeroIdentificacion() != id &&
						itineraryToDelete.getProvinciaOrigen().equals(temporalItinerary.getProvinciaOrigen())) {
				combination = combinationOfItineraries(temporalItinerary, itineraryList, itineraryToDelete, id);
			}
			if(!combination.isBlank()) {
				break;		
			}
		}
		return combination;
	}
	
	public String combinationOfItineraries (Itinerario temporalItinerary, List<Itinerario> itineraryList, Itinerario itineraryToDelete, int id) {
		String combination = "";
		for(int j = itineraryList.size()-1; j>=0; j--) {
			if(itineraryList.get(j).getNumeroIdentificacion() != id && 
						itineraryList.get(j).getProvinciaOrigen().equals(itineraryToDelete.getProvinciaOrigen()) &&
						itineraryList.get(j).getProvinciaDestino().equals(itineraryToDelete.getProvinciaDestino())) {
					combination = itineraryList.get(j).getProvinciaOrigen() + " - " + itineraryList.get(j).getProvinciaDestino() + 
							" ( " + itineraryList.get(j).getNumeroIdentificacion() + " ) ";
					break;								
				} else if(itineraryList.get(j).getNumeroIdentificacion() != id && 
						temporalItinerary.getProvinciaDestino().equals(itineraryList.get(j).getProvinciaOrigen()) &&
						itineraryList.get(j).getProvinciaDestino().equals(itineraryToDelete.getProvinciaDestino())){
					combination = temporalItinerary.getProvinciaOrigen() + " - " + temporalItinerary.getProvinciaDestino() + 
							" , " + itineraryList.get(j).getProvinciaOrigen() + " - " + itineraryList.get(j).getProvinciaDestino() + 
							" ( " + temporalItinerary.getNumeroIdentificacion() + " - " + itineraryList.get(j).getNumeroIdentificacion() + 
							" ) ";
					break;								
				}
			}
		return combination;
	}
	
	@Cacheable(value = "combinationToCoverItinerary")
	public String coverItinerary(int id) {
		log.info("Obtaining the combination of itineraries that can cover the itinerary:{}", id);		
		Itinerario temporalItinerary = new Itinerario();
		String combination = "";
		
		try {
			Itinerario itineraryToDelete = itinerarioService.getItinerarioById((long) id);
			List<Itinerario> itineraryList = itinerarioService.getAllItinerarios();
			
			if(itineraryList.isEmpty()) {
				throw new ResourceNotFoundException("Not itineraries found.");
			} else {
				combination = findCombination(temporalItinerary, itineraryList, itineraryToDelete, id);
				if(combination.isBlank()) {
					return "No";
				}else
					return combination;	
			}
		} catch(Exception ex) {
			System.out.println("An error has occurred: " + ex.getMessage());
			return "";
		}
		
	}

}
