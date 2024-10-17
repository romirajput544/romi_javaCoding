package com.hexaware.lms.service;


import com.hexaware.lms.dao.ILoanRepository;
import com.hexaware.lms.dao.ILoanRepositoryImpl;
import com.hexaware.lms.entity.Loan;
import com.hexaware.lms.exception.InvalidLoanException;


import java.sql.SQLException;

public class LoanRepositoryServiceImpl implements ILoanRepositoryService {

    private  ILoanRepository loanRepository = new ILoanRepositoryImpl();

    
    public void applyLoan(Loan loan) 
    {
        loan.setLoanStatus("Pending");
        loanRepository.applyLoan(loan);
        System.out.println("Loan application submitted with status: " + loan.getLoanStatus());
    }

    public double calculateInterest(String loanId) throws InvalidLoanException {
        return loanRepository.calculateInterest(loanId);
    }

    public void loanStatus(String loanId)  {
        loanRepository.loanStatus(loanId);
    }

    public double calculateEMI(String loanId) throws InvalidLoanException {
        return loanRepository.calculateEMI(loanId);
    }

    public void loanRepayment(String loanId, double amount)  {
        loanRepository.loanRepayment(loanId, amount);
    }

    public void getAllLoan() {
        loanRepository.getAllLoan();
    }

    public void getLoanById(String loanId) {
        loanRepository.getLoanById(loanId);
    }
}

