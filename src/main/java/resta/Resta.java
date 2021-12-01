package resta;


import lombok.Data;
import suma.Suma;

import java.util.concurrent.atomic.AtomicInteger;

@Data
public class Resta {

 // private static final AtomicInteger COUNTER = new AtomicInteger(0);

  private Integer id;
  private double x;
  private String operacion;
  private double y;
  private double resultado;



  public Resta() {
    this.id = Suma.COUNTER.incrementAndGet();
    this.operacion = "-";
  }

}
