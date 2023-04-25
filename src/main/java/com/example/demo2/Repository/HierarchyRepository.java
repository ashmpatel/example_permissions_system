package com.example.demo2.Repository;


import com.example.demo2.models.UserHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HierarchyRepository extends JpaRepository<UserHierarchy, Integer> {
}
