package com.greenfoxacademy.mybookshelf.services;

import com.greenfoxacademy.mybookshelf.models.Loan;
import com.greenfoxacademy.mybookshelf.repositories.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

  private final LoanRepository loanRepository;

  public LoanServiceImpl(LoanRepository loanRepository) {
    this.loanRepository = loanRepository;
  }

  @Override
  public Loan saveLoan(Loan loan) {
    return loanRepository.save(loan);
  }

  @Override
  public Loan findByCopyId(long copyId) {
    return loanRepository.findByCopyId(copyId);
  }

  @Override
  public Loan findById(long id) {
    return loanRepository.findById(id);
  }

  @Override
  public Loan findByLoanerId(long loanerId) {
    return loanRepository.findByLoanerId(loanerId);
  }

  @Override
  public void deleteById(long id) {
    loanRepository.deleteById(id);
  }

  @Override
  public List<Loan> findAllByLoanerId(long id) {
    return loanRepository.findAllByLoanerId(id);
  }

  @Override
  public List<Loan> findAllByBorrowerId(long id) {
    return loanRepository.findAllByBorrowerId(id);
  }
}
