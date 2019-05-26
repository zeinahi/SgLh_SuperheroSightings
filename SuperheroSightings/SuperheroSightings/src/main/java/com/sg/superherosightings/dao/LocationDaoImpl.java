/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sg.thecodetasticfour.superherosightingsgroup.dao.SuperheroSightingsLocationDao;
import sg.thecodetasticfour.superherosightingsgroup.dao.SuperheroSightingsPersistenceException;
import sg.thecodetasticfour.superherosightingsgroup.dto.Location;
import sg.thecodetasticfour.superherosightingsgroup.dto.Sighting;

/**
 *
 * @author zissah
 */
public class LocationDaoImpl implements SuperheroSightingsLocationDao {

    private static final String SQL_INSERT_LOCATION
            = "INSERT INTO Location "
            + "(LocationName, LocationDescription, LocationCountry, LocationState, "
            + "LocationCity, LocationStreet, LocationZipCode, Latitude, Longitude) "
            + "values (?,?,?,?,?,?,?,?,?);";

    private static final String SQL_SELECT_LOCATION
            = "SELECT * FROM Location where LocationID = ?;";

    private static final String SQL_SELECT_ALL_LOCATIONS
            = "SELECT * FROM Location";

    private static final String SQL_UPDATE_LOCATION
            = "UPDATE Location set "
            + "LocationName = ?, LocationDescription = ?, LocationCountry = ?, LocationState = ?, "
            + "LocationCity = ?, LocationStreet = ?, LocationZipCode = ?, Latitude = ?, Longitude = ? "
            + "Where LocationID = ?;";

    private static final String SQL_DELETE_LOCATION
            = "DELETE FROM Location "
            + " where LocationID = ?;";

    private static final String SELECT_LOCATION_BY_SIGHTING_ID
            = "SELECT loc.* "
            + "FROM Sightings s "
            + "JOIN Location loc ON loc.LocationID = s.LocationID "
            + "WHERE SightingID = ?;";

    private static final String SQL_SELECT_ALL_LOCATIONS_BY_PERSON_ID
            = "Select loc.* From Location "
            + "FROM Location "
            + "JOIN Sightings s on s.LocationID = loc.LocationID "
            + "Where s.PersonID = ?;";

    //CRUD
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Location createLocation(Location location) throws SuperheroSightingsPersistenceException {
        jdbcTemplate.update(SQL_INSERT_LOCATION,
                location.getLocationName(),
                location.getLocationDescription(),
                location.getLocationCountry(),
                location.getLocationState(),
                location.getLocationCity(),
                location.getLocationStreet(),
                location.getLocationZipcode(),
                location.getLatitude(),
                location.getLongitude());

        int locationId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
        location.setLocationId(locationId);
        return location;

    }

    @Override
    public Location getLocationById(int locationId) throws SuperheroSightingsPersistenceException {
        return jdbcTemplate.queryForObject(SQL_SELECT_LOCATION, new LocationMapper(), locationId);

    }

    @Override
    public List<Location> getAllLocations() throws SuperheroSightingsPersistenceException {
        try {
            return jdbcTemplate.query(SQL_SELECT_ALL_LOCATIONS, new LocationMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }

    }

    @Override
    public void updateLocation(Location location) throws SuperheroSightingsPersistenceException {
        jdbcTemplate.update(SQL_UPDATE_LOCATION,
                location.getLocationName(),
                location.getLocationDescription(),
                location.getLocationCountry(),
                location.getLocationState(),
                location.getLocationCity(),
                location.getLocationStreet(),
                location.getLocationZipcode(),
                location.getLatitude(),
                location.getLongitude(),
                location.getLocationId());

    }

    @Override
    public void deleteLocation(int locationId) throws SuperheroSightingsPersistenceException {
        jdbcTemplate.update(SQL_DELETE_LOCATION, locationId);
    }

    @Override
    public Location findLocationForSighting(Sighting sighting) throws SuperheroSightingsPersistenceException {
        return jdbcTemplate.queryForObject(SELECT_LOCATION_BY_SIGHTING_ID, new LocationMapper(), sighting.getSightingId());

    }

    @Override
    public List<Location> findAllLocationsPersonWasSightedAt(int personId) throws SuperheroSightingsPersistenceException {
        return jdbcTemplate.query(SQL_SELECT_ALL_LOCATIONS_BY_PERSON_ID, new LocationMapper(), personId);
    }

    //MAPPER
    private static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int i) throws SQLException {

            Location loc = new Location();

            loc.setLocationId(rs.getInt("LocationID"));
            loc.setLocationName(rs.getString("LocationName"));
            loc.setLocationDescription(rs.getString("LocationDescription"));
            loc.setLocationStreet(rs.getString("LocationStreet"));
            loc.setLocationZipcode(rs.getString("LocationZipCode"));
            loc.setLocationCity(rs.getString("LocationCity"));
            loc.setLocationState(rs.getString("LocationState"));
            loc.setLocationCountry(rs.getString("LocationCountry"));
            loc.setLatitude(rs.getBigDecimal("Latitude"));
            loc.setLongitude(rs.getBigDecimal("Longitude"));

            return loc;
        }
    }

}
