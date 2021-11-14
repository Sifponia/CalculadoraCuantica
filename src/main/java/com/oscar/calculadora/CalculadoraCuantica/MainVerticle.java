package com.oscar.calculadora.CalculadoraCuantica;


import calculadora.CalculadoraModel;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.openapi.RouterBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ImpHandleRouterService;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class MainVerticle extends AbstractVerticle {

  private static final Logger LG = LoggerFactory.getLogger(MainVerticle.class);
  private ImpHandleRouterService routerService = new ImpHandleRouterService();
  private final int PORT = 9090;
  public static Vertx vertx = Vertx.vertx();

  public static void main(String[] args) {
    vertx.deployVerticle(new MainVerticle());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    //Cargo los datos guardado en memoria
    routerService.initialData();


    //Router
    Router router = Router.router(vertx);


    //Cors
    Set<String> allowedHeaders = new HashSet<>();
    allowedHeaders.add("x-requested-with");
    allowedHeaders.add("Access-Control-Allow-Origin");
    allowedHeaders.add("origin");
    allowedHeaders.add("Content-Type");
    allowedHeaders.add("accept");
    allowedHeaders.add("X-PINGARUNER");

    Set<HttpMethod> allowedMethods = new HashSet<>();
    allowedMethods.add(HttpMethod.GET);
    allowedMethods.add(HttpMethod.POST);
    allowedMethods.add(HttpMethod.OPTIONS);

    allowedMethods.add(HttpMethod.DELETE);
    allowedMethods.add(HttpMethod.PATCH);
    allowedMethods.add(HttpMethod.PUT);

    router.route().handler(CorsHandler.create("*").allowedHeaders(allowedHeaders).allowedMethods(allowedMethods));
    //End Cors
    router.route().handler(BodyHandler.create()); //::: Segunda opción sin usar cors


    //GetHome
    router.get("/").handler(a -> {
      a.response()
        .putHeader("content-type", "text/html")
        .end("<ul>\n" +
          "    <li><a href=\"http://localhost:\n" + this.PORT + "/calculadora\">Lista Operaciones de Calculadora</a></li>\n" +
          "    <li><a href=\"http://localhost:\n" + this.PORT + "/apiCalculadora\">APi Calculadora</a></li>\n" +
          "</ul>");
    });


    //ApiCalculadora
    AtomicReference<JsonObject> calculadoraApi = new AtomicReference<JsonObject>();
    RouterBuilder.create(this.vertx, "src/main/resources/calculadora.yaml")
      .onFailure(Throwable::printStackTrace)
      .onSuccess(routerBuilder -> {
        calculadoraApi.set(routerBuilder.getOpenAPI().getOpenAPI());
        router.get("/apiCalculadora").respond(ctx -> Future.succeededFuture(calculadoraApi));
      });


    //GetList
    router.get("/calculadora").handler(context -> {
      this.routerService.allCalculadoraFuture(context);


    });


    //GetCalculadora
    router.get("/calculadora/:id").handler(context -> {
      this.routerService.handleGetCalculadoraFuture(context);

    });


    //PostCalculadora
    router.post("/calculadora").handler(context -> {
      this.routerService.handlePostCalculadora(context);


    });

    //DeleteCalculadora
    router.delete("/calculadora/:id").handler(context -> {
      this.routerService.handleDeleteCalculadora(context);
    });

    //PutCalculadora
    router.put("/calculadora/:id").handler(context -> {
      this.routerService.handlePutCalculadoratest(context);
    });


    //Server
    vertx.createHttpServer().requestHandler(router).listen(this.PORT);
    LG.info("Server: {}", PORT, MainVerticle.class.getName());

  }
}


