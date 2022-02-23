package com.neosoft.mongo.controller;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.neosoft.mongo.exception.ResourceNotFoundException;

import com.neosoft.mongo.model.Reservation;
import com.neosoft.mongo.repository.ReservationRepository;
import com.neosoft.mongo.service.ReservationService;
import com.neosoft.mongo.service.SequenceGeneratorService;

@RestController
public class ReservationController {

    private ReservationService reservationService;
    @Autowired
	private SequenceGeneratorService sequenceGeneratorService;
    
    @Autowired
     private ReservationRepository reservationRepository;
    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public Iterable<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }
    @PostMapping("/reservations")
	public Reservation createEmployeee(@Valid @RequestBody Reservation res) {
		res.setId(sequenceGeneratorService.generateSequence(Reservation.SEQUENCE_NAME));
		 res.setLastModified(LocalDateTime.now());
		return reservationRepository.save(res);
	}
    
    @PutMapping("/reservations/{id}")
    public ResponseEntity<Reservation> updateEmployee(@PathVariable(value="id") Long resId,@Valid @RequestBody Reservation resDetails)throws ResourceNotFoundException{
		reservationRepository.findById(resId)
				.orElseThrow(()-> new ResourceNotFoundException("Employee not found for Id::"+resId));
       resDetails.setId(resId);
       resDetails.setLastModified(LocalDateTime.now());
       Reservation updatedres=reservationRepository.save(resDetails);
       return ResponseEntity.ok().body(updatedres);
    }
    
    @DeleteMapping("/reservations/del/{id}")
    public void delete(@PathVariable Long id) {
    	reservationRepository.deleteById(id);
    }
    
    @GetMapping("/reservations/fname/{fname}/{lname}/{emailId}")
    public List<Reservation> search(@PathVariable String fname,@PathVariable String lname,@PathVariable String emailId){
   return 	reservationRepository.findByFnameOrLnameOrEmailId(fname,lname,emailId);
    }
    
   
}
