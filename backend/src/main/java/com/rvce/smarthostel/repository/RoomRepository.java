package com.rvce.smarthostel.repository;

import com.rvce.smarthostel.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
    List<Room> findByHostelNo(String hostelNo);
    List<Room> findByFloor(Integer floor);
    List<Room> findByHostelNoAndFloor(String hostelNo, Integer floor);
    
    @Query("SELECT r FROM Room r WHERE r.hostelNo = :hostelNo ORDER BY r.roomNo")
    List<Room> findAllByHostelOrdered(@Param("hostelNo") String hostelNo);
}
