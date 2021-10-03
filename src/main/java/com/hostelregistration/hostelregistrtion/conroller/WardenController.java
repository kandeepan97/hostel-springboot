
package com.hostelregistration.hostelregistrtion.conroller;


import com.hostelregistration.hostelregistrtion.model.Warden;
import com.hostelregistration.hostelregistrtion.payload.JWTLoginSucessResponse;
import com.hostelregistration.hostelregistrtion.payload.LoginRequest;
import com.hostelregistration.hostelregistrtion.repository.WardenRepository;
import com.hostelregistration.hostelregistrtion.security.JwtTokenProvider;
import com.hostelregistration.hostelregistrtion.services.MapValidationErrorService;
import com.hostelregistration.hostelregistrtion.services.WardenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

import static com.hostelregistration.hostelregistrtion.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api")
public class WardenController {
    private WardenRepository wardenRepository;

    public WardenController(WardenRepository wardenRepository) {
        super();
        this.wardenRepository = wardenRepository;
    }

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private WardenService wardenService;





    @GetMapping("/wardens")
    Collection<Warden> wardens(){
        return wardenRepository.findAll();
    }

    @PostMapping("/warden")
        ResponseEntity<Warden> createWarden(@Validated @RequestBody Warden warden)throws URISyntaxException {

        Warden newWarden = wardenService.saveWarden(warden);
            return ResponseEntity.created(new URI("/api/warden" +newWarden.getWardenid())).body(newWarden);

    }

    @GetMapping("/warden/{id}")
    ResponseEntity<?> getWarden(@PathVariable String id){
        Optional<Warden> warden =wardenRepository.findById(id);
        return warden.map(response -> ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/warden")
    ResponseEntity<Warden> updateWarden(@Validated @RequestBody Warden warden){
        Warden result= wardenRepository.save(warden);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/warden/email/{email}")
    ResponseEntity<?> getWardenByEmail(@PathVariable String email){
        Optional<Warden> warden = Optional.ofNullable(wardenRepository.findByEmail(email));
        return warden.map(response -> ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


   /* @PostMapping("/loginwarden")
    public ResponseEntity<?> authenticateWarden(@Valid @RequestBody LoginRequest loginRequest, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null ) return errorMap;

        Authentication authentication1  = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication1);
        String jwt = TOKEN_PREFIX + tokenProvider.generateTokenWarden(authentication1);

        return ResponseEntity.ok(new JWTLoginSucessResponse(true,jwt ,wardenRepository.findNameByEmail(loginRequest.getEmail()) ));
     }

    @DeleteMapping("/warden/{id}")
    ResponseEntity<?> deleteWarden(@PathVariable String id){
        wardenRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }


*/

}