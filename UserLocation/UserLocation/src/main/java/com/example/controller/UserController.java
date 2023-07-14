package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.UserLocation;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.UserLocationRepository;
import com.example.service.UserLocationService;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserLocationRepository userLocationRepository;
	private final UserLocationService userLocationService;

    @Autowired
    public UserController(UserLocationRepository userLocationRepository) {
        this.userLocationRepository = userLocationRepository;
		this.userLocationService = null;
    }
    @Autowired
    public UserController(UserLocationService userLocationService) {
        this.userLocationRepository = null;
		this.userLocationService = userLocationService;
    }
    @PostMapping
    public ResponseEntity<UserLocation> createUser(@RequestBody UserLocation userLocation) {
        UserLocation createdUserLocation = userLocationRepository.save(userLocation);
        return new ResponseEntity<>(createdUserLocation, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserLocation> updateUser(@PathVariable Integer id, @RequestBody UserLocation updatedUserLocation) {
        UserLocation existingUserLocation = userLocationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User location not found with id: " + id));

        existingUserLocation.setName(updatedUserLocation.getName());
        existingUserLocation.setLatitude(updatedUserLocation.getLatitude());
        existingUserLocation.setLongitude(updatedUserLocation.getLongitude());

        UserLocation updatedUser = userLocationRepository.save(existingUserLocation);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping("/nearest/{n}")
    public ResponseEntity<List<UserLocation>> getNearestUsers(@PathVariable int n) {
    	List<UserLocation> nearestUsers = null;
		try {
			nearestUsers = userLocationService.getNearestUsers(n);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return ResponseEntity.ok(nearestUsers);
    }
}
