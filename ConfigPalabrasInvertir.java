package PLA5_Actividad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*clase Palabras e Invertir (Beans 1º y 2º) como valores para el constructor de
  Codificador (Bean 3º)*/
@Configuration
public class ConfigPalabrasInvertir {
  @Bean
  public IProcesar procesar() {
    return new Palabras();
  }
  @Bean
  public ICodificar codificar() {
    return new Invertir();
  }
  
  @Bean
  public Codificador codificador() {
    return new Codificador(procesar(), codificar());
  }
}