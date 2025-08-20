package org.kinscript.TallerProyecto1E5.service;
import org.kinscript.TallerProyecto1E5.entity.Tarea;

import java.util.List;

public interface ITareaService {
    public List<Tarea> listarTarea();
    public Tarea buscarTareaporId(Integer codigo);
    public void guardarTarea(Tarea tarea);
    public void eliminarTarea(Tarea tarea);
}
