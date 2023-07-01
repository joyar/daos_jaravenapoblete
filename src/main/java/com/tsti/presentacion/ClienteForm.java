package com.tsti.presentacion;

import java.sql.Date;

import com.tsti.entidades.Cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class ClienteForm {
	
	@NotNull
	private Long dni;
	@NotNull
	private String apellido;
	@NotNull
	private String nombre;
	@NotNull
	private String domicilio;
	@Email
	private String email;
	private Date fecha_nac;
	private Long nro_pasaporte;
	private Date fecha_venc;
	
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
	public Cliente toPojo() {
		Cliente c = new Cliente();
		c.setDni(this.getDni());
		c.setApellido(this.getApellido());
		c.setNombre(this.getNombre());
		c.setDomicilio(this.getDomicilio());
		c.setEmail(this.getEmail());
		c.setFecha_nac(this.getFecha_nac());
		c.setNro_pasaporte(this.nro_pasaporte);
		c.setFecha_venc(this.getFecha_venc());
		return c;
	}

}
