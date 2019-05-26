///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.sg.superherosightings.service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import sg.thecodetasticfour.superherosightingsgroup.dao.SuperheroSightingsOrganizationDao;
//import sg.thecodetasticfour.superherosightingsgroup.dao.SuperheroSightingsPersistenceException;
//import sg.thecodetasticfour.superherosightingsgroup.dao.SuperheroSightingsUserDao;
//import sg.thecodetasticfour.superherosightingsgroup.dto.Organization;
//import sg.thecodetasticfour.superherosightingsgroup.dto.User;
//import sg.thecodetasticfour.superherosightingsgroup.service.EntityNotFoundException;
//import sg.thecodetasticfour.superherosightingsgroup.service.SuperheroSightingsUserServiceLayer;
//
///**
// *
// * @author zissah
// */
//public class UserServiceLayerImpl implements SuperheroSightingsUserServiceLayer{
//    
//  SuperheroSightingsUserDao userDao;
// SuperheroSightingsOrganizationDao  orgDao;
//
//    public UserServiceLayerImpl(SuperheroSightingsUserDao userDao, SuperheroSightingsOrganizationDao orgDao) {
//        this.userDao = userDao;
//        this.orgDao = orgDao;
//    }
// 
// 
// 
//
// 
//    @Override
//    public User createUser(User user)  {
//    //Want to try to create user based on username, if already exist throw exception
//    //if usrname provided is null create the user
//      try {
////       if (userDao.getUserByUsername(user.getUserName()) ! = null)
////       List<Organization> organizationsForUser =  orgDao.findOrganizationsForUser(user)
//          return userDao.createUser(user);
//      } catch (SuperheroSightingsPersistenceException ex) {
//          Logger.getLogger(UserServiceLayerImpl.class.getName()).log(Level.SEVERE, null, ex);
//      }
//      return null;
// 
//    }
//
//
//    @Override
//    public List<User> getAllUsers() {
//
//       List<User> usersList = new ArrayList<>();
//       try{
//           usersList  = userDao.getAllUsers();
//       } catch (SuperheroSightingsPersistenceException ex) {
//          Logger.getLogger(UserServiceLayerImpl.class.getName()).log(Level.SEVERE, null, ex);
//      }
//      return usersList;
//    }
//
//    @Override
//    public void updateUser(User user) {
//        try {
//           userDao.updateUser(user);
//    } catch (SuperheroSightingsPersistenceException ex) {
//          Logger.getLogger(UserServiceLayerImpl.class.getName()).log(Level.SEVERE, null, ex);
//      }
//    
//    }
//
//    @Override
//    public User getUserByUsername(String username) throws EntityNotFoundException {
//        User u = new User();
//      try {
//          u = userDao.getUserByUsername(username);
//         List<Organization> organizationsForUser = orgDao.findOrganizationsForUser(u);
//         u.setUserOrganizations(organizationsForUser);
//      } catch (SuperheroSightingsPersistenceException ex) {
//          Logger.getLogger(UserServiceLayerImpl.class.getName()).log(Level.SEVERE, null, ex);
//          //if username is null throw exception
//      }if (u == null){
//          throw new EntityNotFoundException("This user could not be found");
//      }
//      return null;
//    }
//
//    @Override
//    public void deleteUser(String username) {
//      try {
//          userDao.deleteUser(username);
//      } catch (SuperheroSightingsPersistenceException ex) {
//          Logger.getLogger(UserServiceLayerImpl.class.getName()).log(Level.SEVERE, null, ex);
//      }
//    }
//
//    @Override
//    public List<User> findUsersForOrganization(Organization organization) {
//      try {
//          return userDao.findUsersForOrganization(organization);
//      } catch (SuperheroSightingsPersistenceException ex) {
//          Logger.getLogger(UserServiceLayerImpl.class.getName()).log(Level.SEVERE, null, ex);
//      }
//      return null;
//    }
//
//    @Override
//    public void setGlobalUserList(List<User> userList) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public List<User> getGlobalUserList() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//}
