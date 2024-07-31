package com.monjane.beprepared.controller;

import com.monjane.beprepared.dto.request.CitizenRequestDto;
import com.monjane.beprepared.dto.response.CitizenResponseDto;
import com.monjane.beprepared.mapper.Mapper;
import com.monjane.beprepared.model.Citizen;
import com.monjane.beprepared.services.CitizenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/citizens")
@Tag(name = "3. Citizen Controller")
public class CitizenController {

    private final Mapper mapper;
    private final CitizenService citizenService;

    @PostMapping("/")
    public ResponseEntity<String> createCitizen(@Valid @RequestBody CitizenRequestDto citizenRequestDto) {
        return new ResponseEntity<>(citizenService.createCitizen(
                mapper.mapCitizenRequestToModel(citizenRequestDto), citizenRequestDto.getCityId()),
                HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CitizenResponseDto>> getAllCitizens() {
        return ResponseEntity.ok(mapper.mapCitizenToResponseDtoList(
                citizenService.getAllCitizens()
        ));
    }


    @GetMapping("/province")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CitizenResponseDto>> getAllCitizensByProvince(@RequestParam Long id) {
        return ResponseEntity.ok(mapper.mapCitizenToResponseDtoList(
                citizenService.getAllCitizensByProvinceId(id)
        ));
    }

    @GetMapping("/city")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CitizenResponseDto>> getAllCitizensByCity(@RequestParam Long id) {
        return ResponseEntity.ok(mapper.mapCitizenToResponseDtoList(
                citizenService.getAllCitizensCityId(id)
        ));
    }

    @GetMapping("/id")
    public ResponseEntity<CitizenResponseDto> getAllCitizenById(@AuthenticationPrincipal Citizen citizen) {

        return ResponseEntity.ok(mapper.mapCitizenToResponseDto(
                citizenService.getCitizenById(citizen.getId())
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitizenResponseDto> getAllCitizenById(@PathVariable Long id) {

        return ResponseEntity.ok(mapper.mapCitizenToResponseDto(
                citizenService.getCitizenById(id)
        ));
    }

    @PutMapping("/verify-account")
    public ResponseEntity<String> verifyAccount(@RequestParam String otp){
        return ResponseEntity.ok(citizenService.verifyAccount(otp));
    }

    @PutMapping("/new-otp")
    public ResponseEntity<String> generateOTPForCitizen(@RequestBody String phone){
        return ResponseEntity.ok(citizenService.generateOTPForCitizen(phone));
    }
}
