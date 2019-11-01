package PLA5_Actividad;

public interface ICodificar {
  /*Estos son dos valores constantes "invisibles" que usaré para cuando quite o
    ponga los acentos o diéresis*/
  static final String INVISIBLE1 = Character.toString((char)0x200C); //8204
  static final String INVISIBLE2 = Character.toString((char)0x200B); //8203
  
  public String codificar(String cadena);
  public String decodificar (String cadena);
}