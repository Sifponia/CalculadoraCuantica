package dao;

import daoCalculadora.DaoCalculadora;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import suma.Suma;

import java.util.List;

public class ImplCalculadora extends Conexion implements DaoCalculadora {

  @Override
  public void post(Suma suma) throws Exception {
    try {
      this.connect();
      this.client.query("INSERT INTO calculos (x,y,operacion,resultado)  VALUES " +
          "('"+suma.getX()+"','"+suma.getY()+"','"+suma.getOperacion()+"',"+suma.getResultado()+")")

        .execute(ar -> {
          if (ar.succeeded()) {
            RowSet<Row> result = ar.result();
            System.out.println("Got " + result.size() + " rows ");
          } else {
            System.out.println("Failure: " + ar.cause().getMessage());
          }
          client.close();
        });

    } catch (Exception e) {
      throw e;
    }
  }

  @Override
  public List<Suma> getHistorico(Suma suma) {
    return null;
  }

  @Override
  public void findById(Suma suma) {

  }
}
