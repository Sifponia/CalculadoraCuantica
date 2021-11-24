package suma;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

@Data
public class Suma {

  private static final AtomicInteger COUNTER = new AtomicInteger(0);

  private Integer id;
  private String operacion;
  private double x;
  private double y;
  private double resultado;

  public Suma(Integer id, String operacion, double x, double y, double resultado) {
    this.id = id;
    this.operacion = operacion;
    this.x = x;
    this.y = y;
    this.resultado = resultado;
  }

  public Suma() {
    this.id = COUNTER.incrementAndGet();
  }
}
