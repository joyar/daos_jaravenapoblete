package com.tsti.presentacion;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsti.dto.ClienteResponseDTO;
import com.tsti.dto.PersonaResponseDTO;
import com.tsti.entidades.Ciudad;
import com.tsti.entidades.Cliente;
import com.tsti.entidades.Persona;
import com.tsti.exception.Excepcion;
import com.tsti.presentacion.error.MensajeError;
import com.tsti.servicios.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteRestController {
	
	@Autowired
	private ClienteService service;
	
	/**
	 * Permite filtrar clientes. 
	 * Ej1 curl --location --request GET 'http://localhost:8081/clientes?apellido=Perez&&nombre=Juan' Lista las personas llamadas Gonzalez, Juan
	 * Ej2 curl --location --request GET 'http://localhost:8081/clientes?apellido=Gonzalez' Lista aquellas personas de apellido Gonzalez
	 * Ej3 curl --location --request GET 'http://localhost:8081/clientes'   Lista todas las personas
	 * @param apellido
	 * @param nombre
	 * @return
	 * @throws Excepcion 
	 */
	@GetMapping( produces = { MediaType.APPLICATION_JSON_VALUE})
	public List<ClienteResponseDTO> filtrar(@RequestParam(name = "apellido",required = false) String apellido 
			, @RequestParam(name = "nombre",required = false)  @jakarta.validation.constraints.Size(min = 1, max = 20) String nombre) throws Excepcion {
		
		List<Cliente> clientes = service.filtrarClientes(apellido, nombre);
		List<ClienteResponseDTO> dtos = new ArrayList<ClienteResponseDTO>();
		for (Cliente pojo : clientes) {
	       dtos.add(buildResponse(pojo));
		}
		return dtos;
		//return clientes;

	}
	
	/**
	 * Inserta un nuevo cliente en la base de datos
	 * 			curl --location --request POST 'http://localhost:8081/clientes' 
	 *			--header 'Accept: application/json' 
	 * 			--header 'Content-Type: application/json' 
	 *			--data-raw '{
	 *			    "dni": 30257459,
	 *			    "apellido": "Ramirez",
	 *			    "nombre": "Gonzalo",
	 *				"domicilio": "lavalle 123",
	 *				"fecha_nac": "1995-05-25",
	 *				"nro_pasaporte": 35697854,
	 *				"fecha_venc": "2027-04-11"
	 *			}'
	 * @param c Cliente  a insertar
	 * @return Persona insertada o error en otro caso
	 * @throws Exception 
	 */
	@PostMapping
	public ResponseEntity<Object> guardar( @Valid @RequestBody ClienteForm form, BindingResult result) throws Exception
	{
		
		if(result.hasErrors())
		{
			//Dos alternativas:
			//throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatearError(result));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( this.formatearError(result));
		}
		
		
		
		//ahora inserto el cliente
		Cliente c = form.toPojo();
		service.insert(c);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{dni}")
				.buildAndExpand(c.getDni()).toUri(); //Por convención en REST, se devuelve la  url del recurso recién creado

		return ResponseEntity.created(location).build();//201 (Recurso creado correctamente)
		

	}
	
	/**
	 * Modifica un cliente existente en la base de datos:
	 * 			curl --location --request PUT 'http://localhost:8081/clientes/27837171' 
	 *			--header 'Accept: application/json' 
	 * 			--header 'Content-Type: application/json' 
	 *			--data-raw '{
	 *				"dni": 27837171,
	 *			    "apellido": "Perez",
	 *			    "nombre": "Juan Martin"
	 *			}'
	 * @param p Persona a modificar
	 * @return Cliente Editado o error en otro caso
	 * @throws Excepcion 
	 */
	@PutMapping("/{dni}")
	public ResponseEntity<Object> actualizar(@RequestBody ClienteForm form, @PathVariable Long dni) throws Exception
	{
		Optional<Cliente> rta = service.getById(dni);
		if(!rta.isPresent())
			return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra la persona que desea modificar.");
			
		else
		{
			Cliente c = form.toPojo();
			if(!c.getDni().equals(dni))//El dni es el identificador, con lo cual no se permite modificar ese dato
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getError("03", "Dato no editable", "No puede modificar el dni."));
			else
			service.update(c);
			return ResponseEntity.ok(buildResponse(c));
		}
	}
	
	/**
	 * Busca un cliente a partir de su dni
	 * 	curl --location --request GET 'http://localhost:8081/personas/27837171'
	 * @param id DNI del cliente buscado
	 * @return Cliente encontrado o Not found en otro caso
	 * @throws Excepcion 
	 */
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<ClienteResponseDTO> getById(@PathVariable Long id) throws Excepcion
	{
		Optional<Cliente> rta = service.getById(id);
		if(rta.isPresent())
		{
			Cliente pojo=rta.get();
			return new ResponseEntity<ClienteResponseDTO>(buildResponse(pojo), HttpStatus.OK);
		}
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
	}
	
	/**
	 * Método auxiliar que toma los datos del pojo y construye el objeto a devolver en la response, con su hipervinculos
	 * @param pojo
	 * @return
	 * @throws Excepcion 
	 */
	private ClienteResponseDTO buildResponse(Cliente pojo) throws Excepcion {
		try {
			ClienteResponseDTO dto = new ClienteResponseDTO(pojo);
			 //Self link
			Link selfLink = WebMvcLinkBuilder.linkTo(ClienteRestController.class)
										.slash(pojo.getDni())                
										.withSelfRel();
			dto.add(selfLink);
			return dto;
		} catch (Exception e) {
			throw new Excepcion(e.getMessage(), 500);
		}
	}
	
	private String formatearError(BindingResult result) throws JsonProcessingException
	{
//		primero transformamos la lista de errores devuelta por Java Bean Validation
		List<Map<String, String>> errores= result.getFieldErrors().stream().map(err -> {
															Map<String, String> error= new HashMap<>();
															error.put(err.getField(),err.getDefaultMessage() );
															return error;
														}).collect(Collectors.toList());
		MensajeError e1=new MensajeError();
		e1.setCodigo("01");
		e1.setMensajes(errores);
		
		//ahora usamos la librería Jackson para pasar el objeto a json
		ObjectMapper maper = new ObjectMapper();
		String json = maper.writeValueAsString(e1);
		return json;
	}
	
	private String getError(String code, String err, String descr) throws JsonProcessingException
	{
		MensajeError e1=new MensajeError();
		e1.setCodigo(code);
		ArrayList<Map<String,String>> errores=new ArrayList<>();
		Map<String, String> error=new HashMap<String, String>();
		error.put(err, descr);
		errores.add(error);
		e1.setMensajes(errores);
		
		//ahora usamos la librería Jackson para pasar el objeto a json
				ObjectMapper maper = new ObjectMapper();
				String json = maper.writeValueAsString(e1);
				return json;
	}

}
