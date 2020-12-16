package com.hostelregistration.hostelregistrtion.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Data
@Table(name="payment")
public class Payment {

    @Id
    private String paymentId;
    private Long price;
    private String date;
}
