package service;

import calculadora.CalculadoraModel;
import calculadora.ImpOperacionAritmetica;
import com.oscar.calculadora.CalculadoraCuantica.MainVerticle;
import ihandleContextRouter.IHandleRouter;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.openapi.RouterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ImpHandleRouterService implements IHandleRouter {

  private static final Logger LG = LoggerFactory.getLogger(ImpHandleRouterService.class);
  private Map<String, JsonObject> userMap = new HashMap<>();
  private ImpOperacionAritmetica io = new ImpOperacionAritmetica();


  @Override
  public void sendError(int statusCode, HttpServerResponse response) {
    response.setStatusCode(statusCode).end();

  }

  @Override
  public void initialData() {

    double result = 0;

    CalculadoraModel calculadora = new CalculadoraModel();
    calculadora.setNumeroOne(6.05656d);
    calculadora.setNumeroTwo(6.66d);
    calculadora.setOperacion("*");
    result = io.multiplicacion(calculadora.getNumeroOne(), calculadora.getNumeroTwo());
    calculadora.setResultado(result);

    CalculadoraModel calculadora2 = new CalculadoraModel();
    calculadora2.setNumeroOne(6d);
    calculadora2.setNumeroTwo(6d);
    calculadora2.setOperacion("+");
    result = io.suma(calculadora2.getNumeroOne(), calculadora2.getNumeroTwo());
    calculadora2.setResultado(result);

    CalculadoraModel calculadora3 = new CalculadoraModel();
    calculadora3.setNumeroOne(6d);
    calculadora3.setNumeroTwo(6d);
    calculadora3.setOperacion("-");
    result = io.resta(calculadora3.getNumeroOne(), calculadora2.getNumeroTwo());
    calculadora3.setResultado(result);

    CalculadoraModel calculadora4 = new CalculadoraModel();
    calculadora4.setNumeroOne(6d);
    calculadora4.setNumeroTwo(6d);
    calculadora4.setOperacion("/");
    result = io.division(calculadora4.getNumeroOne(), calculadora4.getNumeroTwo());
    calculadora4.setResultado(result);

    addCalculadora(JsonObject.mapFrom(calculadora));
    addCalculadora(JsonObject.mapFrom(calculadora2));
    addCalculadora(JsonObject.mapFrom(calculadora3));
    addCalculadora(JsonObject.mapFrom(calculadora4));

  }

  @Override
  public void addCalculadora(JsonObject calculadora) {
    userMap.put(calculadora.getString("id"), calculadora);

  }


  @Override
  public Future<Void> handlePostCalculadoraFuture(RoutingContext routingContext) {
    CalculadoraModel calculadoraModel = Json.decodeValue(routingContext.getBodyAsString(), CalculadoraModel.class);

    RouterBuilder.create(MainVerticle.vertx, "src/main/resources/calculadora.yaml")
      .onSuccess(routerBuilder -> {
        calculadoraModel.setContext(routerBuilder.getOpenAPI().getOpenAPI());
        addCalculadora(JsonObject.mapFrom(calculadoraModel));
        LG.info("Post : {}", calculadoraModel.toString());

      })
      .onFailure(err -> {
      });
    return routingContext.response()
      .setStatusCode(201)
      .putHeader("content-type", "application/json; charset=utf-8")
      .end(Json.encodePrettily(calculadoraModel));
  }




  @Override
  public Future<Void> allCalculadoraFuture(RoutingContext routingContext) {
    JsonArray arr = new JsonArray();
    userMap.forEach((k, v) -> arr.add(v));
    LG.info("Get Calculadora: {}", arr.toString());

    return routingContext
      .response()
      .putHeader("content-type", "application/json")
      .end(arr.encodePrettily());

  }


  @Override
  public Future<Void> handleGetCalculadoraFuture(RoutingContext routingContext) {
    String calculadoraID = routingContext.request().getParam("id");
    HttpServerResponse response = routingContext.response();
    JsonObject jsonCalcu = null;

    if (calculadoraID == null) {
      sendError(400, response);
    } else {
      jsonCalcu = userMap.get(calculadoraID);
      LG.info("Get ID: {}", jsonCalcu.toString());

      if (jsonCalcu == null) {
        sendError(404, response);
      }
    }
    return response.putHeader("content-type", "application/json")
      .end(jsonCalcu.encodePrettily());

  }

  @Override
  public void handlePutCalculadora(RoutingContext routingContext) {
    String calculadoraID = routingContext.request().getParam("id");
    HttpServerResponse response = routingContext.response();

    if (calculadoraID == null) {
      sendError(400, response);
    } else {
      JsonObject jsonCalcu = routingContext.getBodyAsJson();
      LG.info("PUT ID: {}", jsonCalcu.toString());

      if (jsonCalcu == null) {
        sendError(400, response);
      } else {
        userMap.put(calculadoraID, jsonCalcu);
        response.end();
      }
    }

  }


  @Override
  public void handleDeleteCalculadora(RoutingContext routingContext) {
    String calculadoraID = routingContext.request().getParam("id");
    HttpServerResponse response = routingContext.response();

    if (calculadoraID == null) {
      sendError(400, response);
    } else {
      JsonObject jsonCalcu = userMap.remove(calculadoraID);
      LG.info("DELETE : {}", jsonCalcu.toString());


      if (jsonCalcu == null) {
        sendError(404, response);
      } else {
        response.putHeader("content-type", "application/json").end(jsonCalcu.encodePrettily());
      }
    }
  }
}


