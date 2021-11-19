package com.flixbus.fleetmanager.error;

import com.flixbus.fleetmanager.service.TranslationService;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ErrorInfoService {

  private final TranslationService translationService;

  @Autowired
  public ErrorInfoService(TranslationService translationService) {
    this.translationService = translationService;
  }

  public ErrorInfo createClientErrorInfo(ServerToClientException e) {
    String header = e.getHeader() != null ? translationService.get(e.getHeader()) : translationService.get("common.error.header");
    List<String> messages = !e.getMessages().isEmpty() ? e.getMessages() : Collections.singletonList(translationService.get("common.error.message"));
    ErrorInfo errorInfo = new ErrorInfo(header, messages);
    return errorInfo;
  }

}
