package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.UserLocation;
import com.example.repository.UserLocationRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserLocationService {
    private final UserLocationRepository userLocationRepository;

    @Autowired
    public UserLocationService(UserLocationRepository userLocationRepository) {
        this.userLocationRepository = userLocationRepository;
    }

    public List<UserLocation> getNearestUsers(int n) {
        List<UserLocation> allUsers = userLocationRepository.findAll();

        // Sort the users by distance from (0,0)
        Collections.sort(allUsers, Comparator.comparingDouble(this::calculateDistanceFromOrigin));

        // Return the nearest N users
        return allUsers.stream()
                .limit(n)
                .collect(Collectors.toList());
    }

    private double calculateDistanceFromOrigin(UserLocation userLocation) {
        // Calculate the distance between the user location and (0,0) using the Haversine formula
        double lat1 = userLocation.getLatitude();
        double lon1 = userLocation.getLongitude();
        double lat2 = 0.0; // Latitude of (0,0)
        double lon2 = 0.0; // Longitude of (0,0)

        double earthRadius = 0.0; // Radius of the Earth in kilometers

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadius * c;
    }
}

