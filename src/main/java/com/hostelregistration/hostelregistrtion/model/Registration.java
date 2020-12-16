package com.hostelregistration.hostelregistrtion.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
@Table(name="registration")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer registrationid;

    private String registrationDate;
    private String hostelId;
    private String roomId;
    private String email;
    private String bedId;
    private String paymentId;
    private String status;


    public Integer getREGISTRATIONID() {
        return registrationid;
    }
}
