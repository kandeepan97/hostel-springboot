package com.hostelregistration.hostelregistrtion.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Locale;
import java.util.Set;

@AllArgsConstructor
@Entity
@NoArgsConstructor
@Data
@Table(name="room")
public class Room {

    @Id
    private String roomid;
    private String hostelId;
    private Long numberOfBeds;
    private Long numberOfStudentsNow;
    private String floorNumber;
    private String hostelType;
    private String year;
    private String faculty;


    public String getROOMID() {
        return roomid;
    }




}
