package com.tsti.entidades;

import java.sql.Date;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Cliente {
	//atributos
	@Id
	private Long dni;
	@NotNull
	private String nombre;
	@NotNull
	private String apellido;
	private String domicilio;
	private String email;
	private Date fecha_nac;
	private Long nro_pasaporte;
	private Date fecha_venc;
	
	
	//constructores
	public Cliente() {
		
	}
	public Cliente(Long dni, @NotNull String nombre, @NotNull String apellido, @NotNull String domicilio,
			@Email String email, @NotNull Date fecha_nac, Long nro_pasaporte, Date fecha_venc) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.domicilio = domicilio;
		this.email = email;
		this.fecha_nac = fecha_nac;
		this.nro_pasaporte = nro_pasaporte;
		this.fecha_venc = fecha_venc;
	}
	
	
	//getters y setters
	public Long getDni() {
		return dni;
	}
	public void setDni(Long dni) {
		this.dni = dni;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
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
	
	
	

}
