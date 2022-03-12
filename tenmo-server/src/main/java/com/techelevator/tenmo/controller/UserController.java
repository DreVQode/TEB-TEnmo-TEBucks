package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class UserController {

    private UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping (path= "currentbalance" , method = RequestMethod.GET)
    public Double getBalance(Principal principal){
       return userDao.getCurrentBalance(principal.getName());
    }

    @RequestMapping (path= "/listofusers?idToFilter" , method = RequestMethod.GET)
    public List<User> getUserList(@RequestParam Long idToFilter){
    List<User> allUsers = userDao.listOfUsersExcludingUserId(idToFilter);
        return allUsers;
    }




}
