package com.hostelregistration.hostelregistrtion.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;


@Entity
@Data
@Table(name="login")
@NoArgsConstructor
@AllArgsConstructor
public class Login {

    @Id
    private String email;
    private String password;
    private String role;

 /*  @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_email", nullable = false)
    private Student student;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Warden_email", nullable = false)
    private Warden warden;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Admin_email", nullable = false)
    private Admin admin;

  */


 /*   @OneToOne(mappedBy = "login", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
     private  Admin admin;

    @OneToOne(mappedBy = "login", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private  Warden warden;

    @OneToOne(mappedBy = "login", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private  Student student;


*/


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}