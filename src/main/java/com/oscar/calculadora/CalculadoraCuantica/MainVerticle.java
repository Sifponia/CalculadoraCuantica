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
import util.Varios;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class MainVerticle extends AbstractVerticle {

  private static final Logger LG = LoggerFactory.getLogger(MainVerticle.class);
  private ImpHandleRouterService routerService = new ImpHandleRouterService();




  private final int PORT = 65535;






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

    router.route().handler(CorsHandler.create("http://localhost:4200").allowedHeaders(allowedHeaders).allowedMethods(allowedMethods));
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
      this.routerService.handlePutCalculadora(context);
    });


    //Server
    vertx.createHttpServer().requestHandler(router).listen(this.PORT);

    //LOG
    System.out.println(Varios.vertexMensaje());
    LG.info(" -- Server Port -- {} \uD83D\uDE80 \uD83D\uDE80 \uD83D\uDC4D \uD83D\uDC4D ", PORT,
      MainVerticle.class.getName());



    /*



     */

  }
}


/*

openapi: 3.0.0
info:
  version: 1.0.0
  title: Swagger Petstore
  license:
    name: MIT
servers:
  # Added by API Auto Mocking Plugin
  - description: SwaggerHub API Auto Mocking
    url: https://virtserver.swaggerhub.com/Sifponia/Pet/1.0.0
  - url: http://localhost:8080
paths:
  /pets:
    get:
      summary: List all pets
      operationId: listPets
      tags:
        - pets
      parameters:
        - name: limit
          in: query
          description: How many items to return at one time (max 100)
          required: false
          schema:
            type: integer
            format: int32
      responses:
        '200':
          description: An paged array of pets
          headers:
            x-next:
              description: A link to the next page of responses
              schema:
                type: string
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Pets'
        default:
          description: unexpected error
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Error'
    post:
      summary: Create a pet
      operationId: createPets
      tags:
        - pets
      requestBody:
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/Pet'
      responses:
        '201':
          description: Null response
        default:
          description: unexpected error
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Error'
  /pets/{petId}:
    get:
      summary: Info for a specific pet
      operationId: showPetById
      tags:
        - pets
      parameters:
        - name: petId
          in: path
          required: true
          description: The id of the pet to retrieve
          schema:
            type: string
      responses:
        '200':
          description: Expected response to a valid request
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Pets'
        default:
          description: unexpected error
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Error'
components:
  schemas:
    Pet:
      type: object
      required:
        - id
        - name
      properties:
        id:
          type: integer
          format: int64
        name:
          type: string
        tag:
          type: string
    Pets:
      type: array
      items:
        $ref: '#/components/schemas/Pet'
    Error:
      type: object
      required:
        - code
        - message
      properties:
        code:
          type: integer
          format: int32
        message:
          type: string
 */


//***************** V1.0.0 YAML


/*
openapi: 3.0.0
#-----
info:
  title: Calculadora
  description: calculadora
  version: 1.0.0
  termsOfService: https://es.memedroid.com/tos
  #-----
  contact:
    email: otabora@outlook.es
  #-----
  license:
    name: CalcuCuantica Gúgol
    url: https://www.facebook.com/legal/terms
  #-----
servers:
  # Vert.X
  - description: Vertx
    url: http://localhost:9090/calculadora
  #-----
tags:
  - name: calculadora
    description: Devuelve todos los valores de las operaciones de la
      calculadora
  #-----
paths:
  /calculadora:
    get:
      summary: Lista de operaciones aritmeticas
      operationId: listaCalculadora
      tags:
        - calculadora
      parameters:
        - name: limit
          in: query
          description: How many items to return at one time (max 100)
          required: false
          schema:
            type: integer
            format: int32
      responses:
        '200':
          description: An paged array of pets
          headers:
            x-next:
              description: A link to the next page of responses
              schema:
                type: string
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Calculadora'
        default:
          description: unexpected error
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Error'
    post:
      summary: Recibe dos valores para realizar un operacion         aritmetica.
      operationId: creatCalculadora
      tags:
        - calculadora
      requestBody:
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/Calculadora'
      responses:
        '201':
          description: Null response
        default:
          description: unexpected error
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Error'
  /calculadora/{calculadoraId}:
    get:
      summary: Devuelve una operacion de la BBDD
      operationId: mostrarPorId
      tags:
        - calculadora
      parameters:
        - name: calculadoraId
          in: path
          required: true
          description: The id of the pet to retrieve
          schema:
            type: string
      responses:
        '200':
          description: Expected response to a valid request
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Pets'
        default:
          description: unexpected error
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/Error'
components:
  schemas:
    Calculadora:
      type: object
      required:
        - numeroOne
        - numerTwo
        - result
      properties:
        id:
          type: integer
          format: int64
          description: Id de identificación
        numeroOne:
          type: number
          format: double
          description: Recibe un primer numero
        numerTwo:
          type: number
          format: double
          description: Recibe un segundo numero
        result:
          type: number
          format: double
          description: Guarda el resultado de la operacion
        context:
          type: object
          format: double
          description: Guarda es esquema de la documentación de la Ap
    Pets:
      type: object
      items:
        $ref: '#/components/schemas/Calculadora'
    Error:
      type: object
      required:
        - code
        - message
      properties:
        code:
          type: integer
          format: int32
        message:
          type: string





 */
