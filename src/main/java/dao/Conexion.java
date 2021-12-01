package dao;

import io.vertx.core.Vertx;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.SqlClient;


public class Conexion {

  private PgConnectOptions connectOptions;
  protected PoolOptions poolOptions;
  protected SqlClient client;


  public void connect() throws Exception {
    try {

      connectOptions = new PgConnectOptions()
        .setPort(5432)
        .setHost("localhost")
        .setDatabase("calculadora")
        .setUser("postgres")
        .setPassword("7654321");

      poolOptions = new PoolOptions().setMaxSize(5);
      System.out.println(">>>>>>>: " + connectOptions.isUsingDomainSocket());
      this.client = PgPool.client(connectOptions, poolOptions);

    } catch (Exception e) {

      System.out.println(e.getLocalizedMessage());

    }

  }


  public void close() throws Exception {

    if (client != null) {
      if (!client.close().succeeded()) {
        client.close();
      }
    }

  }
}
