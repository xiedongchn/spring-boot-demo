package com.xd.springboot.product.config;

import com.xd.springboot.product.domain.UserDO;
import com.xd.springboot.product.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Set;

/**
 * @author xd
 */
@Configuration
public class WebFluxConfiguration {

    @Autowired
    public RouterFunction<ServerResponse> routerFunctionUsers(UserRepository userRepository) {
        Set<UserDO> userDO = userRepository.findAll();
        Flux<UserDO> userFlux = Flux.fromIterable(userDO);

        Mono<Collection<UserDO>> userMono = Mono.just(userDO);

        return RouterFunctions.route(RequestPredicates.path("/users"),
                serverRequest -> ServerResponse.ok().body(userFlux, UserDO.class));
    }

}
