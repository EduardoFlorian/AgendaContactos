package com.agenda.contactos.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;

@Entity
@Table(name="tb_contacto")
@Data
public class Contacto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cont_id")
	private Integer id;
	
	@Column(name="cont_nombre")
	@NotBlank(message="Debe ingresar su nombre")
	private String nombre;

	@Column(name="cont_email")
	@NotEmpty(message="No debe estar vacio")
	@Email
	private String email;
	
	@Column(name="cont_celular")
	@NotBlank(message="Ingresar su celular")
	private String celular;
	
	@Column(name="cont_fecha_nacimiento")
	@DateTimeFormat(iso = ISO.DATE) //Para admitir solo formatos fecha
	@Past //Admite Fechas solo pasadas, no presentes ni futuras
	@NotNull(message="Debe ingresar su fecha de nacimiento")
	private LocalDate fechaNacimiento;
	
	@Column(name="cont_fecha_registro")
	private LocalDateTime fechaRegistro;
	
	
	//Metodo para darle un valor por defecto a la fecha de Registro
	@PrePersist //Esta notacion hace que podamos asignarle un valor a un atributo
	public void asignarFechaRegistro() {
		fechaRegistro = LocalDateTime.now();
	}
}
