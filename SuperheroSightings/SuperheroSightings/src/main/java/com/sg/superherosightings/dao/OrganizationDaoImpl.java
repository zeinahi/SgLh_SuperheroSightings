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
import sg.thecodetasticfour.superherosightingsgroup.dao.SuperheroSightingsOrganizationDao;
import sg.thecodetasticfour.superherosightingsgroup.dao.SuperheroSightingsPersistenceException;
import sg.thecodetasticfour.superherosightingsgroup.dto.Organization;
import sg.thecodetasticfour.superherosightingsgroup.dto.Person;
import sg.thecodetasticfour.superherosightingsgroup.dto.User;

/**
 *
 * @author zissah
 */
public class OrganizationDaoImpl implements SuperheroSightingsOrganizationDao {

    //Prepared statements for CRUD Organization database
    private static final String SQL_INSERT_ORGANIZATION
            = "INSERT INTO Organizations "
            + "(OrganizationName, OrganizationDescription, OrganizationCountry, OrganizationState,  "
            + "OrganizationCity, OrganizationStreet, OrganizationZipcode, isHeroOrganization) "
            + "values (?,?,?,?,?,?,?,?);";

    private static final String SQL_SELECT_ORGANIZATION
            = "SELECT * FROM Organizations WHERE OrganizationID = ?;";

    private static final String SQL_SELECT_ALL_ORGANIZATIONS
            = "SELECT * FROM Organizations;";

    private static final String SQL_UPDATE_ORGANIZATION
            = "UPDATE Organizations set "
            + "OrganizationName = ?, OrganizationDescription = ?, OrganizationCountry = ?, OrganizationState = ?, "
            + "OrganizationCity = ?, OrganizationStreet = ?, OrganizationZipcode = ?, isHeroOrganization = ?  "
            + "where OrganizationID = ?;";

    private static final String SQL_DELETE_ORGANIZATION
            = "DELETE FROM Organizations "
            + " where OrganizationID = ?;";

    private static final String SQL_INSERT_ORGANIZATION_MEMBERS
            = "INSERT INTO OrganizationMembers "
            + "(OrganizationID, PersonID  "
            + "values (?,?);";

    private static final String SQL_DELETE_ORGANIZATION_MEMBERS
            = "DELETE FROM OrganizationMembers "
            + "where OrganizationID = ?;";

//    private static final String SQL_SELECT_PERSONS_BY_ORGANIZATION_ID
//            ="SELECT p.PersonID, p.isHero, p.FirstName, p.LastName, p.PersonDescription "
//            + "FROM Person p "
//            +"JOIN OrganizationMembers orgm on p.PersonID = orgm.PersonID "
//            + "where orgm.OrganizationID = ?;"; 
    private static final String SQL_GET_ORGANIZATIONS_BY_PERSON_ID
            = "SELECT org.* "
            + "FROM Organizations org "
            + "JOIN OrganizationMembers orgm on orgm.OrganizationID = org.OrganizationID "
            + "WHERE orgm.PersonID= ?;";
    private static final String SQL_INSERT_ORGANIZATION_ADMINS 
            = "INSERT INTO OrganizationAdmins "
            + "(OrganizationID, UserID) "
            + "values (?,?);";
    
    private static final String SQL_SELECT_ORGANIZATIONS_BY_USER_ID
            = "SELECT org.* "
            + "FROM Organizations org "
            + "JOIN OrganizationAdmins orga ON org.OrganizationID = orga.OrganizationID "
            + "WHERE orga.UserID = ?;";

    //Injecting Jdbdctemplate into code, to allow us to talk to sql database         
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Organization createOrganization(Organization organization) throws SuperheroSightingsPersistenceException {
        jdbcTemplate.update(SQL_INSERT_ORGANIZATION,
                organization.getOrganizationName(),
                organization.getOrganizationDescription(),
                organization.getOrganizationCountry(),
                organization.getOrganizationState(),
                organization.getOrganizationCity(),
                organization.getOrganizationStreet(),
                organization.getOrganizationZipcode(),
                organization.getIsItAHeroOrganization());

        int organizationId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
        organization.setOrganizationId(organizationId);

//       insertOrganizationMembers(organization);
//       insertOrganizationAdmins(organization);    
        return organization;
    }

    @Override
    public Organization getOrganizationById(int organizationId) throws SuperheroSightingsPersistenceException {
        try {

            Organization organization = jdbcTemplate.queryForObject(SQL_SELECT_ORGANIZATION,
                    new OrganizationMapper(), organizationId);

            return organization;
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrganizations() throws SuperheroSightingsPersistenceException {
        return jdbcTemplate.query(SQL_SELECT_ALL_ORGANIZATIONS, new OrganizationMapper());
  }

    @Override
    public void updateOrganization(Organization organization) throws SuperheroSightingsPersistenceException {
       jdbcTemplate.update(SQL_UPDATE_ORGANIZATION,
               organization.getOrganizationName(),
               organization.getOrganizationDescription(),
               organization.getOrganizationCountry(),
               organization.getOrganizationState(),
               organization.getOrganizationCity(),
               organization.getOrganizationStreet(),
               organization.getOrganizationZipcode(),
               organization.getIsItAHeroOrganization(),
               organization.getOrganizationId());
     
       //Delete old info from OrganizationMembers bridge
      // Then put new info in organization using OrganizationMembers bridge
       jdbcTemplate.update(SQL_DELETE_ORGANIZATION_MEMBERS, organization.getOrganizationId());
       //Pass back insert methods
//         insertOrganizationMembers(organization);
//         insertOrganizationAdmins(organization);
    }

    @Override
    public void deleteOrganization(int organizationId) throws SuperheroSightingsPersistenceException {
        //Deletes person from person database
        jdbcTemplate.update(SQL_DELETE_ORGANIZATION, organizationId);
        
        //Delete OrganizationMembers relationship from organization by removing org from bridge
        jdbcTemplate.update(SQL_DELETE_ORGANIZATION_MEMBERS, organizationId);
    }
    
   // Helper Methods 
    private void insertOrganizationMembers(Organization organization) {
        final int organizationId = organization.getOrganizationId();
        final List<Person> personList = organization.getListOfPersons();

    // Update the OrganizationMembers bridge add each person of this organization
    for (Person currentPerson : personList) {
        jdbcTemplate.update(SQL_INSERT_ORGANIZATION_MEMBERS, 
                            organizationId, 
                            currentPerson.getPersonId());
    }
}
    
   private void insertOrganizationAdmins(Organization organization){
       
       final int organizationId = organization.getOrganizationId();
       final List<User> userList = organization.getOrganizationAdmins();
       
       
       for (User currentUser : userList) {
           jdbcTemplate.update(SQL_INSERT_ORGANIZATION_ADMINS,
                           organizationId,
                           currentUser.getUserId());
       }
   }
    
////    //Gets back all the persons for a organization by joining Person and OrganizationMembers tables
////    private List<Person> findPersonsForOrganization(Organization organization) {
////            return jdbcTemplate.query(SQL_GET_ORGANIZATIONS_BY_PERSON_ID,
////                              new PersonMapper(), 
////                              organization.getOrganizationId());
////    }
//
//  
////    private List<Organization> associatePersonsWithOrganizations(List<Organization> orgList) {
////        // set the complete list of Person ids for each Organization
////    for (Organization currentOrganization : orgList) {
////        // add the Person to current Organization
////        currentOrganization.setListOfPersons(findPersonsForOrganization(currentOrganization));   
////    }
////    return orgList;
////    }

    @Override
    public List<Organization> findOrganizationsForPerson(Person person) throws SuperheroSightingsPersistenceException {
          
        List<Organization> org = jdbcTemplate.query(SQL_GET_ORGANIZATIONS_BY_PERSON_ID, 
                  new OrganizationMapper(), person.getPersonId());
        return org;
       
    }

   
    @Override
    public List<Organization> findOrganizationsForUser(User user) throws SuperheroSightingsPersistenceException {
            
        return jdbcTemplate.query(SQL_SELECT_ORGANIZATIONS_BY_USER_ID,
              new OrganizationMapper(),
              user.getUserId());
    }


    
      
     // MAPPERS
    //Access Organization table in database
    private static final class OrganizationMapper implements RowMapper<Organization>{

        @Override
        public Organization mapRow(ResultSet rs, int i) throws SQLException {
           
            Organization org = new Organization();
            
            org.setOrganizationId(rs.getInt("OrganizationID"));
            org.setOrganizationName(rs.getString("OrganizationName"));
            org.setOrganizationDescription(rs.getString("OrganizationDescription"));
            org.setOrganizationCountry(rs.getString("OrganizationCountry"));
            org.setOrganizationState(rs.getString("OrganizationState"));
            org.setOrganizationCity(rs.getString("OrganizationCity"));
            org.setOrganizationStreet(rs.getString("OrganizationStreet"));
            org.setOrganizationZipcode(rs.getString("OrganizationZipCode"));
            org.setIsItAHeroOrganization(rs.getBoolean("isHeroOrganization"));

            return org;
        }
        
    }
//    //Person needs to access the Person mapper in order to create JOIN OrganizationMembers
//        private static final class PersonMapper implements RowMapper<Person>{
//
//        @Override
//        public Person mapRow(ResultSet rs, int i) throws SQLException {
//            
//            Person  per = new Person();
//            
//            per.setPersonId(rs.getInt("PersonID"));
//            per.setIsHero(rs.getBoolean("isHero"));
//            per.setFirstName(rs.getString("FirstName"));
//            per.setLastName(rs.getString("LastName"));
//            per.setDescriptionOfPerson(rs.getString("PersonDescription"));
//            
//      
//            return per;
//        }
//    }
    
    
    
    
    
}
 


