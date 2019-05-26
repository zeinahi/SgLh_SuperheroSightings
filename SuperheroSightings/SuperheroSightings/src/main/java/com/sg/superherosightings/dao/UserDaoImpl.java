/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import sg.thecodetasticfour.superherosightingsgroup.dao.SuperheroSightingsPersistenceException;
import sg.thecodetasticfour.superherosightingsgroup.dao.SuperheroSightingsUserDao;
import sg.thecodetasticfour.superherosightingsgroup.dto.Organization;
import sg.thecodetasticfour.superherosightingsgroup.dto.User;

/**
 *
 * @author zissah
 */
public class UserDaoImpl implements SuperheroSightingsUserDao {

    //Prepared Statements for CRUD User database table
    private static final String SQL_INSERT_USER
            = "INSERT INTO Users "
            + "(Username, UserPassword, FirstName, LastName, Email, isEnabled) "
            + "values (?, ?,?,?,?,?);";

    private static final String SQL_SELECT_USER
            = "SELECT * FROM Users where Username = ?;";

    private static final String SQL_SELECT_ALL_USERS
            = "SELECT * FROM Users;";

    private static final String SQL_UPDATE_USER
            = "UPDATE Users set "
            + "Username = ?,UserPassword = ?,FirstName = ?,LastName = ?  "
            + "Email = ?, isEnabled = ? "
            + "where UserID = ?;" ;

    private static final String SQL_DELETE_USER
            = "DELETE FROM Users "
            + "where Username = ?;";

    private static final String SQL_INSERT_AUTHORITY
            = "INSERT INTO Authorities "
            + "(Username, Authority) "
            + "values (?, ?)";

    private static final String SQL_SELECT_AUTHORITIES_FOR_USER
            = "SELECT a.* "
            + "FROM Authorities a "
            + "JOIN Users u on u.Username = a.Username "
            + "where u.Username = ?";

    private static final String SQL_SELECT_USERS_BY_ORGANIZATION_ID
            = "SELECT u.* "
            + "FROM Users u "
            + "JOIN OrganizationAdmins oa on u.UserID = oa.UserID "
            + "where oa.OrganizationID = ?";

    private static final String SQL_INSERT_ORGANIZATION_ADMINS_FOR_USER
            = "INSERT INTO OrganizationAdmins "
            + "OrganizationID, UserID) "
            + "values (?, ? )";

    private static final String SQL_DELETE_AUTHORITIES
            = "DELETE FROM Authorties where Username = ?";

    //Injecting Jdbdctemplate into code, to allow us to talk to sql database
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User createUser(User user) throws SuperheroSightingsPersistenceException {
        jdbcTemplate.update(SQL_INSERT_USER,
                user.getUserName(),
                user.getUserPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getIsEnabled());

        int userId = jdbcTemplate.queryForObject("select LAST_INSERT_ID()", Integer.class);
        user.setUserId(userId);

        //Get user organization bridge table for when an organization exist to this user
        List<Organization> theUserOrg = user.getUserOrganizations();
        for (Organization org : theUserOrg) {

            jdbcTemplate.update(SQL_INSERT_ORGANIZATION_ADMINS_FOR_USER, user.getUserId(), org.getOrganizationId());
        }

        return user;
    }

//    @Override
//    public User getUserById(int i) throws SuperheroSightingsPersistenceException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    @Override
    public List<User> getAllUsers() throws SuperheroSightingsPersistenceException {
        return jdbcTemplate.query(SQL_SELECT_ALL_USERS, new UserMapper());
    }

    @Override
    public void updateUser(User user) throws SuperheroSightingsPersistenceException {
        jdbcTemplate.update(SQL_UPDATE_USER,
                user.getUserName(),
                user.getUserPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getIsEnabled(),
                user.getUserId());
    }

    @Override
    public void deleteUser(String username) throws SuperheroSightingsPersistenceException {
        //Delete authority first, from this user
        jdbcTemplate.update(SQL_DELETE_AUTHORITIES, username);
        jdbcTemplate.update(SQL_DELETE_USER, username);

    }

    @Override
    public List<User> findUsersForOrganization(Organization organization) throws SuperheroSightingsPersistenceException {
        return jdbcTemplate.query(SQL_SELECT_USERS_BY_ORGANIZATION_ID,
                new UserMapper(),
                organization.getOrganizationId());
    }

    @Override
    public User getUserByUsername(String username) throws SuperheroSightingsPersistenceException {

        User userNameSelected = jdbcTemplate.queryForObject(SQL_SELECT_USER, new UserMapper(), username);

        List<String> userAuthorities = jdbcTemplate.query(SQL_SELECT_AUTHORITIES_FOR_USER, new AuthorityMapper(), username);
        //Go through list and set authories to user
        userNameSelected.setAuthorities(userAuthorities);
        return userNameSelected;

    }

    private static final class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {

            User u = new User();

            u.setUserId(rs.getInt("UserID"));
            u.setIsEnabled(rs.getBoolean("isEnabled"));
            u.setUserName(rs.getString("Username"));
            u.setUserPassword(rs.getString("UserPassword"));
            u.setFirstName(rs.getString("FirstName"));
            u.setLastName(rs.getString("LastName"));
            u.setEmail(rs.getString("Email"));

            return u;
        }

    }

    private static final class AuthorityMapper implements RowMapper<String> {

        @Override
        public String mapRow(ResultSet rs, int i) throws SQLException {

            String authority = (rs.getString("Authority"));
            return authority;
        }
    }

}
