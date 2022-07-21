package com.agenda.contactos.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.agenda.contactos.modelo.Contacto;
import com.agenda.contactos.repositorio.IContactoRepositorio;

@Controller
public class ContactoController {
	
	@Autowired
	private IContactoRepositorio contaRepo;

	@GetMapping({"/", ""})
	public String verPaginaDeInicio(Model modelo)
	{
		//Listado de contactos
		modelo.addAttribute("listadoContactos", contaRepo.findAll());
		
		return "Index";
	}
	
	@GetMapping("/contacto/nuevo")
	public String mostrarFormularioDeRegistrarContacto(Model modelo) {
		
		//Objeto contacto
		modelo.addAttribute("contacto", new Contacto());
		
		return "Nuevo";
	}
	
	@PostMapping("/contacto/nuevo")
	public String guardarContacto(@Validated Contacto contacto, BindingResult result,RedirectAttributes redirect, Model modelo) {
		
		//El @Validated es para indicar que vamos a validar dicho objeto
		//Lo que hace BindingResult es obtenernos todos los errores en la validacion
		//IMPORTANTE: El argumento BindingResult siempre debe ir al lado del objeto a validar sino no funcionara
		
		if(result.hasErrors()) {
			return "Nuevo";
		}
		
		//Registramos contacto
		contaRepo.save(contacto);
		
		//Redireccionamos un mensaje de exito
		redirect.addFlashAttribute("msgExito", "El contacto ha sido agregado con exito");
		
		return "redirect:/";
	}
	
	@GetMapping("/editar/{idContacto}")
	public String mostrarFormularioDeEditarContacto(@PathVariable int idContacto, Model modelo) {
		
		//Este atributo manda un objeto contacto(el cual fue encontrado por el id) 
		//con todos sus campos rellenados
		modelo.addAttribute("contacto", contaRepo.getById(idContacto));
		
		
		return "Nuevo";
	}
	
	@PostMapping("/editar/{idContacto}")
	public String actualizarContacto(@PathVariable int idContacto,@Validated Contacto contacto, BindingResult result,RedirectAttributes redirect, Model modelo) {
		
		Contacto contactoEncontrado = contaRepo.getById(idContacto);
		
		if(result.hasErrors()) {
			return "Nuevo";
		}
		
		contactoEncontrado.setCelular(contacto.getCelular());
		contactoEncontrado.setEmail(contacto.getEmail());
		contactoEncontrado.setFechaNacimiento(contacto.getFechaNacimiento());
		contactoEncontrado.setNombre(contacto.getNombre());
		
		//Registramos contacto
		contaRepo.save(contactoEncontrado);
		
		//Redireccionamos un mensaje de exito
		redirect.addFlashAttribute("msgExito", "El contacto ha sido actualizado con exito");
		
		return "redirect:/";
	}
	
	@PostMapping("/eliminar/{idContacto}")
	public String eliminarContacto(@PathVariable int idContacto, RedirectAttributes redirect) {
		
		//Este atributo manda un objeto contacto(el cual fue encontrado por el id) 
		//con todos sus campos rellenados
		Contacto contactoAEliminar = contaRepo.getById(idContacto);
		
		//Eliminamos contacto
		contaRepo.delete(contactoAEliminar);
		
		//Redireccionamos un mensaje de exito
		redirect.addFlashAttribute("msgExito", "El contacto ha sido eliminado con exito");
				
		return "redirect:/";
	}
	
	
	
}
