package com.maksim.user_service.repository;

import com.maksim.user_service.domain.Client;
import com.maksim.user_service.domain.User;
import com.maksim.user_service.domain.Worker;
import com.maksim.user_service.dto.UserDto;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {

    Optional<User> findUserByUsernameAndPassword(String username, String password);
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserById(Long id);
    @Query("SELECT c FROM Client c")
    Page<Client> findAllClients(Pageable pageable);

    @Query("SELECT w FROM Worker w")
    Page<Worker> findAllWorkers(Pageable pageable);

    Optional<Client> findClientById(Long id);
    Optional<Worker> findWorkerById(Long id);
    Optional<User> findUserByStringZaAktivaciju(String stringZaAktivaciju);

    UserDto findUserByEmail(String email) throws NotFoundException;
    Optional<Client> findByUsernameAndPassword(String username, String password);
}
