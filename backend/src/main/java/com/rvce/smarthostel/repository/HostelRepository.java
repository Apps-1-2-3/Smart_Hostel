package com.rvce.smarthostel.repository;

import com.rvce.smarthostel.entity.Hostel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostelRepository extends JpaRepository<Hostel, String> {
}
