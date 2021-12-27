package com.example.ggsheet;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Component
@Log4j2
public class GoogleAuthorizeUtil {
  private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  // Đây là đường dẫn thư mục sẽ lưu tạo file xác thực khi bạn đăng nhập lần đầu tiên
  private static final String TOKENS_DIRECTORY_PATH = "tokens";

  /**
   * Đây là thuộc tính chỉ phạm vi quyền thực hiện với trang tính, hiện đang để là chỉ đọc
   * Nếu sửa đổi quyền thì bạn cần xóa thư mục  tokens/ để đăng nhập lại
   */
  private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
  private static final String CREDENTIALS_FILE_PATH = "/client_secret.json";

    /**
   * Đây là function để lấy quyền xác thực
   * Creates an authorized Credential object.
   *
   * @param HTTP_TRANSPORT The network HTTP Transport.
   * @return An authorized Credential object.
   * @throws IOException If the credentials.json file cannot be found.
   */

  public static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
    // Load client secrets.
    InputStream in = GoogleAuthorizeUtil.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
    if (in == null) {
      throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
    }


    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

    // Build flow and trigger user authorization request.
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
            .setAccessType("offline")
            .build();
    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8088).build();
    return new AuthorizationCodeInstalledApp(flow, receiver).authorize("dangnc.cntt@gmail.com");
  }

  /**
   * Tương tác thử với một bảng tính để đọc dữ liệu bên trong
   * Prints the names and majors of students in a sample spreadsheet:
   * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
   */
//  public static void main(String... args) throws IOException, GeneralSecurityException {
//    // Build a new authorized API client service.
//    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
//    final String spreadsheetId = "1YTGkiwMfHloeQlBm5j79C1TgX10JtJTzncqL0dEDs1Q";
//    final String range = "Class Data!A2:E";
//    Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
//            .setApplicationName(APPLICATION_NAME)
//            .build();
//    ValueRange response = service.spreadsheets().values()
//            .get(spreadsheetId, range)
//            .execute();
//    List<List<Object>> values = response.getValues();
//    if (values == null || values.isEmpty()) {
//      System.out.println("No data found.");
//    } else {
//      System.out.println("Name, Major");
//      for (List row : values) {
//        // Print columns A and E, which correspond to indices 0 and 4.
//        System.out.printf("%s, %s\n", row.get(0), row.get(4));
//      }
//    }
//  }
  public static Credential authorize(final NetHttpTransport HTTP_TRANSPORT) throws IOException, GeneralSecurityException {

    // build GoogleClientSecrets from JSON file

    List<String> scopes = Collections.singletonList(SheetsScopes.SPREADSHEETS);

    // build Credential object
    InputStream in = GoogleAuthorizeUtil.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
    if (in == null) {
      throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
    }
    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

    // Build flow and trigger user authorization request.
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, scopes)
            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
            .setAccessType("offline")
            .build();
    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8088).build();
    return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
  }
}
