package com.practica.alumno.utilities;

public class DataType
{
  protected Integer parametroInteger = Integer.valueOf(0);
  protected Float parametroFloat = Float.valueOf(0.0F);
  protected String parametroString = "";
  protected Byte parametroByte = Byte.valueOf((byte)0);
  protected Short parametroShort = Short.valueOf((short)0);
  protected Long parametroLong = Long.valueOf(0L);
  protected Double parametroDouble = Double.valueOf(0.0D);
  protected Boolean parametroBoolean = Boolean.valueOf(false);
  protected Character parametroCharacter = Character.valueOf(' ');
  public final int PARAMETRO_Integer = 1;
  public final int PARAMETRO_Float = 2;
  public final int PARAMETRO_String = 3;
  public final int PARAMETRO_Byte = 4;
  public final int PARAMETRO_Short = 5;
  public final int PARAMETRO_Long = 6;
  public final int PARAMETRO_Double = 7;
  public final int PARAMETRO_Boolean = 8;
  public final int PARAMETRO_Character = 9;
  public final int PARAMETRO_SQLDataTable = 10;
  public final String PARAMETRO_SQLDataTable_SQL = "sql";
  public final String PARAMETRO_SQLDataTable_DataTable = "dataTable";
}