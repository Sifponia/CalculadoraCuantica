package dao;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.*;


/*
 * @author <a href="mailto:pmlopes@gmail.com">Paulo Lopes</a>
 */
public class TestDos extends AbstractVerticle {


  public static void main(String[] args) {
    PgConnectOptions connectOptions = new PgConnectOptions()
      .setPort(5432)
      .setHost("localhost")
      .setDatabase("calculadora")
      .setUser("postgres")
      .setPassword("7654321");

// Pool options
    PoolOptions poolOptions = new PoolOptions()
      .setMaxSize(5);

    System.out.println(">>>>>>>: " + connectOptions.isUsingDomainSocket());


// Create the client pool
    SqlClient client = PgPool.client(connectOptions, poolOptions);


    //"INSERT INTO calculos(y, x, operacion, resultado) VALUES (?, ?, ?, ?, ?)"

// A simple query
    client
      .query( "INSERT INTO calculos(y, x, operacion, resultado) VALUES (2, 3, '+', 5)")
      .execute(ar -> {
        if (ar.succeeded()) {
          RowSet<Row> result = ar.result();
          System.out.println("Got " + result.size() + " rows ");
        } else {
          System.out.println("Failure: " + ar.cause().getMessage());
        }

        // Now close the pool
        client.close();
      });


   /* System.out.println(pool.getConnection().succeeded());

    // Get a connection from the pool
    pool.getConnection().compose(conn -> {
      System.out.println("Got a connection from the pool");

      // All operations execute on the same connection
      return conn
        .query("SELECT * FROM calculos")
        .execute()
        .onComplete(ar -> {
          // Release the connection to the pool
          conn.close();
        });
    }).onComplete(ar -> {
      if (ar.succeeded()) {

        System.out.println("Done");
      } else {
        System.out.println("Something went wrong " + ar.cause().getMessage());
      }
    });*/


  }


  @Override
  public void start() {


    PgConnectOptions options = new PgConnectOptions()
      .setPort(5432)
      .setHost("localhost")
      .setDatabase("calculadora")
      .setUser("postgres")
      .setPassword("1234567");


// Pool options
    PoolOptions poolOptions = new PoolOptions()
      .setMaxSize(5);

// Create the client pool
    SqlClient client = PgPool.client(options, poolOptions);

// A simple query
    client
      .query("")
      .execute(ar -> {
        if (ar.succeeded()) {
          RowSet<Row> result = ar.result();
          System.out.println("Got " + result.size() + " rows ");
        } else {
          System.out.println("Failure: " + ar.cause().getMessage());
        }

        // Now close the pool
        client.close();
      });


  }
}
