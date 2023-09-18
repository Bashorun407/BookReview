package com.akinnova.BookReviewGrad.controller;

import com.akinnova.BookReviewGrad.dto.projectdto.*;
import com.akinnova.BookReviewGrad.dto.ratingdto.RatingDto;
import com.akinnova.BookReviewGrad.response.ResponsePojo;
import com.akinnova.BookReviewGrad.service.projectservice.IProjectService;
import com.akinnova.BookReviewGrad.service.projectservice.ProjectServiceImpl;
import com.akinnova.BookReviewGrad.service.ratingservice.IRatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final IProjectService projectService;
    private final IRatingService ratingService;
    public ProjectController(ProjectServiceImpl projectService, IRatingService ratingService) {
        this.projectService = projectService;
        this.ratingService = ratingService;
    }

    //Project
    @PostMapping("/addProject")
    public ResponsePojo<ProjectResponseDto> addProject(@RequestBody ProjectCreateDto projectCreateDto) {
        return projectService.addProject(projectCreateDto);
    }


    @GetMapping("/username/{username}")
    public ResponseEntity<?> findProjectByOwner(@PathVariable(name = "username") String username, @RequestParam(defaultValue = "1") int pageNum,
                                                @RequestParam(defaultValue = "10") int pageSize) {
        return projectService.findProjectByOwner(username, pageNum, pageSize);
    }

//    @GetMapping("/title/{title}")
//    public ResponseEntity<?> findProjectByTitle(@PathVariable String title, @RequestParam(defaultValue = "1") int pageNum,
//                                                @RequestParam(defaultValue = "10") int pageSize) {
//        return projectService.findProjectByTitle(title, pageNum, pageSize);
//    }

    @GetMapping("/projectId/{projectId}")
    public ResponseEntity<?> findProjectByProjectId(@PathVariable String projectId) {
        return projectService.findProjectByProjectId(projectId);
    }



    @PutMapping("/update/{projectId}")
    public ResponseEntity<?> updateProject(@PathVariable String projectId, @RequestBody ProjectUpdateDto projectUpdateDto) {
        return projectService.updateProject(projectId, projectUpdateDto);
    }

    @PutMapping("/addServiceProvider/{projectId}")
    public ResponseEntity<?> addServiceProvider(@PathVariable String projectId, @RequestBody SelectServiceProviderDto serviceProviderDto) {
        return projectService.addServiceProvider(projectId, serviceProviderDto);
    }
    @PutMapping("/serviceProviderUpdate/{projectId}")
    public ResponseEntity<?> serviceProviderProjectUpdate(@PathVariable String projectId, @RequestBody ProjectServiceProviderUpdateDto serviceProviderUpdateDto) {
        return projectService.serviceProviderProjectUpdate(projectId, serviceProviderUpdateDto);
    }

    @PutMapping("/adminUpdate/{projectId}")
    public ResponseEntity<?> projectLevelUpdate(@PathVariable String projectId, @RequestBody ProjectAdminUpdateDto adminUpdateDto) {
        return projectService.projectLevelUpdate(projectId, adminUpdateDto);
    }
    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId) {
        return projectService.deleteProject(projectId);
    }

    @GetMapping("/provider/{username}")
    public ResponseEntity<?> serviceProviderRates(@PathVariable String username) {
        return ratingService.serviceProviderRates(username);
    }
//
//    @GetMapping("/search")
//    public ResponseEntity<?> searchProject(@RequestParam(required = false) String username,
//                                           @RequestParam(required = false) String title,
//                                           @RequestParam(required = false) String projectId,
//                                           @RequestParam(defaultValue = "1") int pageNum,
//                                           @RequestParam(defaultValue = "10") int pageSize) {
//        return projectService.searchProject(username, title, projectId, pageNum, pageSize);
//    }


}
