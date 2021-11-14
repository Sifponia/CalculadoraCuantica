package calculadora;

import io.vertx.core.json.JsonObject;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

@Data
public class CalculadoraModel {

  private static final AtomicInteger COUNTER = new AtomicInteger(0);

  private Integer id;
  private String operacion;
  private double numeroOne;
  private double numeroTwo;
  private double resultado;
  private JsonObject context;


  public CalculadoraModel() {
    this.id = COUNTER.incrementAndGet();
  }
}


