package org.kinscript.TallerProyecto1E5.controller;


import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import org.kinscript.TallerProyecto1E5.entity.Tarea;
import org.kinscript.TallerProyecto1E5.service.ITareaService;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

//Componente generico
@Component
//alcance de tipo VIEW
@ViewScoped
//Getter u Setter de lombok
@Data
public class indexController {
    @Autowired
    ITareaService tareaService;
    private List<Tarea> tareas;
    private Tarea tareaSeleccionada;
    private static Logger logger = LoggerFactory.getLogger(indexController.class);

    @PostConstruct
    public void init() {
        cargarDatos();
    }

    public void cargarDatos() {
        this.tareas = this.tareaService.listarTarea();
        this.tareas.forEach(tarea -> logger.info(tarea.toString()));
    }

    public void agregarTarea() {
        this.tareaSeleccionada = new Tarea();
    }

    public void guardaTarea() {
        logger.info("Tarea a guardar: " +this.tareaSeleccionada);
        //agregar
        if (this.tareaSeleccionada.getCodigoTarea() == null) {
            this.tareaService.guardarTarea(this.tareaSeleccionada);
            this.tareas.add(this.tareaSeleccionada);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tarea agregado"));
        }
        //modificar
        else {
            this.tareaService.guardarTarea(this.tareaSeleccionada);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tarea actualizado"));
        }
        //ocultar la ventana modal
        PrimeFaces.current().executeScript("PF('ventanaModalTarea').hide");
        //Actualizar tabla utilizando tecnologia incorporando - AJAX -
        PrimeFaces.current().ajax().update("formularaio-tareas: mensaje-emergente", "formulario-tareas:table-tareas");
        //limpiar el objeto tarea seleccionada
        this.tareaSeleccionada = null;
    }

    public void eliminarTarea() {
        logger.info("Tarea a eliminar: " +this.tareaSeleccionada);
        this.tareaService.eliminarTarea(this.tareaSeleccionada);
        //Eliminar el registro de la lista de tareas
        this.tareas.remove(this.tareaSeleccionada);
        //Reiniciar el objeto tarea seleccionada
        this.tareaSeleccionada = null;
        //confirmar accion
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tarea eliminada"));
        PrimeFaces.current().ajax().update("formulario-tareas:mensaje-emergente", "formulario-tareas:table-tareas");
    }
}
