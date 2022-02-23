package com.neosoft.mongo.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.neosoft.mongo.model.Reservation;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {
	
	
	Optional<Reservation> findById(Optional<Long> id);
	
	List<Reservation> findByFnameOrLnameOrEmailId(String fname,String lname,String emailId);
}
