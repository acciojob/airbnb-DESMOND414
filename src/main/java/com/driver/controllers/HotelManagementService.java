package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class HotelManagementService {

    HotelManagementRepository hmr=new HotelManagementRepository();

    public String addHotel(Hotel hotel) {
        if (hotel == null || hotel.getHotelName() == null) {
            return "FAILURE";
        }

        return hmr.addHotel(hotel);

    }


    public Integer addUser(User user) {
        return hmr.addUser(user);
    }

    public String getHotelWithMostFacilities() {
        return hmr.getHotelWithMostFacilites();
//        List<Hotel> hotels = hmr.getAllHotels();
//        if (hotels.isEmpty()) {
//            return "";
//        }
//        Map<String, Integer> facilitiesCount = new HashMap<>();
//        for (Hotel hotel : hotels) {
//            for (Facility facility : hotel.getFacilities()) {
//                facilitiesCount.put(String.valueOf(facility), facilitiesCount.getOrDefault(facility, 0) + 1);
//            }
//        }
//        if (facilitiesCount.isEmpty()) {
//            return "";
//        }
//        String hotelWithMostFacilities = "";
//        int maxFacilities = 0;
//        for (Hotel hotel : hotels) {
//            int numFacilities = 0;
//            for (Facility facility : hotel.getFacilities()) {
//                numFacilities += facilitiesCount.getOrDefault(facility, 0);
//            }
//            if (numFacilities > maxFacilities) {
//                maxFacilities = numFacilities;
//                hotelWithMostFacilities = hotel.getHotelName();
//            } else if (numFacilities == maxFacilities) {
//                hotelWithMostFacilities = hotel.getHotelName().compareTo(hotelWithMostFacilities) < 0 ? hotel.getHotelName() : hotelWithMostFacilities;
//            }
//        }
//        return hotelWithMostFacilities;


    }

    public int bookAroom(Booking booking) {

        return hmr.bookAroom(booking);

    }

    public int getBookings(Integer aadharCard) {
        List<Booking> bookings = hmr.findByAadharCard(aadharCard);
        return bookings.size();

    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        return hmr.updateFacilities(newFacilities,hotelName);
    }
}
