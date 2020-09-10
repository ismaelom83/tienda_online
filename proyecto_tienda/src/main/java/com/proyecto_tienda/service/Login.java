package com.proyecto_tienda.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.metamodel.SetAttribute;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.proyecto_tienda.repository.PersonaRepo;

@Service
@Transactional
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Login implements UserDetailsService{

	
	@Autowired
	PersonaRepo rePer;
	@Autowired
	HttpSession session;
	
	
	@SuppressWarnings("null")
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// guardamos en una variable el nombre del usuario de la base de datos cuando
		// esta cargada la sesion el login lo hace por atras spring
		// y lanzamos la excepcion por si el nombre de usuario no existe.
		com.proyecto_tienda.model.Persona cargarSesionUsuario = rePer.findBymail(email)
				.orElseThrow(() -> new UsernameNotFoundException("El Login Del Usuario Es Invalido."));
		List<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority(cargarSesionUsuario.getTipoPersona()));
		// cargamos el usuario en la sesion gracias al objeto UserDetails de spring
		// (guardamos en la sesiion el nombre de usuario(que nos lo da el metodo
		// findByUsername)
		// y el password (al crear el objeto aplicacionusuario nos tare la password de
		// ese usuario))
		session.setAttribute("nombre", cargarSesionUsuario);
		UserDetails persona = (UserDetails) new User(email, cargarSesionUsuario.getPass(), true, true, true, true, roles);

		return persona;
		
		
	}

}
