package farg.apache.camel.apachecamelexamples.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class RestRoute extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    
	//configurações do servidor como endereço de host e porta
    restConfiguration()
      .host("0.0.0.0").port(8080)
      .bindingMode(RestBindingMode.auto);

    //inicia delaração dos serviços REST
    rest("/integration")
      //API retorna todos os clientes
      .get("/clients")
        .route().routeId("rest-all-clients")
        .to("direct:call-rest-all")
      .endRest()

      //API retorna cliente por CPF
      .get("/clients/cpf/{cpf}")
        .route().routeId("rest-client-by-cpf")
        .to("direct:call-rest-client-cpf")
      .endRest()
    
      //API retorna endereço por cpf
      .get("/clients/address/{cpf}")
        .route().routeId("rest-address-by-cpf")
        .to("direct:call-rest-address-cpf")
      .endRest()
    
      //API de cliente+endereço
      .get("/clients-address/{cpf}")
      	.route().routeId("rest-client-address-by-cpf")
      	.to("direct:call-rest-client-address-cpf")
      .endRest();
    
    
  }
}

