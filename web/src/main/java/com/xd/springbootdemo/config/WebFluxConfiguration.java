package com.xd.springbootdemo.config;

import com.xd.springbootdemo.domain.User;
import com.xd.springbootdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Configuration
public class WebFluxConfiguration {

    @Autowired
    public RouterFunction<ServerResponse> routerFunctionUsers(UserRepository userRepository) {
        Collection<User> user = userRepository.findAll();
        Flux<User> userFlux = Flux.fromIterable(user);

        Mono<Collection<User>> userMono = Mono.just(user);

        return RouterFunctions.route(RequestPredicates.path("/users"),
                serverRequest -> ServerResponse.ok().body(userFlux, User.class));
    }

}
