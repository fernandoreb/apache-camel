package farg.apache.camel.apachecamelexamples.aggregators;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.util.json.JsonObject;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;

public class JsonClientAddressAggregator implements AggregationStrategy{

	@Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        String address = newExchange.getIn().getBody(String.class);
        String client = oldExchange.getIn().getBody(String.class);
        
        JsonParser parser = JsonParserFactory.getJsonParser();
        JsonObject json = new JsonObject();
        json.put("cliente", parser.parseMap(client));
        json.put("endereco", parser.parseMap(address));
        //json.put("client", parser.parseList(client));
        
        newExchange.getIn().setBody(json.toJson());
        return newExchange;
    }
	
}

