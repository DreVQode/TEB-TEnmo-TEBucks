package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping (path= "/listofusers" , method = RequestMethod.GET)
    public List<User> getUserList(Principal principal) {
        int idToFilter = userDao.findIdByUsername(principal.getName());
        List<User> allUsers = userDao.listOfUsersExcludingUserId(idToFilter);
        return allUsers;
    }

    @RequestMapping (path = "/transfers", method = RequestMethod.POST)
    public Transfer sendBucks(@RequestBody Transfer transfer) {
//        TODO use info in transfer to adjust from_account, to_account and create transfer record
        return transfer;
    }
// TODO: 1) get from_account (create dao method to get balance)
//  2) subtract transfer amount from balance (create dao method to update balance amount)
//  3) add transfer amount to to_account (create dao method to update balance)
//  4) create dao method to create a transfer record in the table



}
