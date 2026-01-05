package com.rvce.smarthostel.repository;

import com.rvce.smarthostel.entity.Attendance;
import com.rvce.smarthostel.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByStudentOrderByTimestampDesc(Student student);
    
    List<Attendance> findByStudentStudentIdOrderByTimestampDesc(String studentId);
    
    @Query("SELECT a FROM Attendance a WHERE a.student.studentId = :studentId AND a.timestamp BETWEEN :start AND :end ORDER BY a.timestamp DESC")
    List<Attendance> findByStudentAndDateRange(
        @Param("studentId") String studentId,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end
    );
    
    @Query("SELECT a FROM Attendance a WHERE a.timestamp BETWEEN :start AND :end ORDER BY a.timestamp DESC")
    List<Attendance> findByDateRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
    
    @Query("SELECT a FROM Attendance a WHERE a.student.studentId = :studentId ORDER BY a.timestamp DESC LIMIT 1")
    Optional<Attendance> findLatestByStudent(@Param("studentId") String studentId);
    
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.type = 'IN' AND a.timestamp BETWEEN :start AND :end")
    Long countCheckIns(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
    
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.type = 'OUT' AND a.timestamp BETWEEN :start AND :end")
    Long countCheckOuts(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
    
    @Query("SELECT HOUR(a.timestamp) as hour, COUNT(a) as count FROM Attendance a WHERE a.type = :type AND a.timestamp BETWEEN :start AND :end GROUP BY HOUR(a.timestamp)")
    List<Object[]> getHourlyDistribution(
        @Param("type") Attendance.AttendanceType type,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end
    );
}
