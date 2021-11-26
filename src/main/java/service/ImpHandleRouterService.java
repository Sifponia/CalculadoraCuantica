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
  final static List<JsonObject> suma = new ArrayList<>();
  final static List<JsonObject> resta = new ArrayList<>();
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
    suma.setOperacion("+");

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

    Suma suma4 = new Suma();
    suma4.setId(4);
    suma.setOperacion("+");
    suma4.setX(2);
    suma4.setY(2);
    suma4.setResultado(4);

    Suma suma5 = new Suma();
    suma.setOperacion("+");
    suma5.setId(5);
    suma5.setX(5);
    suma5.setY(3);
    suma5.setResultado(8);


    addSuma(JsonObject.mapFrom(suma));
    addSuma(JsonObject.mapFrom(suma2));
    addSuma(JsonObject.mapFrom(suma3));
    addSuma(JsonObject.mapFrom(suma4));
    addSuma(JsonObject.mapFrom(suma5));


  }


  @Override
  public void addSuma(JsonObject sumaJson) {
    suma.add(sumaJson);

  }


  @Override
  public void allSuma(RoutingContext routingContext) {


    routingContext
      .response()
      .setStatusCode(200)
      .putHeader("content-type", "application/json")
      .end(allSumaResult().encodePrettily());

  }

  private List<JsonObject> getAllAdd() {


    System.out.println("-------" + suma.size());
    if (suma.size() > 8) {
      suma.remove(0);
      return suma;
    } else {
      return suma;
    }

    //return suma;
  }


  @Override
  public void findByIdSuma(RoutingContext routingContext) {
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
    suma.forEach(arr::add);
    resta.forEach(arr::add);


    List<Object> listPageable = Pageable.getPage(arr.stream()
      .collect(Collectors.toList()), 1, 10);
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
    resta.setId(1);
    resta.setX(2);
    resta.setOperacion("-");
    resta.setY(2);
    resta.setResultado(0);

    Resta resta2 = new Resta();
    resta2.setId(2);
    resta2.setX(5);
    resta.setOperacion("-");
    resta2.setY(3);
    resta2.setResultado(2);

    Resta resta3 = new Resta();
    resta3.setId(3);
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
    resta.add(restaJson);

  }


  private List<JsonObject> getAllSusb() {
    System.out.println("-------" + resta.size());
    if (resta.size() >= 1000) {
      resta.remove(0);
      return resta;
    } else {
      return resta;
    }

  }


  public JsonArray SusbResult() {
    JsonArray arr = new JsonArray();
    resta.forEach(arr::add);
    List<Object> listPageable = Pageable.getPage(arr.stream()
      .collect(Collectors.toList()), 1, 10);

    System.out.println("-------" + listPageable.size());
    return new JsonArray().add(listPageable);

  }

  @Override
  public void allResta(RoutingContext routingContext) {
    routingContext
      .response()
      .setStatusCode(200)
      .putHeader("content-type", "application/json")
      .end(SusbResult().encodePrettily());
  }


  @Override
  public void findByIResta(RoutingContext routingContext) {
    RequestParameters params = routingContext.get("parsedParameters");
    Integer id = params.pathParameter("id").getInteger();

    Optional<JsonObject> restaOptional = getAllSusb()
      .stream()
      .filter(p -> p.getInteger("id").equals(id))
      .findFirst(); // <3>
    if (restaOptional.isPresent()) {

      LG.info("Get ID: \uD83D\uDD0E\uD83D\uDD0E {}", restaOptional.toString());
      routingContext
        .response()
        .setStatusCode(200)
        .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
        .end(restaOptional.get().toBuffer()); // (4)
    } else {

      System.out.println(">>>>>>>: " + restaOptional.toString());
      routingContext.fail(404, new Exception("Sub not found")); // (5

    }

  }

}


