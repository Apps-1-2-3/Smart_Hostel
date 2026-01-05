package com.rvce.smarthostel.service;

import com.rvce.smarthostel.dto.StudentDTO;
import com.rvce.smarthostel.entity.Student;
import com.rvce.smarthostel.entity.StudentPhoneNo;
import com.rvce.smarthostel.entity.Attendance;
import com.rvce.smarthostel.repository.StudentRepository;
import com.rvce.smarthostel.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final AttendanceRepository attendanceRepository;
    
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    public Optional<StudentDTO> getStudentById(String studentId) {
        return studentRepository.findById(studentId).map(this::toDTO);
    }
    
    public List<StudentDTO> getStudentsByHostel(String hostelNo) {
        return studentRepository.findByHostelNo(hostelNo).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    public List<StudentDTO> getStudentsByRoom(String roomNo) {
        return studentRepository.findByRoomNo(roomNo).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }
    
    public Student updateStudent(String studentId, Student updatedStudent) {
        return studentRepository.findById(studentId)
            .map(student -> {
                student.setFirstName(updatedStudent.getFirstName());
                student.setMiddleName(updatedStudent.getMiddleName());
                student.setLastName(updatedStudent.getLastName());
                student.setRoomNo(updatedStudent.getRoomNo());
                student.setHostelNo(updatedStudent.getHostelNo());
                return studentRepository.save(student);
            })
            .orElseThrow(() -> new RuntimeException("Student not found"));
    }
    
    public void deleteStudent(String studentId) {
        studentRepository.deleteById(studentId);
    }
    
    public Long getTotalStudentCount() {
        return studentRepository.count();
    }
    
    private StudentDTO toDTO(Student student) {
        Optional<Attendance> latestAttendance = attendanceRepository.findLatestByStudent(student.getStudentId());
        String status = latestAttendance.map(a -> a.getType().name()).orElse("UNKNOWN");
        
        String phoneNumber = student.getPhoneNumbers() != null && !student.getPhoneNumbers().isEmpty()
            ? student.getPhoneNumbers().stream()
                .filter(StudentPhoneNo::getIsPrimary)
                .findFirst()
                .map(StudentPhoneNo::getPhoneNo)
                .orElse(student.getPhoneNumbers().get(0).getPhoneNo())
            : null;
        
        return StudentDTO.builder()
            .studentId(student.getStudentId())
            .firstName(student.getFirstName())
            .middleName(student.getMiddleName())
            .lastName(student.getLastName())
            .fullName(student.getFullName())
            .roomNo(student.getRoomNo())
            .hostelNo(student.getHostelNo())
            .status(status)
            .phoneNumber(phoneNumber)
            .build();
    }
}
