package com.flixbus.fleetmanager.error;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class ServerToClientException extends RuntimeException  {

  private String header;
  private List<String> messages = new ArrayList<>();

  public ServerToClientException(String message) {
    this.messages.add(message);
  }

  public ServerToClientException(String header, List<String> messages) {
    this.header = header;
    this.messages.addAll(messages);
  }

  public ServerToClientException(String header, String message) {
    this.header = header;
    this.messages.add(message);
  }

}
