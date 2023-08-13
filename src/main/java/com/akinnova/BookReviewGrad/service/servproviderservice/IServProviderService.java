package com.akinnova.BookReviewGrad.service.servproviderservice;

import com.akinnova.BookReviewGrad.dto.serviceproviderdto.ServProviderDto;
import com.akinnova.BookReviewGrad.dto.serviceproviderdto.ServProviderUpdateDto;
import org.springframework.http.ResponseEntity;

public interface IServProviderService {

    ResponseEntity<?> AddServiceProvider(ServProviderDto servProviderDto);
    ResponseEntity<?> AllServiceProviders(int pageNum, int pageSize);
    ResponseEntity<?> FindServiceProviderByUsername(String username);
    ResponseEntity<?> FindServiceProviderByEmail(String email);
    ResponseEntity<?> FindReceivedApplications(int pageNum, int pageSize);
    ResponseEntity<?> FindReviewingApplications(int pageNum, int pageSize);
    ResponseEntity<?> FindConfirmedApplications(int pageNum, int pageSize);
    ResponseEntity<?> UpdateServiceProvider(ServProviderUpdateDto servProviderUpdateDto);
    ResponseEntity<?> DeleteServiceProvider(String username);

}