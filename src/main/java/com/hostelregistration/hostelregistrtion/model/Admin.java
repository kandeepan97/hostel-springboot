package com.hostelregistration.hostelregistrtion.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import java.util.Collection;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name="admin")
public class Admin implements UserDetails {

    @Id
    private String adminid;
    private String email;
    private String password;
    private String confirmPassword;
    private String role;

   // @OneToOne(mappedBy = "admin", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
   // private  Login login;



  /*  @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "login_email", nullable = false)
    private Login login;
*/

    public String getadminid() {
        return adminid;
    }

    public Object getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String encode) {
    }
}




