package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcUserDao implements UserDao {

    private static final BigDecimal STARTING_BALANCE = new BigDecimal("1000.00");

    private JdbcTemplate jdbcTemplate;
    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Double getCurrentBalance(String username) {

        String sql = "SELECT balance FROM account WHERE user_id = ?";
        Double currentBalance = jdbcTemplate.queryForObject(sql, Double.class, findIdByUsername(username));
        System.out.println("Your current balance is: " + currentBalance);

        return currentBalance;
    }

    //************  NEW METHOD ************\\
    public Transfer viewTransferHistory(Long transferId) {
//        Transfer transfers = new Transfer();
        String sql = "SELECT * FROM transfer WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);

        if (results.next()) {
            return mapRowToTransfer(results);
        } else {
            return null;
        }
    }




    //************  NEW METHOD ************\\
    public Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getLong("transfer_id"));
        transfer.setTransferTypeId(rs.getLong("transfer_type_id"));
        transfer.setTransferStatusId(rs.getLong("transfer_status_id"));
        transfer.setAccountFrom(rs.getLong("account_from"));
        transfer.setAccountTo(rs.getLong("account_to"));
        transfer.setTransferAmount(rs.getDouble("transfer_amount"));
        return transfer;
    }

    //************  NEW METHOD ************\\
    public Transfer sendBucks(Long transferId) {
        Transfer transfer = new Transfer();
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (2, 2, ?, ?, ?)"
        + "RETURNING transfer_id";

        jdbcTemplate.update(sql, transfer.accountFrom*2, transfer.accountTo*2, transfer.transferAmount);

        return transfer;
    }


    @Override
    public int findIdByUsername(String username) {
        String sql = "SELECT user_id FROM tenmo_user WHERE username ILIKE ?;";
        Integer id = jdbcTemplate.queryForObject(sql, Integer.class, username);
        if (id != null) {
            return id;
        } else {
            return -1;
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT user_id, username, password_hash FROM tenmo_user;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            User user = mapRowToUser(results);
            users.add(user);
        }
        return users;
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        String sql = "SELECT user_id, username, password_hash FROM tenmo_user WHERE username ILIKE ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
        if (rowSet.next()){
            return mapRowToUser(rowSet);
        }
        throw new UsernameNotFoundException("User " + username + " was not found.");
    }

    @Override
    public boolean create(String username, String password) {

        // create user
        String sql = "INSERT INTO tenmo_user (username, password_hash) VALUES (?, ?) RETURNING user_id";
        String password_hash = new BCryptPasswordEncoder().encode(password);
        Integer newUserId;
        try {
            newUserId = jdbcTemplate.queryForObject(sql, Integer.class, username, password_hash);
        } catch (DataAccessException e) {
            return false;
        }

        // create account
        sql = "INSERT INTO account (user_id, balance) values(?, ?)";
        try {
            jdbcTemplate.update(sql, newUserId, STARTING_BALANCE);
        } catch (DataAccessException e) {
            return false;
        }

        return true;
    }

    @Override
    public List<User> listOfUsersExcludingUserId(int id) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM tenmo_user WHERE user_id != ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        while(results.next()) {
            User user = mapRowToUser(results);
            users.add(user);
        }
        return users;
    }

    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getLong("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));
        user.setActivated(true);
        user.setAuthorities("USER");
        return user;
    }
}
