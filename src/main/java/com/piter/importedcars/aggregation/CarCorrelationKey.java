package com.piter.importedcars.aggregation;

import com.piter.importedcars.model.Car;
import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.springframework.stereotype.Component;

@Component
public class CarCorrelationKey implements Expression {

  @Override
  public <T> T evaluate(Exchange exchange, Class<T> type) {
    Car car = exchange.getIn().getBody(Car.class);
    return type.cast(car.getBrand());
  }
}
