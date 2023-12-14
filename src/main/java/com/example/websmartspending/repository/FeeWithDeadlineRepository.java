package com.example.websmartspending.repository;

import com.example.websmartspending.model.FeeWithDeadline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeeWithDeadlineRepository extends JpaRepository<FeeWithDeadline, Long> {

}
