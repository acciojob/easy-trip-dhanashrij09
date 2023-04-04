package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AirportRepository {

    public HashMap<Integer, Passenger> passengerMap = new HashMap<>();
    public HashMap<String, Airport> airportMap = new HashMap<>();
    public HashMap<Integer, Flight> flightMap = new HashMap<>();
    public HashMap<Integer, List<Integer>> flightPassengerMap = new HashMap<>();

    public void addAirport(Airport airport) {
        airportMap.put(airport.getAirportName(), airport);
    }

    public String getLargestAirportName() {
        int max = 0;
        String st = "";
        for (Airport airport : airportMap.values()) {
            if (airport.getNoOfTerminals() > max) {
                max = airport.getNoOfTerminals();
                st = airport.getAirportName();
            }
        }

        for (Airport airport : airportMap.values()) {
            if (airport.getNoOfTerminals() == max && airport.getAirportName() != st) {
                if (airport.getAirportName().compareTo(st) < 0) {
                    st = airport.getAirportName();
                }
            }
        }
        return st;
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {
        double ans = Integer.MAX_VALUE;
        for (Flight flight : flightMap.values()) {
            if (flight.getFromCity() == fromCity && flight.getToCity() == toCity) {
                if(flight.getDuration() < ans){
                    ans = flight.getDuration();
                }
            }
        }
        if(ans == Integer.MAX_VALUE){
            return -1;
        }
        return ans;
    }

    public int getNumberOfPeopleOn(Date date, String airportName) {
        int count = 0;
        if(airportMap.containsKey(airportName)){
            City city = airportMap.get(airportName).getCity();
            for(Flight flight : flightMap.values()){
                if(flight.getFlightDate().equals(date)){
                    if(flight.getFromCity().equals(city) || flight.getToCity().equals(city)){
                        count = count + flightPassengerMap.get(flight.getFlightId()).size();
                    }
                }
            }
        }
        return count;
    }

    public int calculateFlightFare(Integer flightId) {
        int res = 3000;
        if(flightPassengerMap.containsKey(flightId)) {
            res = 3000 + (flightPassengerMap.get(flightId).size() * 50);
        }
        return res;
    }

    public String bookATicket(Integer flightId, Integer passengerId) {
        if(flightPassengerMap.containsKey(flightId)) {
            if (flightPassengerMap.get(flightId).size() >= flightMap.get(flightId).getMaxCapacity()) {
                return "FAILURE";
            }
        }
        List<Integer> passengers = flightPassengerMap.get(flightId);
        if (passengers != null && passengers.contains(passengerId)) {
            return "FAILURE";
        }
        else {
            if(passengers == null){
                passengers = new ArrayList<>();
            }
            passengers.add(passengerId);
            flightPassengerMap.put(flightId, passengers);
            return "SUCCESS";
        }
    }

    public String cancelATicket(Integer flightId, Integer passengerId) {
        if (flightPassengerMap.containsKey(flightId)) {
            List<Integer> passengers = flightPassengerMap.get(flightId);
            if (passengers.contains(passengerId)) {
                passengers.remove(passengerId);
                return "SUCCESS";
            }
        }
        return "FAILURE";
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId){
        int count = 0;
        for(List<Integer> passengers : flightPassengerMap.values()){
            if(passengers.contains(passengerId)){
                count++;
            }
        }
        return count;
    }

    public void addFlight(Flight flight) {
        flightMap.put(flight.getFlightId(), flight);
    }

    public String getAirportNameFromFlightId(Integer flightId){
        String ans = null;
        if(flightMap.containsKey(flightId)) {
            City city = flightMap.get(flightId).getFromCity();
            for (Map.Entry<String, Airport> entry : airportMap.entrySet()) {
                if (entry.getValue().getCity() == city) {
                    ans = entry.getKey();
                }
            }
        }
        return ans;
    }

    public int calculateRevenueOfAFlight(Integer flightId){
        int bookedSeat = flightPassengerMap.get(flightId).size();
        int perSeatRevenue = 3000 * bookedSeat;
        int totalRevenue = bookedSeat * perSeatRevenue;
        return totalRevenue;
    }

    public void addPassenger(Passenger passenger) {
        passengerMap.put(passenger.getPassengerId(), passenger);
    }
}
