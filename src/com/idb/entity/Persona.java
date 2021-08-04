package com.idb.entity;

public class Persona {
	private Long id;
	private String dni;
	private String nombre;
	private String apellido1;
	private String apellido2;

	public static Long contador = 0L;

	public Persona(String dni, String nombre, String apellido1, String apellido2) {
		super();
		this.id = contador++;
		this.dni = dni;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
	}

	public Long getId() {
		return id;
	}

	public String getDNI() {
		return dni;
	}

	public void setDNI(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

}
