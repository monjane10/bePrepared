package com.monjane.beprepared.services;

import com.monjane.beprepared.model.City;
import com.monjane.beprepared.model.Province;

import java.util.List;

public interface LocationService {

    List<Province> getAllProvinces();

    List<City> getAllCities();

    List<City> getAllCitiesProvinceId(Long provinceId);

    Province getProvinceById(Long provinceId);

    City getCityById(Long cityId);
}
