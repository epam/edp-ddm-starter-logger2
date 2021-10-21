package com.epam.digital.data.platform.starter.logger;

import org.slf4j.MDC;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.Sink;

import java.io.IOException;

public class LogbookResponseCodeSink implements Sink {

  private static final String RESPONSE_CODE_MDC_KEY = "responseCode";

  private final Sink delegate;

  public LogbookResponseCodeSink(Sink delegate) {
    this.delegate = delegate;
  }

  @Override
  public void write(Precorrelation precorrelation, HttpRequest request) throws IOException {
    delegate.write(precorrelation, request);
  }

  @Override
  public void write(Correlation correlation, HttpRequest request, HttpResponse response)
      throws IOException {
    MDC.put(RESPONSE_CODE_MDC_KEY, String.valueOf(response.getStatus()));
    delegate.write(correlation, request, response);
    MDC.remove(RESPONSE_CODE_MDC_KEY);
  }
}
