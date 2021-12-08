package com.hostelregistration.hostelregistrtion.conroller;


import com.hostelregistration.hostelregistrtion.model.Room;
import com.hostelregistration.hostelregistrtion.repository.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RoomController {
    private RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository) {
        super();
        this.roomRepository = roomRepository;
    }

    @GetMapping("/rooms")
    Collection<Room> rooms() {
        return roomRepository.findAll();
    }

    @GetMapping("/rooms/{email}")
    Collection<Room> roomsOfWarden(@PathVariable String email) {
        List<Room> allAvailableRooms = roomRepository.findAll();
        List<Room> result = new LinkedList<>();
        for (Room each : allAvailableRooms) {
            if (each.getHostel().getEmail().equals(email)) {
                result.add(each);
            }
        }
        return result;
    }

    @PostMapping("/room")
    ResponseEntity<Room> createRoom(@RequestBody Room room) throws URISyntaxException {
        room.setAvailableBeds(room.getNumberOfBeds());
        Room result = roomRepository.save(room);
        return ResponseEntity.created(new URI("/api/room" + result.getROOMID())).body(result);
    }
    @GetMapping("/room/{id}")
    ResponseEntity<?> getRoom(@PathVariable String id){
        Optional<Room> room =roomRepository.findById(id);
        return room.map(response -> ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PutMapping("/room")
    ResponseEntity<Room> updateRoom(@Validated @RequestBody Room room){
        Room result= roomRepository.save(room);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/room/{id}")
    ResponseEntity<?> deleteRoom(@PathVariable String id){
        roomRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/room/{id}")
    ResponseEntity<Room> updateNumberOfBedsInRoom(@PathVariable String id){
        Optional<Room> optionalResult= roomRepository.findById(id);
        Room result = optionalResult.get();
        result.setAvailableBeds(result.getAvailableBeds() - 1);
        roomRepository.save(result);
        return ResponseEntity.ok().body(result);
    }

    static void roomAvalabilty() {


    }
}

