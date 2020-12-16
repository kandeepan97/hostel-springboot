package com.hostelregistration.hostelregistrtion.services;


import com.hostelregistration.hostelregistrtion.model.Warden;
import com.hostelregistration.hostelregistrtion.repository.WardenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class WardenService {

    @Autowired
    private WardenRepository wardenRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Warden saveWarden (Warden newWarden){
        newWarden.setPassword(bCryptPasswordEncoder.encode(newWarden.getPassword()));
        return wardenRepository.save(newWarden);
    }
}