package PLA5_Actividad;

class Uti {
  final static String CABGENERAL = "ACTIVIDAD 5. SPRING ANNOTATIONS";

  static void imp(String... args) {for (String arg : args) {System.out.print(arg);}}
  static void impln(String... args) {for (String arg : args) {System.out.print(arg);} System.out.print("\n");}
  static String abc(int intN) {return Integer.toString(intN);}

  static void Subraya(int intLongitud, String strCaracter) {
    impln(new String(new char[intLongitud]).replace("\0", strCaracter));
  }  

  static void Cabecera(String strCabecera) {
    if(strCabecera.equals("")) {strCabecera = CABGENERAL;}
    impln(strCabecera);
    Subraya(strCabecera.length(),"=");
  }
}
