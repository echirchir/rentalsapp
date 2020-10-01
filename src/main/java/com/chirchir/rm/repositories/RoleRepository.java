package com.chirchir.rm.repositories;

import com.chirchir.rm.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
