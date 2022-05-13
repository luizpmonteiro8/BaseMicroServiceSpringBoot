package br.com.erudio.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {
   public CustomFilter() {
         super(Config.class);
    }

   @Override
   public GatewayFilter apply(Config config) {
	return (exchange, chain) -> {
	System.out.println("First pre filter" + exchange.getRequest().getHeaders());
        //Custom Post Filter.Suppose we can call error response handler based on error code.
	//return chain.filter(exchange).then(Mono.fromRunnable(() -> {
	//			System.out.println("First post filter");
//		    }));
	return chain.filter(exchange);
	//return this.onError(exchange, "Authorization header is missing in request", HttpStatus.ACCEPTED);
	    };
    }

    public static class Config {
	 // Put the configuration properties
    }
    
    /*PRIVATE*/

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    

   
    
}