package com.hostelregistration.hostelregistrtion.conroller;


import com.hostelregistration.hostelregistrtion.model.Admin;
import com.hostelregistration.hostelregistrtion.model.Room;
import com.hostelregistration.hostelregistrtion.model.Student;
import com.hostelregistration.hostelregistrtion.payload.JWTLoginSucessResponse;
import com.hostelregistration.hostelregistrtion.payload.LoginRequest;
import com.hostelregistration.hostelregistrtion.repository.AdminRepository;
import com.hostelregistration.hostelregistrtion.security.JwtTokenProvider;
import com.hostelregistration.hostelregistrtion.services.AdminService;
import com.hostelregistration.hostelregistrtion.services.MapValidationErrorService;
import com.hostelregistration.hostelregistrtion.validater.StudentValidater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

import static com.hostelregistration.hostelregistrtion.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api")
public class AdminController {
    private AdminRepository adminRepository;

    public AdminController(AdminRepository adminRepository) {
        super();
        this.adminRepository = adminRepository;
    }

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AdminService adminService;

    @Autowired
    StudentValidater studentValidater;


    @GetMapping("/admins")
    Collection<Admin> admins(){
        return adminRepository.findAll();
    }

 /*   @PostMapping("/loginadmin")
    public ResponseEntity<?> authenticateadmin(@Valid @RequestBody LoginRequest loginRequest, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null ) return errorMap;

        Authentication authentication  = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX + tokenProvider.generateTokenAdmin(authentication);

        return ResponseEntity.ok(new JWTLoginSucessResponse(true,jwt/*,adminRepository.findNameByEmail(loginRequest.getEmail()) ));
    }
    */

    @PostMapping("/admin")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody Admin admin, BindingResult result){

       // studentValidater.validate(admin,result);

       // System.out.println(result.hasErrors());



        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null)return errorMap;

        Admin newAdmin = adminService.saveAdmin(admin);

        return  new ResponseEntity<Admin>(newAdmin, HttpStatus.CREATED);
    }

    @PutMapping("/admin")
    ResponseEntity<Admin> updateAdmin(@Valid @RequestBody Admin admin){
        Admin result= adminRepository.save(admin);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/admin/{id}")
    ResponseEntity<?> getAdmin(@PathVariable String id){
        Optional<Admin> admin =adminRepository.findById(id);
        return admin.map(response -> ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}
