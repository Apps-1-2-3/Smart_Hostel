package com.rvce.smarthostel.repository;

import com.rvce.smarthostel.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    List<Student> findByHostelNo(String hostelNo);
    List<Student> findByRoomNo(String roomNo);
    
    @Query("SELECT s FROM Student s WHERE s.hostelNo = :hostelNo AND s.roomNo LIKE :floor%")
    List<Student> findByHostelAndFloor(@Param("hostelNo") String hostelNo, @Param("floor") String floor);
    
    @Query("SELECT COUNT(s) FROM Student s WHERE s.hostelNo = :hostelNo")
    Long countByHostel(@Param("hostelNo") String hostelNo);
}
