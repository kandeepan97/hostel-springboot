package com.hostelregistration.hostelregistrtion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@Entity
@Data
@Table(name="hostel")
public class Hostel {
    @Id
    private String hostelid;
    private String hostelName;
    private Long numberOfRooms;
    private String email;
    private String hostelType;



    @ManyToOne
    @JoinColumn(name="wardenid",insertable = false,updatable = false)
    private Warden warden;

    private String wardenid;



    public String getWardenid() {
        return wardenid;
    }

    public void setWardenid(String wardenid) {
        this.wardenid = wardenid;
    }


    public void setHostelid(String hostelid) {
        this.hostelid = hostelid;
    }

    public String getHostelName() {
        return hostelName;
    }

    public void setHostelName(String hostelName) {
        this.hostelName = hostelName;
    }

    public Long getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Long numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public String getWardenId() {
        return email;
    }

    public void setWardenId(String wardenId) {
        this.email = email;
    }

    public String getHostelType() {
        return hostelType;
    }

    public void setHostelType(String hostelType) {
        this.hostelType = hostelType;
    }

   public Warden getWarden() {
        return warden;
    }

    public void setWarden(Warden warden) {
        this.warden = warden;
    }


    public String getHostelid() {
        return hostelid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
