package com.tienda.stc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import config.JWTAuthorizationFilter;

@EnableScheduling
@SpringBootApplication
public class StcApplication {

	public static void main(String[] args) {
		SpringApplication.run(StcApplication.class, args);
	}
	
	@Configuration
	@EnableWebSecurity //Configuracion de seguridad personalizada
	public class WebSecurityConfig {
	    @Value("${jwt.secret.key}")//clave secreta que validara los jwt
	    private String secret;
	    
	    @Bean//Configuracion de reglas de seguridad
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http.cors().and().csrf().disable()
	            .addFilterAfter(new JWTAuthorizationFilter(secret), UsernamePasswordAuthenticationFilter.class)
	            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	            .and().authorizeRequests()//Esta define que rutas son publicas y cuales son protegidas
	            .requestMatchers(HttpMethod.POST, "/api/usuarios/login").permitAll() 
	            .requestMatchers(HttpMethod.POST, "/api/usuarios/login/seleccionar-rol").permitAll() 
	            .requestMatchers(HttpMethod.GET, "/api/productos/listaProductos").permitAll() // 👈 Hacés pública esta ruta
	            .requestMatchers(HttpMethod.GET, "/api/productos/imagenes/{nombreImagen:.+}").permitAll() // 👈 Hacés pública esta ruta
	            .requestMatchers(HttpMethod.GET, "/{imageName:.+}").permitAll()//Solicitud get, imagenes o otros archivos estaticos
	            .anyRequest().authenticated(); //rewuiere autenticacion para otras solicitudes
	        return http.build();
	    }
	}
	


}
