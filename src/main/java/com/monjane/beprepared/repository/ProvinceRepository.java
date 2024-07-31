package com.monjane.beprepared.repository;

import com.monjane.beprepared.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends JpaRepository<Province,Long> {
}
