package com.example.websmartspending.repository;

import com.example.websmartspending.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface StudentRepository extends JpaRepository <Student, Integer> {
}
