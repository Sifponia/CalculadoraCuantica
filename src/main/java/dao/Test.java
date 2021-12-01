package dao;

import suma.Suma;

public class Test {
  public static void main(String[] args) throws Exception {

   Suma suma = new Suma();

    suma.setX(2);
    suma.setY(20);
    suma.setOperacion("+");
    suma.setResultado(24);

    ImplCalculadora implCalculadora = new ImplCalculadora();

    implCalculadora.post(suma);

    /*
    PgConnectOptions connectOptions = new PgConnectOptions()
      .setPort(5432)
      .setHost("localhost")
      .setDatabase("calculadora")
      .setUser("postgres")
      .setPassword("7654321");

// Pool options
    PoolOptions poolOptions = new PoolOptions()
      .setMaxSize(5);

// Create the client pool
    SqlClient client = PgPool.client(connectOptions, poolOptions);

// A simple query
    client
      .query("SELECT y, x, operacion, id, contador\n" +
        "\tFROM public.calculos;")
      .execute(ar -> {
        if (ar.succeeded()) {
          RowSet<Row> result = ar.result();
          System.out.println("Got " + result.size() + " rows ");
        } else {
          System.out.println("Failure: " + ar.cause().getMessage());
        }

        // Now close the pool
        client.close();
      });*/

  }
}
