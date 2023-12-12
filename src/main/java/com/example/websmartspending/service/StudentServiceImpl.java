package com.example.websmartspending.service;

import com.example.websmartspending.model.Student;
import com.example.websmartspending.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
public class StudentServiceImpl implements StudentService{
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student saveStudent(Student student) {
        return null;
    }
}