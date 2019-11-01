package PLA5_Actividad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*clase Palabras y Cesar (Beans 1º y 2º) como valores para el constructor de
  Codificador (Bean 3º)*/
@Configuration
public class ConfigPalabrasCesar {
  @Bean
  public IProcesar procesar() {
    return new Palabras();
  }
  @Bean
  public ICodificar codificar() {
    return new Cesar();
  }
  
  @Bean
  public Codificador codificador() {
    return new Codificador(procesar(), codificar());
  }
}