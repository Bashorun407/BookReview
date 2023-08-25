package com.akinnova.BookReviewGrad.service.servproviderservice;

import com.akinnova.BookReviewGrad.dto.serviceproviderdto.ServProviderDto;
import com.akinnova.BookReviewGrad.dto.serviceproviderdto.ServProviderResponseDto;
import com.akinnova.BookReviewGrad.dto.serviceproviderdto.ServProviderUpdateDto;
import com.akinnova.BookReviewGrad.entity.ServProvider;
import com.akinnova.BookReviewGrad.enums.ApplicationStatus;
import com.akinnova.BookReviewGrad.enums.ResponseType;
import com.akinnova.BookReviewGrad.exception.ApiException;
import com.akinnova.BookReviewGrad.repository.ServProviderRepository;
import com.akinnova.BookReviewGrad.response.ResponsePojo;
import com.akinnova.BookReviewGrad.response.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServProviderServiceImpl implements IServProviderService{
    private final ServProviderRepository servProviderRepository;

    public ServProviderServiceImpl(ServProviderRepository servProviderRepository) {
        this.servProviderRepository = servProviderRepository;
    }


    @Override
    public ResponseEntity<?> AddServiceProvider(ServProviderDto servProviderDto) {
        boolean check = servProviderRepository.existsByUsername(servProviderDto.getUsername())
                || servProviderRepository.existsByEmail(servProviderDto.getEmail());

        if(check)
            return new ResponseEntity<>("Account with this detail exists", HttpStatus.BAD_REQUEST);

        ServProvider servProvider = servProviderRepository.save(ServProvider.builder()
                        .profilePicture(servProviderDto.getProfilePicture())
                        .firstName(servProviderDto.getFirstName())
                        .lastName(servProviderDto.getLastName())
                        .providerId(ResponseUtils.generateUniqueIdentifier(10, servProviderDto.getUsername()))

                        .username(servProviderDto.getUsername())
                        .email(servProviderDto.getEmail())
                        .password(servProviderDto.getPassword())
                        .specialization(servProviderDto.getSpecialization())
                        .description(servProviderDto.getDescription())
                        .applicationStatus(ApplicationStatus.Received)
                        .activeStatus(true)
                        .createdOn(LocalDateTime.now())
                .build());

        //Response dto
        ServProviderResponseDto responseDto = new ServProviderResponseDto(servProvider);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> AllServiceProviders(int pageNum, int pageSize) {

        List<ServProvider> servProviderList = servProviderRepository.findAll();

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All Service providers", servProviderList.stream()
                .filter(ServProvider::getActiveStatus).skip(pageNum).limit(pageSize)
                .map(ServProviderResponseDto::new)));
    }

    @Override
    public ResponseEntity<?> FindServiceProviderByUsername(String username) {

        ServProvider servProvider = servProviderRepository.findByUsername(username).filter(ServProvider::getActiveStatus)
                .orElseThrow(()-> new ApiException(String.format("There is no service provider with username: %s", username)));

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, String.format("Service provider with username: %s found", username), new ServProviderResponseDto(servProvider)));
    }

    @Override
    public ResponseEntity<?> FindServiceProviderByEmail(String email) {

        ServProvider servProvider = servProviderRepository.findByEmail(email).filter(ServProvider::getActiveStatus)
                .orElseThrow(()-> new ApiException(String.format("There is no service provider with email: %s", email)));

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, String.format("Service provider with email: %s found", email), new ServProviderResponseDto(servProvider)));
    }

    // TODO: 13/08/2023 To complete the following methods
    @Override
    public ResponseEntity<?> FindReceivedApplications(int pageNum, int pageSize) {

        List<ServProvider> servProviderList = servProviderRepository.findAll().stream()
                .filter(x-> x.getApplicationStatus() == ApplicationStatus.Received)
                .filter(ServProvider::getActiveStatus)
                .toList();

        if(servProviderList.isEmpty())
            return new ResponseEntity<>("There are no received applications yet.", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All Service providers", servProviderList.stream()
                .skip(pageNum).limit(pageSize)
                .map(ServProviderResponseDto::new)));
    }

    @Override
    public ResponseEntity<?> FindReviewingApplications(int pageNum, int pageSize) {
        List<ServProvider> servProviderList = servProviderRepository.findAll().stream()
                .filter(x-> x.getApplicationStatus() == ApplicationStatus.Reviewing)
                .filter(ServProvider::getActiveStatus)
                .toList();

        if(servProviderList.isEmpty())
            return new ResponseEntity<>("There are no applications in review yet.", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All Service providers", servProviderList.stream()
                .skip(pageNum).limit(pageSize)
                .map(ServProviderResponseDto::new)));
    }

    @Override
    public ResponseEntity<?> FindConfirmedApplications(int pageNum, int pageSize) {
        List<ServProvider> servProviderList = servProviderRepository.findAll().stream()
                .filter(x-> x.getApplicationStatus() == ApplicationStatus.Confirmed)
                .filter(ServProvider::getActiveStatus)
                .toList();

        if(servProviderList.isEmpty())
            return new ResponseEntity<>("There are no confirmed applications yet.", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(new ResponsePojo<>(ResponseType.SUCCESS, "All Service providers", servProviderList.stream()
                .skip(pageNum).limit(pageSize)
                .map(ServProviderResponseDto::new)));

    }

    @Override
    public ResponseEntity<?> UpdateServiceProvider(ServProviderUpdateDto servProviderUpdateDto) {
        ServProvider servProvider = servProviderRepository.findByUsername(servProviderUpdateDto.getUsername()).filter(ServProvider::getActiveStatus)
                .orElseThrow(()-> new ApiException(String.format("There is no service provider with username: %s", servProviderUpdateDto.getUsername())));

        servProvider.setProfilePicture(servProviderUpdateDto.getProfilePicture());
        servProvider.setUsername(servProviderUpdateDto.getUsername());
        servProvider.setEmail(servProviderUpdateDto.getEmail());
        servProvider.setPassword(servProviderUpdateDto.getPassword());
        servProvider.setApplicationStatus(servProviderUpdateDto.getApplicationStatus());
        servProvider.setDescription(servProviderUpdateDto.getDescription());
        servProvider.setModifiedOn(LocalDateTime.now());

        //Save changes to database
        servProviderRepository.save(servProvider);

        return new ResponseEntity<>("Service provider updated successfully", HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<?> DeleteServiceProvider(String username) {
        ServProvider servProvider = servProviderRepository.findByUsername(username).filter(ServProvider::getActiveStatus)
                .orElseThrow(()-> new ApiException(String.format("There is no service provider with username: %s", username)));

        //Delete here means to change servProvider's active status
        servProvider.setActiveStatus(false);
        //Save change in the database
        servProviderRepository.save(servProvider);
        return ResponseEntity.ok("Service Provider has been de-activated");
    }
}
