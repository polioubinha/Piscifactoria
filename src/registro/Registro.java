package registro;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.File;

public class Registro {
    private BufferedWriter transcripcionWriter;
    private BufferedWriter logWriter;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Registro(String transcripcionDir, String logDir, String nombrePartida) {
        try {
            File transDir = new File(transcripcionDir);
            if (!transDir.exists()) {
                transDir.mkdirs();
            }
            File logDirFile = new File(logDir);
            if (!logDirFile.exists()) {
                logDirFile.mkdirs();
            }
            transcripcionWriter = new BufferedWriter(new FileWriter(transcripcionDir + "/" + nombrePartida + ".tr"));
            logWriter = new BufferedWriter(new FileWriter(logDir + "/" + nombrePartida + ".log"));
        } catch (IOException e) {
            System.out.println("Error al crear archivos de registro: " + e.getMessage());
        }
    }

    public void registrarAccion(String accion) {
        String tiempo = LocalDateTime.now().format(formatter);
        String registro = "[" + tiempo + "] " + accion;
        try {
            if (transcripcionWriter != null) {
                transcripcionWriter.write(registro + "\n");
                transcripcionWriter.flush();
            }
            if (logWriter != null) {
                logWriter.write(registro + "\n");
                logWriter.flush();
            }
        } catch (IOException e) {
            System.out.println("Error al registrar la acción: " + e.getMessage());
        }
    }

    public void cerrarRegistro() {
        try {
            if (transcripcionWriter != null) {
                transcripcionWriter.close();
            }
            if (logWriter != null) {
                logWriter.close();
            }
        } catch (IOException e) {
            System.out.println("Error al cerrar los archivos de registro: " + e.getMessage());
        }
    }
}