package com.example.ggsheet;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@Log4j2
@RequestMapping("v1")
public class Controller {
  @Autowired
  private GoogleSheetsLiveTest googleSheetsLiveTest;

  @GetMapping()
  public void ggsheet() throws GeneralSecurityException, IOException {
    googleSheetsLiveTest.setup();
  }

  @GetMapping("h")
  public void ggsheetfdasf() throws GeneralSecurityException, IOException {
    log.error("jasoidjasiod");
  }
}
