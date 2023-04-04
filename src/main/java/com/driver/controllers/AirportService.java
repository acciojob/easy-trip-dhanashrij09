package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Service
public class AirportService {

    AirportRepository airportRepository = new AirportRepository();

    public void addAirport(Airport airport) {
         airportRepository.addAirport(airport);
    }

    public String getLargestAirportName() {
        String st = airportRepository.getLargestAirportName();
        return st;
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {
        double ans = airportRepository.getShortestDurationOfPossibleBetweenTwoCities(fromCity, toCity);
        return ans;
    }

    public int getNumberOfPeopleOn(Date date, String airportName) {
        int res = airportRepository.getNumberOfPeopleOn(date, airportName);
        return res;
    }

    public int calculateFlightFare(Integer flightId) {
        int res = airportRepository.calculateFlightFare(flightId);
        return res;
    }

    public String bookATicket(Integer flightId, Integer passengerId) {
        String ans = airportRepository.bookATicket(flightId, passengerId);
        return ans;
    }

    public String cancelATicket(Integer flightId, Integer passengerId) {
        String ans = airportRepository.cancelATicket(flightId, passengerId);
        return ans;
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
        int res = airportRepository.countOfBookingsDoneByPassengerAllCombined(passengerId);
        return res;
    }

    public void addFlight(Flight flight) {
        airportRepository.addFlight(flight);
    }

    public String getAirportNameFromFlightId(Integer flightId) {
        String ans = airportRepository.getAirportNameFromFlightId(flightId);
        return ans;
    }

    public int calculateRevenueOfAFlight(Integer flightId) {
        int res = airportRepository.calculateRevenueOfAFlight(flightId);
        return res;
    }

    public void addPassenger(Passenger passenger) {
         airportRepository.addPassenger(passenger);

    }
}
