/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sg.thecodetasticfour.superherosightingsgroup.dao.SuperheroSightingsPersistenceException;
import sg.thecodetasticfour.superherosightingsgroup.dto.Location;
import sg.thecodetasticfour.superherosightingsgroup.dto.Person;
import sg.thecodetasticfour.superherosightingsgroup.dto.Sighting;
import sg.thecodetasticfour.superherosightingsgroup.dto.Superpower;
import sg.thecodetasticfour.superherosightingsgroup.service.EntityNotFoundException;
import sg.thecodetasticfour.superherosightingsgroup.service.SuperheroSightingsLocationServiceLayer;
import sg.thecodetasticfour.superherosightingsgroup.service.SuperheroSightingsPersonServiceLayer;
import sg.thecodetasticfour.superherosightingsgroup.service.SuperheroSightingsSightingServiceLayer;

/**
 *
 * @author zissah
 */
@Controller
public class SightingController {

    SuperheroSightingsSightingServiceLayer sightingServiceLayer;
    SuperheroSightingsPersonServiceLayer personServiceLayer;
    SuperheroSightingsLocationServiceLayer locationServiceLayer;

    @Inject
    public SightingController(SuperheroSightingsSightingServiceLayer sightingServiceLayer, SuperheroSightingsPersonServiceLayer personServiceLayer, SuperheroSightingsLocationServiceLayer locationServiceLayer) {
        this.sightingServiceLayer = sightingServiceLayer;
        this.personServiceLayer = personServiceLayer;
        this.locationServiceLayer = locationServiceLayer;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexHomePage(HttpServletRequest request, Model model) throws SuperheroSightingsPersistenceException, EntityNotFoundException {
        //  List<Sighting> lastTenSightings = sightingServiceLayer.getLatestTenSightings();
//
//        List<Person> allPersons = personServiceLayer.getAllPersons();
//        model.addAttribute("allPersons", allPersons);
//
//        List<Location> locationsSeen = locationServiceLayer.getAllLocations();
//        model.addAttribute("locationsSeen", locationsSeen);

        List<Sighting> sightingList = sightingServiceLayer.getLatestTenSightings();

        List<Sighting> lastTenSightings = new ArrayList<>();
        for (Sighting individualSighting : sightingList) {
            Sighting sighting = individualSighting;
            Person person = sighting.getPerson();
            int personId = person.getPersonId();
            Location location = individualSighting.getLocation();
            int locationId = location.getLocationId();

            Person realPerson = personServiceLayer.getPersonById(personId);
            Location realLocation = locationServiceLayer.getLocationById(locationId);
            sighting.setPerson(realPerson);
            sighting.setLocation(realLocation);
            lastTenSightings.add(sighting);
        }

        model.addAttribute("lastTenSightings", lastTenSightings);

        return "index";
    }

    @RequestMapping(value = "/sightingHome", method = RequestMethod.GET)
    public String sightingHome(HttpServletRequest request, Model model) throws SuperheroSightingsPersistenceException, EntityNotFoundException {

        List<Person> allPersons = personServiceLayer.getAllPersons();
        model.addAttribute("allPersons", allPersons);

        List<Location> locationsSeen = locationServiceLayer.getAllLocations();
        model.addAttribute("locationsSeen", locationsSeen);

        List<Sighting> allSightings = sightingServiceLayer.getAllSightings();

        List<Sighting> sightings = new ArrayList<>();
        for (Sighting individualSighting : allSightings) {
            Sighting sighting = individualSighting;
            Person person = sighting.getPerson();
            int personId = person.getPersonId();
            Location location = individualSighting.getLocation();
            int locationId = location.getLocationId();

            Person realPerson = personServiceLayer.getPersonById(personId);
            Location realLocation = locationServiceLayer.getLocationById(locationId);
            sighting.setPerson(realPerson);
            sighting.setLocation(realLocation);
            sightings.add(sighting);
        }

        model.addAttribute("sightings", sightings);
//
//        List<Person> personsSightedByLocation = personServiceLayer.getPersonsFromSightingsByLocation();
//        model.addAttribute("personsSightedByLocation", personsSightedByLocation);
//
//        List<Person> allPersonsSightingsByLocation = personServiceLayer.getPersonsFromSightingsByLocation();
//        model.addAttribute("allPersonsSightingsByLocation", allPersonsSightingsByLocation);

        return "/SightingJSPs/Sighting";
    }

    @RequestMapping(value = "/addSighting", method = RequestMethod.POST)
    public String addSighting(HttpServletRequest request, Model model) throws EntityNotFoundException {

        String personIdString = request.getParameter("personId");
        String locationIdString = request.getParameter("locationId");

        int personIdInt = Integer.parseInt(personIdString);
        Person per = personServiceLayer.getPersonById(personIdInt);

        int locationIdInt = Integer.parseInt(locationIdString);
        Location loc = locationServiceLayer.getLocationById(locationIdInt);

        String isHeroSightingString = request.getParameter("isHeroSighting");
        //returns true booleab value for non-null String, ignores case & returns false for everything else
        Boolean isHeroSightingBool = Boolean.valueOf(isHeroSightingString);

        String sightingDateString = request.getParameter("justTheSightingDate");
        //parse sightingDate value to LocalDate
        LocalDate localSightingDate = LocalDate.parse(sightingDateString);

//      DateTimeFormatter formatSighting = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//      LocalDateTime dateAndTime = LocalDateTime.parse(sightingDateString);
        //set getters
        Sighting sightingAdded = new Sighting();
        sightingAdded.setIsHeroSighting(isHeroSightingBool);
        sightingAdded.setJustTheSightingDate(localSightingDate);
//      sightingAdded.setSightingDate(dateAndTime);
        sightingAdded.setPerson(per);
        sightingAdded.setLocation(loc);

        sightingServiceLayer.createSighting(sightingAdded);

        return "redirect:/sightingHome";

    }

    @RequestMapping(value = "/displaySightingDetails", method = RequestMethod.GET)
    public String displaySightingDetails(HttpServletRequest request, Model model) throws EntityNotFoundException {

        String sightingIdString = request.getParameter("sightingId");
        int sightingIdInt = Integer.parseInt(sightingIdString);
        Sighting sightingDetails = sightingServiceLayer.getSightingById(sightingIdInt);

        //Need to go through person to access list of superpowers
        List<Superpower> personSPs = sightingDetails.getPerson().getListOfSuperpowers();
        //Put back on model
        model.addAttribute("sightingDetails", sightingDetails);
        model.addAttribute("personSPs", personSPs);

        LocalDate sightingDate = sightingDetails.getJustTheSightingDate();
        List<Sighting> specificSightingDate = sightingServiceLayer.getAllSightingsByLocalDate(sightingDate);
        model.addAttribute("specificSightingDate", specificSightingDate);
//        LocalDateTime sightingDateAndTime = sightingForEdit.getSightingDate();
//        List<Sighting> specificSightingDateTime = sightingServiceLayer.getAllSightings();
//
//        Sighting sightingDetails = sightingServiceLayer.getSightingById(sightingIdInt);
//        model.addAttribute("sightingDetails",sightingDetails );

        // Return the logical name of our View component
        return "/SightingJSPs/sightingDetails";
    }

    //displayEditSightingForm
    @RequestMapping(value = "/displayEditSightingForm", method = RequestMethod.GET)
    public String displayEditSightingForm(HttpServletRequest request, Model model) throws EntityNotFoundException {

        String sightingIdString = request.getParameter("sightingId");
        int sightingIdInt = Integer.parseInt(sightingIdString);

        Sighting sightingForEdit = sightingServiceLayer.getSightingById(sightingIdInt);
        model.addAttribute("sightingForEdit", sightingForEdit);

        //Iterate through the list of persons and then locations
        List<Person> allPersons = personServiceLayer.getAllPersons();
        model.addAttribute("allPersons", allPersons);

        List<Location> allLocations = locationServiceLayer.getAllLocations();
        model.addAttribute("allLocations", allLocations);

        return "/SightingJSPs/editSighting";
    }

//   Edit Sighting form submission endpoint
    @RequestMapping(value = "/editSighting", method = RequestMethod.POST)
    public String editSighting(HttpServletRequest request, @ModelAttribute("sightingForEdit") Sighting sighting) throws EntityNotFoundException {

        String sightingDateString = request.getParameter("justTheSightingDate");
        LocalDate localSightingDate = LocalDate.parse(sightingDateString);
        sighting.setJustTheSightingDate(localSightingDate);

        String personIdString = request.getParameter("person.personId");
        int personIdInt = Integer.parseInt(personIdString);
        //Go to service later to get person by id(int)
        Person perAddedToSighting = personServiceLayer.getPersonById(personIdInt);
        //Set this back to the sighting parameter passed on model
        sighting.setPerson(perAddedToSighting);

        String locationIdString = request.getParameter("location.locationId");
        int locationIdInt = Integer.parseInt(locationIdString);
        Location locAddedToSighting = locationServiceLayer.getLocationById(locationIdInt);
        sighting.setLocation(locAddedToSighting);

        sightingServiceLayer.updateSighting(sighting);

        return "redirect:/sightingHome";
    }

    @RequestMapping(value = "/deleteSighting", method = RequestMethod.GET)
    public String deleteSighting(HttpServletRequest request, Model model) throws EntityNotFoundException {

        String sightingIdString = request.getParameter("sightingId");
        int sightingIdInt = Integer.parseInt(sightingIdString);

        sightingServiceLayer.deleteSighting(sightingIdInt);

        return "redirect:/sightingHome";

    }

}
