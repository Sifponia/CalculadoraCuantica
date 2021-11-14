package ihandleContextRouter;

import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public interface IHandleRouter {

  void addCalculadora(JsonObject calculadora);

  void sendError(int statusCode, HttpServerResponse response);

  void initialData();

  Future<Void> allCalculadoraFuture(RoutingContext routingContext);

  Future<Void> handleGetCalculadoraFuture(RoutingContext routingContext);

  void handlePostCalculadora(RoutingContext routingContext);

  void handlePutCalculadora(RoutingContext routingContext);

  void handleDeleteCalculadora(RoutingContext routingContext);


}

