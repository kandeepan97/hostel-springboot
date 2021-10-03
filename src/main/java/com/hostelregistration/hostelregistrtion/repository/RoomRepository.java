package com.hostelregistration.hostelregistrtion.repository;


import com.hostelregistration.hostelregistrtion.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, String> {


    List<Room> findAllByYear(String year);

    List<Room> findAllByGender(String gender);

    List<Room> findAllByFaculty(String faculty);

}
