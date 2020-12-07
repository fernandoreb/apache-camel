package farg.apache.camel.apachecamelexamples.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import farg.apache.camel.apachecamelexamples.aggregators.JsonClientAddressAggregator;
import farg.apache.camel.apachecamelexamples.processors.GetClientCpfProcessor;

@Component
public class RestAggregatorRoute extends RouteBuilder {

	private static final int OK_CODE = 200;
	private static final int APP_RESPONSE_CODE = 204;
	
	@Override
	public void configure() throws Exception {
		
		from("direct:call-rest-all")
			.routeId("all-service")
			.removeHeaders("CamelHttp*")
			.setHeader(Exchange.HTTP_METHOD, constant("GET"))
		.to("http://{{clients.url}}/clients");	
		
		from("direct:call-rest-client-cpf")
			.routeId("cpf-client-service")
			.removeHeaders("CamelHttp*")
			.setHeader(Exchange.HTTP_METHOD, constant("GET"))
		.toD("http://{{clients.url}}/clients/cpf/${header.cpf}");	
		
		from("direct:call-rest-address-cpf")
			.routeId("cpf-address-service")
			.removeHeaders("CamelHttp*")
			.setHeader(Exchange.HTTP_METHOD, constant("GET"))
		.toD("http://{{clients.url}}/clients/endereco/${header.cpf}");
		
		from("direct:call-rest-client-address-cpf")
			.routeId("client-address-cpf")
			.to("direct:call-rest-client-cpf")
			.choice()
			.when(header(Exchange.HTTP_RESPONSE_CODE).isEqualTo(OK_CODE))
				.process(new GetClientCpfProcessor())
				.enrich("direct:call-rest-address-cpf", new JsonClientAddressAggregator())
		.otherwise()
			.setHeader(Exchange.HTTP_RESPONSE_CODE).constant(APP_RESPONSE_CODE);
	}
}
