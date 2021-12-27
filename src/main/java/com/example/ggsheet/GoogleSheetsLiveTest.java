package com.example.ggsheet;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;

@Service
public class GoogleSheetsLiveTest {

  //  @BeforeClass
  public static void setup() throws GeneralSecurityException, IOException {
    Sheets sheetsService = SheetsServiceUtil.getSheetsService();
    ValueRange body = new ValueRange().setValues(Arrays.asList(
            Collections.singletonList("Expenses January"),
            Arrays.asList("books", "30"),
            Arrays.asList("pens", "10"),
            Collections.singletonList("Expenses February"),
            Arrays.asList("clothes", "20"),
            Arrays.asList("shoes", "5")));
    // ...
    String SPREADSHEET_ID = "14cFRQQdeXiFbk2r_QIRU9g7DugZzO7tdcdIv5EJADMs";
    UpdateValuesResponse result = sheetsService.spreadsheets().values()
            .update(SPREADSHEET_ID, "A1", body)
            .setValueInputOption("RAW")
            .execute();
  }


}
