package com.epam.digital.data.platform.starter.logger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.DefaultSink;
import org.zalando.logbook.HttpLogFormatter;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Sink;

@Configuration
public class LoggingAutoConfiguration {

  @Bean
  public Sink responseCodeLoggingSink(HttpLogFormatter formatter, HttpLogWriter writer) {
    return new LogbookResponseCodeSink(new DefaultSink(formatter, writer));
  }
}
