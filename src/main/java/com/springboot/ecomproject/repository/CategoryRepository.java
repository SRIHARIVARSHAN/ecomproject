package com.springboot.ecomproject.repository;

import com.springboot.ecomproject.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

//import java.lang.ScopedValue;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
