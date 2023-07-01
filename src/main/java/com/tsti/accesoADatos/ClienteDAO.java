package com.tsti.accesoADatos;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.tsti.entidades.Cliente;

public interface ClienteDAO extends JpaRepository<Cliente, Long> {
	
	@Query("SELECT c FROM Cliente c WHERE c.nombre like '%?1%'")
	Collection<Cliente> findClientesLike(String parte);
	
	public List<Cliente> findByApellidoOrNombre(String apellido, String nombre);

}
