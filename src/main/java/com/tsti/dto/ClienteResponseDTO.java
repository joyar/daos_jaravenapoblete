package com.tsti.dto;

import java.sql.Date;

import org.springframework.hateoas.RepresentationModel;

import com.tsti.entidades.Cliente;

import jakarta.validation.constraints.Email;

public class ClienteResponseDTO extends RepresentationModel<ClienteResponseDTO>{
	
	private Long dni;
	private String apellido;
	private String nombre;
	private String domicilio;
	private String email;
	private Date fecha_nac;
	private Long nro_pasaporte;
	private Date fecha_venc;
	
	//constructor
	public ClienteResponseDTO(Cliente pojo) {
		super();
		this.dni = pojo.getDni();
		this.apellido = pojo.getApellido();
		this.nombre = pojo.getNombre();
		this.domicilio = pojo.getDomicilio();
		this.email = pojo.getEmail();
		this.fecha_nac = pojo.getFecha_nac();
		this.nro_pasaporte = pojo.getNro_pasaporte();
		this.fecha_venc = pojo.getFecha_venc();
	}
	
	//getters y setters
	public Long getDni() {
		return dni;
	}
	public void setDni(Long dni) {
		this.dni = dni;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFecha_nac() {
		return fecha_nac;
	}

	public void setFecha_nac(Date fecha_nac) {
		this.fecha_nac = fecha_nac;
	}

	public Long getNro_pasaporte() {
		return nro_pasaporte;
	}

	public void setNro_pasaporte(Long nro_pasaporte) {
		this.nro_pasaporte = nro_pasaporte;
	}

	public Date getFecha_venc() {
		return fecha_venc;
	}

	public void setFecha_venc(Date fecha_venc) {
		this.fecha_venc = fecha_venc;
	}

	@Override
	public String toString() {
		return "[dni=" + dni + ", apellido=" + apellido + ", nombre=" + nombre + ", domicilio="
				+ domicilio + ", email=" + email + ", fecha_nac=" + fecha_nac + ", nro_pasaporte=" + nro_pasaporte
				+ ", fecha_venc=" + fecha_venc + "]";
	}

	
	
	
	

}
