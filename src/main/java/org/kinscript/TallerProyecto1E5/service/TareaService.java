package org.kinscript.TallerProyecto1E5.service;

import org.kinscript.TallerProyecto1E5.entity.Tarea;
import org.kinscript.TallerProyecto1E5.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TareaService implements ITareaService{
    //Inyeccion de dependencias
    @Autowired
    private TareaRepository tareaRepository;

    @Override
    public List<Tarea> listarTarea() {
        List<Tarea> tareas = tareaRepository.findAll();
        return tareas;
    }

    @Override
    public Tarea buscarTareaporId(Integer codigo) {
        Tarea tarea = tareaRepository.findById(codigo).orElse(null);
        return  tarea;
    }

    @Override
    public void guardarTarea(Tarea tarea) {
        tareaRepository.save(tarea);
    }

    @Override
    public void eliminarTarea(Tarea tarea) {
        tareaRepository.delete(tarea);
    }


}
