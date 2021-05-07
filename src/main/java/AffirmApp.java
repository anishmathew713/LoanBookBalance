package main.java;

import com.opencsv.exceptions.CsvException;
import main.java.entity.*;
import main.java.service.LoanService;
import main.java.utils.DataLoader;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;


/**
 * This application will process a stream of loans and assign each loan to a facility.
 */
public class AffirmApp {

    static DataLoader dataLoader = new DataLoader();
    static LoanService loanService = new LoanService();
    private final static Logger LOGGER = Logger.getLogger(AffirmApp.class.getName());

    public static void main(String[] args) throws IOException, CsvException {

        LOGGER.info("Loan Processing Started");
        Long startTime = System.currentTimeMillis();
        LinkedHashMap<Integer, Facility> facilityMap = new LinkedHashMap<>();
        //loading data from file
        dataLoader.loadFacilityData(facilityMap);
        // reading covenants
        dataLoader.loadCovenantData(facilityMap);
        List<Loan> loans = new ArrayList<>();
        //reading loans
        dataLoader.loadLoanData(loans);

        // process each loan
        loanService.fundLoan(loans, facilityMap);

        //write to yield file
        loanService.writeYieldFile();
        Long endTime = System.currentTimeMillis();

        LOGGER.info("Loan Processing Completed in " + ((endTime - startTime)) + " milliseconds");
    }




}