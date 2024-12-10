package com.example.SecurityG129.repository;

import com.example.SecurityG129.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
}
