/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controller;

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
import sg.thecodetasticfour.superherosightingsgroup.dto.Organization;
import sg.thecodetasticfour.superherosightingsgroup.dto.Person;
import sg.thecodetasticfour.superherosightingsgroup.dto.Superpower;
import sg.thecodetasticfour.superherosightingsgroup.service.EntityNotFoundException;
import sg.thecodetasticfour.superherosightingsgroup.service.SuperheroSightingsOrganizationServiceLayer;
import sg.thecodetasticfour.superherosightingsgroup.service.SuperheroSightingsPersonServiceLayer;
import sg.thecodetasticfour.superherosightingsgroup.service.SuperheroSightingsSuperpowerServiceLayer;

/**
 *
 * @author zissah
 */
@Controller
public class PersonController {

    SuperheroSightingsPersonServiceLayer personServiceLayer;
    SuperheroSightingsSuperpowerServiceLayer spServiceLayer;
    SuperheroSightingsOrganizationServiceLayer orgServiceLayer;

    @Inject
    public PersonController(SuperheroSightingsPersonServiceLayer personServiceLayer, SuperheroSightingsSuperpowerServiceLayer spServiceLayer, SuperheroSightingsOrganizationServiceLayer orgServiceLayer) {
        this.personServiceLayer = personServiceLayer;
        this.spServiceLayer = spServiceLayer;
        this.orgServiceLayer = orgServiceLayer;
    }

    @RequestMapping(value = "/personHome", method = RequestMethod.GET)
    public String personHome(HttpServletRequest request, Model model) throws SuperheroSightingsPersistenceException {

        List<Person> personList = personServiceLayer.getAllPersons();
        model.addAttribute("personList", personList);

        List<Superpower> superpowerList = spServiceLayer.getAllSuperpowers();
        model.addAttribute("superpowerList", superpowerList);

        List<Organization> orgList = orgServiceLayer.getAllOrganizations();
        model.addAttribute("orgList", orgList);

        return "/PersonJSPs/Person";
    }

    @RequestMapping(value = "/addPerson", method = RequestMethod.POST)
    public String addPerson(HttpServletRequest request) throws EntityNotFoundException {

        String personFirstName = request.getParameter("firstName");
        String personLastName = request.getParameter("lastName");
        String isHeroString = request.getParameter("isHero");
        //convert value to proper data type
        Boolean isHero = Boolean.parseBoolean(isHeroString);
        String personDescription = request.getParameter("personDescription");

        //List to put all the superpowers needed for person
        List<Superpower> superpowersForPerson = new ArrayList<>();
        //this arrayList will be used for drop down menu optins
        // when superpower is added to a person
        String[] superpowersMenuList = request.getParameterValues("personSuperpowers");
        for (String currentSuperpower : superpowersMenuList) {
            int superpowerIdInt = Integer.parseInt(currentSuperpower);
            Superpower sp = spServiceLayer.getSuperpowerById(superpowerIdInt);
            superpowersForPerson.add(sp);
        }

        Person personAdded = new Person();
        personAdded.setFirstName(personFirstName);
        personAdded.setLastName(personLastName);
        personAdded.setIsHero(isHero);
        personAdded.setDescriptionOfPerson(personDescription);
        personAdded.setListOfSuperpowers(superpowersForPerson);//drop down menu superpowers

        personServiceLayer.createPerson(personAdded);

        return "redirect:/personHome";

    }

    @RequestMapping(value = "/displayPersonDetails", method = RequestMethod.GET)
    public String displayPersonDetails(HttpServletRequest request, Model model) throws EntityNotFoundException {

        String personIdString = request.getParameter("personId");
        int personIdInt = Integer.parseInt(personIdString);
        Person personDetails = personServiceLayer.getPersonById(personIdInt);
        model.addAttribute("personDetails", personDetails);

        //Go through the list of superpowers
        List<Superpower> superpowerDetailsForPerson = spServiceLayer.getAllSuperpowers();
        model.addAttribute("superpowerDetailsForPerson", superpowerDetailsForPerson);

        List<Organization> orgDetailsForPerson = orgServiceLayer.getAllOrganizations();
        model.addAttribute("orgDetailsForPerson", orgDetailsForPerson);

        return "/PersonJSPs/personDetails";

    }

    @RequestMapping(value = "/displayEditPersonForm", method = RequestMethod.GET)
    public String displayEditPersonForm(HttpServletRequest request, Model model) throws EntityNotFoundException, SuperheroSightingsPersistenceException {

        String personIdString = request.getParameter("personId");
        int personIdInt = Integer.parseInt(personIdString);

        Person personForEdit = personServiceLayer.getPersonById(personIdInt);
        model.addAttribute("personForEdit", personForEdit);

        //Iterate through the list of superpowers 
        List<Superpower> superpowerList = spServiceLayer.getAllSuperpowers();
        model.addAttribute("superpowerList", superpowerList);

        List<Organization> orgList = orgServiceLayer.getAllOrganizations();
        model.addAttribute("orgList", orgList);

        return "/PersonJSPs/editPerson";

    }

    @RequestMapping(value = "/editPerson", method = RequestMethod.POST)
    public String editPerson(@ModelAttribute("personForEdit") Person person, HttpServletRequest request) throws EntityNotFoundException {
        
        //List for all the superpowers needed for person
        List<Superpower> superpowersForPerson = new ArrayList<>();
        
        //List for all the organizations needed for person
        List<Organization> organizationsForPerson = new ArrayList<>();
        
        
        //uesd to grab values from the list of superpowers on the edit person page
        
        String[] superpowersMenuList = request.getParameterValues("personSuperpowers");
        for (String currentSuperpower : superpowersMenuList){
            int superpowerIdInt = Integer.parseInt(currentSuperpower);
            Superpower sp = spServiceLayer.getSuperpowerById(superpowerIdInt);
            superpowersForPerson.add(sp);
        }
        
        //used to grab values for the list of organizations on the edit person page
        String[] OrganizationMenuList = request.getParameterValues("personOrgs");
        for (String currentOrganization : OrganizationMenuList){
            int organizationIdInt = Integer.parseInt(currentOrganization);
            Organization o = orgServiceLayer.getOrganizationById(organizationIdInt);
            organizationsForPerson.add(o);
        }
        
        person.setListOfSuperpowers(superpowersForPerson);
        person.setListOfOrganizations(organizationsForPerson);
        personServiceLayer.updatePerson(person);
        
          return "redirect:/personHome";
            }
        
        
        
        
  
        
//        List<Integer> superpowerAsInts = person.getListOfSuperpowerIdsToPopulateSuperpowerListInPersonDTO();
//        List<Superpower> superpowersForPerson = new ArrayList<>();
//        //Takes in multiple superpower ID's and puts it inside the string array using request.parameter values
//        String[] superpowersList = request.getParameterValues("personSuperpowers");
//        if (superpowersList != null) {
//
//            for (Integer currentInt : superpowerAsInts) {
//                Superpower spAddedToPerson = spServiceLayer.getSuperpowerById(currentInt);
//                superpowersForPerson.add(spAddedToPerson);
                
//                int superpowerIdInt = Integer.parseInt(currentString);
//                Superpower currentSp = spServiceLayer.getSuperpowerById(superpowerIdInt);
//                superpowersForPerson.add(currentSp);
       

            //TODO: Delete All Orgs on Person
            //TOOD: edit basic info (service.update())
            //TODO: add back all selected personSuperpowers
            //TODO: add back all orgs 
//        }

//        List<Organization> orgsForPerson = new ArrayList<>();
//        String[] orgList = request.getParameterValues("orgsSelected");
//        for (String currentString : orgList) {
//            int orgIdInt = Integer.parseInt(currentString);
//            Organization currentOrg = orgServiceLayer.getOrganizationById(orgIdInt);
//            orgsForPerson.add(currentOrg);
//        }
//        person.setListOfSuperpowers(superpowersForPerson);
//        person.setListOfOrganizations(orgsForPerson);
//
//        personServiceLayer.updatePerson(person);


    @RequestMapping(value = "/deletePerson", method = RequestMethod.GET)
    public String deletePerson(HttpServletRequest request, Model model) throws EntityNotFoundException {

        String personIdString = request.getParameter("personId");
        int personIdInt = Integer.parseInt(personIdString);

        personServiceLayer.deletePerson(personIdInt);

        return "redirect:/personHome";
    }

}
