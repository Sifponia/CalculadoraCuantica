package calculos;

public interface IOperacionAritmetica {
  double suma(double x, double y);

  double resta(double x, double y);

  double division(double x, double y);

  double multiplicacion(double x, double y);

  double operacionAritmetica(double x, double y, String operacion);
}
