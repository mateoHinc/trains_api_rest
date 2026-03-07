package com.company.trains_api_rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.trains_api_rest.model.Route;

public interface RouteRepository extends JpaRepository<Route, Long> {

}
