package com.neosoft.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Document(collection = "reservations")
public class Reservation {
	@Transient
	public static final String SEQUENCE_NAME="users_sequence";
	
    @Id
    private Long id;
    @NotBlank(message="Please provide a fname.")
    private String fname;
    @NotBlank(message="Please provide a lname.")
    private String lname;
    
    @NotBlank
    private String mobileNo;
    
    @Email
    private String emailId;
    
    
    private LocalDateTime lastModified;
    @DateTimeFormat(pattern="dd-MM-yyyy HH:mm")
    @NotNull(message="Please provide a date whit the format dd-MM-yyyy HH:mm")
    private LocalDateTime date;
  
    
    public Reservation() {
    	
    }

	public Reservation(@NotBlank(message = "Please provide a fname.") String fname,
			@NotBlank(message = "Please provide a lname.") String lname, @NotBlank String mobileNo,
			@Email String emailId,
			LocalDateTime lastModified,LocalDateTime date) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.mobileNo = mobileNo;
		this.emailId = emailId;
		this.date=date;
		this.lastModified=lastModified;
		
	}
    
    
}
