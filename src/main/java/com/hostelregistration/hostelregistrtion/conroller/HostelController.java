
package com.hostelregistration.hostelregistrtion.conroller;


        import com.hostelregistration.hostelregistrtion.model.Hostel;
        import com.hostelregistration.hostelregistrtion.model.Student;
        import com.hostelregistration.hostelregistrtion.model.Warden;
        import com.hostelregistration.hostelregistrtion.repository.HostelRepository;
        import com.hostelregistration.hostelregistrtion.repository.WardenRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.validation.annotation.Validated;
        import org.springframework.web.bind.annotation.*;

        import java.net.URI;
        import java.net.URISyntaxException;
        import java.security.Principal;
        import java.util.Collection;
        import java.util.List;
        import java.util.Optional;



@RestController
@RequestMapping("/api")
public class HostelController {
    private HostelRepository hostelRepository;

    @Autowired
    WardenRepository wardenRepository;

    public HostelController(HostelRepository hostelRepository) {
        super();
        this.hostelRepository = hostelRepository;
    }

    @GetMapping("/hostels")
    Collection<Hostel> hostels() {
        return hostelRepository.findAll();
    }

    @PostMapping("/hostel")
    ResponseEntity<Hostel> createHostel(@Validated @RequestBody Hostel hostel) throws URISyntaxException {
        Hostel result = hostelRepository.save(hostel);
        return ResponseEntity.created(new URI("/api/hostel" + result.getHostelid())).body(result);
    }

    @GetMapping("/hostel/{id}")
    ResponseEntity<?> getHostel(@PathVariable String id){
        Optional<Hostel> hostel =hostelRepository.findById(id);
        return hostel.map(response -> ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PutMapping("/hostel")
    ResponseEntity<Hostel> updateHostel(@Validated @RequestBody Hostel hostel){
        Hostel result= hostelRepository.save(hostel);
        return ResponseEntity.ok().body(result);
    }
    @DeleteMapping("/hostel/{id}")
    ResponseEntity<?> deleteHostel(@PathVariable String id){
        hostelRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/hostels/{email}")
    //List<String> getHostels(@PathVariable String wardenid){
       // List<String> hostel =hostelRepository.findByWardenid(wardenid);
       // return hostel;
       // return hostel.map(response -> ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        ResponseEntity<?> getHostels(@PathVariable String email){
            List<Hostel> hostel =hostelRepository.findByEmail(email);
            return new ResponseEntity<List<Hostel>>(hostel, HttpStatus.OK);
    }




}
