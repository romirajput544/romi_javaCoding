package com.hexaware.lms.service;

import java.sql.SQLException;

import com.hexaware.lms.entity.Loan;
import com.hexaware.lms.exception.InvalidLoanException;

public interface ILoanRepositoryService {
	 void applyLoan(Loan loan);

	    
	    double calculateInterest(String loanId) throws InvalidLoanException;

	    
	    void loanStatus(String loanId) ;

	    
	    double calculateEMI(String loanId) throws InvalidLoanException;
	   
	    void loanRepayment(String loanId, double amount) ;

	    void getAllLoan();

	    void getLoanById(String loanId) ;
}
