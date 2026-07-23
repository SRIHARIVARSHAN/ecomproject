package com.springboot.ecomproject.repository;

import com.springboot.ecomproject.enums.JobTitle;
import com.springboot.ecomproject.model.Executive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExecutiveRepository extends JpaRepository<Executive,Long> {

    List<Executive> findByJobTitle(JobTitle jobTitle);

    Executive findByUserUsername(String executiveUsername);
}
