package com.company.trains_api_rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.trains_api_rest.model.Train;

public interface TrainRepository extends JpaRepository<Train, Long> {

}
