package com.akinnova.BookReviewGrad.service.servproviderservice;

import com.akinnova.BookReviewGrad.dto.bookdto.BookResponseDto;
import com.akinnova.BookReviewGrad.dto.serviceproviderdto.ServProviderDto;
import com.akinnova.BookReviewGrad.dto.serviceproviderdto.ServProviderResponseDto;
import com.akinnova.BookReviewGrad.dto.serviceproviderdto.ServProviderUpdateDto;
import com.akinnova.BookReviewGrad.entity.BookEntity;
import com.akinnova.BookReviewGrad.entity.ServProvider;
import com.akinnova.BookReviewGrad.entity.enums.ApplicationStatus;
import com.akinnova.BookReviewGrad.entity.enums.ReviewStatus;
import com.akinnova.BookReviewGrad.exception.ApiException;
import com.akinnova.BookReviewGrad.repository.ServProviderRepository;
import com.akinnova.BookReviewGrad.response.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServProviderService implements IServProviderService{
    private final ServProviderRepository servProviderRepository;

    public ServProviderService(ServProviderRepository servProviderRepository) {
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
                        .specialization(servProviderDto.getSpecialization())
                        .description(servProviderDto.getDescription())
                        .applicationStatus(ApplicationStatus.Received)
                        .activeStatus(true)
                        .createdOn(LocalDateTime.now())
                .build());
        //Response dto
        ServProviderResponseDto responseDto = ServProviderResponseDto.builder()
                .profilePicture(servProvider.getProfilePicture())
                .firstName(servProvider.getFirstName())
                .lastName(servProvider.getLastName())
                .description(servProvider.getDescription())
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> AllServiceProviders(int pageNum, int pageSize) {
        List<ServProvider> servProviderList = servProviderRepository.findAll();
        List<ServProviderResponseDto> responseDtoList = new ArrayList<>();

        servProviderList.stream().filter(ServProvider::getActiveStatus).skip(pageNum).limit(pageSize)
                .map(
                        servProvider -> ServProviderResponseDto.builder()
                                .profilePicture(servProvider.getProfilePicture())
                                .firstName(servProvider.getFirstName())
                                .lastName(servProvider.getLastName())
                                .description(servProvider.getDescription())
                                .build()
                ).forEach(responseDtoList::add);

        return ResponseEntity.ok()
                .header("Service Provider Page: ", String.valueOf(pageNum))
                .header("Service Provider Page Size: ", String.valueOf(pageSize))
                .header("Service Provider Total Count: ", String.valueOf(responseDtoList.size()))
                .body(responseDtoList);
    }

    @Override
    public ResponseEntity<?> FindServiceProviderByUsername(String username) {

        ServProvider servProvider = servProviderRepository.findByUsername(username).filter(ServProvider::getActiveStatus)
                .orElseThrow(()-> new ApiException(String.format("There is no service provider with username: %s", username)));

        //Response dto
        ServProviderResponseDto responseDto = ServProviderResponseDto.builder()
                .profilePicture(servProvider.getProfilePicture())
                .firstName(servProvider.getFirstName())
                .lastName(servProvider.getLastName())
                .description(servProvider.getProviderId())
                .build();

        return ResponseEntity.ok(responseDto);
    }

    @Override
    public ResponseEntity<?> FindServiceProviderByEmail(String email) {
        ServProvider servProvider = servProviderRepository.findByEmail(email).filter(ServProvider::getActiveStatus)
                .orElseThrow(()-> new ApiException(String.format("There is no service provider with email: %s", email)));

        //Response dto
        ServProviderResponseDto responseDto = ServProviderResponseDto.builder()
                .profilePicture(servProvider.getProfilePicture())
                .firstName(servProvider.getFirstName())
                .lastName(servProvider.getLastName())
                .description(servProvider.getProviderId())
                .build();

        return ResponseEntity.ok(responseDto);
    }

    // TODO: 13/08/2023 To complete the following methods
    @Override
    public ResponseEntity<?> FindReceivedApplications(int pageNum, int pageSize) {
        List<ServProvider> servProviderList = servProviderRepository.findAll().stream().filter(x-> x.getApplicationStatus() == ApplicationStatus.Received)
                .toList();
        List<ServProviderResponseDto> responseDtoList = new ArrayList<>();

        if(servProviderList.isEmpty())
            return new ResponseEntity<>("There are no received applications yet.", HttpStatus.NOT_FOUND);

        servProviderList.stream().filter(ServProvider::getActiveStatus).skip(pageNum).limit(pageSize)
                .map(
                        servProvider -> ServProviderResponseDto.builder()
                                .profilePicture(servProvider.getProfilePicture())
                                .firstName(servProvider.getFirstName())
                                .lastName(servProvider.getLastName())
                                .description(servProvider.getDescription())
                                .build()
                ).forEach(responseDtoList::add);

        return ResponseEntity.ok()
                .header("Service providers page number: ", String.valueOf(pageNum))
                .header("Service providers page size: ", String.valueOf(pageSize))
                .header("Service providers total count: ", String.valueOf(responseDtoList.size()))
                .body(responseDtoList);
    }

    @Override
    public ResponseEntity<?> FindReviewingApplications(int pageNum, int pageSize) {
        List<ServProvider> servProviderList = servProviderRepository.findAll().stream().filter(x-> x.getApplicationStatus() == ApplicationStatus.Reviewing)
                .toList();
        List<ServProviderResponseDto> responseDtoList = new ArrayList<>();

        if(servProviderList.isEmpty())
            return new ResponseEntity<>("There are no applications in review yet.", HttpStatus.NOT_FOUND);

        servProviderList.stream().filter(ServProvider::getActiveStatus).skip(pageNum).limit(pageSize)
                .map(
                        servProvider -> ServProviderResponseDto.builder()
                                .profilePicture(servProvider.getProfilePicture())
                                .firstName(servProvider.getFirstName())
                                .lastName(servProvider.getLastName())
                                .description(servProvider.getDescription())
                                .build()
                ).forEach(responseDtoList::add);

        return ResponseEntity.ok()
                .header("Service providers page number: ", String.valueOf(pageNum))
                .header("Service providers page size: ", String.valueOf(pageSize))
                .header("Service providers total count: ", String.valueOf(responseDtoList.size()))
                .body(responseDtoList);
    }

    @Override
    public ResponseEntity<?> FindConfirmedApplications(int pageNum, int pageSize) {
        List<ServProvider> servProviderList = servProviderRepository.findAll().stream().filter(x-> x.getApplicationStatus() == ApplicationStatus.Confirmed)
                .toList();
        List<ServProviderResponseDto> responseDtoList = new ArrayList<>();

        if(servProviderList.isEmpty())
            return new ResponseEntity<>("There are no confirmed applications yet.", HttpStatus.NOT_FOUND);

        servProviderList.stream().filter(ServProvider::getActiveStatus).skip(pageNum).limit(pageSize)
                .map(
                        servProvider -> ServProviderResponseDto.builder()
                                .profilePicture(servProvider.getProfilePicture())
                                .firstName(servProvider.getFirstName())
                                .lastName(servProvider.getLastName())
                                .description(servProvider.getDescription())
                                .build()
                ).forEach(responseDtoList::add);

        return ResponseEntity.ok()
                .header("Service providers page number: ", String.valueOf(pageNum))
                .header("Service providers page size: ", String.valueOf(pageSize))
                .header("Service providers total count: ", String.valueOf(responseDtoList.size()))
                .body(responseDtoList);
    }

    @Override
    public ResponseEntity<?> UpdateServiceProvider(ServProviderUpdateDto servProviderUpdateDto) {
        ServProvider servProvider = servProviderRepository.findByUsername(servProviderUpdateDto.getUsername()).filter(ServProvider::getActiveStatus)
                .orElseThrow(()-> new ApiException(String.format("There is no service provider with username: %s", servProviderUpdateDto.getUsername())));

        servProvider.setProfilePicture(servProviderUpdateDto.getProfilePicture());
        servProvider.setUsername(servProviderUpdateDto.getUsername());
        servProvider.setEmail(servProviderUpdateDto.getEmail());
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
