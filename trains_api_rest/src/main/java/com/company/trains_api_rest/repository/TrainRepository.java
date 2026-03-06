package com.company.trains_api_rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.trains_api_rest.model.Train;
import com.company.trains_api_rest.model.TrainType;

import java.util.List;

public interface TrainRepository extends JpaRepository<Train, Long> {
    List<Train> findByType(TrainType type);

}
