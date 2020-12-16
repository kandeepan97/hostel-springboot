 package com.hostelregistration.hostelregistrtion.services;

import com.hostelregistration.hostelregistrtion.model.Admin;


import com.hostelregistration.hostelregistrtion.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomAdminDetailService implements UserDetailsService {
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByEmail(email);
        if (admin == null) new UsernameNotFoundException("user not found");
        return (UserDetails) admin;

    }

    @Transactional
    public Admin loadUserByAdminid(String adminid){
        Admin admin = adminRepository.getByAdminid(adminid);
        if (admin == null) new UsernameNotFoundException("user not found");
        return admin;
    }


}

