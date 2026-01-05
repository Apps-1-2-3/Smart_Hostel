package com.rvce.smarthostel.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "STUDENT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    @Id
    @Column(name = "StudentID")
    private String studentId;
    
    @Column(name = "FirstName", nullable = false)
    private String firstName;
    
    @Column(name = "MiddleName")
    private String middleName;
    
    @Column(name = "LastName", nullable = false)
    private String lastName;
    
    @Column(name = "RoomNo")
    private String roomNo;
    
    @Column(name = "HostelNo")
    private String hostelNo;
    
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Attendance> attendanceRecords;
    
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MealChoice> mealChoices;
    
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StudentPhoneNo> phoneNumbers;
    
    @Transient
    public String getFullName() {
        StringBuilder name = new StringBuilder(firstName);
        if (middleName != null && !middleName.isEmpty()) {
            name.append(" ").append(middleName);
        }
        name.append(" ").append(lastName);
        return name.toString();
    }
}
