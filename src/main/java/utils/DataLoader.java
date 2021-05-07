package main.java.utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import main.java.entity.Facility;
import main.java.entity.Loan;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * This class reads data from csv files and has methods to load data into memory
 */
public class DataLoader {

    private static final String FACILITY_CSV_PATH = "src\\main\\resources\\facilities.csv";
    private static final String COVENANT_CSV_PATH = "src\\main\\resources\\covenants.csv";
    private static final String LOAN_CSV_PATH = "src\\main\\resources\\loans.csv";

    public void loadFacilityData(LinkedHashMap<Integer, Facility> facilityTreeMap) throws IOException, CsvValidationException {
        // loading facilities into memory
        CSVReader facilityReader = new CSVReaderBuilder(new FileReader(FACILITY_CSV_PATH)).withSkipLines(1).build();
        String[] facilityNextLine;
        while ((facilityNextLine = facilityReader.readNext()) != null) {
            Facility facility = new Facility();
            facility.setAmount(new BigDecimal(facilityNextLine[0]));
            facility.setInterestRate(new BigDecimal(facilityNextLine[1]));
            facility.setFacilityId(Integer.parseInt(facilityNextLine[2]));
            facility.setBankId(Integer.parseInt(facilityNextLine[3]));
            facilityTreeMap.put(facility.getFacilityId(), facility);
        }
        facilityReader.close();
    }

    public void loadCovenantData(LinkedHashMap<Integer, Facility> facilityMap) throws CsvValidationException, IOException {
        // loading covenants into memory by attaching them to a facility
        CSVReader covenantReader = new CSVReaderBuilder(new FileReader(COVENANT_CSV_PATH)).withSkipLines(1).build();
        String[] covenantNextLine;
        while ((covenantNextLine = covenantReader.readNext()) != null) {
            int facilityId = Integer.parseInt(covenantNextLine[0]);
            Facility facility = facilityMap.get(facilityId);
            if (!covenantNextLine[1].isEmpty()) {
                facility.setMaxDefaultLikelihood(new BigDecimal(covenantNextLine[1]));
            }
            if (!covenantNextLine[3].isEmpty()) {
                List<String> bannedState = new ArrayList<>();
                bannedState.add(covenantNextLine[3]);
                facility.setBannedState(bannedState);
            }
        }
        covenantReader.close();
    }

    public void loadLoanData(List<Loan> loans) throws IOException, CsvValidationException {
        // loading loan information into memory
        CSVReader loanReader = new CSVReaderBuilder(new FileReader(LOAN_CSV_PATH)).withSkipLines(1).build();
        String[] loanNextLine;
        while ((loanNextLine = loanReader.readNext()) != null) {
            Loan loan = new Loan();
            loan.setInterestRate(new BigDecimal(loanNextLine[0]));
            loan.setAmount(new BigDecimal(loanNextLine[1]));
            loan.setLoanId(Integer.parseInt(loanNextLine[2]));
            loan.setDefaultLikelihood(new BigDecimal(loanNextLine[3]));
            loan.setState(loanNextLine[4]);
            loans.add(loan);
        }
        loanReader.close();
    }
}