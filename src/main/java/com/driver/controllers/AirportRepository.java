package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AirportRepository {

    public HashMap<Integer, Passenger> passengerDb = new HashMap<>();
    public HashMap<String, Airport> airportDb = new HashMap<>();
    public HashMap<Integer, Flight> flightDb = new HashMap<>();
    public HashMap<Integer, List<Integer>> flightPassengerDb = new HashMap<>();

    public void addAirport(Airport airport) {
        airportDb.put(airport.getAirportName(), airport);
    }

    public String getLargestAirportName() {
        int max = 0;
        String ans = "";
        for (Airport airport : airportDb.values()) {
            if (airport.getNoOfTerminals() > max) {
               // max = airport.getNoOfTerminals();
               // ans = airport.getAirportName();
            }
        }

        for (Airport airport : airportDb.values()) {
            if (airport.getNoOfTerminals() == max && airport.getAirportName() != ans) {
                if (airport.getAirportName().compareTo(ans) < 0) {
                    ans = airport.getAirportName();
                }
            }
        }
        return ans;
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {
        double ans = Integer.MAX_VALUE;
        for (Flight flight : flightDb.values()) {
            if (flight.getFromCity() == fromCity && flight.getToCity() == toCity) {
                if(flight.getDuration() < ans){
                    ans = flight.getDuration();
                }
            }
        }
        if(ans == Integer.MAX_VALUE){
           // return -1;
        }
        return ans;
    }

    public int getNumberOfPeopleOn(Date date, String airportName) {
        int count = 0;
        if(airportDb.containsKey(airportName)){
            City city = airportDb.get(airportName).getCity();
            for(Flight flight : flightDb.values()){
                if(flight.getFlightDate().equals(date)){
                    if(flight.getFromCity().equals(city) || flight.getToCity().equals(city)){
                        count = count + flightPassengerDb.get(flight.getFlightId()).size();
                    }
                }
            }
        }
        return count;
    }

    public int calculateFlightFare(Integer flightId) {
        int ans = 3000;
        if(flightPassengerDb.containsKey(flightId)) {
            ans = 3000 + (flightPassengerDb.get(flightId).size() * 50);
        }
        return ans;
    }

    public String bookATicket(Integer flightId, Integer passengerId) {
        if(flightPassengerDb.containsKey(flightId)) {
            if (flightPassengerDb.get(flightId).size() >= flightDb.get(flightId).getMaxCapacity()) {
                return "FAILURE";
            }
        }
        List<Integer> passengers = flightPassengerDb.get(flightId);
        if (passengers != null && passengers.contains(passengerId)) {
            return "FAILURE";
        }
        else {
            if(passengers == null){
                passengers = new ArrayList<>();
            }
            passengers.add(passengerId);
            flightPassengerDb.put(flightId, passengers);
            return "SUCCESS";
        }
    }

    public String cancelATicket(Integer flightId, Integer passengerId) {
        if (flightPassengerDb.containsKey(flightId)) {
            List<Integer> passengers = flightPassengerDb.get(flightId);
            if (passengers.contains(passengerId)) {
                passengers.remove(passengerId);
                return "SUCCESS";
            }
        }
        return "FAILURE";
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId){
        int count = 0;
        for(List<Integer> passengers : flightPassengerDb.values()){
            if(passengers.contains(passengerId)){
                count++;
            }
        }
        return count;
    }

    public void addFlight(Flight flight) {
        flightDb.put(flight.getFlightId(), flight);
    }

    public String getAirportNameFromFlightId(Integer flightId){
        String ans = null;
        if(flightDb.containsKey(flightId)) {
            City city = flightDb.get(flightId).getFromCity();
            for (Map.Entry<String, Airport> entry : airportDb.entrySet()) {
                if (entry.getValue().getCity() == city) {
                    ans = entry.getKey();
                }
            }
        }
        return ans;
    }

    public int calculateRevenueOfAFlight(Integer flightId){
        int bookedSeat = flightPassengerDb.get(flightId).size();
        int perSeatRevenue = 3000 * bookedSeat;
        int totalRevenue = bookedSeat * perSeatRevenue;
        return totalRevenue;
    }

    public void addPassenger(Passenger passenger) {
        passengerDb.put(passenger.getPassengerId(), passenger);
    }
}
