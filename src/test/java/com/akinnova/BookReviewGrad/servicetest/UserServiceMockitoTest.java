//package com.akinnova.BookReviewGrad.servicetest;
//
//import com.akinnova.BookReviewGrad.dto.userdto.*;
//import com.akinnova.BookReviewGrad.email.emailservice.EmailServiceImpl;
//import com.akinnova.BookReviewGrad.entity.User;
//import com.akinnova.BookReviewGrad.response.ResponseType;
//import com.akinnova.BookReviewGrad.enums.ServiceProviderSpecialization;
//import com.akinnova.BookReviewGrad.repository.UserRepository;
//import com.akinnova.BookReviewGrad.response.ResponsePojo;
//import com.akinnova.BookReviewGrad.response.ResponseUtils;
//import com.akinnova.BookReviewGrad.service.userservice.UserServiceImpl;
//import com.akinnova.BookReviewGrad.utilities.Utility;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import static com.akinnova.BookReviewGrad.enums.ServiceProviderApplicationStatus.NOT_SENT;
//import static com.akinnova.BookReviewGrad.enums.ServiceProviderApplicationStatus.SENT;
//import static com.akinnova.BookReviewGrad.enums.ServiceProviderApplicationReviewStatus.*;
//import static com.akinnova.BookReviewGrad.enums.UserType.CLIENT;
//import static com.akinnova.BookReviewGrad.enums.UserType.SERVICE_PROVIDER;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@SpringBootTest(classes = {UserServiceMockitoTest.class})
//public class UserServiceMockitoTest {
//
//
//    @Mock
//    UserRepository userRepository;
//
//    @InjectMocks
//    UserServiceImpl userService;
//
//    @Mock
//    PasswordEncoder passwordEncoder;
//
//    @Mock
//    EmailServiceImpl emailService;
//
////    @Test
////    @Order(1)
////    public void test_addUser(){
////
////        //User Create Dto
//////        UserCreateDto userCreateDto = UserCreateDto.builder()
//////                .firstName("John")
//////                .lastName("Ade")
//////                .username("Jade")
//////                .email("jade@gmail.com")
//////                .password("1234")
//////                .build();
////
////        UserCreateDto userCreateDto = new UserCreateDto("John", "Ade", "Jade", "jade@gmail.com", "1234");
////
////
////        User user = User.builder()
////                .id(1L)
////                .firstName("John")
////                .lastName("Ade")
////                .userId(Utility.generateUniqueIdentifier(10, "Jade"))
////                .username("Jade")
////                .email("jade@gmail.com")
////                .password(passwordEncoder.encode("1234"))
////                .userRole(REGULAR_USER)
////                .userType(CLIENT)
////                .specialization(ServiceProviderSpecialization.NONE)
////                .serviceProviderApplicationStatus(NOT_SENT)
////                .reviewStatus(NOT_CONFIRMED)
////                .activeStatus(true)
////                .createdOn(LocalDateTime.now())
////                .build();
////
////        ResponsePojo<UserResponseDto> expectedResult = new ResponsePojo<>(ResponseType.SUCCESS, ResponseUtils.USER_ADDED, new UserResponseDto(user));
////
////        //This is where the Mocking begins
////        when(userRepository.save(user)).thenReturn(user);
////        when(user)
////        //doNothing().when(emailService.sendSimpleEmail(any()));
////
////        assertEquals(expectedResult, userService.addUser(userCreateDto));
////
////    }
//
//    @Test
//    @Order(2)
//    public void test_allUsers(){
//        List<User> userList = new ArrayList<>();
//
//        userList.add(User.builder()
//                .id(1L)
//                .firstName("John")
//                .lastName("Ade")
//                .userId(Utility.generateUniqueIdentifier(10, "Jade"))
//                .username("Jade")
//                .email("jade@gmail.com")
//                .password(passwordEncoder.encode("1234"))
//                .userType(CLIENT)
//                .specialization(ServiceProviderSpecialization.NONE)
//                .serviceProviderApplicationStatus(NOT_SENT)
//                .reviewStatus(NOT_CONFIRMED)
//                .activeStatus(true)
//                .createdOn(LocalDateTime.now())
//                .build());
//
//        userList.add(User.builder()
//                .id(2L)
//                .firstName("Ola")
//                .lastName("Ade")
//                .userId(Utility.generateUniqueIdentifier(10, "Olad"))
//                .username("Olad")
//                .email("olad@gmail.com")
//                .password(passwordEncoder.encode("4567"))
//                .userType(SERVICE_PROVIDER)
//                .specialization(ServiceProviderSpecialization.EDITOR)
//                .serviceProviderApplicationStatus(SENT)
//                .reviewStatus(CONFIRMED)
//                .activeStatus(true)
//                .createdOn(LocalDateTime.now())
//                .build());
//
//        List<UserResponseDto> userResponseDtos = userList.stream().map(UserResponseDto::new).collect(Collectors.toList());
//        ResponseEntity<ResponsePojo<List<UserResponseDto>>> allUsers = ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All users", userResponseDtos));
//
//        //This is where the mocking begins
//        when(userRepository.findAll()).thenReturn(userList);
//        assertEquals(allUsers, userService.allUsers(1, 2));
//    }
//
//    @Test
//    @Order(3)
//    public void test_findClients(){
//        List<User> userList = new ArrayList<>();
//
//        userList.add(User.builder()
//                .id(1L)
//                .firstName("John")
//                .lastName("Ade")
//                .userId(Utility.generateUniqueIdentifier(10, "Jade"))
//                .username("Jade")
//                .email("jade@gmail.com")
//                .password(passwordEncoder.encode("1234"))
//                .userType(CLIENT)
//                .specialization(ServiceProviderSpecialization.NONE)
//                .serviceProviderApplicationStatus(NOT_SENT)
//                .reviewStatus(NOT_CONFIRMED)
//                .activeStatus(true)
//                .createdOn(LocalDateTime.now())
//                .build());
//
//        userList.add(User.builder()
//                .id(2L)
//                .firstName("Ola")
//                .lastName("Ade")
//                .userId(Utility.generateUniqueIdentifier(10, "Olad"))
//                .username("Olad")
//                .email("olad@gmail.com")
//                .password(passwordEncoder.encode("4567"))
//                .userType(SERVICE_PROVIDER)
//                .specialization(ServiceProviderSpecialization.EDITOR)
//                .serviceProviderApplicationStatus(SENT)
//                .reviewStatus(CONFIRMED)
//                .activeStatus(true)
//                .createdOn(LocalDateTime.now())
//                .build());
//
//        List<UserResponseDto> userResponseDtos = userList.stream().filter(x -> x.getUserType() == CLIENT).map(UserResponseDto::new).collect(Collectors.toList());
//        ResponseEntity<ResponsePojo<List<UserResponseDto>>> allUsers = ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All clients", userResponseDtos));
//
//        when(userRepository.findAll()).thenReturn(userList);
//        assertEquals(allUsers, userService.FindClients(1, 2));
//    }
//
//    @Test
//    @Order(4)
//    public void test_findServiceProviders(){
//        List<User> userList = new ArrayList<>();
//
//        userList.add(User.builder()
//                .id(1L)
//                .firstName("John")
//                .lastName("Ade")
//                .userId(Utility.generateUniqueIdentifier(10, "Jade"))
//                .username("Jade")
//                .email("jade@gmail.com")
//                .password(passwordEncoder.encode("1234"))
//                .userType(CLIENT)
//                .specialization(ServiceProviderSpecialization.NONE)
//                .serviceProviderApplicationStatus(NOT_SENT)
//                .reviewStatus(NOT_CONFIRMED)
//                .activeStatus(true)
//                .createdOn(LocalDateTime.now())
//                .build());
//
//        userList.add(User.builder()
//                .id(2L)
//                .firstName("Ola")
//                .lastName("Ade")
//                .userId(Utility.generateUniqueIdentifier(10, "Olad"))
//                .username("Olad")
//                .email("olad@gmail.com")
//                .password(passwordEncoder.encode("4567"))
//                .userType(SERVICE_PROVIDER)
//                .specialization(ServiceProviderSpecialization.EDITOR)
//                .serviceProviderApplicationStatus(SENT)
//                .reviewStatus(CONFIRMED)
//                .activeStatus(true)
//                .createdOn(LocalDateTime.now())
//                .build());
//
//        List<UserResponseDto> userResponseDtos = userList.stream().filter(x -> x.getUserType() == SERVICE_PROVIDER).map(UserResponseDto::new).collect(Collectors.toList());
//        ResponseEntity<ResponsePojo<List<UserResponseDto>>> allUsers = ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All service providers", userResponseDtos));
//
//        when(userRepository.findAll()).thenReturn(userList);
//        assertEquals(allUsers, userService.FindServiceProviders(1, 2));
//    }
//
//    @Test
//    @Order(5)
//    public void test_noClients(){
//
//        //Testing the response if there are no clients
//        List<User> userList = new ArrayList<>();
//
//        userList.add(User.builder()
//                .id(1L)
//                .firstName("John")
//                .lastName("Ade")
//                .userId(Utility.generateUniqueIdentifier(10, "Jade"))
//                .username("Jade")
//                .email("jade@gmail.com")
//                .password(passwordEncoder.encode("1234"))
//                .userType(SERVICE_PROVIDER)
//                .specialization(ServiceProviderSpecialization.NONE)
//                .serviceProviderApplicationStatus(NOT_SENT)
//                .reviewStatus(NOT_CONFIRMED)
//                .activeStatus(true)
//                .createdOn(LocalDateTime.now())
//                .build());
//
//        userList.add(User.builder()
//                .id(2L)
//                .firstName("Ola")
//                .lastName("Ade")
//                .userId(Utility.generateUniqueIdentifier(10, "Olad"))
//                .username("Olad")
//                .email("olad@gmail.com")
//                .password(passwordEncoder.encode("4567"))
//                .userType(SERVICE_PROVIDER)
//                .specialization(ServiceProviderSpecialization.EDITOR)
//                .serviceProviderApplicationStatus(SENT)
//                .reviewStatus(CONFIRMED)
//                .activeStatus(true)
//                .createdOn(LocalDateTime.now())
//                .build());
//
//        when(userRepository.findAll()).thenReturn(userList);
//        assertEquals(new ResponseEntity<>(ResponseUtils.NO_CLIENT_YET, HttpStatus.NOT_FOUND), userService.FindClients(1, 2));
//    }
//
//    @Test
//    @Order(6)
//    public void test_findRegularUsers(){
//        List<User> userList = new ArrayList<>();
//
//        userList.add(User.builder()
//                .id(1L)
//                .firstName("John")
//                .lastName("Ade")
//                .userId(Utility.generateUniqueIdentifier(10, "Jade"))
//                .username("Jade")
//                .email("jade@gmail.com")
//                .password(passwordEncoder.encode("1234"))
//                .userType(CLIENT)
//                .specialization(ServiceProviderSpecialization.NONE)
//                .serviceProviderApplicationStatus(NOT_SENT)
//                .reviewStatus(NOT_CONFIRMED)
//                .activeStatus(true)
//                .createdOn(LocalDateTime.now())
//                .build());
//
//        userList.add(User.builder()
//                .id(2L)
//                .firstName("Ola")
//                .lastName("Ade")
//                .userId(Utility.generateUniqueIdentifier(10, "Olad"))
//                .username("Olad")
//                .email("olad@gmail.com")
//                .password(passwordEncoder.encode("4567"))
//                .userType(SERVICE_PROVIDER)
//                .specialization(ServiceProviderSpecialization.EDITOR)
//                .serviceProviderApplicationStatus(SENT)
//                .reviewStatus(CONFIRMED)
//                .activeStatus(true)
//                .createdOn(LocalDateTime.now())
//                .build());
//
////        List<UserResponseDto> userResponseDtos = userList.stream().filter(x -> x.getRoleName().equals(REGULAR_USER)).map(UserResponseDto::new).collect(Collectors.toList());
////        ResponseEntity<ResponsePojo<List<UserResponseDto>>> allUsers = ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All regular users", userResponseDtos));
//
//        when(userRepository.findAll()).thenReturn(userList);
////        assertEquals(allUsers, userService.FindRegularUsers(1, 2));
//    }
//
////    @Test
////    @Order(7)
////    public void test_findAdmins(){
////        List<User> userList = new ArrayList<>();
////
////        userList.add(User.builder()
////                .id(1L)
////                .firstName("John")
////                .lastName("Ade")
////                .userId(Utility.generateUniqueIdentifier(10, "Jade"))
////                .username("Jade")
////                .email("jade@gmail.com")
////                .password(passwordEncoder.encode("1234"))
////                .userType(CLIENT)
////                .specialization(ServiceProviderSpecialization.NONE)
////                .serviceProviderApplicationStatus(NOT_SENT)
////                .reviewStatus(NOT_CONFIRMED)
////                .activeStatus(true)
////                .createdOn(LocalDateTime.now())
////                .build());
////
////        userList.add(User.builder()
////                .id(2L)
////                .firstName("Ola")
////                .lastName("Ade")
////                .userId(Utility.generateUniqueIdentifier(10, "Olad"))
////                .username("Olad")
////                .email("olad@gmail.com")
////                .password(passwordEncoder.encode("4567"))
////                .userType(SERVICE_PROVIDER)
////                .specialization(ServiceProviderSpecialization.EDITOR)
////                .serviceProviderApplicationStatus(SENT)
////                .reviewStatus(CONFIRMED)
////                .activeStatus(true)
////                .createdOn(LocalDateTime.now())
////                .build());
////
////        List<UserResponseDto> userResponseDtos = userList.stream().filter(x -> x.getRoleName().equals(ADMIN)).map(UserResponseDto::new).collect(Collectors.toList());
////        ResponseEntity<ResponsePojo<List<UserResponseDto>>> allUsers = ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All admins", userResponseDtos));
////
////        when(userRepository.findAll()).thenReturn(userList);
////        assertEquals(allUsers, userService.FindAdmins(1, 2));
////    }
//
//    @Test
//    @Order(8)
//    public void test_updateUser(){
//        List<User> userList = new ArrayList<>();
//
//        userList.add(User.builder()
//                .id(1L)
//                .firstName("John")
//                .lastName("Ade")
//                .userId(Utility.generateUniqueIdentifier(10, "Jade"))
//                .username("Jade")
//                .email("jade@gmail.com")
//                .password(passwordEncoder.encode("1234"))
//                .userType(CLIENT)
//                .specialization(ServiceProviderSpecialization.NONE)
//                .serviceProviderApplicationStatus(NOT_SENT)
//                .reviewStatus(NOT_CONFIRMED)
//                .activeStatus(true)
//                .createdOn(LocalDateTime.now())
//                .build());
//
//        userList.add(User.builder()
//                .id(2L)
//                .firstName("Ola")
//                .lastName("Ade")
//                .userId(Utility.generateUniqueIdentifier(10, "Olad"))
//                .username("Olad")
//                .email("olad@gmail.com")
//                .password(passwordEncoder.encode("4567"))
//                .userType(SERVICE_PROVIDER)
//                .specialization(ServiceProviderSpecialization.EDITOR)
//                .serviceProviderApplicationStatus(SENT)
//                .reviewStatus(CONFIRMED)
//                .activeStatus(true)
//                .createdOn(LocalDateTime.now())
//                .build());
//
//        //Username of user to update
//        String username = userList.get(1).getUsername();
//        //User to update
//        //User user = userRepository.findByUsername(username).orElseThrow();
//
//        UserUpdateDto userUpdateDto = new UserUpdateDto(userList.get(1));
//
//        //Set the condition for the mock
//        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userList.get(1)));
//        when(userRepository.save(userList.get(1))).thenReturn(userList.get(1));
//        //doNothing().when(emailService.sendSimpleEmail(any()));
//        assertEquals(ResponseEntity.ok(ResponseUtils.USER_UPDATE_MESSAGE), userService.updateUser(userUpdateDto));
//    }
//
//    @Test
//    @Order(9)
//    public void test_serviceProviderUpdate(){
//        List<User> userList = new ArrayList<>();
//
//        userList.add(User.builder()
//                .id(1L)
//                .firstName("John")
//                .lastName("Ade")
//                .userId(Utility.generateUniqueIdentifier(10, "Jade"))
//                .username("Jade")
//                .email("jade@gmail.com")
//                .password(passwordEncoder.encode("1234"))
//                .userType(CLIENT)
//                .specialization(ServiceProviderSpecialization.NONE)
//                .serviceProviderApplicationStatus(NOT_SENT)
//                .reviewStatus(NOT_CONFIRMED)
//                .activeStatus(true)
//                .createdOn(LocalDateTime.now())
//                .build());
//
//        userList.add(User.builder()
//                .id(2L)
//                .firstName("Ola")
//                .lastName("Ade")
//                .userId(Utility.generateUniqueIdentifier(10, "Olad"))
//                .username("Olad")
//                .email("olad@gmail.com")
//                .password(passwordEncoder.encode("4567"))
//                .userType(SERVICE_PROVIDER)
//                .specialization(ServiceProviderSpecialization.EDITOR)
//                .serviceProviderApplicationStatus(SENT)
//                .reviewStatus(CONFIRMED)
//                .activeStatus(true)
//                .createdOn(LocalDateTime.now())
//                .build());
//
//        //Username of user to update
//        String username = userList.get(1).getUsername();
//        //User to update
//        //User user = userRepository.findByUsername(username).orElseThrow();
//
//        //Service provider dto that will be passed to the method to update service provider
//        ServiceProviderUpdateDto serviceProviderUpdateDto = new ServiceProviderUpdateDto(userList.get(1));
//        //Set the condition for the mock
//        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userList.get(1)));
//        when(userRepository.save(userList.get(1))).thenReturn(userList.get(1));
//        //doNothing().when(emailService.sendSimpleEmail(any()));
//        assertEquals(ResponseEntity.ok(ResponseUtils.USER_UPDATE_MESSAGE), userService.serviceProviderUpdate(username, serviceProviderUpdateDto));
//
//    }
////    @Test
////    @Order(10)
////    public void test_jobRoleUpdate(){
////        List<User> userList = new ArrayList<>();
////
////        userList.add(User.builder()
////                .id(1L)
////                .firstName("John")
////                .lastName("Ade")
////                .userId(Utility.generateUniqueIdentifier(10, "Jade"))
////                .username("Jade")
////                .email("jade@gmail.com")
////                .password(passwordEncoder.encode("1234"))
////                .userType(CLIENT)
////                .specialization(ServiceProviderSpecialization.NONE)
////                .serviceProviderApplicationStatus(NOT_SENT)
////                .reviewStatus(NOT_CONFIRMED)
////                .activeStatus(true)
////                .createdOn(LocalDateTime.now())
////                .build());
////
////        userList.add(User.builder()
////                .id(2L)
////                .firstName("Ola")
////                .lastName("Ade")
////                .userId(Utility.generateUniqueIdentifier(10, "Olad"))
////                .username("Olad")
////                .email("olad@gmail.com")
////                .password(passwordEncoder.encode("4567"))
////                .userType(SERVICE_PROVIDER)
////                .specialization(ServiceProviderSpecialization.EDITOR)
////                .serviceProviderApplicationStatus(SENT)
////                .reviewStatus(CONFIRMED)
////                .activeStatus(true)
////                .createdOn(LocalDateTime.now())
////                .build());
////
////        //Username of user to update
////        String username = userList.get(1).getUsername();
////        //User to update
////        User user = new User();
////
////        //AdminUpdateDto is the dto that will be passed to the jobRole method
////        //AdminUpdateDto adminUpdateDto = AdminUpdateDto.builder().username(username).userRole(ADMIN).userType(SERVICE_PROVIDER).reviewStatus(REVIEWING).build();
////        AdminUpdateDto adminUpdateDto = new AdminUpdateDto(ADMIN, SERVICE_PROVIDER, REVIEWING);
////        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
////        assertEquals(ResponseEntity.ok(ResponseUtils.USER_UPDATE_MESSAGE), userService.jobRoleUpdate(username, adminUpdateDto));
////    }
//
//    @Test
//    @Order(11)
//    public void test_deleteUser(){
//        List<User> userList = new ArrayList<>();
//
//        userList.add(User.builder()
//                .id(1L)
//                .firstName("John")
//                .lastName("Ade")
//                .userId(Utility.generateUniqueIdentifier(10, "Jade"))
//                .username("Jade")
//                .email("jade@gmail.com")
//                .password(passwordEncoder.encode("1234"))
//                .userType(CLIENT)
//                .specialization(ServiceProviderSpecialization.NONE)
//                .serviceProviderApplicationStatus(NOT_SENT)
//                .reviewStatus(NOT_CONFIRMED)
//                .activeStatus(true)
//                .createdOn(LocalDateTime.now())
//                .build());
//
//        userList.add(User.builder()
//                .id(2L)
//                .firstName("Ola")
//                .lastName("Ade")
//                .userId(Utility.generateUniqueIdentifier(10, "Olad"))
//                .username("Olad")
//                .email("olad@gmail.com")
//                .password(passwordEncoder.encode("4567"))
//                .userType(SERVICE_PROVIDER)
//                .specialization(ServiceProviderSpecialization.EDITOR)
//                .serviceProviderApplicationStatus(SENT)
//                .reviewStatus(CONFIRMED)
//                .activeStatus(true)
//                .createdOn(LocalDateTime.now())
//                .build());
//
//        //Username of user to update
//        String username = userList.get(1).getUsername();
//
//        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userList.get(1)));
//        when(userRepository.save(userList.get(1))).thenReturn(userList.get(1));
//
//        assertEquals(ResponseEntity.ok(ResponseUtils.USER_DELETE_MESSAGE), userService.deleteUser(username));
//    }
//}
