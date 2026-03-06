package com.company.trains_api_rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.trains_api_rest.model.Station;

public interface StationRepository extends JpaRepository<Station, Long> {

}
