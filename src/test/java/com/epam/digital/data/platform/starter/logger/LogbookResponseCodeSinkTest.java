package com.epam.digital.data.platform.starter.logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.HttpResponse;
import org.zalando.logbook.Precorrelation;
import org.zalando.logbook.Sink;

import java.io.IOException;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LogbookResponseCodeSinkTest {

  @Spy
  private Sink spySink;

  @Mock
  private Precorrelation mockPrecorrelation;
  @Mock
  private Correlation mockCorrelation;
  @Mock
  private HttpRequest mockHttpRequest;
  @Mock
  private HttpResponse mockHttpResponse;

  private Sink responseCodeSink;

  @BeforeEach
  void beforeEach() {
    responseCodeSink = new LogbookResponseCodeSink(spySink);
  }

  @Test
  void expectWrappedSinkIsCalled() throws IOException {
    responseCodeSink.write(mockPrecorrelation, mockHttpRequest);

    verify(spySink).write(mockPrecorrelation, mockHttpRequest);
  }

  @Test
  void expectWrappedSinkAndResponseStatusIsCalled() throws IOException {
    responseCodeSink.write(mockCorrelation, mockHttpRequest, mockHttpResponse);

    verify(mockHttpResponse).getStatus();
    verify(spySink).write(mockCorrelation, mockHttpRequest, mockHttpResponse);
  }
}
