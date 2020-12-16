package com.hostelregistration.hostelregistrtion.services;



import com.hostelregistration.hostelregistrtion.model.Admin;
import com.hostelregistration.hostelregistrtion.model.Warden;
import com.hostelregistration.hostelregistrtion.repository.AdminRepository;
import com.hostelregistration.hostelregistrtion.repository.WardenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Admin saveAdmin (Admin newAdmin){
        newAdmin.setPassword(bCryptPasswordEncoder.encode(newAdmin.getPassword()));
        return adminRepository.save(newAdmin);
    }
}


