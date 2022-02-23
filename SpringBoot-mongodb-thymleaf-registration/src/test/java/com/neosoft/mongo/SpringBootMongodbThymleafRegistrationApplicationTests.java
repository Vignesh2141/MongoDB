package com.neosoft.mongo;

import com.neosoft.mongo.model.Reservation;
import com.neosoft.mongo.repository.ReservationRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neosoft.mongo.controller.ReservationController;
import com.neosoft.mongo.service.ReservationService;
import com.neosoft.mongo.service.SequenceGeneratorService;

import static org.hamcrest.Matchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class SpringBootMongodbThymleafRegistrationApplicationTests {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper mapper;
	
	@MockBean 
	ReservationService reservationService;
	
	@MockBean
	ReservationRepository repository;
	
	@Autowired
	SequenceGeneratorService sequenceGeneratorService;
	

	
	@Test
	void contextLoads() {}
	
	@Test
	public void getAllRecords() throws Exception {
		Reservation res1=new Reservation();
	
		
		res1.setId(1L);
		res1.setFname("Vignesh");
		res1.setLname("K");
		res1.setMobileNo("9098909898");
		res1.setEmailId("Vignesh@gmail.com");
		res1.setLastModified(LocalDateTime.now());
		res1.setDate(LocalDateTime.now());
		
		List<Reservation> list=new ArrayList<Reservation>(Arrays.asList(res1));
		
		
		Mockito.when(reservationService.getAllReservations()).thenReturn(list);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/reservations").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
		
		
	}
	
	@Test
	public void addRes() throws Exception {
Reservation res1=new Reservation();
	
		
		res1.setId(1L);
		res1.setFname("Vignesh");
		res1.setLname("K");
		res1.setMobileNo("9098909898");
		res1.setEmailId("Vignesh@gmail.com");
		res1.setLastModified(LocalDateTime.now());
		res1.setDate(LocalDateTime.now());
		
		
		List<Reservation> list=new ArrayList<Reservation>(Arrays.asList(res1));
		
		Mockito.when(repository.findByFnameOrLnameOrEmailId(res1.getFname(),res1.getLname(), res1.getEmailId())).thenReturn(list);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/reservations/fname/Vignesh/K/Vignesh@gmail.com").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
	

}
