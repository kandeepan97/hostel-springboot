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

    @PostMapping("/room")
    ResponseEntity<Room> createRoom(@Validated @RequestBody Room room) throws URISyntaxException {
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

}
