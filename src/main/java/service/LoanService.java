package main.java.service;

import com.opencsv.CSVWriter;
import main.java.entity.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * This class has methods to fund loan, balance facility amount,
 */
public class LoanService {

    private static final String YIELD_CSV_PATH = "src\\main\\resources\\yields.csv";
    private static final String ASSIGNMENT_CSV_PATH = "src\\main\\resources\\assignments.csv";

    HashMap<Integer, BigDecimal> facilityYieldMap = new HashMap<>();
    List<Yield> yields = new ArrayList<>();

    public void fundLoan(List<Loan> loans, LinkedHashMap<Integer, Facility> facilityMap) throws IOException {
        List<Assignment> assignments = new ArrayList<>();

        for (Loan loan : loans) {
            for (Facility facility : facilityMap.values()) {
                if (facility.getAmount().compareTo(loan.getAmount()) < 0) {
                    continue;
                }
                if (loan.getDefaultLikelihood().compareTo(facility.getMaxDefaultLikelihood()) > 0) {
                    continue;
                }
                if (facility.getBannedState().contains(loan.getState())) {
                    continue;
                }

                facility.setAmount(facility.getAmount().subtract(loan.getAmount()));
                Assignment assignment = new Assignment();
                assignment.setLoanId(loan.getLoanId());
                assignment.setFacilityId(facility.getFacilityId());
                assignments.add(assignment);
                BigDecimal yieldValue = facilityYieldMap.getOrDefault(facility.getFacilityId(), BigDecimal.ZERO);

                if (facilityYieldMap.get(facility.getFacilityId()) != null && yieldValue != null){
                    yieldValue = yieldValue.add(calculateYield(loan, facility));
                } else {
                    yieldValue = calculateYield(loan, facility);
                }
                facilityYieldMap.put(facility.getFacilityId(), yieldValue);
                writeAssignmentFile(assignments);
                break;
            }
        }
    }

    public void writeYieldFile() throws IOException {
        Iterator iterator = facilityYieldMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Yield yield = new Yield();
            Map.Entry mapElement = (Map.Entry)iterator.next();
            yield.setFacilityId((Integer) mapElement.getKey());
            yield.setExpectedYield((BigDecimal) mapElement.getValue());
            yields.add(yield);
            System.out.println(mapElement.getValue() + " : ");
        }
        File file = new File(YIELD_CSV_PATH);
        FileWriter outputFile = new FileWriter(file);
        CSVWriter yieldWriter = new CSVWriter(outputFile, ',',
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);
        yieldWriter.writeNext(new String[]{"facility_id", "expected_yield"});

        List<String[]> data = new ArrayList<>();
        for (Yield yield : yields) {
            data.add(new String[]
                    {yield.getFacilityId().toString(),
                            String.valueOf(yield.getExpectedYield().setScale(2, RoundingMode.HALF_EVEN))});
        }
        yieldWriter.writeAll(data);
        yieldWriter.close();
    }

    private void writeAssignmentFile(List<Assignment> assignments) throws IOException {

        File file = new File(ASSIGNMENT_CSV_PATH);

        FileWriter outputfile = new FileWriter(file);
        CSVWriter writer = new CSVWriter(outputfile, ',',
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);
        writer.writeNext(new String[]{"loan_id", "facility_id"});

        List<String[]> data = new ArrayList<>();
        for (Assignment assignment : assignments) {
            data.add(new String[]
                    {assignment.getLoanId().toString(),
                            assignment.getFacilityId().toString()});
        }
        writer.writeAll(data);
        writer.close();
    }

    private BigDecimal calculateYield(Loan loan, Facility facility) {
        return (BigDecimal.ONE
                .subtract(loan.getDefaultLikelihood())
                .multiply(loan.getInterestRate())
                .multiply(loan.getAmount())
                .subtract(loan.getAmount().multiply(loan.getDefaultLikelihood()))
                .subtract(loan.getAmount().multiply(facility.getInterestRate())));
    }

}