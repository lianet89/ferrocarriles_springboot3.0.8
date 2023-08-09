package com.example.springBoot_hibernate_ferrocarriles.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springBoot_hibernate_ferrocarriles.model.Itinerario;

@Repository
public interface ItinerarioRepository extends JpaRepository<Itinerario, Long>{

}
