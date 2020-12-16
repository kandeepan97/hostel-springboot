package com.hostelregistration.hostelregistrtion.services;

import com.hostelregistration.hostelregistrtion.model.Warden;
import com.hostelregistration.hostelregistrtion.repository.WardenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomWardenDetailService implements UserDetailsService {
    @Autowired
    private WardenRepository wardenRepository;

    @Override
    public Warden loadUserByUsername(String email) throws UsernameNotFoundException {
        Warden warden = wardenRepository.findByEmail(email);
        if (warden == null) new UsernameNotFoundException("user not found");
        return warden;

    }

    @Transactional
    public Warden loadUserByWardenid(String wardenid){
        Warden warden = wardenRepository.getByWardenid(wardenid);
        if (warden == null) new UsernameNotFoundException("user not found");
        return warden;
    }
}
