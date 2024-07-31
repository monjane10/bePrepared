package com.monjane.beprepared.services.impl;

import com.monjane.beprepared.exception.EntityNotFoundException;
import com.monjane.beprepared.model.Alert;
import com.monjane.beprepared.model.City;
import com.monjane.beprepared.model.Province;
import com.monjane.beprepared.repository.AlertRepository;
import com.monjane.beprepared.services.AlertServices;
import com.monjane.beprepared.services.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AlertServicesImpl implements AlertServices {

    private final AlertRepository alertRepository;
    private final LocationService locationService;

    @Override
    @Transactional
    public String createAlert(Alert alert, Long cityId, Long provinceId) {

        City city = locationService.getCityById(cityId);
        Province province = locationService.getProvinceById(provinceId);
        alert.setActive(false);
        alert.setCity(city);
        alert.setProvince(province);
        alertRepository.save(alert);
        return "Alerta criado com sucesso!";
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alert> getAllAlert() {
        return alertRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alert> getAllActiveAlert() {
        return alertRepository.findAllByActive(true);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alert> getAllAlertByCityId(Long cityId) {
        return alertRepository.findAllByActiveAndCityId(true, cityId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alert> getAllAlertByProvinceId(Long provinceId) {

        return alertRepository.findAllByActiveAndProvinceId(true, provinceId);
    }

    @Override
    @Transactional(readOnly = true)
    public Alert getAlertById(Long alertId) {

        return alertRepository.findById(alertId).orElseThrow(()->
                new EntityNotFoundException("Alerta nao encontrada"));
    }

    @Override
    @Transactional
    public String activeAlert(Long alertId) {

        Alert alert = getAlertById(alertId);
        alert.setActive(true);
        alertRepository.save(alert);
        return "Perigo, Proteja-se";
    }
}
