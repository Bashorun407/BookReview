package com.akinnova.BookReviewGrad.servicetest;

import com.akinnova.BookReviewGrad.dto.projectdto.*;
import com.akinnova.BookReviewGrad.email.emailservice.EmailServiceImpl;
import com.akinnova.BookReviewGrad.entity.Project;
import com.akinnova.BookReviewGrad.entity.UserEntity;
import com.akinnova.BookReviewGrad.enums.*;
import com.akinnova.BookReviewGrad.repository.ProjectRepository;
import com.akinnova.BookReviewGrad.repository.UserRepository;
import com.akinnova.BookReviewGrad.response.ResponsePojo;
import com.akinnova.BookReviewGrad.response.ResponseUtils;
import com.akinnova.BookReviewGrad.service.projectservice.ProjectServiceImpl;
import com.akinnova.BookReviewGrad.utilities.Utility;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.akinnova.BookReviewGrad.enums.ApplicationStatus.NOT_SENT;
import static com.akinnova.BookReviewGrad.enums.ApplicationStatus.SENT;
import static com.akinnova.BookReviewGrad.enums.JobAcceptanceStatus.NOT_ACCEPTED;
import static com.akinnova.BookReviewGrad.enums.ProjectCompletion.*;
import static com.akinnova.BookReviewGrad.enums.ProjectLevelApproval.NOT_SATISFIED;
import static com.akinnova.BookReviewGrad.enums.ProjectStartApproval.NOT_APPROVED;
import static com.akinnova.BookReviewGrad.enums.ApplicationReviewStatus.CONFIRMED;
import static com.akinnova.BookReviewGrad.enums.ApplicationReviewStatus.NOT_CONFIRMED;
import static com.akinnova.BookReviewGrad.enums.UserRole.REGULAR_USER;
import static com.akinnova.BookReviewGrad.enums.UserType.CLIENT;
import static com.akinnova.BookReviewGrad.enums.UserType.SERVICE_PROVIDER;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ProjectServiceMockitoTest.class})
public class ProjectServiceMockitoTest {


    @Mock
    ProjectRepository projectRepository;
    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    EmailServiceImpl emailService;

    @InjectMocks
    ProjectServiceImpl projectService;


//    @Test
//    @Order(1)
//    public void test_addProject(){
//
//        ProjectCreateDto projectCreateDto = new ProjectCreateDto("www.image.com", "Peanut Machine", Category.EDIT, "OluDot", "Peanut processing machine powered by solar");
//        Project project = Project.builder()
//                .coverImage(projectCreateDto.getCoverImage())
//                .title(projectCreateDto.getTitle())
//                .category(Category.ACADEMIC_PROJECT)
//                .clientUsername(projectCreateDto.getClientUsername())
//                .content(projectCreateDto.getContent())
//                .projectId(Utility.generateBookSerialNumber(10, projectCreateDto.getTitle()))
//                .projectStartApproval(NOT_APPROVED)
//                .projectLevelApproval(NOT_SATISFIED)
//                .projectCompletion(PENDING)
//                .activeStatus(true)
//                .createdOn(LocalDateTime.now())
//                .build();
//
//        UserEntity user = UserEntity.builder()
//                .id(1L)
//                .firstName("Olu")
//                .lastName("Dotun")
//                .userId(Utility.generateUniqueIdentifier(10, "Jade"))
//                .username("OluDot")
//                .email("olu@gmail.com")
//                .password(passwordEncoder.encode("1234"))
//                .userRole(REGULAR_USER)
//                .userType(CLIENT)
//                .specialization(ServiceProviderSpecialization.NONE)
//                .applicationStatus(NOT_SENT)
//                .reviewStatus(NOT_CONFIRMED)
//                .activeStatus(true)
//                .createdOn(LocalDateTime.now())
//                .build();
//
//
//
//        //Dto that will be passed to the addProject method
//        ProjectResponseDto projectResponseDto = new ProjectResponseDto(project);
//        //Expected result
//        ResponsePojo<ProjectResponseDto> responsePojo = new ResponsePojo<>(ResponseType.SUCCESS, ResponseUtils.PROJECT_CREATION_MESSAGE, projectResponseDto);
//        when(projectRepository.save(project)).thenReturn(project);
//        // Mock userRepository to return userEntity when findByUsername is called
//        when(userRepository.findByUsername(project.getClientUsername())).thenReturn(Optional.of(user));
//        //when(userEntity.getEmail()).thenReturn("olu@gmail.com");
//        //doNothing().when(emailService).sendSimpleEmail(any()); // Mock emailService call
//
//        assertEquals(responsePojo, projectService.addProject(projectCreateDto));
//    }

    @Test
    @Order(2)
    public void test_findAllProjects(){
        List<Project> projectList = new ArrayList<>();

        projectList.add(Project.builder()
                .id(1L)
                .coverImage("Project")
                .title("ProjectTitle")
                .category(Category.ACADEMIC_PROJECT)
                .clientUsername("OluDot")
                .content("Project content")
                .projectId(Utility.generateBookSerialNumber(10, "ProjectTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(PENDING)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        projectList.add(Project.builder()
                .id(2L)
                .coverImage("Research")
                .title("ResearchTitle")
                .category(Category.BOOK_WRITING)
                .clientUsername("BashOlu")
                .content("Research content")
                .projectId(Utility.generateBookSerialNumber(10, "ResearchTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(PENDING)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        List<ProjectResponseDto> projectResponseDtos = projectList.stream().map(ProjectResponseDto::new).collect(Collectors.toList());
        ResponseEntity<ResponsePojo<List<ProjectResponseDto>>> allProjects = ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All projects", projectResponseDtos));

        when(projectRepository.findAll()).thenReturn(projectList);
        assertEquals(allProjects, projectService.findAllProjects(1, 2));
    }

    @Test
    @Order(3)
    public void test_findProjectByOwner(){
        List<Project> projectList = new ArrayList<>();

        projectList.add(Project.builder()
                .id(1L)
                .coverImage("Project")
                .title("ProjectTitle")
                .category(Category.ACADEMIC_PROJECT)
                .clientUsername("OluDot")
                .content("Project content")
                .projectId(Utility.generateBookSerialNumber(10, "ProjectTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(PENDING)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        projectList.add(Project.builder()
                .id(2L)
                .coverImage("Research")
                .title("ResearchTitle")
                .category(Category.BOOK_WRITING)
                .clientUsername("BashOlu")
                .content("Research content")
                .projectId(Utility.generateBookSerialNumber(10, "ResearchTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(PENDING)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        List<Project> projects = projectList.stream().filter(x-> x.getClientUsername().equals("BashOlu")).collect(Collectors.toList());

        List<ProjectResponseDto> projectResponseDtos = projectList.stream().filter(x -> x.getClientUsername().equals("BashOlu")).map(ProjectResponseDto::new).collect(Collectors.toList());
        ResponseEntity<ResponsePojo<List<ProjectResponseDto>>> expectedResult = ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "Projects by username", projectResponseDtos));

        when(projectRepository.findByClientUsername("BashOlu")).thenReturn(Optional.of(projects));
        assertEquals(expectedResult, projectService.findProjectByOwner("BashOlu", 1, 2));
    }

//    @Test
//    @Order(4)
//    public void test_projectByTitle(){
//        List<Project> projectList = new ArrayList<>();
//
//        projectList.add(Project.builder()
//                .id(1L)
//                .coverImage("Project")
//                .title("ProjectTitle")
//                .category(Category.ACADEMIC_PROJECT)
//                .clientUsername("OluDot")
//                .content("Project content")
//                .projectId(Utility.generateBookSerialNumber(10, "ProjectTitle"))
//                .projectStartApproval(NOT_APPROVED)
//                .projectLevelApproval(NOT_SATISFIED)
//                .projectCompletion(PENDING)
//                .activeStatus(true)
//                .createdOn(LocalDateTime.now())
//                .build());
//
//        projectList.add(Project.builder()
//                .id(2L)
//                .coverImage("Research")
//                .title("ResearchTitle")
//                .category(Category.BOOK_WRITING)
//                .clientUsername("BashOlu")
//                .content("Research content")
//                .projectId(Utility.generateBookSerialNumber(10, "ResearchTitle"))
//                .projectStartApproval(NOT_APPROVED)
//                .projectLevelApproval(NOT_SATISFIED)
//                .projectCompletion(PENDING)
//                .activeStatus(true)
//                .createdOn(LocalDateTime.now())
//                .build());
//
//        List<Project> projects = projectList.stream().filter(x-> x.getTitle().equals("ProjectTitle")).collect(Collectors.toList());
//
//        List<ProjectResponseDto> projectResponseDtos = projectList.stream().filter(x -> x.getTitle().equals("ProjectTitle")).map(ProjectResponseDto::new).collect(Collectors.toList());
//        ResponseEntity<ResponsePojo<List<ProjectResponseDto>>> expectedResult = ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All projects by title", projectResponseDtos));
//
//        when(projectRepository.findByTitle("ProjectTitle")).thenReturn(Optional.of(projects));
//        assertEquals(expectedResult, projectService.findProjectByTitle("ProjectTitle", 1, 2));
//    }

    @Test
    @Order(5)
    public void test_findProjectByProjectId(){
        //This will act as project repository
        List<Project> projectList = new ArrayList<>();

        projectList.add(Project.builder()
                .id(1L)
                .coverImage("Project")
                .title("ProjectTitle")
                .category(Category.ACADEMIC_PROJECT)
                .clientUsername("OluDot")
                .content("Project content")
                .projectId(Utility.generateBookSerialNumber(10, "ProjectTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(PENDING)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        projectList.add(Project.builder()
                .id(2L)
                .coverImage("Research")
                .title("ResearchTitle")
                .category(Category.BOOK_WRITING)
                .clientUsername("BashOlu")
                .content("Research content")
                .projectId(Utility.generateBookSerialNumber(10, "ResearchTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(PENDING)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        String projectId = projectList.get(1).getProjectId();

        //User List...which will act as user repository
        List<UserEntity> userList = new ArrayList<>();

        userList.add(UserEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Ade")
                .userId(Utility.generateUniqueIdentifier(10, "Jade"))
                .username("Jade")
                .email("jade@gmail.com")
                .password(passwordEncoder.encode("1234"))
                .userType(CLIENT)
                .specialization(ServiceProviderSpecialization.NONE)
                .applicationStatus(NOT_SENT)
                .reviewStatus(NOT_CONFIRMED)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        userList.add(UserEntity.builder()
                .id(2L)
                .firstName("Bashorun")
                .lastName("Olu")
                .userId(Utility.generateUniqueIdentifier(10, "Olad"))
                .username("BashOlu")
                .email("bash@gmail.com")
                .password(passwordEncoder.encode("4567"))
                .userType(SERVICE_PROVIDER)
                .specialization(ServiceProviderSpecialization.EDITOR)
                .applicationStatus(SENT)
                .reviewStatus(CONFIRMED)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        String username = userList.get(1).getUsername(); //Username to find user


        ResponseEntity<ResponsePojo<Object>> expectedResult = ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, String.format(ResponseUtils.FOUND_MESSAGE, projectId), new ProjectResponseDto(projectList.get(1))));

        when(projectRepository.findByProjectId(projectId)).thenReturn(Optional.of(projectList.get(1)));
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userList.get(1)));
        assertEquals(expectedResult, projectService.findProjectByProjectId(projectId));
    }


    @Test
    @Order(5)
    public void test_findPendingProjects(){
        List<Project> projectList = new ArrayList<>();

        projectList.add(Project.builder()
                .id(1L)
                .coverImage("Project")
                .title("ProjectTitle")
                .category(Category.ACADEMIC_PROJECT)
                .clientUsername("OluDot")
                .content("Project content")
                .projectId(Utility.generateBookSerialNumber(10, "ProjectTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(PENDING)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        projectList.add(Project.builder()
                .id(2L)
                .coverImage("Research")
                .title("ResearchTitle")
                .category(Category.BOOK_WRITING)
                .clientUsername("BashOlu")
                .content("Research content")
                .projectId(Utility.generateBookSerialNumber(10, "ResearchTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(PENDING)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        //List<Project> projects = projectList.stream().filter(x-> x.getProjectCompletion() == PENDING).collect(Collectors.toList());

        List<ProjectResponseDto> projectResponseDtos = projectList.stream().filter(x -> x.getProjectCompletion() == PENDING).map(ProjectResponseDto::new).collect(Collectors.toList());
        ResponseEntity<ResponsePojo<List<ProjectResponseDto>>> expectedResult = ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "Pending projects", projectResponseDtos));

        when(projectRepository.findAll()).thenReturn(projectList);
        assertEquals(expectedResult, projectService.findPendingProjects(1, 2));
    }

    @Test
    @Order(6)
    public void test_findStartedProjects(){

        //This will represent the project repository
        List<Project> projectList = new ArrayList<>();

        projectList.add(Project.builder()
                .id(1L)
                .coverImage("Project")
                .title("ProjectTitle")
                .category(Category.ACADEMIC_PROJECT)
                .clientUsername("OluDot")
                .content("Project content")
                .projectId(Utility.generateBookSerialNumber(10, "ProjectTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(PENDING)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        projectList.add(Project.builder()
                .id(2L)
                .coverImage("Research")
                .title("ResearchTitle")
                .category(Category.BOOK_WRITING)
                .clientUsername("BashOlu")
                .content("Research content")
                .projectId(Utility.generateBookSerialNumber(10, "ResearchTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(STARTED)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        //List<Project> projects = projectList.stream().filter(x-> x.getTitle().equals("ProjectTitle")).collect(Collectors.toList());

        List<ProjectResponseDto> projectResponseDtos = projectList.stream().filter(x-> x.getProjectCompletion() == STARTED).map(ProjectResponseDto::new).collect(Collectors.toList());
        ResponseEntity<ResponsePojo<List<ProjectResponseDto>>> expectedResult = ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "Started projects", projectResponseDtos));

        when(projectRepository.findAll()).thenReturn(projectList);
        assertEquals(expectedResult, projectService.findStartedProjects(1, 2));
    }

    @Test
    @Order(7)
    public void test_updateProject(){
        //This will represent project repository
        List<Project> projectList = new ArrayList<>();

        projectList.add(Project.builder()
                .id(1L)
                .coverImage("Project")
                .title("ProjectTitle")
                .category(Category.ACADEMIC_PROJECT)
                .clientUsername("OluDot")
                .content("Project content")
                .projectId(Utility.generateBookSerialNumber(10, "ProjectTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(PENDING)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        projectList.add(Project.builder()
                .id(2L)
                .coverImage("Research")
                .title("ResearchTitle")
                .category(Category.BOOK_WRITING)
                .clientUsername("BashOlu")
                .content("Research content")
                .projectId(Utility.generateBookSerialNumber(10, "ResearchTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(COMPLETED)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        String projectId = projectList.get(1).getProjectId(); //Used to find project

        //User List...which will act as user repository
        List<UserEntity> userList = new ArrayList<>();

        userList.add(UserEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Ade")
                .userId(Utility.generateUniqueIdentifier(10, "Jade"))
                .username("Jade")
                .email("jade@gmail.com")
                .password(passwordEncoder.encode("1234"))
                .userType(CLIENT)
                .specialization(ServiceProviderSpecialization.NONE)
                .applicationStatus(NOT_SENT)
                .reviewStatus(NOT_CONFIRMED)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        userList.add(UserEntity.builder()
                .id(2L)
                .firstName("Bashorun")
                .lastName("Olu")
                .userId(Utility.generateUniqueIdentifier(10, "Olad"))
                .username("BashOlu")
                .email("bash@gmail.com")
                .password(passwordEncoder.encode("4567"))
                .userType(SERVICE_PROVIDER)
                .specialization(ServiceProviderSpecialization.EDITOR)
                .applicationStatus(SENT)
                .reviewStatus(CONFIRMED)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        String username = userList.get(1).getUsername(); //Username to find user

        ProjectUpdateDto projectUpdateDto = new ProjectUpdateDto("www.image.com", "New Title",
                "This is the new content of the updated project", true);



        ResponseEntity<String> expectedResult = ResponseEntity.ok(ResponseUtils.PROJECT_UPDATE_SUCCESSFUL);
        ResponseEntity<?> response = new ResponseEntity<>("Mail sent successfully", HttpStatus.GONE);
        when(projectRepository.findByProjectId(projectId)).thenReturn(Optional.of(projectList.get(1)));
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userList.get(1)));
        //doNothing().when(emailService.sendSimpleEmail(any()));
        //when(emailService.sendSimpleEmail(any())).thenReturn(response);
        assertEquals(expectedResult, projectService.updateProject(projectId, projectUpdateDto));
    }

    @Test
    @Order(8)
    public void test_serviceProviderProjectUpdate(){

        //This will represent project repository
        List<Project> projectList = new ArrayList<>();

        projectList.add(Project.builder()
                .id(1L)
                .coverImage("Project")
                .title("ProjectTitle")
                .category(Category.ACADEMIC_PROJECT)
                .clientUsername("OluDot")
                .content("Project content")
                .projectId(Utility.generateBookSerialNumber(10, "ProjectTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(PENDING)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        projectList.add(Project.builder()
                .id(2L)
                .coverImage("Research")
                .title("ResearchTitle")
                .category(Category.BOOK_WRITING)
                .clientUsername("BashOlu")
                .content("Research content")
                .projectId(Utility.generateBookSerialNumber(10, "ResearchTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(COMPLETED)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        String projectId = projectList.get(1).getProjectId();

        //User List...which will act as user repository
        List<UserEntity> userList = new ArrayList<>();

        userList.add(UserEntity.builder()
                .id(1L)
                .firstName("John")
                .lastName("Ade")
                .userId(Utility.generateUniqueIdentifier(10, "Jade"))
                .username("Jade")
                .email("jade@gmail.com")
                .password(passwordEncoder.encode("1234"))
                .userType(CLIENT)
                .specialization(ServiceProviderSpecialization.NONE)
                .applicationStatus(NOT_SENT)
                .reviewStatus(NOT_CONFIRMED)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        userList.add(UserEntity.builder()
                .id(2L)
                .firstName("Bashorun")
                .lastName("Olu")
                .userId(Utility.generateUniqueIdentifier(10, "Olad"))
                .username("BashOlu")
                .email("bash@gmail.com")
                .password(passwordEncoder.encode("4567"))
                .userType(SERVICE_PROVIDER)
                .specialization(ServiceProviderSpecialization.EDITOR)
                .applicationStatus(SENT)
                .reviewStatus(CONFIRMED)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        String username = userList.get(1).getUsername(); //Username to find user
        //Project project = projectRepository.findByProjectId(projectId).orElseThrow();

        ProjectServiceProviderUpdateDto projectUpdateDto = new ProjectServiceProviderUpdateDto("BashOlu", NOT_ACCEPTED, PENDING,
                LocalDateTime.now(), LocalDateTime.of(2023, 10, 18, 18, 30));

        ResponseEntity<String> expectedResult = ResponseEntity.ok(ResponseUtils.PROJECT_UPDATE_SUCCESSFUL);

        when(projectRepository.findByProjectId(projectId)).thenReturn(Optional.of(projectList.get(0)));
        when(projectRepository.save(projectList.get(0))).thenReturn(projectList.get(0));
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userList.get(1)));
        //doNothing().when(emailService.sendSimpleEmail(any()));
        assertEquals(expectedResult, projectService.serviceProviderProjectUpdate(projectId, projectUpdateDto));
    }

    @Test
    @Order(9)
    public void test_adminProjectUpdate(){
        List<Project> projectList = new ArrayList<>();

        projectList.add(Project.builder()
                .id(1L)
                .coverImage("Project")
                .title("ProjectTitle")
                .category(Category.ACADEMIC_PROJECT)
                .clientUsername("OluDot")
                .content("Project content")
                .projectId(Utility.generateBookSerialNumber(10, "ProjectTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(PENDING)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        projectList.add(Project.builder()
                .id(2L)
                .coverImage("Research")
                .title("ResearchTitle")
                .category(Category.BOOK_WRITING)
                .clientUsername("BashOlu")
                .content("Research content")
                .projectId(Utility.generateBookSerialNumber(10, "ResearchTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(COMPLETED)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        String projectId = projectList.get(0).getProjectId();


        ProjectAdminUpdateDto projectUpdateDto = new ProjectAdminUpdateDto(ProjectLevelApproval.SATISFIED);

        ResponseEntity<String> expectedResult = ResponseEntity.ok(ResponseUtils.PROJECT_UPDATE_SUCCESSFUL);

        when(projectRepository.findByProjectId(projectId)).thenReturn(Optional.of(projectList.get(1)));
        when(projectRepository.save(projectList.get(1))).thenReturn(projectList.get(1));
        //doNothing().when(emailService.sendSimpleEmail(any()));
        assertEquals(expectedResult, projectService.projectLevelUpdate(projectId, projectUpdateDto));
    }

    @Test
    @Order(7)
    public void test_findCompletedProjects(){
        List<Project> projectList = new ArrayList<>();

        projectList.add(Project.builder()
                .id(1L)
                .coverImage("Project")
                .title("ProjectTitle")
                .category(Category.ACADEMIC_PROJECT)
                .clientUsername("OluDot")
                .content("Project content")
                .projectId(Utility.generateBookSerialNumber(10, "ProjectTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(PENDING)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        projectList.add(Project.builder()
                .id(2L)
                .coverImage("Research")
                .title("ResearchTitle")
                .category(Category.BOOK_WRITING)
                .clientUsername("BashOlu")
                .content("Research content")
                .projectId(Utility.generateBookSerialNumber(10, "ResearchTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(COMPLETED)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        List<ProjectResponseDto> projectResponseDtos = projectList.stream().filter(x-> x.getProjectCompletion() == COMPLETED).map(ProjectResponseDto::new).collect(Collectors.toList());
        ResponseEntity<ResponsePojo<List<ProjectResponseDto>>> expectedResult = ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "Completed projects", projectResponseDtos));

        when(projectRepository.findAll()).thenReturn(projectList);
        assertEquals(expectedResult, projectService.findCompletedProjects(1, 2));
    }


    @Test
    @Order(8)
    public void test_deleteProject(){
        List<Project> projectList = new ArrayList<>();

        projectList.add(Project.builder()
                .id(1L)
                .coverImage("Project")
                .title("ProjectTitle")
                .category(Category.ACADEMIC_PROJECT)
                .clientUsername("OluDot")
                .content("Project content")
                .projectId(Utility.generateBookSerialNumber(10, "ProjectTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(PENDING)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        projectList.add(Project.builder()
                .id(2L)
                .coverImage("Research")
                .title("ResearchTitle")
                .category(Category.BOOK_WRITING)
                .clientUsername("BashOlu")
                .content("Research content")
                .projectId(Utility.generateBookSerialNumber(10, "ResearchTitle"))
                .projectStartApproval(NOT_APPROVED)
                .projectLevelApproval(NOT_SATISFIED)
                .projectCompletion(COMPLETED)
                .activeStatus(true)
                .createdOn(LocalDateTime.now())
                .build());

        //Determing the string by which to select the project to delete
        String projectId = projectList.get(1).getProjectId();


        when(projectRepository.findByProjectId(projectId)).thenReturn(Optional.of(projectList.get(1)));
        assertEquals(ResponseEntity.ok(ResponseUtils.PROJECT_DELETED), projectService.deleteProject(projectId));

    }

}
