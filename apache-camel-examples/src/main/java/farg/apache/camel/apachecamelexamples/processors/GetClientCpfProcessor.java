package farg.apache.camel.apachecamelexamples.processors;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;

public class GetClientCpfProcessor implements Processor{
	
	@Override
	public void process(Exchange exchange) throws Exception {
		String client = exchange.getIn().getBody(String.class);
        JsonParser parser = JsonParserFactory.getJsonParser();
        Map<String, Object> jsonMap = parser.parseMap(client);
        String clientCpf = (String) jsonMap.get("cpf");
    
        exchange.getIn().setHeader("cpf", clientCpf);
        exchange.getIn().setBody(client);
	}
}
