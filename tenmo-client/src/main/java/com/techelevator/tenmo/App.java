package com.techelevator.tenmo;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import io.cucumber.java.eo.Do;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final RestTemplate restTemplate = new RestTemplate();

    private AuthenticatedUser currentUser;

    Double currentUserBalance = 0.00;
    Double transferAmount = 0.00;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(currentUser.getToken());

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Double> response = restTemplate.exchange(API_BASE_URL + "currentbalance", HttpMethod.GET,entity,Double.class);
        Double balance = response.getBody();
        System.out.println("Your current balance is = " + balance);
		// TODO Auto-generated method stub
        currentUserBalance = balance;
       	}

    private void printListOfUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(currentUser.getToken());

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<User[]> response = restTemplate.exchange(API_BASE_URL + "/listofusers", HttpMethod.GET, entity, User[].class);
        User[] userList = response.getBody();

        for (User users : userList) {
            System.out.println(users.getId() + " " + users.getUsername());
        }
    }

    //************  NEW METHOD ************\\
    private void viewTransferHistory() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(currentUser.getToken());
        Transfer transfer = new Transfer();

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Transfer> response = restTemplate.exchange(API_BASE_URL + "transferhistory", HttpMethod.GET,entity,Transfer.class);
        Transfer view = response.getBody();
        System.out.println("Please enter transfer ID to view details (0 to cancel): \"\n " + view);
        // TODO Auto-generated method stub
    }
		// TODO Auto-generated method stub


	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
        viewCurrentBalance();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(currentUser.getToken());

        if (currentUserBalance <= 0.00) {
            System.out.println("Insufficient Funds for Transfer");
        } else if (currentUserBalance > 0.00) {
            System.out.println("");
            System.out.println("You can transfer to the following users: ");
            System.out.println("");
            printListOfUsers();
            System.out.println("");
            Transfer transfer = consoleService.promptForTransfers();

            if (transferAmount < 0 || transferAmount > currentUserBalance) {
                System.out.println("Please enter a correct amount to transfer.");
            } else {

                HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
                ResponseEntity<Transfer> response = restTemplate.exchange(API_BASE_URL + "transfers", HttpMethod.POST, entity, Transfer.class);
                transfer = response.getBody();

                System.out.println("");
                System.out.println("*** Transfer complete, your new balance is: " + currentUserBalance + " ***");
                System.out.println("Transfer = " + transfer);

                // TODO Auto-generated method stub
            }
        }
    }

	private void requestBucks() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBearerAuth(currentUser.getToken());
//        Transfer transfer = new Transfer();
//        System.out.println("");
//        System.out.println("You can request a transfer from the following users: ");
//        System.out.println("");
//        printListOfUsers();
//        System.out.println("");
//        consoleService.promptForTransfers();
//        //TODO set from accountId, to accountId, amount on transfer
//
//        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
//
//        ResponseEntity<Transfer> response = restTemplate.exchange(API_BASE_URL + "transfers", HttpMethod.POST,entity, Transfer.class);
//        transfer = response.getBody();
//        System.out.println("Transfer = " + transfer);
//        // TODO Auto-generated method stub
    }

}
