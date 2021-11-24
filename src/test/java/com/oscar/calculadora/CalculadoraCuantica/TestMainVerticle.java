package com.oscar.calculadora.CalculadoraCuantica;

import io.vertx.core.Vertx;

import io.vertx.junit5.Checkpoint;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import service.ImpHandleRouterService;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(VertxExtension.class)
public class TestMainVerticle {


  Vertx vertx = Vertx.vertx();
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




}




