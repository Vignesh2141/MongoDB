package com.neosoft.mongo.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.neosoft.mongo.exception.ResourceNotFoundException;
import com.neosoft.mongo.model.Reservation;
import com.neosoft.mongo.repository.ReservationRepository;
import com.neosoft.mongo.service.ReservationService;
import com.neosoft.mongo.service.SequenceGeneratorService;



import javax.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class ReservationThymeleafController {

	 private static final Logger logger = LoggerFactory.getLogger(ReservationThymeleafController.class);
    private ReservationService reservationService;
    @Autowired
   	private SequenceGeneratorService sequenceGeneratorService;
    
    @Autowired
    public ReservationRepository reservationRepository;

    @Autowired
    public ReservationThymeleafController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations-ui")
    public String reservations(Model model,Reservation reservation,String fname) {
    	logger.debug("Saved");
        model.addAttribute("reservations", reservationService.getAllReservations());
    	
        return "reservations";
    }

    @GetMapping("/delete-reservation/{id}")
    public String removeReservation(@PathVariable("id") Long id, Model model) {
        reservationService.deleteReservationById(id);
        logger.debug("deleted");
        model.addAttribute("reservations", reservationService.getAllReservations());
        return "reservations";
    }

    @GetMapping(value = {"/edit-add-reservation/{id}", "/edit-add-reservation"})
    public String editReservation(@PathVariable("id") Optional<Long> id, Model model) {
        Reservation reservation;
		try {
			reservation = id.isPresent()?reservationService.findReservationById(id):new Reservation();
			 model.addAttribute("reservation", reservation);
			 
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        return "add-edit";
    }
    
    @GetMapping(value = {"/search/new","/search-now"})
    public String searchName(Model model,String fname,Reservation reservation) {
    	 logger.info("searches");
    	List<Reservation> res=reservationRepository.findByFnameOrLnameOrEmailId(fname, fname, fname);
    	model.addAttribute("reservation1",res);
    	return "search";
    	
    }
        

    @PostMapping("/save-reservation")
    public String editReservation(@ModelAttribute("reservation") @Valid Reservation reservation,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-edit";
        }
        reservation.setId(sequenceGeneratorService.generateSequence(Reservation.SEQUENCE_NAME));
        reservation.setLastModified(LocalDateTime.now());
        reservationService.saveReservation(reservation);
        return "redirect:reservations-ui";
    }
}
