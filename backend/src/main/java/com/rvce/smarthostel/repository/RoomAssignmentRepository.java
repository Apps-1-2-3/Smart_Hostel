package com.rvce.smarthostel.repository;

import com.rvce.smarthostel.entity.RoomAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomAssignmentRepository extends JpaRepository<RoomAssignment, Long> {
    List<RoomAssignment> findByRoomRoomNo(String roomNo);
    List<RoomAssignment> findByRoomRoomNoAndIsActive(String roomNo, Boolean isActive);
    Optional<RoomAssignment> findByStudentStudentIdAndIsActive(String studentId, Boolean isActive);
    
    @Query("SELECT COUNT(ra) FROM RoomAssignment ra WHERE ra.room.roomNo = :roomNo AND ra.isActive = true")
    Long countActiveByRoom(@Param("roomNo") String roomNo);
    
    @Query("SELECT ra.room.hostelNo, COUNT(ra) FROM RoomAssignment ra WHERE ra.isActive = true GROUP BY ra.room.hostelNo")
    List<Object[]> getOccupancyByHostel();
}
