package com.hostelregistration.hostelregistrtion.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;


@Entity
@Data
@Table(name="student")
@NoArgsConstructor
public class Student implements UserDetails {

    @Id
    @Column(name = "studentid",unique = true)
    private String studentid;
    @Email(message = "Valid Email is required")
    @Column(name="email",unique = true)
    @NotBlank(message = "Email is required")
    private String email;
    @NonNull
    @NotBlank(message = "First Name is required")
    private String firstName;
    @NotBlank(message = "Last Name is required")
    private String lastName;
    @NotEmpty()
    @NotBlank(message = "Address is required")
    private String address;
    @NotNull(message = "Mobile Number is required")
    @Size(min=9, message = "Please Enter mobile Number without first Zero")
    private String mobileNumber;
    @NotBlank(message = "Year is required")
    private String year;
    @NotBlank(message = "Faculty is required")
    private String faculty;
    @NotBlank(message = "Department is required")
    private String department;
    private String roomId;
    private String hostelId;
    @NotBlank(message = "Gender is required")
    private String gender;
    @NotBlank(message = "Password is required")
    private String password;
    @Transient
    private String confirmPassword;
    private String role;





  //  @OneToOne(mappedBy = "student", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
  //  private  Login login;



   /* @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "login_email", nullable = false)
    private Login login; */



    /*user details interface methods*/

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

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }


    public void setPassword(String password) {
        this.password = password;
    }




    public String getStudentid() {
        return studentid;
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

    public String getRole() {
        return role;
    }
}
