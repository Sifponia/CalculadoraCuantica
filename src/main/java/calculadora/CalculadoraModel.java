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

  public CalculadoraModel(Integer id, String operacion, double numeroOne, double numeroTwo, double resultado, JsonObject context) {
    this.id = id;
    this.operacion = operacion;
    this.numeroOne = numeroOne;
    this.numeroTwo = numeroTwo;
    this.resultado = resultado;
    this.context = context;
  }

  public CalculadoraModel() {
    this.id = COUNTER.incrementAndGet();
  }
}


