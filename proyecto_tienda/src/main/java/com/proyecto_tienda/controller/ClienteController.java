package com.proyecto_tienda.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyecto_tienda.dto.ChangePasswordForm;
import com.proyecto_tienda.model.CabeceraPedido;
import com.proyecto_tienda.model.CategoriasMenu;
import com.proyecto_tienda.model.Cliente;
import com.proyecto_tienda.model.DetallePedido;
import com.proyecto_tienda.model.Persona;
import com.proyecto_tienda.model.Producto;
import com.proyecto_tienda.model.ValoracionesProducto;
import com.proyecto_tienda.repository.PersonaRepo;
import com.proyecto_tienda.repository.ValoracionesProductoRepo;
import com.proyecto_tienda.service.CabeceraPedidoService;
import com.proyecto_tienda.service.CategoriaMenuService;
import com.proyecto_tienda.service.ClienteService;
import com.proyecto_tienda.service.DetallePedidoService;
import com.proyecto_tienda.service.Login;
import com.proyecto_tienda.service.ProductosService;
import com.proyecto_tienda.service.TrabajadoreService;
//import javax.validation.Valid;
import com.proyecto_tienda.service.ValoracionesProductosService;

@Controller
public class ClienteController {

	static Logger logger = Logger.getLogger(ClienteController.class);

	@Autowired
	ProductosService pService;

	@Autowired
	Login login;

	@Autowired
	CabeceraPedidoService caSer;

	@Autowired
	DetallePedidoService deSer;

	@Autowired
	TrabajadoreService traSer;

	@Autowired
	ClienteService cliService;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	CategoriaMenuService catService;

	@Autowired
	ValoracionesProductoRepo valoracionesRepoInterface;

	@Autowired
	ValoracionesProductosService valoService;
	
	@Autowired
	PersonaRepo personaRepoInterface;

	@GetMapping({ "/", "index" })
	public String index(Model model) {
		ArrayList<CategoriasMenu> listaCategorias = null;
		try {
			listaCategorias = catService.buscarCategoria();
		} catch (Exception e1) {
			logger.error("Lista de productos no encontrada");
			e1.printStackTrace();
		}
		model.addAttribute("listaCategorias", listaCategorias);
		try {
			model.addAttribute("listaProductos", pService.buscarTodosProductos());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Lista de productos no encontrada");
		}
		return "index";
	}

	@GetMapping("/controllerFiltro")
	public String filtro(@RequestParam(name = "categoria") String categoria, Model model) {
		List<Producto> filtroProducto = null;
		if (categoria != null) {
			try {
				filtroProducto = pService.buscarPructoCaregoria(categoria);
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("Categoria no encontrada");
			}
		}
		model.addAttribute("listaProductos", filtroProducto);
		return "index";

	}

	@GetMapping("/login")
	public String login(Model model, Persona persona) {
		model.addAttribute("persona", new Persona());
		String tipo="CN";
		model.addAttribute("tipoPersona", tipo);
		return "app/login";
	}

	@GetMapping("/registro")
	public String registro(Model model, Persona persona) {
		model.addAttribute("persona", new Persona());
		return "app/registro";
	}

	@GetMapping("/logeos")
	public String logeos(@ModelAttribute Persona persona, Model model, @AuthenticationPrincipal User currentUser,
			HttpSession session) {
		persona = (Persona) session.getAttribute("nombre");
		model.addAttribute("nombre", persona);
		Collection<GrantedAuthority> u = currentUser.getAuthorities();
		for (GrantedAuthority grantedAuthority2 : u) {
			if (grantedAuthority2.getAuthority().contains("CN") || grantedAuthority2.getAuthority().contains("CP")) {
				model.addAttribute("usuario", currentUser.getUsername());
//				model.addAttribute("listaProductos", pService.buscarTodosProductos());
				logger.info("cliente logeado con exito: " + persona.getNombre());
				return "redirect:/clientes";
			} else if (grantedAuthority2.getAuthority().contains("TV")) {
				model.addAttribute("usuario", currentUser.getUsername());
				logger.info("Trabajador ventas logeado con exito: " + persona.getNombre());
				return "redirect:/trabajadorVentas";
			} else if (grantedAuthority2.getAuthority().contains("TC")) {
				model.addAttribute("usuario", currentUser.getUsername());
				logger.info("Trabajador Compras logeado con exito: " + persona.getNombre());
				return "redirect:/trabajadorCompras";
			} else if (grantedAuthority2.getAuthority().contains("AD")) {
				model.addAttribute("usuario", currentUser.getUsername());
				logger.info("Administrador logeado con exito: " + persona.getNombre());
				return "redirect:/administrador";
			} else {
				logger.warn("login erroneo: ");
				return "app/login";
			}

		}
		logger.warn("login erroneo: ");
		return "app/login";

	}

	@SuppressWarnings("unchecked")
	@GetMapping("/clientes")
	public String productos2(Persona persona, Model model, HttpSession session, Producto producto) {
		ArrayList<CategoriasMenu> listaCategorias = null;
		try {
			listaCategorias = catService.buscarCategoria();
		} catch (Exception e1) {
			logger.error("Lista de productos no encontrada");
			e1.printStackTrace();
		}
	
		model.addAttribute("listaCategorias", listaCategorias);
		persona = (Persona) session.getAttribute("nombre");
		model.addAttribute("nombre", persona);
		model.addAttribute("passwordForm",new ChangePasswordForm(persona.getId()));
		try {
			model.addAttribute("listaProductos", pService.buscarTodosProductos());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Lista de productos no encontrada: " + persona.getNombre());
		}
		return "app/clientes";

	}

	@SuppressWarnings("unchecked")
	@GetMapping("/app/carrito/{id}")
	public String carrito(@PathVariable Long id, @RequestParam(name = "cantidad") String cantidad, Model model,
			HttpSession session, Producto producto) {

		int sumaTotal = 0;
		try {
			producto = pService.buscarProductoId(id);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Productos no encontrado");
		}
		producto.setCantidad(Integer.parseInt(cantidad));
		ArrayList<Producto> listaCarrito = null;
		listaCarrito = (ArrayList<Producto>) session.getAttribute("carrito");
		if (listaCarrito == null) {
			listaCarrito = new ArrayList<>();
			session.setAttribute("carrito", listaCarrito);
		}

		if (listaCarrito != null) {
			listaCarrito.add(producto);
			logger.info("producto añadido al carrito con exito");
		}

		for (Producto pro : listaCarrito) {
			sumaTotal += pro.getPrecioUnitarioSinIva() * pro.getCantidad();
		}
		session.setAttribute("sumaTotal", sumaTotal);
		sumaTotal = (int) session.getAttribute("sumaTotal");
		model.addAttribute("sumaTotal", sumaTotal);
		model.addAttribute("carrito", listaCarrito);
		

		return "app/carrito";

	}

	@SuppressWarnings("unchecked")
	@GetMapping("/carrito")
	public String carrito2(Model model, HttpSession session, Producto producto) {

		ArrayList<Producto> listaCarrito = null;
		listaCarrito = (ArrayList<Producto>) session.getAttribute("carrito");
//		int sumaTotal = (int) session.getAttribute("sumaTotal");
		model.addAttribute("carrito", listaCarrito);
//		model.addAttribute("sumaTotal", sumaTotal);

		return "app/carrito";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/borrarCarrito/{id}")
	public String borrarCarrito(@PathVariable Long id, Producto producto, HttpSession session, Model model) {

		ArrayList<Producto> listaCarrito = null;
		listaCarrito = (ArrayList<Producto>) session.getAttribute("carrito");
		for (Producto producto2 : listaCarrito) {
			if (producto2.getId() == id) {
				producto = producto2;
			}
		}

		if (producto != null) {
			listaCarrito.remove(producto);
		}
		session.setAttribute("carrito", listaCarrito);
		listaCarrito = (ArrayList<Producto>) session.getAttribute("carrito");
		model.addAttribute("carrito", listaCarrito);
		Persona persona = (Persona) session.getAttribute("nombre");
		logger.info("Carrito borrado con exito");
		return "app/carrito";

	}

	@PostMapping("/detallePedido")
	public String detallePedido(Model model, HttpSession session, Persona persona, CabeceraPedido cabecera) {

		persona = (Persona) session.getAttribute("nombre");
		model.addAttribute("nombre", persona);
		@SuppressWarnings("unchecked")
		ArrayList<Producto> listaCarrito = (ArrayList<Producto>) session.getAttribute("carrito");
		for (Producto producto : listaCarrito) {
			try {
				deSer.actualizarStock(producto.getId(), producto.getCantidad());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		model.addAttribute("listaCarrito", listaCarrito);
		int sumaTotal = (int) session.getAttribute("sumaTotal");
		model.addAttribute("sumaTotal", sumaTotal);
		persona = (Persona) session.getAttribute("nombre");
		System.out.println("esto es el id de la persona y el cliente: " + persona.getId());
		try {
			caSer.insertCabeceraPedido(persona.getId(), sumaTotal);
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.warn("pedido no realizado");
		}

		try {
			cabecera = caSer.consultaUltimoIdCabecera();
		} catch (IOException e) {
			e.printStackTrace();
			logger.warn("id de cliente no encontrado");
		}
		System.out.println("esto es el id de la cabecera " + cabecera.getId());
		for (Producto producto : listaCarrito) {
			try {
				deSer.insertDetallePedido(cabecera.getId(), producto.getId(), producto.getCantidad(),
						producto.getPrecioUnitarioSinIva() * producto.getCantidad());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		logger.info("Vista de los detalles del pedido: "+persona.getNombre());

		return "app/listaPedidoCarrito";

	}

	@GetMapping("/borrarCarrito")
	public String borrarCarrito(HttpSession session) {
		int suma = (int) session.getAttribute("sumaTotal");
		System.out.println("antes de borrar" + suma);
		session.removeAttribute("carrito");
		session.removeAttribute("sumaTotal");
		System.out.println("despues de borrar" + suma);

		return "redirect:/clientes";
	}

	@GetMapping("/misPedidos")
	public String mostrarCabecera(HttpSession session, Model model, Persona persona) {
		persona = (Persona) session.getAttribute("nombre");
		ArrayList<CabeceraPedido> mostrarCabecera = null;
		try {
		 mostrarCabecera = caSer.mostrarCabeceraPedido(persona.getId());		
		} catch (Exception e) {
			logger.warn("Cabecera no encontrada");
		}
		model.addAttribute("cabeceraPedido", mostrarCabecera);
		System.out.println("este ees el array list de cabecera: "+mostrarCabecera);
		return "app/misPedidos";

	}

	@PostMapping("/lineasPedido/{id}")
	public String mostrarLineasPedido(@PathVariable int id, Model model, Persona persona, HttpSession session) {
		persona = (Persona) session.getAttribute("nombre");
		model.addAttribute("nombre", persona);
		try {
			int total = 0;
			ArrayList<DetallePedido> detallePedido = deSer.mostrarLineasPedido(id);
			for (DetallePedido detallePedido2 : detallePedido) {
				total += detallePedido2.getTotalLinea();
			}
			for (DetallePedido detallePedido2 : detallePedido) {
				System.out.println("esto es el id de la cabecera producto: " + detallePedido2.getId());
			}
			model.addAttribute("lineasPedido", detallePedido);
			model.addAttribute("total", total);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "app/detallePedido";

	}

	@PostMapping("/devolucion/{id}")
	public String solicitarDevolucion(@PathVariable int id, HttpSession session, Persona persona) {
		persona = (Persona) session.getAttribute("nombre");
		ArrayList<Persona> personaLista = traSer.buscarDepartamentoVentas("TV");
		for (Persona persona2 : personaLista) {
			traSer.insertarMensajesDevolucion(persona2.getId(), persona.getId(), "devolucion", "devolucion",
					"http://localhost:8080/ControllerDevolver?id=" + id + "", 0, 0);
		}
		logger.info("Devolucion realizada con exito: " + persona.getNombre());
		return "redirect:/clientes";

	}

	@PostMapping("/registro")
	public String registroClientes(@Valid @ModelAttribute("persona") Persona persona, BindingResult resultado,
			ModelMap modelo,HttpSession session) {
		modelo.addAttribute("persona", persona);
		modelo.addAttribute("registro", true);
		System.out.println(resultado);
		if (resultado.hasErrors()) {
			modelo.addAttribute("persona", persona);
			logger.warn("Registro fallido");
			return "app/registro";
		} else {
			try {
				modelo.addAttribute("persona", persona);
				traSer.registrarPersona(persona);
				persona = cliService.consultaUltimoCliente();
				cliService.registrarClientes(persona.getId(), 1000, 1000, "normal");
			} catch (Exception e) {
				modelo.addAttribute("mensajeError", e.getMessage());
			}
		}
		logger.info("Registro realizado con exito");
		return "app/login";

	}

	@GetMapping("/controllerFiltroClientes")
	public String filtroCategorias(@RequestParam(name = "categoria") String categoria, Model model, Persona persona,
			HttpSession session) {
		persona = (Persona) session.getAttribute("nombre");
		model.addAttribute("nombre", persona);
		ArrayList<CategoriasMenu> listaCategorias = null;
		try {
			listaCategorias = catService.buscarCategoria();
		} catch (Exception e1) {
			logger.error("Lista de productos no encontrada");
			e1.printStackTrace();
		}
		model.addAttribute("listaCategorias", listaCategorias);
		List<Producto> filtroProducto = null;
		if (categoria != null) {
			try {
				filtroProducto = pService.buscarPructoCaregoria(categoria);
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("Categoria no encontrada");
			}
		}
		model.addAttribute("listaProductos", filtroProducto);
		return "app/clientes";

	}

	@GetMapping("/app/productos/{id}")
	public String productos(@PathVariable Long id, Model model, HttpSession session, Producto producto) {

		try {
			producto = pService.buscarProductoId(id);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Productos no encontrado");
		}
		session.setAttribute("producto", producto);
		producto = (Producto) session.getAttribute("producto");
		model.addAttribute("productomodel", producto);
		ArrayList<ValoracionesProducto> listaProducto = valoService.valorarProducto(id);
		model.addAttribute("listaValoraciones", listaProducto);

		for (ValoracionesProducto valoracionesProducto : listaProducto) {
			System.out.println("esto es la lista del producto" + valoracionesProducto.getNombre_persona());
		}

		return "app/productos";

	}

	@GetMapping("/valorarProducto/{id}")
	public String valorarProductoGet(@PathVariable Long id, Model model, HttpSession session, Producto producto) {
		try {
			producto = pService.buscarProductoId(id);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Productos no encontrado");
		}
		model.addAttribute("productomodel", producto);
		return "app/valorarProducto";

	}

	@PostMapping("/valorarProducto/{id}")
	public String valorarProductoPost(@PathVariable Long id, Model model, HttpSession session, Producto producto,
			Persona persona, @RequestParam("valorarDescripcion") String valorarDescripcion,
			@RequestParam("puntuacion") String puntuacion) {
		persona = (Persona) session.getAttribute("nombre");
		model.addAttribute("nombre", persona);
		ValoracionesProducto valoraciones = new ValoracionesProducto();
		valoraciones.setId_producto(id);
		valoraciones.setComentarios(valorarDescripcion);
		valoraciones.setValoracion(puntuacion);
		valoraciones.setNombre_persona(persona.getNombre());

		valoracionesRepoInterface.save(valoraciones);
		logger.info("Producto valorado con exito: " + persona.getNombre());
		return "redirect:/clientes";

	}

	@GetMapping("/verPerfil/{id}")
	public String verPerfil(@PathVariable int id, HttpSession session, Persona persona, Model model, Cliente cliente) {
		persona = (Persona) session.getAttribute("nombre");
		model.addAttribute("nombre", persona);
		System.out.println("esto es el id :"+id);
		try {
			cliente = cliService.buscarClienteId(id);
		} catch (Exception e) {
			logger.error("Cliente no encontrado");
			e.printStackTrace();
		}
		model.addAttribute("cliente", cliente);
		logger.info("Perfil visto con exito: " + persona.getNombre());
		return "app/perfilCliente";

	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping("/editarUsuario/cambiarPassword")
	public ResponseEntity cambiarPasswordPost(@Valid @RequestBody ChangePasswordForm form,Errors errors) {
		try {
			if(errors.hasErrors()) {
				String result = errors.getAllErrors()
                        .stream().map(x -> x.getDefaultMessage())
                        .collect(Collectors.joining(""));

				throw new Exception(result);
			}
			cliService.cambiarPassword(form);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok("Success");
		
	}
	

}
