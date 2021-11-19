package com.flixbus.fleetmanager.error;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.openMocks;

import com.flixbus.fleetmanager.service.TranslationService;
import java.util.Collections;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ErrorInfoServiceTest {

  public static final String MESSAGE = "message";
  public static final String COMMON_ERROR_HEADER_VALUE = "header";
  public static final String CUSTOM_HEADER_VALUE = "customheader";
  public static final String CUSTOM_HEADER = "custom.header";
  public static final String COMMON_ERROR_HEADER = "common.error.header";

  @Mock
  private TranslationService translationService;

  @InjectMocks
  private ErrorInfoService service;

  @BeforeEach
  public void setup() {
    openMocks(this);
  }

  @Test
  public void shouldCreateErrorInfo_messageOnly() {
    //given
    ServerToClientException exception = new ServerToClientException(MESSAGE);
    given(translationService.get(COMMON_ERROR_HEADER)).willReturn(COMMON_ERROR_HEADER_VALUE);

    //when
    ErrorInfo errorInfo = service.createClientErrorInfo(exception);

    //then
    assertEquals(errorInfo.getHeader(), COMMON_ERROR_HEADER_VALUE);
    assertEquals(errorInfo.getMessages().size(), 1);
    assertEquals(errorInfo.getMessages().get(0), MESSAGE);
  }

  @Test
  public void shouldCreateErrorInfo_headerAndMessage() {
    //given
    ServerToClientException exception = new ServerToClientException(CUSTOM_HEADER, MESSAGE);
    given(translationService.get(CUSTOM_HEADER)).willReturn(CUSTOM_HEADER_VALUE);

    //when
    ErrorInfo errorInfo = service.createClientErrorInfo(exception);

    //then
    assertEquals(errorInfo.getHeader(), CUSTOM_HEADER_VALUE);
    assertEquals(errorInfo.getMessages().size(), 1);
    assertEquals(errorInfo.getMessages().get(0), MESSAGE);
  }

  @Test
  public void shouldCreateErrorInfo_headerAndMessageList() {
    //given
    ServerToClientException exception = new ServerToClientException(CUSTOM_HEADER, Collections.singletonList(MESSAGE));
    given(translationService.get(CUSTOM_HEADER)).willReturn(CUSTOM_HEADER_VALUE);

    //when
    ErrorInfo errorInfo = service.createClientErrorInfo(exception);

    //then
    assertEquals(errorInfo.getHeader(), CUSTOM_HEADER_VALUE);
    assertEquals(errorInfo.getMessages().size(), 1);
    assertEquals(errorInfo.getMessages().get(0), MESSAGE);
  }

}
