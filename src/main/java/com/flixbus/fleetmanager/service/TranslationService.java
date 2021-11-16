package com.flixbus.fleetmanager.service;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TranslationService {

  private final Locale locale;

  private final MessageSource messageSource;

  public TranslationService(MessageSource messageSource) {
    this.messageSource = messageSource;
    this.locale = LocaleContextHolder.getLocale();
  }

  public String get(String key, Object... params) {
    return messageSource.getMessage(key, params, locale);
  }

}
