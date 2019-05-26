/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sg.thecodetasticfour.superherosightingsgroup.dao.SuperheroSightingsPersistenceException;
import sg.thecodetasticfour.superherosightingsgroup.dao.SuperheroSightingsPersonDao;
import sg.thecodetasticfour.superherosightingsgroup.dto.Organization;
import sg.thecodetasticfour.superherosightingsgroup.dto.Person;
import sg.thecodetasticfour.superherosightingsgroup.dto.Sighting;
import sg.thecodetasticfour.superherosightingsgroup.dto.Superpower;

/**
 *
 * @superpower zissah
 */
public class PersonDaoImpl implements SuperheroSightingsPersonDao {

    //Prepared statements for CRUD Person database 
    private static final String SQL_INSERT_PERSON
            = "INSERT INTO Person"
            + "(FirstName, LastName, isHero, PersonDescription)"
            + "values (?,?,?,?);";

    private static final String SQL_SELECT_PERSON
            = "SELECT * FROM Person Where PersonID = ?;";

    private static final String SQL_SELECT_ALL_PERSONS
            = "SELECT * FROM Person;";

    
           private static final String SQL_UPDATE_PERSON
           = " UPDATE Person set FirstName = ?, LastName = ?, isHero = ?, PersonDescription = ? where PersonID = ?;";

    private static final String SQL_DELETE_PERSON
            = "DELETE FROM Person "
            + "where PersonID = ?;";

    private static final String SQL_INSERT_PERSON_SUPERPOWERS
            = "INSERT INTO PersonSuperpowers"
            + "(PersonID,SuperpowerID)"
            + "values (?,?);";

    private static final String SQL_DELETE_PERSON_SUPERPOWERS
            = "DELETE FROM PersonSuperpowers "
            + "where PersonID = ?;";

    //Need to access a list of persons that belong to Org based on org ID, need Person Dao to retrieve this
    private static final String SQL_SELECT_PERSONS_BY_ORGANIZATION_ID
            = "SELECT per.isHero, per.FirstName, per.LastName, per.PersonDescription"
            + "FROM Person per "
            + "JOIN OrganizationMembers orgmem ON per.PersonID =  orgmem.PersonID "
            + "where orgmem.OrganizationID = ?;";

    private static final String SQL_INSERT_ORGANIZATION_MEMBERS
            = "INSERT INTO OrganizationMembers"
            + "(OrganizationID, PersonID)"
            + "values (?,?);";

 private static final String SQL_DELETE_ORGANIZATION_MEMBERS
           = "DELETE FROM OrganizationMembers "
           + "where PersonID = ?;";

    private static final String SQL_SELECT_PERSONS_BY_SUPERPOWER
            = "SELECT per.* "
            + "FROM Person per "
            + "JOIN PersonSuperpowers persp ON per.PersonID = persp.PersonID "
            + "where persp.SuperpowerID = ?;";
    
    //Need to get all persons by sightingID
    private static final String SELECT_PERSON_BY_SIGHTING_ID
            = "SELECT p.* "
            + "FROM Sightings s "
            + "JOIN Person p ON p.PersonID = s.PersonID "
            + "WHERE SightingID = ?;";

//    private static final String SQL_SELECT_PERSONS_BY_LOCATION
//            = "SELECT per.* "
//            + "FROM Person per "
//            + "JOIN Sightings sig ON per.PersonID = sig.PersonID"
//            + "where sig.LocationID = ?;";

    //Injecting Jdbdctemplate into code, to allow us to talk to sql database
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Person createPerson(Person person) throws SuperheroSightingsPersistenceException {
        jdbcTemplate.update(SQL_INSERT_PERSON,
                person.getFirstName(),
                person.getLastName(),
                person.getIsHero(),
                person.getDescriptionOfPerson());
        //Takes last ID created in database  & auto increments 
        int personId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
        person.setPersonId(personId);
        //PersonSuperpower needs to pass in person using personID for each superpower person has into bridge table
        insertPersonSuperpowers(person);
        //Handles person without an organization
         if (person.getListOfOrganizations() != null) {
            insertOrganizationMembers(person);
        }

        return person;
    }

    @Override
    public Person getPersonById(int personId) throws SuperheroSightingsPersistenceException {
        try {
            // get the properties from the person table in database
            Person per = jdbcTemplate.queryForObject(SQL_SELECT_PERSON, new PersonMapper(), personId);
            // Return person
            return per;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Person> getAllPersons() throws SuperheroSightingsPersistenceException {
        //Get list of all persons 
        return jdbcTemplate.query(SQL_SELECT_ALL_PERSONS, new PersonMapper());

    }

//    @Override
//    public void updatePerson(Person person) throws SuperheroSightingsPersistenceException {
//        jdbcTemplate.update(SQL_UPDATE_PERSON,
//                person.getFirstName(),
//                person.getLastName(),
//                person.getIsHero(),
//                person.getDescriptionOfPerson(),
//                person.getPersonId());
//        //Get PersonSuperpowers to delete person old info from bridge table relationship
//        jdbcTemplate.update(SQL_DELETE_PERSON_SUPERPOWERS,
//                person.getPersonId());
//        //Reset bridge table info by putting the person w/ new info back in the PersonSuperpowers bridge table
//        insertPersonSuperpowers(person);
//
//        //Get OrganizationMembers to delete person old info from bridge table 
//        jdbcTemplate.update(SQL_DELETE_ORGANIZATION_MEMBERS,
//                person.getPersonId());
//        //handle person with no orgx
//        if (person.getListOfOrganizations() != null) {
//            insertOrganizationMembers(person);
//
//        }
//    }
    
    @Override
   public void updatePerson(Person person) throws SuperheroSightingsPersistenceException {
       jdbcTemplate.update(SQL_UPDATE_PERSON,

               person.getFirstName(),
               person.getLastName(),
               person.getIsHero(),
               person.getDescriptionOfPerson(),
               person.getPersonId());
       //Get PersonSuperpowers to delete person from bridge table relationship
       jdbcTemplate.update(SQL_DELETE_PERSON_SUPERPOWERS,
               person.getPersonId());
       //Get OrganizationMembers to delete person from bridge table
       jdbcTemplate.update(SQL_DELETE_ORGANIZATION_MEMBERS,
               person.getPersonId());

   }

    @Override
    public void deletePerson(int personId) throws SuperheroSightingsPersistenceException {

        //Delete PersonSuperpowers realtionship using personId by removing person from PersonSuperpowers bridge table
        jdbcTemplate.update(SQL_DELETE_PERSON_SUPERPOWERS, personId);
        
        //Delete person from person table in database 
        jdbcTemplate.update(SQL_DELETE_PERSON, personId);
    }

//    private List<Person> associateSuperpowersWithPersons(List<Person> personList) {
//      //list of superpower ids associated with each person
//      for (Person currentPerson : personList) {
//          
//          currentPerson.setListOfSuperpowers(findSuperpowersForPerson(currentPerson));
//      }
//        return personList;
//    }
    @Override
    public List<Person> getAllPersonsSightedAtLocation(List<Sighting> sightingList) throws SuperheroSightingsPersistenceException {

        List<Person> personsFromSightings = new ArrayList<>();

        for (Sighting currentSighting : sightingList) {
            personsFromSightings.add(currentSighting.getPerson());
        }
        return personsFromSightings;

    }


    private void insertPersonSuperpowers(Person person) {
        final int personIdInt = person.getPersonId();
        final List<Superpower> sp = person.getListOfSuperpowers(); // the superpowers assigned to person DTO

        // Update the PersonSuperpowers bridge table with an entry for 
        // each superpower for this person, current superpower is updated with each iteration
        for (Superpower currentSuperpower : sp) {
            jdbcTemplate.update(SQL_INSERT_PERSON_SUPERPOWERS,
                    personIdInt,
                    currentSuperpower.getSuperpowerId());
        }
    }

    private void insertOrganizationMembers(Person person) {
        // person ID of the person passed in, to be able to re-use it, assign it to variable
        final int personIdInt = person.getPersonId();
        final List<Organization> orgOfPerson = person.getListOfOrganizations();

        for (Organization currentOrganization : orgOfPerson) {
            jdbcTemplate.update(SQL_INSERT_ORGANIZATION_MEMBERS,
                    currentOrganization.getOrganizationId(), personIdInt);
        }

    }
    
       @Override
    public List<Person> findPersonsForOrganization(Organization organization) throws SuperheroSightingsPersistenceException {
        return jdbcTemplate.query(SQL_SELECT_PERSONS_BY_ORGANIZATION_ID,
                new PersonMapper(),
                organization.getOrganizationId());
    }


    @Override
    public List<Person> findPersonsForSuperpower(Superpower superpower) throws SuperheroSightingsPersistenceException {
        List<Person> p = jdbcTemplate.query(SQL_SELECT_PERSONS_BY_SUPERPOWER, 
                new PersonMapper(), superpower.getSuperpowerId());

        return p;

    }

    @Override
    public Person findPersonForSighting(Sighting sighting) throws SuperheroSightingsPersistenceException {
        return jdbcTemplate.queryForObject(SELECT_PERSON_BY_SIGHTING_ID,
                new PersonMapper(), sighting.getSightingId());
    }

    @Override
    public List<Person> getAllPersonsSightedAtLocation(int locationID) throws SuperheroSightingsPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // MAPPERS
    //Access Person table in database (needs to appear the same as the database)
    private static final class PersonMapper implements RowMapper<Person> {

        @Override
        public Person mapRow(ResultSet rs, int i) throws SQLException {

            Person per = new Person();

            per.setPersonId(rs.getInt("PersonID"));
            per.setFirstName(rs.getString("FirstName"));
            per.setLastName(rs.getString("LastName"));
            per.setIsHero(rs.getBoolean("isHero"));
            per.setDescriptionOfPerson(rs.getString("PersonDescription"));

            return per;
        }
    }

    //Person needs to access the Superpower mapper in order to create JOIN
//    private static final class SuperpowerMapper implements RowMapper<Superpower> {
//
//        @Override
//        public Superpower mapRow(ResultSet rs, int i) throws SQLException {
//            //Name in quotes should appear extact to sql database    
//            Superpower suppow = new Superpower();
//            suppow.setSuperpowerId(rs.getInt("SuperpowerID"));
//            suppow.setSuperpowerName(rs.getString("SuperpowerName"));
//            suppow.setSuperpowerDescription(rs.getString("SuperpowerDescription"));
//
//            return suppow;
//        }
//
//    }
}
