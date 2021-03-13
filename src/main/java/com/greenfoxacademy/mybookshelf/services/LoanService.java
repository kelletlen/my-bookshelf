package com.greenfoxacademy.mybookshelf.services;

import com.greenfoxacademy.mybookshelf.models.Loan;

import java.util.List;

public interface LoanService {
  Loan saveLoan (Loan loan);
  Loan findByCopyId (long copyId);
  Loan findById (long id);
  Loan findByLoanerId (long loanerId);
  void deleteById (long id);
  List<Loan> findAllByLoanerId (long id);
  List<Loan> findAllByBorrowerId (long id);
}
