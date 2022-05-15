package br.com.erudio.filter;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import br.com.erudio.exception.StandardError;
import br.com.erudio.util.JwtUtil;
import io.jsonwebtoken.Claims;
import reactor.core.publisher.Flux;
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

			if (jwtToken == null)
				return this.onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);

			return this.onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);
			// return chain.filter(exchange);
		};
	}

	public static class Config {
		// Put the configuration properties
	}

	/* PRIVATE */

	private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
		StandardError error = new StandardError(System.currentTimeMillis(),
				HttpStatus.UNAUTHORIZED.value(), "Não Autorizado", err,
				"");
		
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);
		response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
		byte[] bytes = error.toString().getBytes(StandardCharsets.UTF_8);
		DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);

		return response.writeWith(Mono.just(buffer));
	}

	private String getAuthHeader(ServerHttpRequest request) {
		return request.getHeaders().getOrEmpty("Authorization").get(0);
	}

}