package com.maksim.user_service.service.implementation;

import com.maksim.user_service.domain.Client;
import com.maksim.user_service.domain.User;
import com.maksim.user_service.domain.Worker;
import com.maksim.user_service.dto.*;
import com.maksim.user_service.listener.MessageHelper;
import com.maksim.user_service.mapper.UserMapper;
import com.maksim.user_service.repository.RoleRepository;
import com.maksim.user_service.repository.UserRepository;
import com.maksim.user_service.security.TokenService;
import com.maksim.user_service.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.security.auth.login.LoginException;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private TokenService tokenService;

    private RoleRepository roleRepository;

    private JmsTemplate jmsTemplate;
    private MessageHelper messageHelper;

    private RestTemplate restTemplate;
    private String deleteReviewsDestination;
    private String deleteJobPostsDestination;
    private String deleteJobOffersDestination;
    private String registerDestination;


    public UserServiceImpl(UserRepository userRepository,RestTemplate restTemplate ,UserMapper userMapper, TokenService tokenService, RoleRepository roleRepository
                           ,JmsTemplate jmsTemplate, MessageHelper messageHelper,@Value("${destination.deleteReviews}") String deleteReviewsDestination,
                           @Value("${destination.deleteJobPosts}") String deleteJobPostsDestination, @Value("${destination.deleteJobOffers}") String deleteJobOffersDestination,
                           @Value("${destination.register}") String registerDestination) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
        this.jmsTemplate = jmsTemplate;
        this.messageHelper = messageHelper;
        this.restTemplate = restTemplate;
        this.deleteReviewsDestination = deleteReviewsDestination;
        this.deleteJobPostsDestination = deleteJobPostsDestination;
        this.deleteJobOffersDestination = deleteJobOffersDestination;
        this.registerDestination = registerDestination;
    }

    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) throws NotFoundException {
        User user = userRepository
                .findUserByUsernameAndPassword(tokenRequestDto.getUsername(), tokenRequestDto.getPassword())
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with username: %s and password: %s not found.", tokenRequestDto.getUsername(),
                                tokenRequestDto.getPassword())));

        // Proveri da li je korisnik aktivan
        if (!user.isActive()) {
            throw new NotFoundException("User account is not active.");
        }

        //Create token payload
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("role", user.getRole().getName());

        //Generate token
        return new TokenResponseDto(tokenService.generate(claims));
    }

    @Override
    public ClientDto addClient(UserCreateDto createDto) {
        // Mapiraj UserCreateDto u Client entitet koristeći UserMapper
        Client client = userMapper.userCreateDtoToClient(createDto);

        // Proveri da li korisničko ime već postoji
        if (userRepository.findUserByUsername(createDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Sačuvaj novog klijenta u bazi podataka
        Client savedClient = userRepository.save(client);



        RegisterNotification registerNotification = new RegisterNotification();
        registerNotification.setEmail(client.getEmail());
        registerNotification.setName(client.getName());
        registerNotification.setSurname(client.getLast_name());
        registerNotification.setReceiverId(client.getId());
        registerNotification.setLink("localhost:8080/api/user/" + client.getStringZaAktivaciju() + "/activate");

        jmsTemplate.convertAndSend(registerDestination, messageHelper.createTextMessage(registerNotification));

        // Mapiraj sačuvanog klijenta u ClientDto koristeći UserMapper
        return userMapper.clientToClientDto(savedClient);

    }

    @Override
    public Void activateUser(String aktivacijaString) throws NotFoundException {

        User user =  userRepository.findUserByStringZaAktivaciju(aktivacijaString).orElseThrow(() -> new NotFoundException(String.format("User with string: %s not found.", aktivacijaString)));
        user.setActive(true);
        return null;
    }


//    @Override
//    public WorkerDto addWorker(UserCreateDto createDto) {
//        // Mapiraj UserCreateDto u Worker entitet koristeći UserMapper
//        Worker worker = userMapper.userCreateDtoToWorker(createDto);
//
//        // Proveri da li korisničko ime već postoji
//        if (userRepository.findUserByUsername(createDto.getUsername()).isPresent()) {
//            throw new RuntimeException("Username already exists");
//        }
//
//        // Sačuvaj novog radnika u bazi podataka
//        Worker savedWorker = userRepository.save(worker);
//
//
//        RegisterNotification notification = new RegisterNotification();
//        notification.setEmail(worker.getEmail());
//        notification.setName(worker.getName());
//        notification.setSurname(worker.getLast_name());
//        notification.setReceiverId(worker.getId());
//        notification.setLink("localhost:8080/api/user/" + worker.getStringZaAktivaciju() + "/activate");
//
//        jmsTemplate.convertAndSend(registerDestination,messageHelper.createTextMessage(notification));
//
//
//
//        // Mapiraj sačuvanog radnika u WorkerDto koristeći UserMapper
//        return userMapper.workerToWorkerDto(savedWorker);
//    }

    @Override
    public WorkerDto addWorker(WorkerCreateDto createDto) {
        // Mapiraj WorkerCreateDto u Worker entitet koristeći UserMapper
        Worker worker = userMapper.workerCreateDtoToWorker(createDto);

        // Proveri da li korisničko ime već postoji
        if (userRepository.findUserByUsername(createDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Sačuvaj novog radnika u bazi podataka
        Worker savedWorker = userRepository.save(worker);

        // Kreiraj notifikaciju za registraciju
        RegisterNotification notification = new RegisterNotification();
        notification.setEmail(worker.getEmail());
        notification.setName(worker.getName());
        notification.setSurname(worker.getLast_name());
        notification.setReceiverId(worker.getId());
        notification.setLink( worker.getStringZaAktivaciju() );

        // Pošalji notifikaciju preko JMS-a
        jmsTemplate.convertAndSend(registerDestination, messageHelper.createTextMessage(notification));

        // Mapiraj sačuvanog radnika u WorkerDto koristeći UserMapper
        return userMapper.workerToWorkerDto(savedWorker);
    }


    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);
        return usersPage.map(userMapper::UsertoUserDto);
    }
    @Override
    public Page<ClientDto> findAllClients(Pageable pageable) {
        // Pretpostavljamo da postoji metoda u UserRepository za preuzimanje svih klijenata
        Page<Client> clientsPage = userRepository.findAllClients(pageable);

        // Mapiraj Page<Client> u Page<ClientDto> koristeći UserMapper
        return clientsPage.map(userMapper::clientToClientDto);
    }


    @Override
    public Page<WorkerDto> findAllWorkers(Pageable pageable) {
        // Pretpostavljamo da postoji metoda u UserRepository za preuzimanje svih radnika
        Page<Worker> workersPage = userRepository.findAllWorkers(pageable);

        // Mapiraj Page<Worker> u Page<WorkerDto> koristeći UserMapper
        return workersPage.map(userMapper::workerToWorkerDto);
    }

    @Override
    public ClientDto findClient(Long id) throws NotFoundException {
        Client client = userRepository.findClientById(id)
                .orElseThrow(() -> new NotFoundException("Client not found"));
        return userMapper.clientToClientDto(client);
    }

    @Override
    public WorkerDto findWorker(Long id) throws NotFoundException {
        Worker worker = userRepository.findWorkerById(id)
                .orElseThrow(() -> new NotFoundException("Worker not found"));
        return userMapper.workerToWorkerDto(worker);
    }


//    @Override
//    public void toggleActiveStatus(Long id, boolean active) throws NotFoundException {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("User not found"));
//
//        user.setActive(active);
//        userRepository.save(user);
//    }

    @Override
    public void toggleActiveStatus(Long id, boolean active) throws NotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        // Update the user's active status
        user.setActive(active);
        userRepository.save(user);

        // If setting to inactive, delete all associated reviews
//        if (!active) {
//            // Call the review service to delete all reviews for this client or worker
//            restTemplate.delete("/reviews/client/" + id);
//        }
    }

    @Override
    public void deleteClient(Long id) throws NotFoundException {
        Client client = userRepository.findClientById(id)
                .orElseThrow(() -> new NotFoundException("Client not found"));

        // Šaljemo poruku za brisanje svih recenzija klijenta
        jmsTemplate.convertAndSend(deleteReviewsDestination, messageHelper.createTextMessage(id));

        // Šaljemo poruku za brisanje svih poslova klijenta
        jmsTemplate.convertAndSend(deleteJobPostsDestination, messageHelper.createTextMessage(id));

        userRepository.delete(client);
    }


    @Override
    public void deleteWorker(Long id) throws NotFoundException {
        Worker worker = userRepository.findWorkerById(id)
                .orElseThrow(() -> new NotFoundException("Worker not found"));

        // Šaljemo poruku za brisanje svih job offer-a radnika
        jmsTemplate.convertAndSend(deleteJobOffersDestination, messageHelper.createTextMessage(id));

        // Brisanje radnika
        userRepository.delete(worker);
    }



    @Override
    public ClientDto findClientByUsernameAndPassword(String username, String password) throws NotFoundException {
        Client client = userRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new NotFoundException("Client not found with given username and password"));
        return userMapper.clientToClientDto(client); // Assuming you have a method to map Client to ClientDto
    }




}
