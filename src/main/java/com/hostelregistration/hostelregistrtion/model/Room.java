package com.hostelregistration.hostelregistrtion.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private Long numberOfBeds;
    private Long numberOfStudentsNow;
    private String floorNumber;
    private String gender;
    private String year;
    private String faculty;
    private Long availableBeds;

    @ManyToOne
    @JoinColumn(name="hostelid",insertable = false,updatable = false)
    private Hostel hostel;

    public Hostel getHostel() {
        return hostel;
    }

    public void setHostel(Hostel hostel) {
        this.hostel = hostel;
    }

    public String getHostelid() {
        return hostelid;
    }

    public void setHostelid(String hostelid) {
        this.hostelid = hostelid;
    }

    private String hostelid;

    public String getROOMID() {
        return roomid;
    }

    public Long getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(Long numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public Long getAvailableBeds() {
        return availableBeds;
    }

    public void setAvailableBeds(Long availableBeds) {
        this.availableBeds = availableBeds;
    }
}
