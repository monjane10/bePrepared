package com.monjane.beprepared.controller;

import com.monjane.beprepared.dto.request.AlertRequestDto;
import com.monjane.beprepared.dto.response.AlertResponseDto;
import com.monjane.beprepared.mapper.Mapper;
import com.monjane.beprepared.services.AlertServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/alerts")
@Tag(name = "2. Alert Controller")
public class AlertController {

    private  final Mapper mapper;
    private  final AlertServices alertServices;

    @PostMapping("/")
    public ResponseEntity<String> createAlert(@Valid @RequestBody AlertRequestDto alertRequestDto){
        return new ResponseEntity<>(alertServices.createAlert(mapper.mapAlertRequestToModel(
                alertRequestDto),
                alertRequestDto.getCityId(), alertRequestDto.getProvinceId()), HttpStatus.CREATED);
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AlertResponseDto>> getAllAlerts(){
        return ResponseEntity.ok(mapper.mapAlertToResponseDtoList(
                alertServices.getAllAlert()
        ));
    }
    @GetMapping("/active")
    public ResponseEntity<List<AlertResponseDto>> getAllActiveAlerts() {
        return ResponseEntity.ok(mapper.mapAlertToResponseDtoList(
                alertServices.getAllActiveAlert()
        ));
    }

    @GetMapping("/city")
    public  ResponseEntity<List<AlertResponseDto>> getAllAlertsByCity(@RequestParam Long cityId){
        return ResponseEntity.ok(mapper.mapAlertToResponseDtoList(
                alertServices.getAllAlertByCityId(cityId)
        ));
    }

    @GetMapping("/province")
    public ResponseEntity<List<AlertResponseDto>> getAllAlertsByProvince(@RequestParam Long provinceId){
        return ResponseEntity.ok(mapper.mapAlertToResponseDtoList(
                alertServices.getAllAlertByProvinceId(provinceId)
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertResponseDto> getAlertById(@PathVariable Long id){
        return ResponseEntity.ok(mapper.mapAlertToResponseDto(
                alertServices.getAlertById(id)
        ));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> activeAlert(@PathVariable Long id){
        return ResponseEntity.ok(alertServices.activeAlert(id));
    }
}
