package PLA5_Actividad;

import java.util.ArrayList;

public interface IProcesar {
  /*Estos son dos valores constantes "invisibles" que usaré para cuando quite o
    ponga los acentos o diéresis*/
  static final String INVISIBLE1 = Character.toString((char)0x200C); //8204
  static final String INVISIBLE2 = Character.toString((char)0x200B); //8203
  
  public ArrayList<String> dividir(String cadena);
  public String unir(ArrayList<String> cadenas);
}