package com.neosoft.mongo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.neosoft.mongo.exception.ResourceNotFoundException;
import com.neosoft.mongo.model.Reservation;
import com.neosoft.mongo.repository.ReservationRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReservationService {

    private ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation saveReservation(Reservation reservation){
    	
        return reservationRepository.save(reservation);
    }

    public Iterable<Reservation> getAllReservations(){
        return reservationRepository.findAll();
    }

    public void deleteAllReservations(){
        reservationRepository.deleteAll();
    }

    public void deleteReservationById(Long id){
        reservationRepository.deleteById(id);
    }

    public Reservation findReservationById(Optional<Long> id)throws ResourceNotFoundException{
        Reservation res=  reservationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Resource not found for id:"+id));
         return res;
    }
}
