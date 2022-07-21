package com.agenda.contactos.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agenda.contactos.modelo.Contacto;

@Repository
public interface IContactoRepositorio extends JpaRepository<Contacto, Integer>{

}
