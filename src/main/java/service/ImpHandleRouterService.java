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
import suma.Suma;

import java.util.*;
import java.util.stream.Collectors;

public class ImpHandleRouterService implements IHandleRouter {

  ///:::: Atributos Globales dentro de ImpHandleRouterService
  private static final Logger LG = LoggerFactory.getLogger(ImpHandleRouterService.class);
  private ImpOperacionAritmetica io = new ImpOperacionAritmetica();
  final static List<JsonObject> suma = new ArrayList<>();


  ///************* :::::::: ProyectoOpenApiV2 ::::::::::::: **************//


  ///:::::::::::: LÓGICA API SUMA
  @Override
  public void handlePostSuma(RoutingContext routingContext) {
    final Suma suma = Json.decodeValue(routingContext.getBodyAsString(),
      Suma.class);
    double result = io.operacionAritmetica(suma.getX(), suma.getY(),
      suma.getOperacion());
    suma.setResultado(result);

    addSuma(JsonObject.mapFrom(suma));
    LG.info("Post : ✔️✔️ {}", suma.toString(), " OPERACION: {} ", suma.getOperacion());

    routingContext.response()
      .setStatusCode(200)
      .putHeader("content-type", "application/json; charset=utf-8")
      .end(Json.encodePrettily(suma));

  }


  @Override
  public void initialDataSuma() {

    Suma suma = new Suma();
    suma.setId(1);
    suma.setY(2);
    suma.setY(2);
    suma.setOperacion("+");
    suma.setResultado(4);

    Suma suma2 = new Suma();
    suma2.setId(2);
    suma2.setY(2);
    suma2.setY(2);
    suma2.setOperacion("+");
    suma2.setResultado(4);

    Suma suma3 = new Suma();
    suma3.setId(3);
    suma3.setY(2);
    suma3.setY(2);
    suma3.setOperacion("+");
    suma3.setResultado(4);

    Suma suma4 = new Suma();
    suma4.setId(4);
    suma4.setY(2);
    suma4.setY(2);
    suma4.setOperacion("+");
    suma4.setResultado(4);

    Suma suma5 = new Suma();
    suma5.setId(5);
    suma5.setY(2);
    suma5.setY(2);
    suma5.setOperacion("+");
    suma5.setResultado(4);

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
      .putHeader("content-type", "application/json") // (2)
      .end(allSumaResult().encodePrettily()); // (3)

  }

  private List<JsonObject> getAllAdd() {
    return suma;
  }


  @Override
  public void findByIdSuma(RoutingContext routingContext) {
    RequestParameters params = routingContext.get("parsedParameters"); // <1>
    Integer id = params.pathParameter("id").getInteger(); // <2>

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
    //return  arr;
    List<Object> listPageable = Pageable.getPage(arr.stream()
      .collect(Collectors.toList()), 1, 2);

    return new JsonArray().add(listPageable);

  }


  ///:::::::::::: LÓGICA API RESTA
  @Override
  public void handlePostResta(RoutingContext routingContext) {

  }

  @Override
  public void initialDataResta() {

  }


  @Override
  public void addResta(JsonObject calculadora) {

  }


  @Override
  public void allResta(RoutingContext routingContext) {

  }


  @Override
  public void findByIResta(RoutingContext routingContext) {

  }

}


