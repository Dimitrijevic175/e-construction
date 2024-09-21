package com.maksim.user_service.repository;


import com.maksim.user_service.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{

    Optional<Role> findRoleByName(String name);

}
