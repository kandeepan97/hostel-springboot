package com.hostelregistration.hostelregistrtion.conroller;

import com.hostelregistration.hostelregistrtion.payload.JWTLoginSucessResponse;
import com.hostelregistration.hostelregistrtion.payload.LoginRequest;
import com.hostelregistration.hostelregistrtion.repository.LoginRepository;
import com.hostelregistration.hostelregistrtion.repository.StudentRepository;
import com.hostelregistration.hostelregistrtion.security.JwtTokenProvider;
import com.hostelregistration.hostelregistrtion.services.CustomStudentDetailService;
import com.hostelregistration.hostelregistrtion.services.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.hostelregistration.hostelregistrtion.security.SecurityConstants.TOKEN_PREFIX;

@RequestMapping("/api")
@RestController
@CrossOrigin
public class LoginController {
    private LoginRepository loginRepository;

    public LoginController(LoginRepository loginRepository) {
        super();
        this.loginRepository = loginRepository;
    }

    @Autowired
    MapValidationErrorService mapValidationErrorService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    CustomStudentDetailService customStudentDetailService;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateStudent(@Valid @RequestBody LoginRequest loginRequest, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null ) return errorMap;

        Authentication authentication  = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String role = customStudentDetailService.loadUserByUsername(loginRequest.getEmail()).getUsername();
        System.out.println("Role is = " + role);

        if(role.equals("student")){
            String jwt = TOKEN_PREFIX + tokenProvider.generateTokenStudent(authentication);
            return ResponseEntity.ok(new JWTLoginSucessResponse(true, jwt,role));
        }
        else if(role.equals("warden")){
            String jwt = TOKEN_PREFIX + tokenProvider.generateTokenWarden(authentication);
            return ResponseEntity.ok(new JWTLoginSucessResponse(true, jwt,role));
        }
        else if(role.equals("admin")){
            String jwt = TOKEN_PREFIX + tokenProvider.generateTokenAdmin(authentication);
            return ResponseEntity.ok(new JWTLoginSucessResponse(true, jwt,role));
        }
        else{
           // String jwt = TOKEN_PREFIX + tokenProvider.generateTokenWarden(authentication);
            return ResponseEntity.ok(new JWTLoginSucessResponse(false, null,null));
        }





    }

}
