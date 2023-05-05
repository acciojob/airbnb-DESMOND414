package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class HotelManagementRepository {

    private Map<String, Hotel> hotels = new HashMap<>();
    private Map<Integer,User> userMap=new HashMap<>();

    Map<String, Integer> facilitiesCount = new HashMap<>();

    Map<String, Booking> bookings = new HashMap<>();
    public String addHotel(Hotel hotel) {
        if(hotels.containsKey(hotel.getHotelName())){
            return "FAILURE";
        }
        hotels.put(hotel.getHotelName(),hotel);
        return "SUCCESS";
    }

    public Integer addUser(User user) {
        userMap.put(user.getaadharCardNo(),user);
        return user.getaadharCardNo();
    }

    public List<Hotel> getAllHotels() {
        return new ArrayList<>(hotels.values());
    }


    public Hotel getHotelByName(String hotelName) {
        if (hotels.containsKey(hotelName)) {
            return hotels.get(hotelName);
        } else {
            return null;
        }
    }

    public int bookAroom(Booking booking) {
        String bookingId = UUID.randomUUID().toString();
        booking.setBookingId(bookingId);

        // Calculate amount to be paid
        Hotel hotel = getHotelByName(booking.getHotelName());
        int pricePerNight = hotel.getPricePerNight();
        int numRooms = booking.getNoOfRooms();
        int totalAmount = pricePerNight * numRooms;

        // Check if enough rooms are available
        int availableRooms = hotel.getAvailableRooms();
        if (availableRooms < numRooms) {
            return -1;
        }

        // Update available rooms
        hotel.setAvailableRooms(availableRooms - numRooms);

        // Update booking and save to repository
        booking.setAmountToBePaid(totalAmount);
        bookings.put(bookingId, booking);

        return totalAmount;
    }

    public List<Booking> findByAadharCard(Integer aadharCard) {
        List<Booking> book = new ArrayList<>();
        for (Booking booking : bookings.values()) {
            if (booking.getBookingAadharCard()==aadharCard) {
                book.add(booking);
            }
        }
        return book;
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        Hotel hotel = getHotelByName(hotelName);

        Set<Facility> currentFacilities = new HashSet<>(hotel.getFacilities());

        // Add the new facilities to the set of current facilities
        for (Facility facility : newFacilities) {
            currentFacilities.add(facility);
        }

        // Update the hotel's facilities with the updated set of facilities
        hotel.setFacilities(new ArrayList<>(currentFacilities));

        // Update the hotel in the hotelDb
        addOrUpdateHotel(hotel);

        // Return the updated hotel object
        return hotel;
    }

    private void addOrUpdateHotel(Hotel hotel) {
        if (hotels.containsKey(hotel.getHotelName())) {
            // If it does, update the hotel object in the map with the new one
            hotels.put(hotel.getHotelName(), hotel);
        } else {
            // If it doesn't, add the hotel object to the map
            hotels.put(hotel.getHotelName(), hotel);
        }
    }
}
