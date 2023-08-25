package com.akinnova.BookReviewGrad.controller;

import com.akinnova.BookReviewGrad.dto.serviceproviderdto.ServProviderDto;
import com.akinnova.BookReviewGrad.dto.serviceproviderdto.ServProviderUpdateDto;
import com.akinnova.BookReviewGrad.service.servproviderservice.ServProviderServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/provider/auth")
public class ServiceProviderController {

    private final ServProviderServiceImpl providerService;

    public ServiceProviderController(ServProviderServiceImpl providerService) {
        this.providerService = providerService;
    }

    @PostMapping("/addService")
    public ResponseEntity<?> AddServiceProvider(@RequestBody ServProviderDto servProviderDto) {
        return providerService.AddServiceProvider(servProviderDto);
    }

    @GetMapping("/allProviders")
    public ResponseEntity<?> AllServiceProviders(@RequestParam(defaultValue = "1") int pageNum,
                                                 @RequestParam(defaultValue = "20") int pageSize) {
        return providerService.AllServiceProviders(pageNum, pageSize);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> FindServiceProviderByUsername(@PathVariable String username) {
        return providerService.FindServiceProviderByUsername(username);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> FindServiceProviderByEmail(String email) {
        return providerService.FindServiceProviderByEmail(email);
    }

    @GetMapping("/receivedApps")
    public ResponseEntity<?> FindReceivedApplications(@RequestParam(defaultValue = "1") int pageNum,
                                                      @RequestParam(defaultValue = "20") int pageSize) {
        return providerService.FindReceivedApplications(pageNum, pageSize);
    }

    @GetMapping("/reviewedApps")
    public ResponseEntity<?> FindReviewingApplications(@RequestParam(defaultValue = "1") int pageNum,
                                                       @RequestParam(defaultValue = "20") int pageSize) {
        return providerService.FindReviewingApplications(pageNum, pageSize);
    }

    @GetMapping("/confirmedApps")
    public ResponseEntity<?> FindConfirmedApplications(@RequestParam(defaultValue = "1") int pageNum,
                                                       @RequestParam(defaultValue = "20") int pageSize) {
        return providerService.FindConfirmedApplications(pageNum, pageSize);
    }

    @PutMapping ("/update")
    public ResponseEntity<?> UpdateServiceProvider(@RequestBody ServProviderUpdateDto servProviderUpdateDto) {
        return providerService.UpdateServiceProvider(servProviderUpdateDto);
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<?> DeleteServiceProvider(@PathVariable String username) {
        return providerService.DeleteServiceProvider(username);
    }
}
