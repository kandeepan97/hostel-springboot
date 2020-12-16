package com.hostelregistration.hostelregistrtion.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name="warden")
public class Warden implements UserDetails {

    @Id
    private String wardenid;
    private String email;
    private Long mobileNumber;
    private String firstName;
    private String lastName;
    private String hostelId;
    private String password;
    private String confirmPassword;
    private String role;

   @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "warden", orphanRemoval = true)
    private List<Hostel> hostels = new ArrayList<>();

   // @OneToOne(mappedBy = "warden", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    // private  Login login;

  /*  @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "login_email", nullable = false)
    private Login login;

   */

    public String getWardenid() {
        return wardenid;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return role;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public Long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setHostelId(String hostelId) {
        this.hostelId = hostelId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Hostel> getHostels() {
        return hostels;
    }

    public void setHostels(List<Hostel> hostels) {
        this.hostels = hostels;
    }


    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }


    public void setPassword(String password) {
        this.password = password;
    }




    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public String getEmail(){
        return email;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }


    public String getHostelId() { return hostelId;}



}
