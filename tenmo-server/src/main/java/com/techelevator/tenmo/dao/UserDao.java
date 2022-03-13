package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

public interface UserDao {

    List<User> findAll();

    Double getCurrentBalance(String username);

    User findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password);

    List<User> listOfUsersExcludingUserId(int idToFilter);

    //************  NEW METHOD ************\\
    Transfer sendBucks(Long id);

    //************  NEW METHOD ************\\
    Transfer viewTransferHistory(Long transferId);

    //************  NEW METHOD ************\\
    public Transfer mapRowToTransfer(SqlRowSet rs);

}
