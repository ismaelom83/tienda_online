package com.proyecto_tienda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{
	
	// areglo donde nombramos las carpetas que en el siguiente metodo le damos
		// permiso para entrar si no no nos cargaria el js nilos css ni las imagenes
		String[] resources = new String[] { "/css/**", "/img/**", "/js/**", "/DOC/**" };

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
			// cualquiera que tenga la ruta resources tiene aceso
			.antMatchers(resources).permitAll()
			// cualquiera tiene acceso al index principal
			.antMatchers("/", "/index", "/registro","/prueba","/app/carrito/{id}","/app/productos/{id}","/controllerFiltro","/controllerFiltroClientes","/borrarCarrito/{id}").permitAll()
			// cualquier request necesitara autentificacion
			.anyRequest().authenticated().and().formLogin()
			// esta es la pagina que utilizamos en el login que esta permitida la entrada a
			// todo el mundo.
			.loginPage("/login").permitAll()
			// nos redirige ala lista al logearnos con exito
			.defaultSuccessUrl("/logeos")
			// si falla el login nos muestra error
			.failureUrl("/login?error=verdad")
			// estos parametros se corrsponden a los campos name de nuestro formulario de
			// login
			.usernameParameter("email").passwordParameter("password")
			// y con esta ultima instruccion le decimos que cuando pulsemos el logout nos
			// cierre la sesion y nos redigira al login
			.and().csrf().disable().logout().permitAll().logoutSuccessUrl("/login?logout");
		}
		
		// clase de spring security para descifrar o cifrar la contraseña (de momento la
		// he cifrado desde su pagina web
		// https://www.dailycred.com/article/bcrypt-calculator
		// mas adelante habra que incorporarlo en la aplicacion)
		BCryptPasswordEncoder passwordDescifrada;
		
		@Bean
		public BCryptPasswordEncoder passwordEncoder() {
			// el numero 4 indica la longitud de la contraseña.
			passwordDescifrada = new BCryptPasswordEncoder();
			return passwordDescifrada;
		}
		
		
		// hacemos uso de la interface userdetailsservice que se utiliza para cargar los
		// datos del usuario y implementa el metodoAuthenticationManagerBuilder
		@Autowired
		UserDetailsService userDetailsService;
		
		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		}
		
		

}
