package com.maksim.user_service.mapper;

import com.maksim.user_service.domain.Client;
import com.maksim.user_service.domain.User;
import com.maksim.user_service.domain.Worker;
import com.maksim.user_service.dto.*;
import com.maksim.user_service.repository.RoleRepository;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private RoleRepository roleRepository;

    public UserMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    // Metod za mapiranje Client u ClientDto
    public ClientDto clientToClientDto(Client client) {
        if (client == null) {
            return null;
        }
        ClientDto clientDto = new ClientDto();
        clientDto.setId(client.getId());
        clientDto.setName(client.getName());
        clientDto.setLast_name(client.getLast_name());
        clientDto.setEmail(client.getEmail());
        clientDto.setUsername(client.getUsername());
        clientDto.setPassword(client.getPassword());
        clientDto.setActive(client.isActive());
        return clientDto;
    }
    public Client userCreateDtoToClient(UserCreateDto userCreateDto){
        Client client = new Client();
        client.setName(userCreateDto.getFirst_name());
        client.setLast_name(userCreateDto.getLast_name());
        client.setUsername(userCreateDto.getUsername());
        client.setPassword(userCreateDto.getPassword());
        client.setEmail(userCreateDto.getEmail());
        client.setActive(false);
        client.setRole(roleRepository.findRoleByName("ROLE_CLIENT").get());

        return client;
    }

    public Worker userCreateDtoToWorker(UserCreateDto userCreateDto){
        Worker worker = new Worker();
        worker.setName(userCreateDto.getFirst_name());
        worker.setLast_name(userCreateDto.getLast_name());
        worker.setUsername(userCreateDto.getUsername());
        worker.setPassword(userCreateDto.getPassword());
        worker.setEmail(userCreateDto.getEmail());
        worker.setRole(roleRepository.findRoleByName("ROLE_WORKER").get());
        worker.setActive(false);

        return worker;
    }

    public UserDto UsertoUserDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setLast_name(user.getLast_name());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setActive(user.isActive());
        userDto.setPassword(user.getPassword());

        return userDto;
    }

    public WorkerDto workerToWorkerDto(Worker worker) {
        if (worker == null) {
            return null;
        }
        WorkerDto workerDto = new WorkerDto();
        workerDto.setId(worker.getId());
        workerDto.setName(worker.getName());
        workerDto.setLast_name(worker.getLast_name());
        workerDto.setEmail(worker.getEmail());
        workerDto.setUsername(worker.getUsername());
        workerDto.setPassword(worker.getPassword());
        workerDto.setContactInfo(worker.getContactInfo());
        workerDto.setProfession(worker.getProfession());
        workerDto.setActive(worker.isActive());

        return workerDto;
    }

    public Worker workerCreateDtoToWorker(WorkerCreateDto workerCreateDto) {
        if (workerCreateDto == null) {
            return null;
        }
        Worker worker = new Worker();
        worker.setName(workerCreateDto.getFirst_name());
        worker.setLast_name(workerCreateDto.getLast_name());
        worker.setUsername(workerCreateDto.getUsername());
        worker.setPassword(workerCreateDto.getPassword());
        worker.setEmail(workerCreateDto.getEmail());
        worker.setProfession(workerCreateDto.getProfession());
        worker.setContactInfo(workerCreateDto.getContactInfo());
        worker.setActive(false);
        worker.setRole(roleRepository.findRoleByName("ROLE_WORKER").get());

        return worker;
    }

    public WorkerDto workerCreateDtoToWorkerDto(WorkerCreateDto workerCreateDto) {
        if (workerCreateDto == null) {
            return null;
        }

        WorkerDto workerDto = new WorkerDto();

        // Map fields from WorkerCreateDto to WorkerDto
        workerDto.setName(workerCreateDto.getFirst_name()); // Assuming 'first_name' maps to 'name'
        workerDto.setLast_name(workerCreateDto.getLast_name());
        workerDto.setUsername(workerCreateDto.getUsername());
        workerDto.setEmail(workerCreateDto.getEmail());
        workerDto.setPassword(workerCreateDto.getPassword());
        workerDto.setProfession(workerCreateDto.getProfession());
        workerDto.setContactInfo(workerCreateDto.getContactInfo());

        // Set default values for id and active
//        workerDto.setId(null); // id will be set when the worker is saved
        workerDto.setActive(false); // Default to false until explicitly set

        return workerDto;
    }


}
