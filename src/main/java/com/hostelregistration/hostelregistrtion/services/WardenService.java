package com.hostelregistration.hostelregistrtion.services;


import com.hostelregistration.hostelregistrtion.exceptions.WardenNotFoundException;
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

    public void updateResetPasswordToken(String token,String email) throws WardenNotFoundException {
        Warden warden = wardenRepository.findByEmail(email);

        if(warden != null){
            warden.setResetPasswordToken(token);
            wardenRepository.save(warden);
        }else{
            throw new WardenNotFoundException("Could not find any warden" + email);
        }
    }

    public Warden get(String resetPasswordToken){
        return wardenRepository.findByResetPasswordToken(resetPasswordToken);
    }

    public void updatePassword(Warden warden,String newPassword){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);

        warden.setPassword(encodedPassword);
        warden.setResetPasswordToken(null);

        wardenRepository.save(warden);
    }
}