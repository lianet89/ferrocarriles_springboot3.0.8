package com.example.springBoot_hibernate_ferrocarriles.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.springBoot_hibernate_ferrocarriles.exception.ResourceNotFoundException;
import com.example.springBoot_hibernate_ferrocarriles.model.Itinerario;
import com.example.springBoot_hibernate_ferrocarriles.repository.ItinerarioRepository;
import com.example.springBoot_hibernate_ferrocarriles.service.ItinerarioService;


public class ItinerarioServiceTest {
	
	@InjectMocks
    private ItinerarioService itinerarioService;
	
	@Mock
    private ItinerarioRepository itinerarioRepository;
    
		
	@BeforeEach 
	public void setUp() { 
		MockitoAnnotations.openMocks(this); 
	}
	 
	@Test
    void getAllItinerarios_WhenItinerariesExist_ReturnsListOfItinerarios() {
        // Given
        Itinerario mockItinerario = new Itinerario();
        
        mockItinerario.setNumeroIdentificacion(33);
        mockItinerario.setCantidadKilometros(148);
        mockItinerario.setCantidadVagones(2);
        mockItinerario.setProvinciaOrigen("HAB");
        mockItinerario.setProvinciaDestino("PRI");  
        
        List<Itinerario> mockItinerarios = Collections.singletonList(mockItinerario);

        when(itinerarioRepository.findAll()).thenReturn(mockItinerarios);

        // When
        List<Itinerario> result = itinerarioService.getAllItinerarios();

        // Then
        verify(itinerarioRepository, times(1)).findAll();
        assertFalse(result.isEmpty());
        assertEquals(mockItinerarios, result);
    }

    @Test
    void getAllItinerarios_WhenNoItinerariesExist_ThrowsResourceNotFoundException() {
        // Given
        when(itinerarioRepository.findAll()).thenReturn(Collections.emptyList());

        // When/Then
        assertThrows(ResourceNotFoundException.class, () -> itinerarioService.getAllItinerarios());
        verify(itinerarioRepository, times(1)).findAll();
    }
    
    @Test
    public void testGetItinerarioById() {
        // Given
        Long id = 1L;
        Itinerario mockItinerario = new Itinerario();
        mockItinerario.setNumeroIdentificacion(Integer.parseInt(id.toString()));

        // When
        when(itinerarioRepository.findById(id)).thenReturn(Optional.of(mockItinerario));
        
        // Then
        try {
            Itinerario result = itinerarioService.getItinerarioById(id);
            assertEquals(id, result.getNumeroIdentificacion());
        } catch (ResourceNotFoundException ex) {
            fail("Expected to find an itinerary, but got ResourceNotFoundException");
        }
    }

    @Test
    public void testGetItinerarioByIdNotFound() {
        // Given
        Long id = 2L;

        // When
        when(itinerarioRepository.findById(id)).thenReturn(Optional.empty());

        // Then
        assertThrows(ResourceNotFoundException.class, () -> {
            itinerarioService.getItinerarioById(id);
        });
    }
    
    @Test
    public void testAddItinerario() {
        // Given
        Itinerario itinerarioToAdd = new Itinerario();
        itinerarioToAdd.setNumeroIdentificacion(100); // Assuming you set an ID before saving
        itinerarioToAdd.setCantidadKilometros(148);
        itinerarioToAdd.setCantidadVagones(2);
        itinerarioToAdd.setProvinciaOrigen("HAB");
        itinerarioToAdd.setProvinciaDestino("PRI");  

        // When
        when(itinerarioRepository.save(itinerarioToAdd)).thenReturn(itinerarioToAdd);
        Itinerario result = itinerarioService.addItinerario(itinerarioToAdd);

        // Then
        assertNotNull(result, "Expected a non-null Itinerario object");
        assertEquals(itinerarioToAdd, result, "Returned Itinerario should be the same as the one added");
    }
    
    @Test
    public void testUpdateItinerario() {
        // Given
        Long id = 1L;
        Itinerario existingItinerario = new Itinerario();
        existingItinerario.setNumeroIdentificacion(Integer.parseInt(id.toString()));
        
        Itinerario updatedItinerario = new Itinerario();
        updatedItinerario.setNumeroIdentificacion(Integer.parseInt(id.toString()));
        updatedItinerario.setCantidadKilometros(148);
        updatedItinerario.setCantidadVagones(2);
        updatedItinerario.setProvinciaOrigen("HAB");
        updatedItinerario.setProvinciaDestino("PRI"); 
        
        // When
        when(itinerarioRepository.findById(id)).thenReturn(Optional.of(existingItinerario));
        when(itinerarioRepository.save(updatedItinerario)).thenReturn(updatedItinerario);
        
        // Then
        try {
            Itinerario result = itinerarioService.updateItinerario(id, updatedItinerario);
            assertEquals(updatedItinerario, result, "Returned Itinerario should be the updated one");
        } catch (ResourceNotFoundException ex) {
            fail("Expected to find an itinerary, but got ResourceNotFoundException");
        } catch (Exception ex) {
            fail("Unexpected exception: " + ex.getMessage());
        }
    }

    @Test
    public void testUpdateItinerarioNotFound() {
        // Given
        Long id = 2L;
        Itinerario updatedItinerario = new Itinerario();
        updatedItinerario.setNumeroIdentificacion(Integer.parseInt(id.toString()));
        
        // When
        when(itinerarioRepository.findById(id)).thenReturn(Optional.empty());

        // Then
        assertThrows(ResourceNotFoundException.class, () -> {
            itinerarioService.updateItinerario(id, updatedItinerario);
        });
    }
    
    @Test
    public void testDeleteItinerario_WhenItineraryExists_ThenItIsDeleted() {
        // Given
        Long idToDelete = 1L;
        when(itinerarioRepository.findById(idToDelete)).thenReturn(Optional.of(new Itinerario()));

        // When
        itinerarioService.deleteItinerario(idToDelete);

        // Then
        verify(itinerarioRepository).deleteById(idToDelete);
    }

    @Test
    public void testDeleteItinerario_WhenItineraryDoesNotExist_ThenResourceNotFoundExceptionThrown() throws ResourceNotFoundException{
        // Given
        Long idToDelete = 200L;         

        // When   
        when(itinerarioRepository.findById(idToDelete)).thenReturn(Optional.empty());
       
       // Then 
        assertThrows(ResourceNotFoundException.class, () -> itinerarioService.deleteItinerario(idToDelete));
       
    }
	
}



