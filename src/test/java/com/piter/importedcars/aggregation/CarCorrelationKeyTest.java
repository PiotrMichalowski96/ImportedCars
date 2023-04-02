package com.piter.importedcars.aggregation;

import static org.assertj.core.api.Assertions.assertThat;

import com.piter.importedcars.model.Car;
import com.piter.importedcars.util.ExchangeUtils;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class CarCorrelationKeyTest {

  private final CarCorrelationKey carCorrelationKey = new CarCorrelationKey();

  @Test
  void shouldEvaluateCorrelationKey() {
    //given
    var brand = "Audi";

    var car = Car.builder()
        .brand(brand)
        .model("A6")
        .firstRegistrationDate(LocalDate.now())
        .build();

    var exchange = ExchangeUtils.createExchange(car);

    //when
    String key = carCorrelationKey.evaluate(exchange, String.class);

    //then
    assertThat(key).isEqualTo(brand);
  }

}