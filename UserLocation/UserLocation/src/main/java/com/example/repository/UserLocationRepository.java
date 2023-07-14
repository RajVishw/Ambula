package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.UserLocation;

public interface UserLocationRepository extends JpaRepository<UserLocation, Integer>{

}
