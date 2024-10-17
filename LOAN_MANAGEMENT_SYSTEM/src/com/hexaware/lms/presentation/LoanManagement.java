package com.hexaware.lms.presentation;

import com.hexaware.lms.entity.Loan;
import com.hexaware.lms.exception.InvalidLoanException;
import com.hexaware.lms.service.ILoanRepositoryService;
import com.hexaware.lms.service.LoanRepositoryServiceImpl;

import java.sql.SQLException;
import java.util.Scanner;

public class LoanManagement {
    
    private static  ILoanRepositoryService loanService = new LoanRepositoryServiceImpl();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        
        while (!exit) {
            System.out.println("\n=== Loan Management System ===");
            System.out.println("1. Apply Loan");
            System.out.println("2. Get All Loans");
            System.out.println("3. Get Loan by ID");
            System.out.println("4. Loan Repayment");
            System.out.println("5. Calculate Interest");
            System.out.println("6. Calculate EMI");
            System.out.println("7. Check Loan Status");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  

           
            switch (choice) {
			    case 1:
			        applyLoan(scanner);
			        break;
			    case 2:
			    	 loanService.getAllLoan();
			        break;
			    case 3:
			        getLoanById(scanner);
			        break;
			    case 4:
			        loanRepayment(scanner);
			        break;
			    case 5:
			        calculateInterest(scanner);
			        break;
			    case 6:
			        calculateEMI(scanner);
			        break;
			    case 7:
			        checkLoanStatus(scanner);
			        break;
			    case 8:
			        exit = true;
			        System.out.println("Exiting Loan Management System...");
			        break;
			    default:
			        System.out.println("Invalid choice, please try again.");
			}
        }
        scanner.close(); 
    }

    
    private static void applyLoan(Scanner scanner) {
        Loan loan = new Loan();

        System.out.print("Enter Loan ID: ");
        loan.setLoanId(scanner.nextInt());

        System.out.print("Enter Customer ID: ");
        int customerId = scanner.nextInt();
        loan.setCustomerId(customerId);  

        System.out.print("Enter Principal Amount: ");
        loan.setPrincipalAmount(scanner.nextInt());

        System.out.print("Enter Interest Rate: ");
        loan.setInterestRate(scanner.nextInt());

        System.out.print("Enter Loan Term (months): ");
        loan.setLoanTerm(scanner.nextInt());

        scanner.nextLine();  

        System.out.print("Enter Loan Type: ");
        loan.setLoanType(scanner.nextLine());

        loan.setLoanStatus("Pending");

        try {
			loanService.applyLoan(loan);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Loan application submitted successfully.");
    }

    
    
    
    
    private static void getLoanById(Scanner scanner)  {
        System.out.print("Enter Loan ID: ");
        String loanId = scanner.nextLine();
        try {
			loanService.getLoanById(loanId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    
    
   
    private static void loanRepayment(Scanner scanner)  {
        System.out.print("Enter Loan ID: ");
        String loanId = scanner.nextLine();

        System.out.print("Enter repayment amount: ");
        double amount = scanner.nextDouble();

        try {
			loanService.loanRepayment(loanId, amount);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    
    private static void calculateInterest(Scanner scanner)  {
        System.out.print("Enter Loan ID: ");
        String loanId = scanner.nextLine();

        double interest;
		
        try {
			interest = loanService.calculateInterest(loanId);
			System.out.println("Interest for loan " + loanId + ": " + interest);
		} catch (InvalidLoanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    
    private static void calculateEMI(Scanner scanner)  {
        System.out.print("Enter Loan ID: ");
        String loanId = scanner.nextLine();

        double emi;
		try {
			emi = loanService.calculateEMI(loanId);
			System.out.println("EMI for loan " + loanId + ": " + emi);
		} catch (InvalidLoanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    
    private static void checkLoanStatus(Scanner scanner) {
        System.out.print("Enter Loan ID: ");
        String loanId = scanner.nextLine();

        try {
			loanService.loanStatus(loanId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
