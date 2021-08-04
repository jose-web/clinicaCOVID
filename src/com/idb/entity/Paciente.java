package com.idb.entity;

import java.util.ArrayList;
import java.util.Scanner;

public class Paciente extends Persona {

	private static ArrayList<Paciente> todos = new ArrayList<Paciente>();

	public Paciente(String dni, String nombre, String apellido1, String apellido2) {
		super(dni, nombre, apellido1, apellido2);
		// TODO Auto-generated constructor stub
		todos.add(this);
	}

	@Override
	public String toString() {
		return "Paciente [getId()=" + getId() + ", getDNI()=" + getDNI() + ", getNombre()=" + getNombre()
				+ ", getApellido1()=" + getApellido1() + ", getApellido2()=" + getApellido2() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	public static void alta() {
		Scanner entradaEscaner = new Scanner(System.in);

		System.out.println("-------------------------------------------");
		System.out.println("Alta paciente");
		System.out.println("-------------------------------------------");
		System.out.println();

		System.out.println("Insique su dni: ");
		String dni = entradaEscaner.nextLine();

		System.out.println("Insique su nombre: ");
		String nombre = entradaEscaner.nextLine();

		System.out.println("Insique su primer apellido: ");
		String apellido1 = entradaEscaner.nextLine();

		System.out.println("Insique su segundo apellido: ");
		String apellido2 = entradaEscaner.nextLine();

		Paciente nuevo = new Paciente(dni, nombre, apellido1, apellido2);

		System.out.println("-------------------------------------------");
		System.out.println("Se ha creado correctamente el paciente");
		System.out.println(nuevo.toString());

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

				}
			}

		} while (entradaTeclado != 0);

	}

	public static Paciente buscarDni() {
		Scanner entradaEscaner = new Scanner(System.in);
		do {
			System.out.println("Indique el dni del paciente: ");

			String dni = entradaEscaner.nextLine();

			for (Paciente paciente : todos) {
				if (paciente.getDNI().equals(dni))
					return paciente;
			}
		} while (true);
	}

	public static void verTodos() {
		System.out.println(
				"*************************************************************************************************************************");
		System.out.println("TODOS LOS PACIENTES");
		System.out.println(
				"*************************************************************************************************************************");
		System.out.println("DNI\t\tNOMBRE\t\tPRIMER APELLIDO\t\tSEGUNDO APELLIDO");
		for (Paciente paciente : todos) {
			System.out.println(paciente.getDNI() + "\t" + paciente.getNombre() + "\t\t" + paciente.getApellido1()
					+ "\t\t\t" + paciente.getApellido2());
		}
		System.out.println(
				"*************************************************************************************************************************");
	}

}
