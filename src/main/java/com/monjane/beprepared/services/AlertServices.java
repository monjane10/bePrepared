package com.monjane.beprepared.services;

import com.monjane.beprepared.model.Alert;

import java.util.List;

public interface AlertServices {

    String createAlert(Alert alert, Long cityId, Long provinceId);

    List<Alert> getAllAlert();

    List<Alert> getAllActiveAlert();

    List<Alert> getAllAlertByCityId(Long cityId);

    List<Alert> getAllAlertByProvinceId(Long provinceId);

    Alert getAlertById(Long alertId);

    String activeAlert(Long alertId);
}
