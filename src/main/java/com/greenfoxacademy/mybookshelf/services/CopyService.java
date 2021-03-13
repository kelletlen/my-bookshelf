package com.greenfoxacademy.mybookshelf.services;

import com.greenfoxacademy.mybookshelf.models.Copy;

public interface CopyService {
  Copy saveCopy(Copy copy);
  Copy findById (long id);
}
