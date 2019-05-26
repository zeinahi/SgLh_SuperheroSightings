/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controller;

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
import sg.thecodetasticfour.superherosightingsgroup.dto.User;
import sg.thecodetasticfour.superherosightingsgroup.service.EntityNotFoundException;
import sg.thecodetasticfour.superherosightingsgroup.service.SuperheroSightingsOrganizationServiceLayer;
import sg.thecodetasticfour.superherosightingsgroup.service.SuperheroSightingsPersonServiceLayer;

/**
 *
 * @author zissah
 */
@Controller
public class OrganizationController {

    @Inject
    SuperheroSightingsOrganizationServiceLayer orgServiceLayer;
    SuperheroSightingsPersonServiceLayer personServiceLayer;

    public OrganizationController(SuperheroSightingsOrganizationServiceLayer orgServiceLayer, SuperheroSightingsPersonServiceLayer personServiceLayer) {
        this.orgServiceLayer = orgServiceLayer;
        this.personServiceLayer = personServiceLayer;
    }

    @RequestMapping(value = "/organizationHome", method = RequestMethod.GET)
    public String organizationHome(HttpServletRequest request, Model model) throws SuperheroSightingsPersistenceException {

        List<Organization> orgList = orgServiceLayer.getAllOrganizations();
        model.addAttribute("orgList", orgList);

        //Need to go through personList to list persons to org
        List<Person> personList = personServiceLayer.getAllPersons();
        model.addAttribute("personList", personList);

        return "/OrganizationJSPs/Organization";
    }

    @RequestMapping(value = "/addOrganization", method = RequestMethod.POST)
    public String addOrganization(HttpServletRequest request) throws EntityNotFoundException {

        String orgName = request.getParameter("organizationName");
        String orgDescription = request.getParameter("organizationDescription");
        String orgCountry = request.getParameter("organizationCountry");
        String orgState = request.getParameter("organizationState");
        String orgCity = request.getParameter("organizationCity");
        String orgStreet = request.getParameter("organizationStreet");
        String orgZipcode = request.getParameter("organizationZipCode");

        // grab the incoming values from the form and create a new Organization object
        Organization orgAdded = new Organization();

        orgAdded.setOrganizationName(orgName);
        orgAdded.setOrganizationDescription(orgDescription);
        orgAdded.setOrganizationCountry(orgCountry);
        orgAdded.setOrganizationState(orgState);
        orgAdded.setOrganizationCity(orgCity);
        orgAdded.setOrganizationStreet(orgStreet);
        orgAdded.setOrganizationZipcode(orgZipcode);
        orgAdded.setIsItAHeroOrganization(true);

//      //List to put all persons needed for this organization
//        List<Person> personsInOrg = new ArrayList<>();
//        String[] personsUserSelects = request.getParameterValues("personsUserSelects");
//        
//        for (String currentPerson : personsUserSelects) {
//            int personIdInt = Integer.parseInt(currentPerson);
//            
//            Person per = personServiceLayer.getPersonById(personIdInt);
//            personsInOrg.add(per);
//            
//        }
//        orgAdded.setListOfPersons(personsInOrg);
        // persist the new Organization
        orgServiceLayer.createOrganization(orgAdded);

        // we don't want to forward to a View component - we want to
        // redirect to the endpoint that displays the organizationHome
        // so it can display the new Organization in the table.
        return "redirect:/organizationHome";
    }

    @RequestMapping(value = "/displayOrganizationDetails", method = RequestMethod.GET)
    public String displayOrganizationDetails(HttpServletRequest request, Model model) throws EntityNotFoundException {
        String organizationIdString = request.getParameter("organizationId");
        int organizationIdInt = Integer.parseInt(organizationIdString);

        Organization organizationDetails = orgServiceLayer.getOrganizationById(organizationIdInt);

        model.addAttribute("organizationDetails", organizationDetails);

        List<Person> personsInOrg = organizationDetails.getListOfPersons();
        model.addAttribute("personsInOrg", personsInOrg);

        //Get list of users with admin rights who are apart of an org
        List<User> usersInOrg = organizationDetails.getOrganizationAdmins();
        model.addAttribute("usersInOrg ", usersInOrg);

        return "/OrganizationJSPs/organizationDetails";
    }

    @RequestMapping(value = "/displayEditOrganizationForm", method = RequestMethod.GET)
    public String displayEditOrganizationForm(HttpServletRequest request, Model model) throws EntityNotFoundException {

        String organizationIdString = request.getParameter("organizationId");
        int organizationId = Integer.parseInt(organizationIdString);

        Organization organizationForEdit = orgServiceLayer.getOrganizationById(organizationId);
        model.addAttribute("organizationForEdit", organizationForEdit);

        List<Person> personsList = personServiceLayer.getAllPersons();
        model.addAttribute("personsList", personsList);
        
        return "/OrganizationJSPs/editOrganization";
    }

    //   Edit Organization form submission endpoint
    @RequestMapping(value = "/editOrganization", method = RequestMethod.POST)
    public String editOrganization(@ModelAttribute("organization") Organization organization) {

        orgServiceLayer.updateOrganization(organization);

        return "redirect:organizationHome";
    }

    @RequestMapping(value = "/deleteOrganization", method = RequestMethod.GET)
    public String deleteOrganization(HttpServletRequest request, Model model) throws SuperheroSightingsPersistenceException {

        String organizationIdString = request.getParameter("organizantionId");
        int organizationId = Integer.parseInt(organizationIdString);

        orgServiceLayer.deleteOrganization(organizationId);

        return "redirect:/organizationHome";

    }
}
