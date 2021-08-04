package com.idb.main;

import java.time.LocalDate;
import java.util.Scanner;

import com.idb.entity.Empleado;
import com.idb.entity.Paciente;
import com.idb.entity.PruebasDiagnosticas;
import com.idb.entity.Vacuna;

public class Main {
	public static void main(String[] args) {

		new Empleado("11111111A", "jose", "primero", "segundo", "admin", "1");
		new Empleado("2", "sebastian", "primero", "segundo", "tecnico", "1");
		new Empleado("3", "patricia", "primero", "segundo", "enfermero", "1");
		new Empleado("4", "patricia", "primero", "segundo", "enfermero", "1");

		new Paciente("22222222D", "maria", "ramirez", "rivas");
		new Paciente("33456373D", "maria", "perez", "luque");
		new Paciente("32442073L", "Paco", "rueda", "martinez");
		new Paciente("1", "Paco", "rueda", "martinez");
		new Paciente("2", "Paco", "rueda", "martinez");
		new Paciente("3", "Paco", "rueda", "martinez");
		new Paciente("4", "Paco", "rueda", "martinez");
		new Paciente("5", "Paco", "rueda", "martinez");
		new Paciente("6", "Paco", "rueda", "martinez");

		new PruebasDiagnosticas(2L, 2L, 1L, "PCR", LocalDate.parse("2021-08-14"));
		new PruebasDiagnosticas(2L, 2L, 1L, "PCR", LocalDate.parse("2021-08-14"));
		new PruebasDiagnosticas(2L, 2L, 1L, "PCR", LocalDate.parse("2021-08-14"));
		new PruebasDiagnosticas(2L, 2L, 1L, "PCR", LocalDate.parse("2021-08-14"));
		new PruebasDiagnosticas(2L, 2L, 1L, "PCR", LocalDate.parse("2021-08-14"));
		new PruebasDiagnosticas(2L, 2L, 2L, "PCR", LocalDate.parse("2020-08-14"));
		new PruebasDiagnosticas(2L, 2L, 1L, "PCR", LocalDate.now());
		new PruebasDiagnosticas(3L, 2L, 1L, "test serologicos", LocalDate.now());
		new PruebasDiagnosticas(4L, 2L, 1L, "test de antigenos", LocalDate.now());

		new Vacuna(1L, "Pfizer", LocalDate.now());

		System.out.println("======================================================");
		System.out.println("Le damos la bienvenida al sistema de la clínica COVID");
		System.out.println("======================================================");

		login();

		System.out.println("======================================================");
		System.out.println("Hasta la próxima :D");
		System.out.println("======================================================");
	}

	private static void login() {
		Scanner entradaEscaner;
		boolean login;

		do {
			entradaEscaner = new Scanner(System.in);
//			System.out.println();
//			System.out.println("DNI: ");
//			String dni = entradaEscaner.nextLine();
//			System.out.println("pass: ");
//			String pass = entradaEscaner.nextLine();
//
//			login = !Empleado.login(dni, pass);

///////////////////////////// LOGIN AUTOMÁTICO //////////////////////////////
//			login = !Empleado.login("11111111A", "1"); // admin
			login = !Empleado.login("3", "1"); // enfermero
/////////////////////////////////////////////////////////////////////////////

			if (login) {
				System.out.println("--------------------------------------------");
				System.out.println("Incorrecto, intentelo de nuevo");
			}
		} while (login);

		entradaEscaner.close();
	}

}
