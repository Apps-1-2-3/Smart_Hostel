package com.rvce.smarthostel.service;

import com.rvce.smarthostel.dto.RoomDTO;
import com.rvce.smarthostel.entity.Room;
import com.rvce.smarthostel.repository.RoomRepository;
import com.rvce.smarthostel.repository.RoomAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomAssignmentRepository roomAssignmentRepository;
    
    public List<RoomDTO> getAllRooms() {
        return roomRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    public Optional<RoomDTO> getRoomByNumber(String roomNo) {
        return roomRepository.findById(roomNo).map(this::toDTO);
    }
    
    public List<RoomDTO> getRoomsByHostel(String hostelNo) {
        return roomRepository.findByHostelNo(hostelNo).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    public List<RoomDTO> getRoomsByFloor(String hostelNo, Integer floor) {
        return roomRepository.findByHostelNoAndFloor(hostelNo, floor).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    public Map<String, Long> getOccupancyByHostel() {
        List<Object[]> results = roomAssignmentRepository.getOccupancyByHostel();
        Map<String, Long> occupancy = new HashMap<>();
        
        for (Object[] row : results) {
            String hostelNo = (String) row[0];
            Long count = (Long) row[1];
            occupancy.put(hostelNo, count);
        }
        
        return occupancy;
    }
    
    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }
    
    public Room updateRoom(String roomNo, Room updatedRoom) {
        return roomRepository.findById(roomNo)
            .map(room -> {
                room.setCapacity(updatedRoom.getCapacity());
                room.setHostelNo(updatedRoom.getHostelNo());
                room.setFloor(updatedRoom.getFloor());
                return roomRepository.save(room);
            })
            .orElseThrow(() -> new RuntimeException("Room not found"));
    }
    
    public void deleteRoom(String roomNo) {
        roomRepository.deleteById(roomNo);
    }
    
    private RoomDTO toDTO(Room room) {
        Long currentOccupancy = roomAssignmentRepository.countActiveByRoom(room.getRoomNo());
        String status = currentOccupancy >= room.getCapacity() ? "FULL" : 
                       currentOccupancy > 0 ? "PARTIAL" : "EMPTY";
        
        return RoomDTO.builder()
            .roomNo(room.getRoomNo())
            .capacity(room.getCapacity())
            .hostelNo(room.getHostelNo())
            .floor(room.getFloor())
            .currentOccupancy(currentOccupancy)
            .status(status)
            .build();
    }
}
