/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import sg.thecodetasticfour.superherosightingsgroup.dao.SuperheroSightingsLocationDao;
import sg.thecodetasticfour.superherosightingsgroup.dao.SuperheroSightingsOrganizationDao;
import sg.thecodetasticfour.superherosightingsgroup.dao.SuperheroSightingsPersistenceException;
import sg.thecodetasticfour.superherosightingsgroup.dao.SuperheroSightingsPersonDao;
import sg.thecodetasticfour.superherosightingsgroup.dao.SuperheroSightingsSightingDao;
import sg.thecodetasticfour.superherosightingsgroup.dao.SuperheroSightingsSuperpowerDao;
import sg.thecodetasticfour.superherosightingsgroup.dto.Location;
import sg.thecodetasticfour.superherosightingsgroup.dto.Person;
import sg.thecodetasticfour.superherosightingsgroup.dto.Sighting;
import sg.thecodetasticfour.superherosightingsgroup.dto.Superpower;
import sg.thecodetasticfour.superherosightingsgroup.service.EntityNotFoundException;
import sg.thecodetasticfour.superherosightingsgroup.service.SuperheroSightingsSightingServiceLayer;

/**
 *
 * @author zissah
 */
public class SightingServiceLayerImpl implements SuperheroSightingsSightingServiceLayer {

    SuperheroSightingsSightingDao sightingDao;
    SuperheroSightingsLocationDao locationDao;
    SuperheroSightingsPersonDao personDao;
    SuperheroSightingsOrganizationDao orgDao;
    SuperheroSightingsSuperpowerDao superpowerDao;

    @Inject
    public SightingServiceLayerImpl(SuperheroSightingsSightingDao sightingDao, SuperheroSightingsLocationDao locationDao, SuperheroSightingsPersonDao personDao, SuperheroSightingsOrganizationDao orgDao, SuperheroSightingsSuperpowerDao superpowerDao) {
        this.sightingDao = sightingDao;
        this.locationDao = locationDao;
        this.personDao = personDao;
        this.orgDao = orgDao;
        this.superpowerDao = superpowerDao;
    }

    @Override
    public Sighting createSighting(Sighting sighting) {
        Sighting sight = new Sighting();

        try {
          sight = sightingDao.createSighting(sighting);
        } catch (SuperheroSightingsPersistenceException ex) {
            Logger.getLogger(SightingServiceLayerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sight;
    }

    @Override
    public Sighting getSightingById(int sightingId) throws EntityNotFoundException {
        Sighting sightSelected = new Sighting();

        try {
            sightSelected = sightingDao.getSightingById(sightingId);
           Person per = personDao.findPersonForSighting(sightSelected);
           //Need to get superpowers for person and set it each time we have a new person 
           per.setListOfSuperpowers(superpowerDao.findSuperpowersForPerson(per));
           Location loc = locationDao.findLocationForSighting(sightSelected);
           
           sightSelected.setPerson(per);
           sightSelected.setLocation(loc);
           //return back the selected sighting
           return sightSelected;

        } catch (SuperheroSightingsPersistenceException ex) {
            Logger.getLogger(SightingServiceLayerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;




 }

    @Override
    public List<Sighting> getAllSightings() {
        try {
            // Actually need to go through the list of all sightings
            List <Sighting> allSightings = sightingDao.getAllSightings();
            return associatePersonsAndLocationsWithSightings(allSightings);
//            return sightingDao.getAllSightings();
        } catch (SuperheroSightingsPersistenceException ex) {
            Logger.getLogger(SightingServiceLayerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void updateSighting(Sighting sighting) {
        try {
            sightingDao.updateSighting(sighting);
        } catch (SuperheroSightingsPersistenceException ex) {
            Logger.getLogger(SightingServiceLayerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteSighting(int sightingId) {
        try {
            sightingDao.deleteSighting(sightingId);
        } catch (SuperheroSightingsPersistenceException ex) {
            Logger.getLogger(SightingServiceLayerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//
//    @Override
//    public List<Sighting> getAllSightingsByDate(LocalDateTime dateSelected) {
//           try {
//               List<Sighting> sightingDate = sightingDao.getAllSightingsByDate(dateSelected);
//           } catch (SuperheroSightingsPersistenceException ex) {
//               Logger.getLogger(SightingServiceLayerImpl.class.getName()).log(Level.SEVERE, null, ex);
//           }
//           return null;
//    }
//    
    @Override
    public List<Sighting> getLatestTenSightings() {
        try {
            List<Sighting> sightingList = sightingDao.getLatestTenSightings();
        // Need persons and locations for each sighting, by going through all sightings
            return associatePersonsAndLocationsWithSightings (sightingList);
        } catch (SuperheroSightingsPersistenceException ex) {
            Logger.getLogger(SightingServiceLayerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Sighting> getAllSightingsByLocation(int locationId) {
        try {
            List<Sighting> sightingsByLocation = sightingDao.getAllSightingsByLocation(locationId);
            return associatePersonsAndLocationsWithSightings (sightingsByLocation);
        } catch (SuperheroSightingsPersistenceException ex) {
            Logger.getLogger(SightingServiceLayerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
//=====================================IGNORE
    @Override
    public List<Sighting> getAllSightingsByDate(LocalDateTime dateSelected) {
        try {
            List<Sighting> sightingList = sightingDao.getAllSightingsByDate(dateSelected);
        } catch (SuperheroSightingsPersistenceException ex) {
            Logger.getLogger(SightingServiceLayerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Sighting> getAllSightingsByLocalDate(LocalDate ld) {
        try {
            List<Sighting> sightingList = sightingDao.getAllSightingsByLocalDate(ld);
            return associatePersonsAndLocationsWithSightings(sightingList);
        } catch (SuperheroSightingsPersistenceException ex) {
            Logger.getLogger(SightingServiceLayerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
       private List<Sighting> associatePersonsAndLocationsWithSightings(List<Sighting> sightingList) throws SuperheroSightingsPersistenceException {
        // set the complete list of location ids for each sighting
        for (Sighting currentSighting : sightingList) {
            //Need to set the person to a sighting using dao
            Person perToASighting = personDao.findPersonForSighting(currentSighting);
           //Get list of superpowers for person
           List <Superpower> superpowerForPer = superpowerDao.findSuperpowersForPerson(perToASighting);
            currentSighting.setPerson(perToASighting);
            
            // Set location to a sighing using dao
            Location locToASighting = locationDao.findLocationForSighting(currentSighting);
            currentSighting.setLocation(locToASighting);
        }
        return sightingList;
    }
    
    
    
    
}
