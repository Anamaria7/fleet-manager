package com.flixbus.fleetmanager.error;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ErrorInfo {

  private String header;
  private List<String> messages = new ArrayList<>();
  private String stackTrace;

  public ErrorInfo(String header, String message) {
    this.header = header;
    this.messages.add(message);
  }

  public ErrorInfo(String header, List<String> messages) {
    this.header = header;
    this.messages.addAll(messages);
  }

}
