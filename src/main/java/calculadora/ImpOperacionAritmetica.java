package calculadora;

public class ImpOperacionAritmetica implements IOperacionAritmetica {
  private double result = 0;


  @Override
  public double suma(double x, double y) {
    result = x + y;
    return bigDecimal(result);


  }

  @Override
  public double resta(double x, double y) {
    result = x - y;
    return bigDecimal(result);
  }

  @Override
  public double division(double x, double y) {
    result = x / y;
    return bigDecimal(result);
  }

  @Override
  public double multiplicacion(double x, double y) {
    result = x * y;
    return bigDecimal(result);
  }


  public double bigDecimal(double number) {
    return (double) Math.round(number * 1000d) / 1000d;


  }

  @Override
  public double operacionAritmetica(double x, double y, String operacion) {
    switch (operacion) {
      case "+":
        return suma(x, y);
      case "-":
        return resta(x, y);
      case "*":
        return multiplicacion(x, y);
      case "/":
        return division(x, y);
      default:
        return 404;
    }


  }

}
