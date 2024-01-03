//package com.akinnova.BookReviewGrad.controller;
//
//import com.akinnova.BookReviewGrad.dto.userdto.AdminUpdateDto;
//import com.akinnova.BookReviewGrad.dto.userdto.UserCreateDto;
//import com.akinnova.BookReviewGrad.dto.userdto.UserResponseDto;
//import com.akinnova.BookReviewGrad.response.ResponsePojo;
//import com.akinnova.BookReviewGrad.service.projectservice.IProjectService;
//import com.akinnova.BookReviewGrad.service.ratingservice.IRatingService;
//import com.akinnova.BookReviewGrad.service.userservice.IUserService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("api/v1/admin")
//public class AdminController {
//
//    private final IProjectService projectService;
//    private final IRatingService ratingService;
//    private final IUserService userService;
//
//    public AdminController(IProjectService projectService, IRatingService ratingService, IUserService userService) {
//        this.projectService = projectService;
//        this.ratingService = ratingService;
//        this.userService = userService;
//    }
//
//
//    @GetMapping("/allProjects")
//    public ResponseEntity<?> findAllProjects(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
//        return projectService.findAllProjects(pageNum, pageSize);
//    }
//
//    @GetMapping("/pending")
//    public ResponseEntity<?> findPendingProjects(@RequestParam(defaultValue = "1") int pageNum,
//                                                 @RequestParam(defaultValue = "10") int pageSize) {
//        return projectService.findPendingProjects(pageNum, pageSize);
//    }
//
//    @GetMapping("/started")
//    public ResponseEntity<?> findStartedProjects(@RequestParam(defaultValue = "1") int pageNum,
//                                                 @RequestParam(defaultValue = "10") int pageSize) {
//        return projectService.findStartedProjects(pageNum, pageSize);
//    }
//
//    @GetMapping("/completed")
//    public ResponseEntity<?> findCompletedProjects(@RequestParam(defaultValue = "1") int pageNum,
//                                                   @RequestParam(defaultValue = "10") int pageSize) {
//        return projectService.findCompletedProjects(pageNum, pageSize);
//    }
//
//
//    //Rating
//    @GetMapping("/allRates")
//    public ResponseEntity<?> allRates(@RequestParam(defaultValue = "1") int pageNum,
//                                      @RequestParam(defaultValue = "20") int pageSize) {
//        return ratingService.allRates(pageNum, pageSize);
//    }
//
//    //Users
//    @GetMapping("/allUsers")
//    public ResponseEntity<?> allUsers(@RequestParam(defaultValue = "1") int pageNum,
//                                      @RequestParam(defaultValue = "20" ) int pageSize) {
//        return userService.allUsers(pageNum, pageSize);
//    }
//
//    @GetMapping("/clients")
//    public ResponseEntity<?> FindClients(@RequestParam(defaultValue = "1") int pageNum,
//                                         @RequestParam(defaultValue = "20") int pageSize) {
//        return userService.FindClients(pageNum, pageSize);
//    }
//
//
//    @GetMapping("/admins")
//    public ResponseEntity<?> FindAdmins(@RequestParam(defaultValue = "1") int pageNum,
//                                        @RequestParam(defaultValue = "20") int pageSize) {
//        return userService.FindAdmins(pageNum, pageSize);
//    }
//
//    @GetMapping("/regularUsers")
//    public ResponseEntity<?> FindRegularUsers(@RequestParam(defaultValue = "1") int pageNum,
//                                              @RequestParam(defaultValue = "20") int pageSize) {
//        return userService.FindRegularUsers(pageNum, pageSize);
//    }
//
//    //ROLE
//
//    @PutMapping("/roleUpdate/{username}")
//    public ResponseEntity<?> jobRoleUpdate(@PathVariable String username, AdminUpdateDto adminUpdateDto) {
//        return userService.jobRoleUpdate(username, adminUpdateDto);
//    }
//
//}
