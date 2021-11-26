package proxy;



import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpServer;
import io.vertx.core.net.ProxyOptions;
import io.vertx.core.net.SocketAddress;
import io.vertx.httpproxy.HttpProxy;
import io.vertx.httpproxy.ProxyRequest;

/**
 * @author <a href="mailto:emad.albloushi@gmail.com">Emad Alblueshi</a>
 */

public class MainVerticle {

  public void origin(Vertx vertx) {
    HttpServer originServer = vertx.createHttpServer();

    originServer.requestHandler(req -> {
      req.response()
        .putHeader("content-type", "text/html")
        .end("<html><body><h1>I'm the target resource!</h1></body></html>");
    }).listen(7070);
  }

  public void proxy(Vertx vertx) {
    HttpClient proxyClient = vertx.createHttpClient();

    HttpProxy proxy = HttpProxy.reverseProxy(proxyClient);
    proxy.origin(7070, "origin");

    HttpServer proxyServer = vertx.createHttpServer();

    proxyServer.requestHandler(proxy).listen(8080);
  }

  public void more(Vertx vertx, HttpClient proxyClient) {
    HttpProxy proxy = HttpProxy.reverseProxy(proxyClient).originSelector(
      address -> Future.succeededFuture(SocketAddress.inetSocketAddress(7070, "origin"))
    );
  }

  public void lowLevel(Vertx vertx, HttpServer proxyServer, HttpClient proxyClient) {

    proxyServer.requestHandler(outboundRequest -> {
      ProxyRequest proxyRequest = ProxyRequest.reverseProxy(outboundRequest);

      proxyClient.request(proxyRequest.getMethod(), 8080, "origin", proxyRequest.getURI())
        .compose(proxyRequest::send)
        .onSuccess(proxyResponse -> {
          // Send the proxy response
          proxyResponse.send();
        })
        .onFailure(err -> {
          // Release the request
          proxyRequest.release();

          // Send error
          outboundRequest.response().setStatusCode(500)
            .send();
        });
    });
  }


}


/*


openapi: 3.0.0
servers:
  - description: Proxy Server
    url: localhost:8080
info:
  description: Simple Web Cliente
  version: "1.0.0"
  title: Proxy Server
tags:
  - name: Web Client
    description: Web Client
paths:
  '/web':
    post:
      tags:
        - Web Client
      summary: Valores Web Client
      operationId: webClienId
      description:  Valores Web Client
      responses:
        '200':
          description: return token string.
        '400':
          description: 'invalid input, object invalid'
      requestBody:
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/Proxy'
        description: Pass proxy object
components:


 schemas:
    Proxy:
      type: object
      properties:
        host:
          type: string
          example: localhost
          format: hostname
        port:
          type: integer
          example: 12323
          minimum: 0
          maximum: 65535
          minLength: 0
          maxLength: 5
        ssl:
          type: boolean
          example: true
        uri:
          type: string
          example: /pepinos
          format: uri
 */
