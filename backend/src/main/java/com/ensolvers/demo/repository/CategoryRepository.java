package com.ensolvers.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.ensolvers.demo.model.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long>{

}
