package com.tienda.stc.Controladores;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.stc.Clases.Empleado;
import com.tienda.stc.Clases.Cliente;
import com.tienda.stc.Clases.Usuario;
import com.tienda.stc.Clases.UsuarioRoles;
import com.tienda.stc.Clases.UsuarioRoles_pk;
import com.tienda.stc.Repositorios.UsuarioRepository;
import com.tienda.stc.Repositorios.UsuarioRolesRepository;
import com.tienda.stc.Servicios.ClienteService;
import com.tienda.stc.Servicios.EmpleadoService;
import com.tienda.stc.Servicios.UsuarioService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



@RestController
@CrossOrigin("*")
@RequestMapping("/api/usuarios")
public class UsuarioController {
	
	 @Autowired
	    private UsuarioService usuarioService;
	 @Autowired
	    private EmpleadoService empleadoService;
	 @Autowired
	    private ClienteService clienteService;
	 
	 @Autowired
	 private UsuarioRepository usuarioRepository;
	 
	 @Autowired
	 private UsuarioRolesRepository usuarioRolesRepository;
	 
		@Value("${jwt.secret.key}")
		String secretKey;
		@Value("${jwt.time.expiration}")
		String timeExpiration;
	
	    
	    //Funciones
	    @GetMapping
	    public List<Usuario> listarUsuarios() {
	        return usuarioService.listarUsuarios();
	    }
	   
	    @GetMapping("/detalle")
	    public List<Map<String, Object>> obtenerDetallesUsuarios() {
	        List<Usuario> usuarios = usuarioService.listarUsuarios();
	        List<Map<String, Object>> detalles = new ArrayList<>();

	        for (Usuario usuario : usuarios) {
	            Map<String, Object> detalle = new HashMap<>();
	            detalle.put("idUsuario", usuario.getIdUsuario());
	            detalle.put("nombreUsuario", usuario.getNombre());
	            detalle.put("password", usuario.getPassword());
	            detalle.put("estado", usuario.getEstado());

	            // Buscar en Empleado
	            Empleado empleado = empleadoService.obtenerPorIdUsuario(usuario.getIdUsuario());
	            if (empleado != null) {
	                detalle.put("nombre", empleado.getNombre());
	                detalle.put("apellido", empleado.getApellidos());
	                detalle.put("tipo", "Empleado");
	            } else {
	                // Buscar en Cliente
	                Cliente cliente = clienteService.obtenerPorIdUsuario(usuario.getIdUsuario());
	                if (cliente != null) {
	                    detalle.put("nombre", cliente.getNombre());
	                    detalle.put("apellido", cliente.getApellidos());
	                    detalle.put("tipo", "Cliente");
	                } else {
	                    detalle.put("nombre", "Desconocido");
	                    detalle.put("apellido", "Desconocido");
	                    detalle.put("tipo", "Sin asignar");
	                }
	            }

	            detalles.add(detalle);
	        }
	        return detalles;
	    }
	    
	    @PutMapping("/{id}")
	    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuarioDetails) {
	        Optional<Usuario> optionalUsuario = usuarioService.obtenerUsuario(id);
	        System.out.println("===========================");
	        System.out.println(usuarioDetails.getNombre());
	        System.out.println(usuarioDetails.getPassword());
	        if (optionalUsuario.isPresent()) {
	            Usuario usuario = optionalUsuario.get();
	            //usuario.setNombre(usuarioDetails.getNombreUsuario());
	            usuario.setPassword(usuarioDetails.getPassword());

	            Usuario updatedUsuario = usuarioService.save(usuario);
	            return ResponseEntity.ok(updatedUsuario);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	    
	    @PutMapping("/cambiarEstado/{id}")
	    public ResponseEntity<Usuario> updateEstadoProveedor(@PathVariable Long id, @RequestBody Usuario empleadoFrontend) {
	        Optional<Usuario> optionalUsuario = usuarioService.obtenerUsuario(id);
	        Usuario updatedUsuario;
	        if (optionalUsuario.isPresent()) {
	        	Usuario usuario1 = optionalUsuario.get();
	            if(empleadoFrontend.getEstado() == 1) {
	            	usuario1.setEstado(2);
	            	updatedUsuario = usuarioService.save(usuario1);
	            }else {
	            	usuario1.setEstado(1);
	            	updatedUsuario = usuarioService.save(usuario1);
	            }
	  
	          //  Proveedor updatedProveedor = proveedorService.save(proveedor1);
	            return ResponseEntity.ok(updatedUsuario);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	/*    
	    @PostMapping("/login")
	    public Map<String, Object> login(@RequestBody Usuario usuario) {
	        Map<String, Object> response = new HashMap<>();

	        // Buscar al usuario en la base de datos usando su nombre
	        Usuario user = usuarioRepository.findByNombre(usuario.getNombre());

	        // Verificar que el usuario existe y que la contraseña es correcta
	        if (user != null && user.getPassword().equals(usuario.getPassword())) {
	            try {
	                System.out.println("El usuario existe y la contraseña es correcta");

	                // Generar un token JWT utilizando el nombre del usuario
	                String xtoken = getJWTToken(user.getNombre()); // Usamos el nombre de usuario para generar el token
	                user.setToken(xtoken); // Asignamos el token al usuario

	                // Obtener el rol del usuario desde la relación UsuarioRoles
	                Optional<UsuarioRoles> usuarioRolesOpt = usuarioRolesRepository.findByUsuarioId(user.getIdUsuario());
	                
	                // Verificar que el usuario tenga un rol asociado
	                if (usuarioRolesOpt.isPresent()) {
	                    UsuarioRoles usuarioRoles = usuarioRolesOpt.get();
	                    String rol = usuarioRoles.getRoles().getNombre(); // Asumiendo que el rol tiene un campo 'nombre'

	                    // Agregar la información del rol a la respuesta
	                    response.put("rol", rol);
	                } else {
	                    response.put("rol", "SIN_ROL"); // Si no tiene rol asignado
	                }

	                // Guardar el usuario con el nuevo token (si es necesario)
	                usuarioRepository.save(user); 

	            } catch (Exception e) {
	                user = null;
	                System.out.println("Error al generar el token: " + e.getMessage());
	            }

	            // Responder con datos de autenticación, incluyendo el token y los detalles del usuario
	            response.put("authenticated", true);
	            response.put("persona", user.getNombre()); // Devuelves el nombre del usuario, puedes ajustar esto a lo que necesites
	            response.put("token", user.getToken()); // Incluye el token generado en la respuesta

	        } else {
	            // Si las credenciales no son correctas, indicar que la autenticación falló
	            System.out.println("No existe el usuario o las credenciales son incorrectas");
	            response.put("authenticated", false);
	        }

	        return response;
	    }
*/
	    
	    
	    
	    @PostMapping("/login")
	    public Map<String, Object> login(@RequestBody Usuario usuario) {
	        Map<String, Object> response = new HashMap<>();

	        // Buscar al usuario
	        Usuario user = usuarioRepository.findByNombre(usuario.getNombre());

	        if (user != null && user.getPassword().equals(usuario.getPassword())) {
	            try {
	                // Obtener todos los roles del usuario
	                List<UsuarioRoles> rolesUsuario = usuarioRolesRepository.findAllByUsuarioIdUsuario(user.getIdUsuario());

	                if (rolesUsuario.isEmpty()) {
	                    response.put("authenticated", false);
	                    response.put("error", "El usuario no tiene roles asignados");
	                    return response;
	                }

	                if (rolesUsuario.size() == 1) {
	                    // Un solo rol: generamos token y devolvemos como antes
	                    String rol = rolesUsuario.get(0).getRoles().getNombre();

	                    String xtoken = getJWTTokenConRol(user.getNombre(), rol);
	                    user.setToken(xtoken);
	                    usuarioRepository.save(user);
	                    System.out.println("Token generado para usuario " + user.getNombre() + ": " + xtoken);
	                    
	                    response.put("authenticated", true);
	                    response.put("persona", user.getNombre());
	                    response.put("token", user.getToken());
	                    response.put("rol", rol);
	                } else {
	                    // Más de un rol: devolver lista de roles para seleccionar
	                    List<String> listaRoles = rolesUsuario.stream()
	                        .map(ur -> ur.getRoles().getNombre())
	                        .collect(Collectors.toList());

	                    response.put("authenticated", true);
	                    response.put("persona", user.getNombre());
	                    response.put("roles", listaRoles);
	                   // response.put("token", user.getToken());
	                    // No generamos token todavía
	                }

	            } catch (Exception e) {
	                response.put("authenticated", false);
	                response.put("error", e.getMessage());
	            }
	        } else {
	            response.put("authenticated", false);
	            response.put("error", "Usuario o contraseña incorrectos");
	        }

	        return response;
	    }

	    
	    private String getJWTTokenConRol(String username, String rol) {
	        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
	                .commaSeparatedStringToAuthorityList(rol);

	        String token = Jwts
	                .builder()
	                .setId("softtekJWT")
	                .setSubject(username)
	                .claim("authorities",
	                        grantedAuthorities.stream()
	                                .map(GrantedAuthority::getAuthority)
	                                .collect(Collectors.toList()))
	                .setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
	                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();
	        return "Bearer " + token;
	    }

	    
	    @PostMapping("/login/seleccionar-rol")
	    public Map<String, Object> loginSeleccionarRol(@RequestBody Map<String, String> loginData) {
	        Map<String, Object> response = new HashMap<>();

	        String usuario = loginData.get("usuario");
	        String password = loginData.get("password");
	        String rolSeleccionado = loginData.get("rolSeleccionado");

	        Usuario user = usuarioRepository.findByNombre(usuario);

	        if (user != null && user.getPassword().equals(password)) {
	            List<UsuarioRoles> rolesUsuario = usuarioRolesRepository.findAllByUsuarioIdUsuario(user.getIdUsuario());

	            boolean tieneRol = rolesUsuario.stream()
	                    .anyMatch(ur -> ur.getRoles().getNombre().equals(rolSeleccionado));

	            if (!tieneRol) {
	                response.put("authenticated", false);
	                response.put("error", "El usuario no tiene asignado el rol seleccionado");
	                return response;
	            }

	            try {
	                String xtoken = getJWTTokenConRol(user.getNombre(), rolSeleccionado);
	                user.setToken(xtoken);
	                usuarioRepository.save(user);
	                // Imprimir el token en consola aquí:
	                System.out.println("Token generado para usuario " + user.getNombre() + ": " + xtoken);

	                response.put("authenticated", true);
	                response.put("persona", user.getNombre());
	                response.put("token", xtoken);
	                response.put("rol", rolSeleccionado);

	            } catch (Exception e) {
	                response.put("authenticated", false);
	                response.put("error", e.getMessage());
	            }
	        } else {
	            response.put("authenticated", false);
	            response.put("error", "Usuario o contraseña incorrectos");
	        }

	        return response;
	    }

	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	   /* 
	    @PostMapping("/login")
	    public Map<String, Object> login(@RequestBody Usuario usuario) {
	        Map<String, Object> response = new HashMap<>();

	        // Buscar al usuario en la base de datos usando su nombre
	        Usuario user = usuarioRepository.findByNombre(usuario.getNombre());

	        // Verificar que el usuario existe y que la contraseña es correcta
	        if (user != null && user.getPassword().equals(usuario.getPassword())) {
	            try {
	                System.out.println("EL USUARIO SI EXISTE ES CORRECTO");
	                // Generar un token JWT utilizando el nombre del usuario
	                String xtoken = getJWTToken(user.getNombre());
	                user.setToken(xtoken);

	                // Actualizar el usuario (por ejemplo, para almacenar el token)
	                usuarioRepository.save(user);

	                // Recuperar el rol del usuario usando el id del usuario
	                Optional<UsuarioRoles> urOpt = usuarioRolesRepository.findByUsuarioId(user.getIdUsuario());
	                String rol;
	                if (urOpt.isPresent() && urOpt.get().getRoles() != null) {
	                    rol = urOpt.get().getRoles().getNombre();
	                } else {
	                    rol = "SIN_ROL";
	                }

	                // Construir la respuesta
	                response.put("authenticated", true);
	                response.put("persona", user.getNombre());
	                response.put("token", user.getToken());
	                response.put("rol", rol);

	            } catch (Exception e) {
	                System.out.println("Error al generar el token: " + e.getMessage());
	                response.put("authenticated", false);
	                response.put("error", e.getMessage());
	            }
	        } else {
	            System.out.println("NO EXISTE EL USUARIO o credenciales incorrectas");
	            response.put("authenticated", false);
	        }

	        return response;
	    }
	    
		private String getJWTToken(String username) {
			List<GrantedAuthority> grantedAuthorities = AuthorityUtils
					.commaSeparatedStringToAuthorityList("ROLE_USER");
			
			String token = Jwts
					.builder()
					.setId("softtekJWT")
					.setSubject(username)
					.claim("authorities",
							grantedAuthorities.stream()
									.map(GrantedAuthority::getAuthority)
									.collect(Collectors.toList()))
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() +Long.parseLong(timeExpiration)))
					//.setExpiration(new Date(System.currentTimeMillis() + 600000 ))
					.signWith(SignatureAlgorithm.HS512,
							secretKey.getBytes()).compact();
			System.out.print(token);
			return "Bearer " + token;
		}*/


}
