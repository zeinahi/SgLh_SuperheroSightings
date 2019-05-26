/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controller;

import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sg.thecodetasticfour.superherosightingsgroup.dao.SuperheroSightingsPersistenceException;
import sg.thecodetasticfour.superherosightingsgroup.dao.SuperheroSightingsUserDao;
import sg.thecodetasticfour.superherosightingsgroup.dto.User;
import sg.thecodetasticfour.superherosightingsgroup.service.EntityNotFoundException;
import sg.thecodetasticfour.superherosightingsgroup.service.SuperheroSightingsUserServiceLayer;

/**
 *
 * @author zissah
 */
public class UserController {

   private SuperheroSightingsUserDao dao;
   private PasswordEncoder encoder;

      @Inject
   public UserController(SuperheroSightingsUserDao dao, PasswordEncoder encoder) {
       this.dao = dao;
       this.encoder = encoder;
   }

    // This endpoint retrieves all users from the database and puts the
   // List of users on the model
   @RequestMapping(value = "/displayUserList", method = RequestMethod.GET)
   public String displayUserList(Map<String, Object> model) throws SuperheroSightingsPersistenceException {
       List users = dao.getAllUsers();
       model.put("users", users);
       return "displayUserList";
   }
   // This endpoint just displays the Add User form

   @RequestMapping(value = "/displayUserForm", method = RequestMethod.GET)
   public String displayUserForm(Map<String, Object> model) {
       return "addUserForm";
   }
   // This endpoint processes the form data and creates a new User

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(HttpServletRequest req) throws SuperheroSightingsPersistenceException {
        User newUser = new User();
        // This example uses a plain HTML form so we must get values 
        // from the request
        newUser.setUserName(req.getParameter("username"));
        String pwd = req.getParameter("password");
        String encPwd = this.encoder.encode(pwd);
        newUser.setUserPassword(encPwd);
        // All users have ROLE_USER, only add ROLE_ADMIN if the isAdmin 
        // box is checked
        newUser.addAuthority("ROLE_USER");
        if (null != req.getParameter("isAdmin")) {
            newUser.addAuthority("ROLE_ADMIN");
        }

        dao.createUser(newUser);
        return "redirect:displayUserList";
    }
    
   // This endpoint deletes the specified User
   @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
   public String deletUser(@RequestParam("username") String username,
           Map<String, Object> model) throws SuperheroSightingsPersistenceException {
       dao.deleteUser(username);
       return "redirect:displayUserList";
   }
}
    
//    
//     @RequestMapping(value = "/userExceptions", method = RequestMethod.GET)
//   public String handleUserExceptions(HttpServletRequest request, Model model) throws EntityNotFoundException {
// 
//       String errorMsg = request.getParameter("userNameNotAvailable");
//       
//       model.addAttribute("errorMsg", errorMsg);
// 
//       return "redirect:userHome";
//   }
