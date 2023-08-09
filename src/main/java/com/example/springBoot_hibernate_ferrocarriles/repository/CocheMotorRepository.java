package com.example.springBoot_hibernate_ferrocarriles.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springBoot_hibernate_ferrocarriles.model.CocheMotor;

@Repository
public interface CocheMotorRepository extends JpaRepository<CocheMotor, Long>{

}
