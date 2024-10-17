package com.hexaware.lms.dao;

import com.hexaware.lms.DbUtil.DbUtil;
import com.hexaware.lms.dao.ILoanRepository;
import com.hexaware.lms.entity.Loan;
import com.hexaware.lms.exception.InvalidLoanException;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ILoanRepositoryImpl implements ILoanRepository {

    private final Connection con;

    public ILoanRepositoryImpl() {
        this.con = DbUtil.getDBConnection(); // Assuming DBUtil provides the connection
    }
    
    
    
    
    
    
    private void updateLoanStatus(String loanId, String status) throws SQLException {
        String query = "UPDATE loan SET LoanStatus = ? WHERE LoanID = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, status);
            ps.setString(2, loanId);
            ps.executeUpdate();
        }
    }

    
    
    
    

    // Method to apply for a loan
    
    public void applyLoan(Loan loan){
        String query = "INSERT INTO loan (LoanID, CustomerID, PrincipalAmount, InterestRate, LoanTerm, LoanType, LoanStatus) VALUES (?, ?, ?, ?, ?, ?, ?)";
      
		try {
			 PreparedStatement pstmt  = con.prepareStatement(query);
			 pstmt.setInt(1, loan.getLoanId());
		        pstmt.setInt(2, loan.getCustomer().getCustomerId());
		        pstmt.setDouble(3, loan.getPrincipalAmount());
		        pstmt.setDouble(4, loan.getInterestRate());
		        pstmt.setInt(5, loan.getLoanTerm());
		        pstmt.setString(6, loan.getLoanType());
		        pstmt.setString(7, loan.getLoanStatus());

		        int rowsInserted = pstmt.executeUpdate();
		        
		        System.out.println(rowsInserted + " loan record inserted successfully.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

   
    public double calculateInterest(String loanId) throws InvalidLoanException {
        String query = "SELECT PrincipalAmount, InterestRate, LoanTerm FROM loan WHERE LoanId = ?";
        
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, loanId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                double principalAmount = rs.getDouble("PrincipalAmount");
                double interestRate = rs.getDouble("InterestRate");
                int loanTerm = rs.getInt("LoanTerm");

                
                double interest = (principalAmount * interestRate * loanTerm) / 12;
                return interest;
            } else {
                throw new InvalidLoanException();
            }
        } catch (SQLException e) {
            throw new InvalidLoanException();
        }
    }

 
   
    public void loanStatus(String loanId) {
        String query = "SELECT c.CreditScore FROM customer c INNER JOIN loan l ON c.CustomerID = l.CustomerID WHERE l.LoanID = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, loanId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int creditScore = rs.getInt("CreditScore");

                if (creditScore > 650) {
                    updateLoanStatus(loanId, "Approved");
                    System.out.println("Loan approved based on credit score.");
                } else {
                    updateLoanStatus(loanId, "Rejected");
                    System.out.println("Loan rejected due to low credit score.");
                }
            } 
        } catch (SQLException e) {
        }
    }


    
   
    public double calculateEMI(String loanId){
        String query = "SELECT  PrincipalAmount , InterestRate , LoanTerm FROM Loan WHERE LoanID = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, loanId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next())
            {
                double principalAmount = rs.getDouble("PrincipalAmount");
                double interestRate = rs.getDouble("InterestRate") / 12 / 100; 
                int loanTerm = rs.getInt("InterestRate");

                
                double emi = (principalAmount * interestRate * Math.pow(1 + interestRate, loanTerm))
                        / (Math.pow(1 + interestRate, loanTerm) - 1);
                return emi;
            } 
            
        } catch (SQLException e) {
        }
		return 0;
    }

  
   
    public void loanRepayment(String loanId, double amount) {
        double emi = calculateEMI(loanId);
        if (amount < emi) 
        {
            System.out.println("Amount is too low to pay even one EMI.");
        } else 
        {
            int emiCount = (int) (amount / emi);
            System.out.println("You can pay " + emiCount + " months of EMI with the provided amount.");
        }
    }

   
    public void getLoanById(String loanId){
        String query = "SELECT * FROM Loan WHERE LoanID = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, loanId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
            	System.out.println("Loan ID: " + rs.getInt("LoanID") +
                        ", Customer ID: " + rs.getInt(" CustomerID") +
                        ", Principal Amount: " + rs.getDouble("PrincipalAmount") +
                        ", Interest Rate: " + rs.getDouble(" InterestRate") +
                        ", Loan Term: " + rs.getInt(" LoanTerm ") +
                        ", Loan Type: " + rs.getString("LoanType") +
                        ", Loan Status: " + rs.getString(" LoanStatus"));
            } 
            
        } 
        catch (SQLException e) {
         
        }
    }


	public void getAllLoan() {
		String query = "SELECT * FROM Loan";
        
        try (PreparedStatement pstmt = con.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
            	System.out.println("Loan ID: " + rs.getInt("LoanID") +
                        ", Customer ID: " + rs.getInt(" CustomerID") +
                        ", Principal Amount: " + rs.getDouble("PrincipalAmount") +
                        ", Interest Rate: " + rs.getDouble(" InterestRate") +
                        ", Loan Term: " + rs.getInt(" LoanTerm ") +
                        ", Loan Type: " + rs.getString("LoanType") +
                        ", Loan Status: " + rs.getString(" LoanStatus"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }   
		
	}

}
