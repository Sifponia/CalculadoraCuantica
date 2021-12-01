package daoCalculadora;

import suma.Suma;

import java.util.List;

public interface DaoCalculadora {

  public void post(Suma suma) throws Exception;

  public List<Suma> getHistorico(Suma suma);

  public void findById(Suma suma);

}
