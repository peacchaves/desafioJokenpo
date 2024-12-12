package acc.br.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"acc.br.demo", "acc.br.projetoquatro"})
public class ProjetoQuatroApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProjetoQuatroApplication.class, args);
    }
}