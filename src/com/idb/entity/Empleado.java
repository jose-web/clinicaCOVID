package com.idb.entity;

import java.util.ArrayList;
import java.util.Scanner;

public class Empleado extends Persona {

	private String tipo;
	// admin
	// enfermero
	// tecnico

	private String pass;

	public static ArrayList<Empleado> todos = new ArrayList<Empleado>();

	public Empleado(String dni, String nombre, String apellido1, String apellido2, String tipo, String pass) {
		super(dni, nombre, apellido1, apellido2);
		this.tipo = tipo;
		this.pass = pass;

		todos.add(this);
	}

	public String getTipo() {
		return tipo;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "Empleado [tipo=" + tipo + ", getId()=" + getId() + ", getDNI()=" + getDNI() + ", getNombre()="
				+ getNombre() + ", getApellido1()=" + getApellido1() + ", getApellido2()=" + getApellido2()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

	public String getPass() {
		return pass;
	}

	public static boolean login(String dni, String pass) {
		for (Empleado empleado : todos) {

			if (empleado.getDNI().equals(dni) && empleado.getPass().equals(pass)) {
				switch (empleado.getTipo()) {
				case "admin":
					empleado.zonaAdmin();
					break;

				case "enfermero":
					empleado.zonaEnfermero();
					break;

				case "tecnico":
					break;
				}
				return true;
			}
		}

		return false;
	}

	private void zonaAdmin() {
		Scanner entradaEscaner = new Scanner(System.in);
		int entradaTeclado = -1;
		do {
			System.out.println("-------------------------------------------");
			System.out.println("Hola " + this.getNombre() + " " + this.getApellido1() + " " + this.getApellido2());
			System.out.println("-------------------------------------------");
			System.out.println();
			System.out.println("-Paciente-\t-Empleado-");
			System.out.println("1 - alta\t2- alta");
			System.out.println("3 - modificar\t4 - modificar");
			System.out.println("5 - ver \t6 - ver");
			System.out.println("7 - baja \t8 - baja");

			System.out.println("-------------------------");
			System.out.println("9 - asignar prueba covid");
			System.out.println("10 - asignar vacuna covid");
			System.out.println("11 - administrar vacunas");
			System.out.println();
			System.out.println("Escriba aquí su elección: ");
			try {
				entradaTeclado = Integer.parseInt(entradaEscaner.nextLine());
			} catch (Exception e) {
				entradaTeclado = -1;
				System.out.println("ha ocurrido un error con su elección: " + e);
			}

			switch (entradaTeclado) {
			case 1:
				Paciente.alta();
				break;
			case 2:
				Empleado.alta();
				break;
			case 3:
				Paciente.buscarDni().modifica();
				break;
			case 4:
				Empleado.buscarDni().modifica();
				break;
			case 5:
				Paciente.verTodos();
				break;
			case 6:
				Empleado.verTodos();
				break;
			case 7:
				Paciente.baja();
				break;
			case 8:
				Empleado.baja();
				break;
			case 9:
				PruebasDiagnosticas.cita();
				break;
			case 10:
				Vacuna.cita();
				break;
			case 11:
				Vacuna.administrar();
				break;
			}
		} while (entradaTeclado != 0);
	}

	private void zonaEnfermero() {
		Scanner entradaEscaner;
		int entradaTeclado = -1;
		do {
			entradaEscaner = new Scanner(System.in);
			System.out.println("-------------------------------------------");
			System.out.println("Hola " + this.getNombre() + " " + this.getApellido1() + " " + this.getApellido2());
			System.out.println("-------------------------------------------");
			System.out.println();
			System.out.println("1 - Ver pacientes a la espera");
			System.out.println("2 - Ver todos tus pacientes");
			System.out.println("3 - Hacer prueba");
			System.out.println("4 - Poner vacuna");
			System.out.println("5 - Ver los pacientes confinados");
			System.out.println();
			System.out.println("Escriba aquí su elección: ");
			try {
				entradaTeclado = Integer.parseInt(entradaEscaner.nextLine());
			} catch (Exception e) {
				entradaTeclado = -1;
				System.out.println("ha ocurrido un error con su elección: " + e);
			}

			switch (entradaTeclado) {
			case 1:
				PruebasDiagnosticas.verProximosPacientes(this.getId());
				break;
			case 2:
				PruebasDiagnosticas.verTodosPacientes(this.getId());
				break;
			case 3:
				PruebasDiagnosticas.hacerPrueba(this.getId());
				break;
			case 4:
				Vacuna.vacunar();
				break;
			case 5:
				PruebasDiagnosticas.verPacientesConfinados();
				break;
			}
		} while (entradaTeclado != 0);
	}

	private static String seleccionTipo() {
		Scanner entradaEscaner = new Scanner(System.in);

		int entradaTeclado = -1;
		do {
			System.out.println("-------------------------------------------");
			System.out.println("Seleccione el tipo");
			System.out.println("-------------------------------------------");
			System.out.println("1 - admin");
			System.out.println("2 - enfermero");
			System.out.println("3 - tecnico");
			System.out.println("-------------------------------------------");

			System.out.println("indique el tipo: ");
			try {
				entradaTeclado = Integer.parseInt(entradaEscaner.nextLine());
			} catch (Exception e) {
				entradaTeclado = -1;
				System.out.println("ha ocurrido un error con su elección: " + e);
			}

			switch (entradaTeclado) {
			case 1:
				return "admin";

			case 2:
				return "enfermero";

			case 3:
				return "tecnico";
			}

		} while (true);
	}

	private static void alta() {
		Scanner entradaEscaner = new Scanner(System.in);

		System.out.println("-------------------------------------------");
		System.out.println("Alta Empleado");
		System.out.println("-------------------------------------------");
		System.out.println();

		System.out.println("indique su dni: ");
		String dni = entradaEscaner.nextLine();

		System.out.println("indique su nombre: ");
		String nombre = entradaEscaner.nextLine();

		System.out.println("indique su primer apellido: ");
		String apellido1 = entradaEscaner.nextLine();

		System.out.println("indique su segundo apellido: ");
		String apellido2 = entradaEscaner.nextLine();

		String tipo = seleccionTipo();

		Empleado nuevo = new Empleado(dni, nombre, apellido1, apellido2, tipo, "1234");

		System.out.println("-------------------------------------------");
		System.out.println("Se ha creado correctamente el empleado");
		System.out.println(nuevo.toString());

	}

	public static void baja() {
		verTodos();
		Empleado empleado = buscarDni();
		todos.remove(empleado);
	}

	public void modifica() {
		Scanner entradaEscaner = new Scanner(System.in);

		System.out.println("-------------------------------------------");
		System.out.println("Modifica paciente");
		System.out.println();

		int entradaTeclado = -1;
		do {
			System.out.println("--------------------------------------------");
			System.out.println("0 - salir ");
			System.out.println();
			System.out.println("1 - dni: " + this.getDNI());
			System.out.println("2 - nombre: " + this.getNombre());
			System.out.println("3 - primer apellido: " + this.getApellido1());
			System.out.println("4 - segundo apellido: " + this.getApellido2());
			System.out.println("5 - tipo de empleado: " + this.getTipo());
			System.out.println();
			System.out.println("Escriba aquí su elección: ");

			try {
				entradaTeclado = Integer.parseInt(entradaEscaner.nextLine());
			} catch (Exception e) {
				entradaTeclado = -1;
				System.out.println("ha ocurrido un error con su elección: " + e);
			}
			if (entradaTeclado != 0) {
				System.out.println("Escriba el nuevo dato modificado: ");
				String teclado = entradaEscaner.nextLine();

				switch (entradaTeclado) {
				case 1:
					this.setDNI(teclado);
					break;
				case 2:
					this.setNombre(teclado);
					break;
				case 3:
					this.setApellido1(teclado);
					break;
				case 4:
					this.setApellido2(teclado);
					break;
				case 5:
					this.setTipo(teclado);
					break;
				}
			}

		} while (entradaTeclado != 0);

	}

	public static Empleado buscarDni() {
		Scanner entradaEscaner = new Scanner(System.in);

		do {
			System.out.println("Indique el dni: ");

			String dni = entradaEscaner.nextLine();

			for (Empleado empleado : todos) {
				if (empleado.getDNI().equals(dni))
					return empleado;
			}
		} while (true);
	}

	public static void verTodos() {
		System.out.println(
				"*************************************************************************************************************************");
		System.out.println("TODOS LOS PACIENTES");
		System.out.println(
				"*************************************************************************************************************************");
		System.out.println("DNI\t\tNOMBRE\t\tPRIMER APELLIDO\t\tSEGUNDO APELLIDO\tTIPO");
		for (Empleado empleado : todos) {
			System.out.println(empleado.getDNI() + "\t" + empleado.getNombre() + "\t\t" + empleado.getApellido1()
					+ "\t\t\t" + empleado.getApellido2() + "\t\t\t" + empleado.getTipo());
		}
		System.out.println(
				"*************************************************************************************************************************");
	}

}
