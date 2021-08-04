package com.idb.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class PruebasDiagnosticas {
	private static ArrayList<PruebasDiagnosticas> todos = new ArrayList<PruebasDiagnosticas>();

	private Long idPaciente;
	private Long idEnfermero;
	private Long idTecnico;
	private boolean completado;
	private String tipo;
	// pruebas de antigenos (rapida o clásica)
	// PCR - 15 días
	// analisis serológicos - 6 meses

	private boolean resultado;
	private LocalDate fecha;

	// analisis serológicos
	int anticuerpos; // entre 0 y 10 ( si es mayor a 2 ha pasado el covid)

	public PruebasDiagnosticas(Long idPaciente, Long idEnfermero, Long idTecnico, String tipo, LocalDate fecha) {
		super();
		this.idPaciente = idPaciente;
		this.idEnfermero = idEnfermero;
		this.idTecnico = idTecnico;
		this.tipo = tipo;
		this.fecha = fecha;

		todos.add(this);
	}

	public boolean isCompletado() {
		return completado;
	}

	public void setCompletado(boolean completado) {
		this.completado = completado;
	}

	public Long getIdPaciente() {
		return idPaciente;
	}

	public void setIdPaciente(Long idPaciente) {
		this.idPaciente = idPaciente;
	}

	public Long getIdEnfermero() {
		return idEnfermero;
	}

	public void setIdEnfermero(Long idEnfermero) {
		this.idEnfermero = idEnfermero;
	}

	public Long getIdTecnico() {
		return idTecnico;
	}

	public void setIdTecnico(Long idTecnico) {
		this.idTecnico = idTecnico;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public boolean isResultado() {
		return resultado;
	}

	public void setResultado(boolean resultado) {
		this.resultado = resultado;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public int getAnticuerpos() {
		return anticuerpos;
	}

	public void setAnticuerpos(int anticuerpos) {
		this.anticuerpos = anticuerpos;
	}

	@Override
	public String toString() {
		return "PruebasDiagnosticas [idPaciente=" + idPaciente + ", idEnfermero=" + idEnfermero + ", idTecnico="
				+ idTecnico + ", tipo=" + tipo + ", resultado=" + resultado + ", fecha=" + fecha + ", anticuerpos="
				+ anticuerpos + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	public static void cita() {
		Scanner entradaEscaner = new Scanner(System.in);

		///////////////////////////////////////////////////////
		// FECHA
		boolean repetir;
		LocalDate fecha = null;

		System.out.println("Introduzca una fecha: ");
		do {
			repetir = false;
			try {
				fecha = LocalDate.parse(entradaEscaner.nextLine());
			} catch (Exception e) {
				repetir = true;
				System.out.println("Introduzca una fecha válida por favor");
			}
		} while (repetir);

		///////////////////////////////////////////////////////
		// Tipo de test
		System.out.println("tipo");

		int tipo;

		do {
			System.out.println("---------------------------------------------");
			System.out.println("1 - PCR");
			System.out.println("2 - test serologicos");
			System.out.println("3 - test de antigenos");
			System.out.println("---------------------------------------------");

			try {
				tipo = Integer.parseInt(entradaEscaner.nextLine());
			} catch (Exception e) {
				tipo = 0;
			}
		} while (tipo < 1 || tipo > 3);

		String tipoTest = "";
		switch (tipo) {
		case 1:
			tipoTest = "PCR";
			break;
		case 2:
			tipoTest = "test serologicos";
			break;
		case 3:
			tipoTest = "test de antigenos";
			break;
		}
		///////////////////////////////////////////////////////
		// Enfermero
		Empleado enfermero;
		boolean limitePruebas = false;
		do {
			System.out.println("Enfermero");
			enfermero = Empleado.buscarDni();
			limitePruebas = contarEnfermeros(enfermero.getId(), fecha);
		} while (limitePruebas || enfermero.getTipo() != "enfermero");
		///////////////////////////////////////////////////////

		Paciente paciente = Paciente.buscarDni();
		boolean cita = buscar(paciente.getId(), tipoTest, fecha);
		if (cita) {
			System.out.println("Ya tiene una cita próxima");
			return;
		}

		///////////////////////////////////////////////////////

		Empleado tecnico;
		do {
			System.out.println("Tecnico");
			tecnico = Empleado.buscarDni();
			limitePruebas = contarTecnicos(tecnico.getId(), fecha);

		} while (limitePruebas || tecnico.getTipo() != "tecnico");

		///////////////////////////////////////////////////////

		new PruebasDiagnosticas(paciente.getId(), enfermero.getId(), tecnico.getId(), tipoTest, fecha).toString();

		for (PruebasDiagnosticas pruebasDiagnosticas : todos) {
			System.out.println(pruebasDiagnosticas.toString());
		}

	}

	public static boolean buscar(Long idPaciente, String tipoVacuna, LocalDate fecha) {

		if (tipoVacuna.equals("test de antigenos"))
			return true;
		for (PruebasDiagnosticas prueba : todos) {
			if (prueba.getIdPaciente() == idPaciente) {

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

				int diferencia = PruebasDiagnosticas.diferenciaFechas(prueba.getFecha().format(formatter),
						fecha.format(formatter));

				switch (prueba.getTipo()) {
				case "PCR":
					// 15 dias
					if (diferencia < 15) {
						System.out.println("Hay " + diferencia + " días de diferencia, y el mínimo son 15");
						return false;
					}

					break;
				case "test serologicos":
					// 6 meses
					System.out.println("Hay " + diferencia + " días de diferencia, y el mínimo son 182 (6 meses)");

					if (diferencia < 182)
						return false;
					break;
				}
			}
		}
		return true;
	}

	public static boolean contarEnfermeros(Long idEnfermero, LocalDate fecha) {

		Calendar day = Calendar.getInstance();
		Date date;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(fecha.toString());
			day.setTime(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int semana = day.get(Calendar.WEEK_OF_YEAR);

		int contador = 0;

		Calendar calendar = Calendar.getInstance();

		for (PruebasDiagnosticas pruebasDiagnosticas : todos) {

			try {
				date = new SimpleDateFormat("yyyy-MM-dd").parse(pruebasDiagnosticas.getFecha().toString());
				day.setTime(date);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			int nuevaSemana = day.get(Calendar.WEEK_OF_YEAR);
			System.out.println(pruebasDiagnosticas.getIdEnfermero() == idEnfermero);
			System.out.println(nuevaSemana == semana);
			System.out.println(fecha.getYear() == pruebasDiagnosticas.getFecha().getYear());

			if (pruebasDiagnosticas.getIdEnfermero() == idEnfermero && nuevaSemana == semana
					&& fecha.getYear() == pruebasDiagnosticas.getFecha().getYear()) {
				contador++;
			}
		}

		if (contador >= 5)
			System.out.println("Se han hecho ya 5 pruebas con el mismo enfermero/a");

		return contador >= 5;
	}

	public static void hacerPrueba(Long idEnfermero) {
		Scanner entradaEscaner = new Scanner(System.in);
		Long entradaTeclado;
		PruebasDiagnosticas pacientePrueba = null;
		boolean volver;
		do {
			volver = true;
			System.out.println("----------------------------------------");
			System.out.println("Listado de pruebas por hacer hoy");
			System.out.println("IdPaciente\tTipo");
			ArrayList<PruebasDiagnosticas> validos = new ArrayList<PruebasDiagnosticas>();
			for (PruebasDiagnosticas pruebasDiagnosticas : todos) {
				if (!pruebasDiagnosticas.isCompletado()
						&& pruebasDiagnosticas.getFecha().compareTo(LocalDate.now()) == 0
						&& pruebasDiagnosticas.getIdEnfermero() == idEnfermero) {
					validos.add(pruebasDiagnosticas);
					System.out.println(pruebasDiagnosticas.getIdPaciente() + "\t\t" + pruebasDiagnosticas.getTipo());
				}
			}

			System.out.println("Introduzca el id del paciente: ");
			try {
				entradaTeclado = Long.parseLong(entradaEscaner.nextLine());
			} catch (Exception e) {
				entradaTeclado = -1L;
				System.out.println("ha ocurrido un error con su elección: " + e);
			}

			for (PruebasDiagnosticas paciente : validos) {
				if (paciente.getIdPaciente() == entradaTeclado) {
					volver = false;
					pacientePrueba = paciente;
					break;
				}
			}

		} while (volver);

////////////////////////////////////////////////////////////

		System.out.println("Resultado");
		System.out.println("0 - negativo");
		System.out.println("1 - positivo");
		System.out.println();
		System.out.println("Introduzca el resultado:");

		boolean resultado = false;
		boolean repetir;
		do {
			repetir = false;
			try {
				resultado = entradaEscaner.nextLine().equals("1") ? true : false;
			} catch (Exception e) {
				repetir = true;
				System.out.println("ha ocurrido un error con su elección: " + e);
			}
		} while (repetir);
		pacientePrueba.setResultado(resultado);
		pacientePrueba.setCompletado(true);

		LocalDate proximaCita = LocalDate.now().plusDays(10);
		System.out.println("***********************************************");
		System.out.println("Debe de estar 10 días de cuarentena");
		System.out.println("Próxima cita: " + proximaCita);
		System.out.println("***********************************************");

		new PruebasDiagnosticas(pacientePrueba.getIdPaciente(), pacientePrueba.getIdEnfermero(),
				pacientePrueba.getIdTecnico(), "test serologicos", proximaCita);

////////////////////////////////////////////////////////////

		if (pacientePrueba.getTipo().equals("test serologicos")) {
			System.out.println("Indicar valor de anticuerpos (0-10): ");
			int anticuerpos;
			do {
				try {
					anticuerpos = Integer.parseInt(entradaEscaner.nextLine());
				} catch (Exception e) {
					anticuerpos = -1;
					System.out.println("ha ocurrido un error con su elección: " + e);
				}

				if (anticuerpos < 0 || anticuerpos > 10) {
					System.out.println("Valor de anticuerpos no válido, pruebe otra vez:");
				}

			} while (anticuerpos < 0 || anticuerpos > 10);

			pacientePrueba.setAnticuerpos(anticuerpos);

		}

	}

	public static boolean contarTecnicos(Long idTecnico, LocalDate fecha) {

		Calendar day = Calendar.getInstance();
		Date date;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(fecha.toString());
			day.setTime(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int semana = day.get(Calendar.WEEK_OF_YEAR);

		int contador = 0;

		Calendar calendar = Calendar.getInstance();

		for (PruebasDiagnosticas pruebasDiagnosticas : todos) {

			try {
				date = new SimpleDateFormat("yyyy-MM-dd").parse(pruebasDiagnosticas.getFecha().toString());
				day.setTime(date);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			int nuevaSemana = day.get(Calendar.WEEK_OF_YEAR);
			if (nuevaSemana == semana && fecha.getYear() == pruebasDiagnosticas.getFecha().getYear()) {
				if (pruebasDiagnosticas.getIdTecnico() == idTecnico) {
					contador++;
				}
			}
		}

		if (contador >= 4)
			System.out.println("Se han hecho ya 4 pruebas con el mismo técnico/a");

		return contador >= 4;
	}

	public static int diferenciaFechas(String fecha1, String fecha2) {
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha1);
			date2 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha2);

		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Calendar day1 = Calendar.getInstance();
		Calendar day2 = Calendar.getInstance();
		day1.setTime(date1);
		day2.setTime(date2);

		return Math.abs(day1.get(Calendar.DAY_OF_YEAR) - day2.get(Calendar.DAY_OF_YEAR));
	}

	public static void verProximosPacientes(Long idEnfermero) {
		System.out.println(
				"*************************************************************************************************************************");
		System.out.println("TODOS LOS PACIENTES");
		System.out.println(
				"*************************************************************************************************************************");
		System.out.println("TÉCNICO\t\tPACIENTE\tTIPO\t\t\tANTICUERPOS\tFECHA");
		for (PruebasDiagnosticas pruebasDiagnosticas : todos) {
			if (pruebasDiagnosticas.getIdEnfermero() == idEnfermero
					&& pruebasDiagnosticas.getFecha().compareTo(LocalDate.now()) >= 0)
				System.out.println(pruebasDiagnosticas.getIdTecnico() + "\t\t" + pruebasDiagnosticas.getIdPaciente()
						+ "\t\t" + pruebasDiagnosticas.getTipo()
						+ (pruebasDiagnosticas.getTipo().equals("PCR") ? "\t\t\t" : "\t")
						+ pruebasDiagnosticas.getAnticuerpos() + "\t\t" + pruebasDiagnosticas.getFecha());
		}
		System.out.println(
				"*************************************************************************************************************************");

	}

	public static void verTodosPacientes(Long idEnfermero) {
		System.out.println(
				"*************************************************************************************************************************");
		System.out.println("TODOS LOS PACIENTES");
		System.out.println(
				"*************************************************************************************************************************");
		System.out.println("TÉCNICO\t\tPACIENTE\tTIPO\t\t\tRESULTADO\tANTICUERPOS\tFECHA");
		for (PruebasDiagnosticas pruebasDiagnosticas : todos) {
			if (pruebasDiagnosticas.getIdEnfermero() == idEnfermero)
				System.out.println(pruebasDiagnosticas.getIdTecnico() + "\t\t" + pruebasDiagnosticas.getIdPaciente()
						+ "\t\t" + pruebasDiagnosticas.getTipo()
						+ (pruebasDiagnosticas.getTipo().equals("PCR") ? "\t\t\t" : "\t")
						+ (pruebasDiagnosticas.isResultado() ? "Positivo" : "Negativo") + "\t\t"
						+ pruebasDiagnosticas.getAnticuerpos() + "\t\t" + pruebasDiagnosticas.getFecha());
		}
		System.out.println(
				"*************************************************************************************************************************");

	}

	public static void verPacientesConfinados() {
		System.out.println(
				"*************************************************************************************************************************");
		System.out.println("TODOS LOS PACIENTES");
		System.out.println(
				"*************************************************************************************************************************");
		System.out.println("TÉCNICO\t\tPACIENTE\tTIPO\tRESULTADO\tANTICUERPOS\tFECHA\t\tFECHA FIN");
		for (PruebasDiagnosticas pruebasDiagnosticas : todos) {
			if (pruebasDiagnosticas.isResultado())
				System.out.println(pruebasDiagnosticas.getIdTecnico() + "\t\t" + pruebasDiagnosticas.getIdPaciente()
						+ "\t\t" + pruebasDiagnosticas.getTipo() + "\t"
						+ (pruebasDiagnosticas.isResultado() ? "Positivo" : "Negativo") + "\t"
						+ pruebasDiagnosticas.getAnticuerpos() + "\t\t" + pruebasDiagnosticas.getFecha() + "\t"
						+ pruebasDiagnosticas.getFecha().plusDays(10));
		}
		System.out.println(
				"*************************************************************************************************************************");
	}

}
