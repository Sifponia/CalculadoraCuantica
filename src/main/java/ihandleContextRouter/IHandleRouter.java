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
  void historico(RoutingContext routingContext);

  //Buscar Por Id
  void findById(RoutingContext routingContext);




}

