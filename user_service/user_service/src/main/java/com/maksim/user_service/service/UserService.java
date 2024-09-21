package com.maksim.user_service.service;

import com.maksim.user_service.dto.*;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public interface UserService {

    TokenResponseDto login(TokenRequestDto tokenRequestDto) throws NotFoundException;
    ClientDto addClient(UserCreateDto createDto);
    WorkerDto addWorker(WorkerCreateDto workerCreateDto);
    Page<UserDto> findAll(Pageable pageable);
    Page<ClientDto> findAllClients(Pageable pageable);
    Page<WorkerDto> findAllWorkers(Pageable pageable);
    ClientDto findClient(Long id) throws NotFoundException;
    WorkerDto findWorker(Long id) throws NotFoundException;
    void toggleActiveStatus(Long id, boolean active) throws NotFoundException;
    void deleteClient(Long id) throws NotFoundException;
    void deleteWorker(Long id) throws NotFoundException;
    Void activateUser(String aktivacijaString) throws NotFoundException;
    ClientDto findClientByUsernameAndPassword(String username, String password) throws NotFoundException;

}
