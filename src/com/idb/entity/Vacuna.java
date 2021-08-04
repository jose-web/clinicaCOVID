package com.idb.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Vacuna {
	private Long idPaciente;
	private String tipo;
	// Pfizer 2 dosis 21 dias
	// Moderna 2 dosis 21 dias
	// Johnson&Johnson 1 dosis
	private LocalDate fecha;
	private boolean vacunado = false;

	public static ArrayList<Vacuna> todos = new ArrayList<Vacuna>();

	public Vacuna(Long idPaciente, String tipo, LocalDate fecha) {
		super();
		this.idPaciente = idPaciente;
		this.tipo = tipo;
		this.fecha = fecha;

		todos.add(this);
	}

	public Long getIdPaciente() {
		return idPaciente;
	}

	public void setIdPaciente(Long idPaciente) {
		this.idPaciente = idPaciente;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public boolean isVacunado() {
		return vacunado;
	}

	public void setVacunado(boolean vacunado) {
		this.vacunado = vacunado;
	}

	public static void cita() {
		Scanner entradaEscaner = new Scanner(System.in);
		Long idCliente;

		do {
			System.out.println("Introduzca el id del paciente a vacunar");

			try {
				idCliente = Long.parseLong(entradaEscaner.nextLine());
			} catch (Exception e) {
				idCliente = -1L;
				System.out.println("ha ocurrido un error con su elección: " + e);
			}
		} while (idCliente == -1L);

		int dosis = 0;
		String tipo = "";
		LocalDate fecha = LocalDate.now();

		for (Vacuna vacuna : todos) {
			if (vacuna.getIdPaciente() == idCliente) {
				dosis++;
				tipo = vacuna.getTipo();

			}
		}

		int elijeTipo;
		switch (dosis) {
		case 0:
			do {
				System.out.println("Tipos de vacuna a elejir");
				System.out.println();
				System.out.println("1 - Pfizer");
				System.out.println("2 - Moderna");
				System.out.println("3 - Johnson&Johnson");
				System.out.println();
				System.out.println("Introduzca el tipo de vacuna:");

				try {
					elijeTipo = Integer.parseInt(entradaEscaner.nextLine());
				} catch (Exception e) {
					elijeTipo = -1;
					System.out.println("ha ocurrido un error con su elección: " + e);
				}
			} while (elijeTipo == -1);

			switch (elijeTipo) {
			case 1:
				tipo = "Pfizer";
				break;
			case 2:
				tipo = "Moderna";
				break;
			case 3:
				tipo = "Johnson&Johnson";
				break;
			}

			do {

				System.out.println("Introduzca la fecha de la vacuna:");

				try {
					fecha = LocalDate.parse(entradaEscaner.nextLine());
				} catch (Exception e) {
					fecha = null;
					System.out.println("ha ocurrido un error con su elección: " + e);
				}
			} while (fecha == null);
			new Vacuna(idCliente, tipo, fecha);
			System.out.println("Se ha puesto la vacuna correctamente");

			break;

		case 1:
			if (tipo.equals("Johnson&Johnson")) {
				System.out.println("No se puede poner más vacunas");
			} else {
				LocalDate fechaNueva = fecha.plusDays(21L);
				new Vacuna(idCliente, tipo, fechaNueva);

				System.out.println("Se ha puesto la vacuna " + tipo + " correctamente");
				System.out.println("Próxima cita " + fechaNueva);
			}
			break;
		case 2:
			System.out.println("No se puede poner más vacunas");
			break;
		}

	}

	public static boolean vacunar() {

		boolean noHayCitas = true;
		for (Vacuna vacuna : todos) {
			if (!vacuna.isVacunado() && vacuna.getFecha().compareTo(LocalDate.now()) == 0) {
				noHayCitas = false;
				break;
			}
		}

		if (noHayCitas) {
			System.out.println("************************************************");
			System.out.println("No hay vacunaciones para hoy");
			System.out.println("************************************************");

			return false;
		}

		Scanner entradaEscaner = new Scanner(System.in);
		ArrayList<Vacuna> idPacientes = new ArrayList<Vacuna>();

		boolean repite = false;
		Long idPaciente;

		Vacuna pacienteVacuna = null;

		do {
			repite = true;
			System.out.println("idPaciente\ttipo");
			for (Vacuna vacuna : todos) {
				if (!vacuna.isVacunado() && vacuna.getFecha().compareTo(LocalDate.now()) == 0) {

					System.out.println(vacuna.getIdPaciente() + "\t\t" + vacuna.getTipo());
					idPacientes.add(vacuna);
				}
			}
			System.out.println("Introcuza la id de un paciente:");

			try {
				idPaciente = Long.parseLong(entradaEscaner.nextLine());
			} catch (Exception e) {
				idPaciente = -1L;
				System.out.println("ha ocurrido un error con su elección: " + e);
			}

			for (Vacuna paciente : idPacientes) {
				if (paciente.getIdPaciente() == idPaciente) {
					repite = false;
					pacienteVacuna = paciente;
				}
			}

		} while (repite);

		if (pacienteVacuna.getTipo().equals("Johnson&Johnson")) {
			System.out.println("Esta es la última dosis");
		} else {
			int contadorDosis = 0;
			for (Vacuna vacuna : todos) {
				if (vacuna.getIdPaciente() == pacienteVacuna.getIdPaciente())
					contadorDosis++;
			}
			if (contadorDosis != 2) {
				LocalDate fechaNueva = LocalDate.now().plusDays(21L);
				new Vacuna(pacienteVacuna.getIdPaciente(), pacienteVacuna.getTipo(), fechaNueva);

				System.out.println("Se ha puesto la vacuna " + pacienteVacuna.getTipo() + " correctamente");
				System.out.println("Próxima cita " + fechaNueva);
			} else {
				System.out.println("Esta es la última dosis");
			}
		}

		pacienteVacuna.setVacunado(true);
		return true;
	}
}
