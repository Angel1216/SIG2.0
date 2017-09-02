package com.anzen.bean;

public class ParametrosSP
{
  private int tipoDato;
  private String nombreParametro;
  private Object parametro;
  
  public ParametrosSP() {}
  
  public ParametrosSP(int tipoDato, String nombreParametro, Object parametro)
  {
    this.tipoDato = tipoDato;
    this.nombreParametro = nombreParametro;
    this.parametro = parametro;
  }
  
  public int getTipoDato()
  {
    return this.tipoDato;
  }
  
  public void setTipoDato(int tipoDato)
  {
    this.tipoDato = tipoDato;
  }
  
  public String getNombreParametro()
  {
    return this.nombreParametro;
  }
  
  public void setNombreParametro(String nombreParametro)
  {
    this.nombreParametro = nombreParametro;
  }
  
  public Object getParametro()
  {
    return this.parametro;
  }
  
  public void setParametro(Object parametro)
  {
    this.parametro = parametro;
  }
}
