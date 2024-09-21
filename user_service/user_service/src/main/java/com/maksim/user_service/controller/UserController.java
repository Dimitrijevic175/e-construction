package com.maksim.user_service.controller;

import com.maksim.user_service.domain.Worker;
import com.maksim.user_service.dto.*;
import com.maksim.user_service.security.CheckSecurity;
import com.maksim.user_service.service.UserService;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Login")
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> loginUser(@RequestBody @Valid TokenRequestDto tokenRequestDto) throws NotFoundException {
        return new ResponseEntity<>(userService.login(tokenRequestDto), HttpStatus.OK);
    }

    @GetMapping("/allUsers")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Page<UserDto>> getAllUsers(@RequestHeader("Authorization") String authorization,
                                                     Pageable pageable) {

        return new ResponseEntity<>(userService.findAll(pageable), HttpStatus.OK);
    }

    @PostMapping("/add-client")
//    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<ClientDto> addClient(@RequestBody @Valid UserCreateDto createDto) {
        ClientDto clientDto = userService.addClient(createDto);
        return new ResponseEntity<>(clientDto, HttpStatus.CREATED);
    }

    @PostMapping("/add-worker")
//    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<WorkerDto> addWorker(@RequestBody @Valid WorkerCreateDto createDto) {
        return new ResponseEntity<>(userService.addWorker(createDto), HttpStatus.CREATED);
    }

    @GetMapping("/all-clients")
    public ResponseEntity<Page<ClientDto>> getAllClients(Pageable pageable) {
        return new ResponseEntity<>(userService.findAllClients(pageable), HttpStatus.OK);
    }

    @GetMapping("/all-workers")
    public ResponseEntity<Page<WorkerDto>> getAllWorkers(Pageable pageable) {
        return new ResponseEntity<>(userService.findAllWorkers(pageable), HttpStatus.OK);
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable Long id) throws NotFoundException {
        ClientDto clientDto = userService.findClient(id);
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }

    @GetMapping("/worker/{id}")
    public ResponseEntity<WorkerDto> getWorkerById(@PathVariable Long id) throws NotFoundException {
        WorkerDto workerDto = userService.findWorker(id);
        return new ResponseEntity<>(workerDto, HttpStatus.OK);
    }


    @PostMapping("/active/{id}")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Void> toggleActiveStatus(@RequestHeader("Authorization") String authorization,@PathVariable Long id, @RequestBody boolean active) throws NotFoundException {
        userService.toggleActiveStatus(id, active);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/client/{id}")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Void> deleteClient(@RequestHeader("Authorization") String authorization, @PathVariable Long id) throws NotFoundException {
        userService.deleteClient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/worker/{id}")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Void> deleteWorker(@RequestHeader("Authorization") String authorization, @PathVariable Long id) throws NotFoundException {
        userService.deleteWorker(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{string}/activate")
    public ResponseEntity<Void> activateUser(@PathVariable("string") String string) throws NotFoundException {
        return new ResponseEntity<>(userService.activateUser(string), HttpStatus.OK);
    }



    @GetMapping("/client/credentials")
    public ResponseEntity<ClientDto> getClientByUsernameAndPassword(@RequestParam String username, @RequestParam String password) throws NotFoundException {
        ClientDto clientDto = userService.findClientByUsernameAndPassword(username, password);
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }

}
