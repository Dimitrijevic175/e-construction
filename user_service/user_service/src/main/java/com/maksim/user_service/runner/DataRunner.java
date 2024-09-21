package com.maksim.user_service.runner;

import com.maksim.user_service.domain.Admin;
import com.maksim.user_service.domain.Client;
import com.maksim.user_service.domain.Role;
import com.maksim.user_service.domain.Worker;
import com.maksim.user_service.repository.RoleRepository;
import com.maksim.user_service.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"default"})
@Component
public class DataRunner implements CommandLineRunner {

    private RoleRepository roleRepository;
    private UserRepository userRepository;

    public DataRunner(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {


        Role roleClient = new Role("ROLE_CLIENT", "Client role");
        Role roleAdmin = new Role("ROLE_ADMIN", "Admin role");
        Role roleWorker = new Role("ROLE_WORKER", "Worker role");

        roleRepository.save(roleClient);
        roleRepository.save(roleAdmin);
        roleRepository.save(roleWorker);

        Admin admin = new Admin();
        admin.setEmail("admin@gmail.com");
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setActive(true);
        admin.setRole(roleAdmin);

        userRepository.save(admin);

        // Create client
        Client client = new Client();
        client.setName("Maksim");
        client.setLast_name("Dimitrijevic");
        client.setUsername("maksim");
        client.setPassword("client123");
        client.setEmail("example@raf.rs");
        client.setActive(true);
        client.setRole(roleClient);

        userRepository.save(client);

        // Create worker
        Worker worker = new Worker();
        worker.setName("Mika");
        worker.setLast_name("Mikic");
        worker.setUsername("mikix");
        worker.setPassword("worker123");
        worker.setEmail("random@gmail.com");
        worker.setProfession("keramicar");
        worker.setContactInfo("0631734993");
        worker.setActive(true);
        worker.setRole(roleWorker);

        userRepository.save(worker);
    }
}
