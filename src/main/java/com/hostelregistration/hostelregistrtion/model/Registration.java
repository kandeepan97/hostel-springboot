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
    private String hostelName;
    private String roomId;
    private String paymentId;
    private String status;
    private String firstName;
    private String lastName;
    private String address;
    private String bankAddress;
    private String distance;
    private String district;
    private String province;
    private String floorNumber;
    private String numberOfBeds;
    private String hostelId;
    private String paymentDate;


    @OneToOne
    @JoinColumn(name="email",insertable = false,updatable = false)
    private Student student;

    private String email;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getREGISTRATIONID() {
        return registrationid;
    }
}
