package com.oscar.calculadora.CalculadoraCuantica;

import calculadora.CalculadoraModel;
import calculadora.ImpOperacionAritmetica;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.Checkpoint;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import io.vertx.rxjava.ext.web.client.WebClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import service.ImpHandleRouterService;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BooleanSupplier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(VertxExtension.class)
public class TestMainVerticle {


  Vertx vertx = Vertx.vertx();
  CalculadoraModel calculadora = null;
  ImpHandleRouterService impHandleRouterService = null;


  void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
    vertx.deployVerticle(new MainVerticle(), testContext.succeeding(id -> testContext.completeNow()));
  }


  @Test
  void verticle_deployed(Vertx vertx, VertxTestContext testContext) throws Throwable {
    testContext.completeNow();
  }


  //******************** Vert.X Port ****************************************


  @Test
  @DisplayName("⏱ Count 3 timer ticks")
  void countThreeTicks(Vertx vertx, VertxTestContext testContext) {
    AtomicInteger counter = new AtomicInteger();
    vertx.setPeriodic(100, id -> {
      if (counter.incrementAndGet() == 3) {
        testContext.completeNow();
      }
    });
  }

  @Test
  @DisplayName("⏱ Count 3 timer ticks, with a checkpoint")
  void countThreeTicksWithCheckpoints(Vertx vertx, VertxTestContext testContext) {
    Checkpoint checkpoint = testContext.checkpoint(3);
    vertx.setPeriodic(100, id -> checkpoint.flag());
  }


  //********************* Class CalculadoraModel ***************************




  @Test
  void calculadoraList() {
    Assertions.assertFalse(list().isEmpty());
    Assertions.assertEquals(1, list().size());
    Assertions.assertTrue(list().stream().anyMatch(calculadora ->
      calculadora.getId() == 1 && calculadora.getOperacion().equalsIgnoreCase("+")
        && calculadora.getNumeroOne() == 3 && calculadora.getNumeroTwo() == 9
        && calculadora.getResultado() == 12

    ));


  }


  List<CalculadoraModel> list() {

    List<CalculadoraModel> calculadoraModelList = Arrays.asList(
      new CalculadoraModel(1, "+", 3, 9, 12,
        new JsonObject().put("id", 1))
    );


    return calculadoraModelList;
  }


  //********************* Class ImpOperacionAritmetica ***********************
  ImpOperacionAritmetica io = new ImpOperacionAritmetica();

  @Test
  void suma() {
    double x = io.suma(2, 2);
    assertEquals(x, 4);

  }

  @Test
  void resta() {
    double x = io.resta(2, 2);
    assertEquals(x, 0);

  }

  @Test
  void multiplicacion() {
    double x = io.multiplicacion(2, 2);
    assertEquals(x, 4);

  }

  @Test
  void division() {
    double x = io.division(2, 2);
    assertEquals(x, 1);

  }

  @Test
  @DisplayName(" - Passed -")
  void operacionesAritmetics() {
    double x = io.operacionAritmetica(2, 2, "+");
    assertEquals(x, 4);

    double y = io.operacionAritmetica(2, 2, "-");
    assertEquals(y, 0);

    double z = io.operacionAritmetica(2, 2, "*");
    assertEquals(z, 4);

    double a = io.operacionAritmetica(2, 2, "/");
    assertEquals(a, 1);


  }


}




