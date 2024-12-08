package registro;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.File;

public class Registro {
    private static Registro instance;
    private BufferedWriter transcripcionWriter;
    private BufferedWriter logWriter;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Registro(String transcripcionDir, String logDir, String nombrePartida) {
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

    public static Registro getInstance(String transcripcionDir, String logDir, String nombrePartida) {
        if (instance == null) {
            instance = new Registro(transcripcionDir, logDir, nombrePartida);
        }
        return instance;
    }

    private void registrar(String mensaje, BufferedWriter writer) {
        try {
            String timestamp = "[" + LocalDateTime.now().format(formatter) + "] ";
            writer.write(timestamp + mensaje + "\n");
            writer.flush();
        } catch (IOException e) {
            System.out.println("Error al registrar: " + e.getMessage());
        }
    }

    // Métodos específicos
    public void registrarInicio(String nombreEmpresa, String nombrePiscifactoria, String pecesPorTipo, int monedasIniciales) {
        StringBuilder inicio = new StringBuilder();
        inicio.append("Inicio de la simulación ").append(nombreEmpresa).append(".\n");
        inicio.append("Dinero inicial: ").append(monedasIniciales).append(" monedas.\n");
        inicio.append(pecesPorTipo).append("\n");
        inicio.append("-------------------------\n");
        inicio.append("Piscifactoría inicial: ").append(nombrePiscifactoria).append(".\n");
        registrar(inicio.toString(), transcripcionWriter);
    }

    public void registrarAccion(String mensaje) {
        registrar(mensaje, transcripcionWriter);
    }

    public void registrarTranscripcion(String mensaje) {
        registrar(mensaje, transcripcionWriter);
    }

    public void registrarLog(String mensaje) {
        registrar(mensaje, logWriter);
    }

    public void cerrarRegistro() {
        try {
            if (transcripcionWriter != null) transcripcionWriter.close();
            if (logWriter != null) logWriter.close();
        } catch (IOException e) {
            System.out.println("Error al cerrar los registros: " + e.getMessage());
        }
    }
}
