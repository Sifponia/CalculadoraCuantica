package ihandleContextRouter;

import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public interface IHandleRouter {




  //Verbos
  void handlePostSuma(RoutingContext routingContext);

  void handlePostResta(RoutingContext routingContext);

  //Inicio Data
  void initialDataResta();

  void initialDataSuma();

  //Añadir Datos
  void addResta(JsonObject calculadora);

  void addSuma(JsonObject calculadora);

  //Obtener Historial
  void allSuma(RoutingContext routingContext);

  void allResta(RoutingContext routingContext);

  //Buscar Por Id
  void findByIdSuma(RoutingContext routingContext);

  void findByIResta(RoutingContext routingContext);


}

