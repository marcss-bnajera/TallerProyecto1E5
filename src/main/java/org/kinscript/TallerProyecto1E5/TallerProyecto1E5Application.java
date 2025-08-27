package org.kinscript.TallerProyecto1E5;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import org.kinscript.TallerProyecto1E5.entity.Tarea;
import org.kinscript.TallerProyecto1E5.service.ITareaService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TallerProyecto1E5Application implements CommandLineRunner {
	//Inyeccion de dependencias
	@Autowired
	private ITareaService tareaService;

	//profesionalizar nuestro SOUT como un Logger
	private static final Logger logger = LoggerFactory.getLogger(TallerProyecto1E5Application.class);

	//Agregar un String para salto de linea
	String salto = System.lineSeparator();

	public static void main(String[] args) {
		//Antes de iniciar
		logger.info("Iniciando la Aplicacion");
		SpringApplication.run(TallerProyecto1E5Application.class, args);
		//Al finalizar
		logger.info("Aplicacion Finalizada");
	}

	@Override
	public void run(String... args) throws Exception {
		registroTareasApp();
	}

	private void registroTareasApp() {
		logger.info("=====Bienvenido a la aplicacion de Registro de Tareas=====");
		var salir = false;
		var consola = new Scanner(System.in);
		while (!salir) {
			try {
				var opcion = mostrarMenu(consola);
				salir = ejecutarOpciones(consola, opcion);
				logger.info(salto);
			} catch (Exception e) {
				logger.error("Ha ocurrido un error: " + e.getMessage());
			}
		}
	}

	private int mostrarMenu(Scanner consola) {
		logger.info("""
             ***===Aplicacion==***
             1. Listar Tareas
             2. Buscar Tareas
             3. Agregar Tareas
             4. Modificar Tareas
             5. Eliminar Tareas
             6. Salir del Programa
             """);
		logger.info("Ingrese su opcion: ");
		var opcion = Integer.parseInt(consola.nextLine());
		return opcion;
	}

	private boolean ejecutarOpciones(Scanner consola, int opcion) {
		var salir = false;
		switch (opcion) {
			case 1 -> {
				logger.info(salto+ "***==Lista Tareas==***" +salto);
				List<Tarea> tareas = tareaService.listarTarea();
				tareas.forEach(tarea -> logger.info(tarea.toString() + salto));
			}
			case 2 -> {
				logger.info("***==Buscar Tarea por su ID==***"+salto);
				logger.info("Ingrese el código de la Tarea a buscar: ");
				var codigo = Integer.parseInt(consola.nextLine());
				Tarea tarea = tareaService.buscarTareaporId(codigo);
				if (tarea != null) {
					logger.info("Tarea encontrada: " + tarea+salto);
				} else {
					logger.info("Tarea NO encontrada: " + codigo+salto);
				}
			}
			case 3 -> {
				logger.info("***==Agregar Tareas==***" +salto);
				logger.info("Ingrese el nombre de la Tarea: ");
				var nombre = consola.nextLine();
				logger.info("Ingrese la descripcion de la Tarea: ");
				var descripcion = consola.nextLine();
				logger.info("Ingrese la fecha limite de la Tarea (Ej: YYYY-MM-DD): ");
				var fechaLimiteStr = consola.nextLine();
				logger.info("Ingrese el estado de la Tarea (Ej: Pendiente | Completada): ");
				var estado = consola.nextLine();

				try {
					LocalDate fechaLimite = LocalDate.parse(fechaLimiteStr);
					var tarea = new Tarea();
					tarea.setNombre(nombre);
					tarea.setDescripcion(descripcion);
					tarea.setFechaLimite(fechaLimite);
					tarea.setEstado(estado);
					tareaService.guardarTarea(tarea);
					logger.info("Tarea agregada correctamente: " +tarea+salto);
				} catch (DateTimeParseException e) {
					logger.error("Formato de fecha inválido. Por favor, use YYYY-MM-DD.");
				}
			}
			case 4 -> {
				logger.info("***==Modificar Tarea==***" +salto);
				//Buscar por codigo
				logger.info("Ingrese el código de la Tarea a modificar: ");
				var codigo = Integer.parseInt(consola.nextLine());
				Tarea tarea = tareaService.buscarTareaporId(codigo);
				//Guardar si no es null
				if (tarea != null) {
					logger.info("***==Modificar Tareas==***" +salto);
					logger.info("Ingrese el nombre de la Tarea: ");
					var nombre = consola.nextLine();
					logger.info("Ingrese la descripcion de la Tarea: ");
					var descripcion = consola.nextLine();
					logger.info("Ingrese la fecha limite de la Tarea (Ej: YYYY-MM-DD): ");
					var fechaLimiteStr = consola.nextLine();
					logger.info("Ingrese el estado de la Tarea (Ej: Pendiente | Completada): ");
					var estado = consola.nextLine();

					try {
						LocalDate fechaLimite = LocalDate.parse(fechaLimiteStr);
						tarea.setNombre(nombre);
						tarea.setDescripcion(descripcion);
						tarea.setFechaLimite(fechaLimite);
						tarea.setEstado(estado);
						tareaService.guardarTarea(tarea);
						logger.info("Tarea modificada correctamente: " +tarea+salto);
					} catch (DateTimeParseException e) {
						logger.error("Formato de fecha inválido. Por favor, use YYYY-MM-DD.");
					}
				} else {
					logger.info("Tarea NO encontrada: " +codigo+salto);
				}
			}
			case 5 -> {
				logger.info("***==Eliminar Tarea==***" +salto);
				logger.info("Ingrese el codigo de la Tarea a Eliminar: ");
				var codigo = Integer.parseInt(consola.nextLine());
				var tarea = tareaService.buscarTareaporId(codigo);
				if (tarea != null) {
					tareaService.eliminarTarea(tarea);
					logger.info("Tarea eliminada correctamente: " +tarea+salto);
				} else {
					logger.info("Tarea NO encontrada: " +codigo+salto);
				}
			}
			case 6 -> {
				logger.info("Hasta la Proxima :)" +salto+salto);
				salir = true;
			}
			default -> logger.info("Opcion invalida, vuelva a intentarlo");
		}
		return salir;
	}
}