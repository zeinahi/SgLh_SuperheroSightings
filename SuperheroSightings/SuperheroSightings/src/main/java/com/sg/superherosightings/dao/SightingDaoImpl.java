/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sg.thecodetasticfour.superherosightingsgroup.dao.SuperheroSightingsPersistenceException;
import sg.thecodetasticfour.superherosightingsgroup.dao.SuperheroSightingsSightingDao;
import sg.thecodetasticfour.superherosightingsgroup.dto.Sighting;

/**
 *
 * @author zissah
 */
public class SightingDaoImpl implements SuperheroSightingsSightingDao {

    private static final String SQL_INSERT_SIGHTINGS
            = "INSERT INTO Sightings "
            + "(isHeroSighting,PersonID,LocationID,SightingDate) "
            + "values(?,?,?,?);";

    private static final String SQL_SELECT_SIGHTINGS
            = "SELECT * FROM Sightings Where SightingID = ?;";

    private static final String SQL_SELECT_ALL_SIGHTINGS
            = "SELECT * FROM Sightings;";

    private static final String SQL_UPDATE_SIGHTINGS
            = "UPDATE Sightings set "
            + "isHeroSighting = ?, PersonID = ?, LocationID = ?, SightingDate = ? "
            + "Where SightingID = ?;";

    private static final String SQL_DELETE_SIGHTINGS
            = "DELETE FROM Sightings "
            + "Where SightingID = ?;";

    private static final String SQL_SELECT_LATEST_TEN_SIGHTINGS
            = "SELECT * "
            + "FROM Sightings "
            + "ORDER BY SightingDate Desc LIMIT 10;";

    private static final String SQL_SELECT_ALL_SIGHTINGS_BY_LOCATION
            = "SELECT * "
            + "FROM Sightings "
            + "Where LocationID = ?;";

    private static final String SQL_SELECT_ALL_SIGHTINGS_BY_DATE
            = "SELECT * "
            + "FROM Sightings "
            + "Where SightingDate >= ? ";
//            + "AND SightingDate < ? + INTERVAL 1 DAY;";
    
// Only need to be using sightings mapper
//    private static final String SELECT_LOCATION_BY_SIGHTING_ID
//            = "Select location.* From Location "
//            + "INNER JOIN Sightings  "
//            + "ON Location.LocationID = Sightings.LocationID "
//            + "Where Sightings.SightingID = ?;";
//    
//        private static final String SQL_SELECT_PERSONS_BY_SIGHTING_ID
//            = "Select * From Person "
//            + "INNER JOIN Sightings "
//            + "ON Person.PersonID = Sightings.PersonID "
//            + "Where Person.PersonID = ?;";
//    
    
    //Injecting Jdbdctemplate into code, to allow us to talk to sql database
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Sighting createSighting(Sighting sighting) throws SuperheroSightingsPersistenceException {
        jdbcTemplate.update(SQL_INSERT_SIGHTINGS,
                sighting.getIsHeroSighting(),
                sighting.getPerson().getPersonId(),
                sighting.getLocation().getLocationId(),
                sighting.getJustTheSightingDate()); // this one uses localdate instead of localdatetime

        // Returns the AUTO_INCREMENT id of the last row that has been inserted
        int sightingId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
        sighting.setSightingId(sightingId);
        return sighting;

    }
    
    @Override
    public Sighting getSightingById(int sightingId) throws SuperheroSightingsPersistenceException {
        return jdbcTemplate.queryForObject(SQL_SELECT_SIGHTINGS, new SightingMapper(), sightingId);

    }

    @Override
    public List<Sighting> getAllSightings() throws SuperheroSightingsPersistenceException {
        return jdbcTemplate.query(SQL_SELECT_ALL_SIGHTINGS, new SightingMapper());
    }

    @Override
    public void updateSighting(Sighting sighting) throws SuperheroSightingsPersistenceException {
        jdbcTemplate.update(SQL_UPDATE_SIGHTINGS,
                sighting.getIsHeroSighting(),
                sighting.getPerson().getPersonId(),
                sighting.getLocation().getLocationId(),
                sighting.getJustTheSightingDate(),
//                sighting.getSightingDate(),
                sighting.getSightingId());
    }

    @Override
    public void deleteSighting(int sightingId) throws SuperheroSightingsPersistenceException {
        jdbcTemplate.update(SQL_DELETE_SIGHTINGS, sightingId);
    }

    @Override
    public List<Sighting> getLatestTenSightings() throws SuperheroSightingsPersistenceException {
        return jdbcTemplate.query(SQL_SELECT_LATEST_TEN_SIGHTINGS, new SightingMapper());
    }

    // Use the service layer to get a list of persons sighted at a given location
    // First we need to all person who were sighted at what particular location (use locationId)
    @Override
    public List<Sighting> getAllSightingsByLocation(int locationId) throws SuperheroSightingsPersistenceException {
        return jdbcTemplate.query(SQL_SELECT_ALL_SIGHTINGS_BY_LOCATION, new SightingMapper(), locationId);
    }
   
    @Override
    public List<Sighting> getAllSightingsByLocalDate(LocalDate justTheSightingDate) throws SuperheroSightingsPersistenceException {
       return jdbcTemplate.query(SQL_SELECT_ALL_SIGHTINGS_BY_DATE, new SightingMapper(), justTheSightingDate);
    }
    
    // ---------------------------------DISREGARD this method----------------------------------
    @Override
    public List<Sighting> getAllSightingsByDate(LocalDateTime dateSelected) throws SuperheroSightingsPersistenceException {
        return null;
//     return jdbcTemplate.query(SQL_SELECT_ALL_SIGHTINGS_BY_DATE, new SightingMapper()

//     Need to convert LocalDateTime to a LocalDate to use in query
//      Use parameter passed to make conversion to local date
//        LocalDate dateForQuery = dateSelected.toLocalDate();
//        String dateChosen = dateForQuery.toString();
//
//        return jdbcTemplate.query(SQL_SELECT_ALL_SIGHTINGS_BY_DATE, new SightingMapper(), dateChosen);
    }

    
//    // Helper Methods
//    //Used here to access Person by a certain Sighting by joining Person & Sighting tables
//    //Need return type of Person for Sighting
//    private Person findPersonForSighting(Sighting sighting) {
//        return jdbcTemplate.queryForObject(SQL_SELECT_PERSONS_BY_SIGHTING_ID,
//                new PersonMapper(),
//                sighting.getSightingId());
//    }
//    //Used here to access Location of a certain Sighting by joining Person & Sighting tables
//    //Need return type of Location for Sighting
//     private Location findLocationForSighting(Sighting sighting) {
//        return jdbcTemplate.queryForObject(SELECT_LOCATION_BY_SIGHTING_ID,
//                new LocationMapper(),
//                sighting.getSightingId());      
//                
//    }
    
//            private List<Sighting> associatePersonsAndLocationsWithSightings(List<Sighting> sightingList) {
//        // set the complete list of location ids for each sighting
//        for (Sighting currentSighting : sightingList) {
//            // add locations to current sighting
//            currentSighting.setLocation(findLocationForSighting(currentSighting));
//            // add the person to current sightings
//            currentSighting.setPerson(
//                findPersonForSighting(currentSighting));
//        }
//        return sightingList;
//    }
//    
    
    
    //MAPPERS
    private static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int i) throws SQLException {
            Sighting sight = new Sighting();
            sight.setSightingId(rs.getInt("SightingID"));
            sight.setIsHeroSighting(rs.getBoolean("isHeroSighting"));
            // this is how we get timestamp converted to a local date
            sight.setJustTheSightingDate(rs.getTimestamp("sightingDate").toLocalDateTime().toLocalDate());
            return sight;

        }

    }
//    //Temp
//        private static final class LocationMapper implements RowMapper<Location>{
//
//        @Override
//        public Location mapRow(ResultSet rs, int i) throws SQLException {
//            
//           Location loc = new Location ();
//           
//           loc.setLocationId(rs.getInt("LocationID"));
//           loc.setLocationName(rs.getString("LocationName"));
//           loc.setLocationDescription(rs.getString("LocationDescription"));
//           loc.setLocationStreet(rs.getString("LocationStreet"));
//           loc.setLocationZipcode(rs.getString("LocationZipcode")); 
//           loc.setLocationCity(rs.getString("LocationCity"));
//           loc.setLocationState(rs.getString("LocationState"));
//           loc.setLocationCountry(rs.getString("LocationCountry"));
//           loc.setLatitude(rs.getBigDecimal("Latitude"));
//           loc.setLongitude(rs.getBigDecimal("Longitude"));
//           
//            return loc;
//        }    
//    }

}
