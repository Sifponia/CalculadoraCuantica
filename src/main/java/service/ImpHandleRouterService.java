package service;

import calculos.ImpOperacionAritmetica;
import ihandleContextRouter.IHandleRouter;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.validation.RequestParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import paginacion.Pageable;
import resta.Resta;
import suma.Suma;

import java.util.*;
import java.util.stream.Collectors;

public class ImpHandleRouterService implements IHandleRouter {

  ///:::: Atributos Globales dentro de ImpHandleRouterService
  private static final Logger LG = LoggerFactory.getLogger(ImpHandleRouterService.class);
  private ImpOperacionAritmetica io = new ImpOperacionAritmetica();
  final static List<JsonObject> historico = new ArrayList<>();


  ///************* :::::::: ProyectoOpenApiV2 ::::::::::::: **************//


  ///:::::::::::: LÓGICA API SUMA
  @Override
  public void handlePostSuma(RoutingContext routingContext) {
    final Suma suma = Json.decodeValue(routingContext.getBodyAsString(),
      Suma.class);


    double result = io.suma(suma.getX(), suma.getY());
    suma.setResultado(result);

    addSuma(JsonObject.mapFrom(suma));
    LG.info("Post : ✔️✔️ {}", suma.toString(), " OPERACION: {} ", suma.toString());


    routingContext.response()
      .setStatusCode(200)
      .putHeader("content-type", "application/json; charset=utf-8")
      .end(Json.encodePrettily(suma));

  }


  @Override
  public void initialDataSuma() {

    Suma suma = new Suma();
    suma.setOperacion("+");
    suma.setId(1);
    suma.setX(2);
    suma.setY(2);
    suma.setResultado(4);

    Suma suma2 = new Suma();
    suma.setOperacion("+");
    suma2.setId(2);
    suma2.setX(2);
    suma2.setY(2);
    suma2.setResultado(4);

    Suma suma3 = new Suma();
    suma.setOperacion("+");
    suma3.setId(3);
    suma3.setX(2);
    suma3.setY(2);
    suma3.setResultado(4);


    addSuma(JsonObject.mapFrom(suma));
    addSuma(JsonObject.mapFrom(suma2));
    addSuma(JsonObject.mapFrom(suma3));


  }


  @Override
  public void addSuma(JsonObject sumaJson) {
    historico.add(sumaJson);
    if (historico.size() >= 10) {
      historico.remove(0);
    } else {

    }


  }


  @Override
  public void historico(RoutingContext routingContext) {
    System.out.println("-------" + historico.size());

    routingContext
      .response()
      .setStatusCode(200)
      .putHeader("content-type", "application/json")
      .end(allSumaResult().encodePrettily());

  }

  private List<JsonObject> getAllAdd() {
    return historico;
  }


  @Override
  public void findById(RoutingContext routingContext) {
    RequestParameters params = routingContext.get("parsedParameters");
    Integer id = params.pathParameter("id").getInteger();

    Optional<JsonObject> sumaOptional = getAllAdd()
      .stream()
      .filter(p -> p.getInteger("id").equals(id))
      .findFirst(); // <3>
    if (sumaOptional.isPresent()) {

      LG.info("Get ID: \uD83D\uDD0E\uD83D\uDD0E {}", sumaOptional.toString());
      routingContext
        .response()
        .setStatusCode(200)
        .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
        .end(sumaOptional.get().toBuffer()); // (4)
    } else {

      System.out.println(">>>>>>>: " + sumaOptional.toString());
      routingContext.fail(404, new Exception("Pet not found")); // (5

    }

  }


  public JsonArray allSumaResult() {
    JsonArray arr = new JsonArray();
    historico.forEach(arr::add);
    List<Object> listPageable = Pageable.getPage(arr.stream()
      .collect(Collectors.toList()), 1, 100);
    return new JsonArray().add(listPageable);

  }


  ///:::::::::::: LÓGICA API RESTA
  @Override
  public void handlePostResta(RoutingContext routingContext) {
    final Resta resta = Json.decodeValue(routingContext.getBodyAsString(),
      Resta.class);

    double result = io.resta(resta.getX(), resta.getY());
    resta.setResultado(result);


    addResta(JsonObject.mapFrom(resta));
    LG.info("Post : ✔️✔️ {}", resta.toString(), " OPERACION: {} ", resta.toString());

    routingContext.response()
      .setStatusCode(200)
      .putHeader("content-type", "application/json; charset=utf-8")
      .end(Json.encodePrettily(resta));


  }

  @Override
  public void initialDataResta() {
    Resta resta = new Resta();
    resta.setId(4);
    resta.setX(2);
    resta.setOperacion("-");
    resta.setY(2);
    resta.setResultado(0);

    Resta resta2 = new Resta();
    resta2.setId(5);
    resta2.setX(5);
    resta.setOperacion("-");
    resta2.setY(3);
    resta2.setResultado(2);

    Resta resta3 = new Resta();
    resta3.setId(6);
    resta3.setX(10);
    resta.setOperacion("-");
    resta3.setY(9);
    resta3.setResultado(1);


    addResta(JsonObject.mapFrom(resta));
    addResta(JsonObject.mapFrom(resta2));
    addResta(JsonObject.mapFrom(resta3));


  }


  @Override
  public void addResta(JsonObject restaJson) {
    historico.add(restaJson);

    if (historico.size() >= 10) {
      historico.remove(0);

    } else {

    }


  }

  private List<JsonObject> getAllSusb() {
    return historico;


  }




}


