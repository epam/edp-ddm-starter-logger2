/*
 * Copyright 2021 EPAM Systems.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
