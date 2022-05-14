package br.com.erudio.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import br.com.erudio.util.JwtUtil;
import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	public AuthenticationFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			String token = getAuthHeader(exchange.getRequest());
			
			 if (token == null || token.isEmpty())
	                return this.onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);
			 
			 Claims jwtToken = jwtUtil.getClaims(token);
			 
			 if (jwtToken == null) return this.onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);
			
			
			return chain.filter(exchange);
			
		
			
			

			// Custom Post Filter.Suppose we can call error response handler based on error
			// code.
		//	 return chain.filter(exchange).then(Mono.fromRunnable(() -> {
	
			 
//				 System.out.println("First post filter");
	//	    }));
			
			// return this.onError(exchange, "Authorization header is missing in request",
			// HttpStatus.ACCEPTED);
		};
	}

	public static class Config {
		// Put the configuration properties
	}

	/* PRIVATE */

	private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
		
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);
		response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
		return response.setComplete();
	}

	private String getAuthHeader(ServerHttpRequest request) {
		return request.getHeaders().getOrEmpty("Authorization").get(0);
	}


}