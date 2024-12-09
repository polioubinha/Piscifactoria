package registro;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.File;

/**
 * Clase Registro para gestionar los logs y las transcripciones de la
 * simulación.
 */
public class Registro {
    /** Instancia del registro */
    private static Registro instance;
    /** Elemento para escribir las transcripciones */
    private BufferedWriter transcripcionWriter;
    /** Elemento para escribir los logs */
    private BufferedWriter logWriter;
    /** Formato */
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Constructor privado de la clase Registro.
     * Se encarga de crear los archivos de transcripción y log en las rutas
     * especificadas.
     *
     * @param transcripcionDir Directorio donde se guardarán las transcripciones.
     * @param logDir           Directorio donde se guardarán los logs.
     * @param nombrePartida    Nombre de la partida, utilizado para nombrar los
     *                         archivos.
     */
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

    /**
     * Obtiene la instancia única de la clase Registro.
     * Si no existe, crea una nueva instancia.
     *
     * @param transcripcionDir Directorio donde se guardarán las transcripciones.
     * @param logDir           Directorio donde se guardarán los logs.
     * @param nombrePartida    Nombre de la partida, utilizado para nombrar los
     *                         archivos.
     * @return La instancia única de la clase Registro.
     */
    public static Registro getInstance(String transcripcionDir, String logDir, String nombrePartida) {
        if (instance == null) {
            instance = new Registro(transcripcionDir, logDir, nombrePartida);
        }
        return instance;
    }

    /**
     * Método interno para registrar un mensaje en un archivo específico.
     *
     * @param mensaje Mensaje a registrar.
     * @param writer  Writer asociado al archivo de destino.
     */
    private void registrar(String mensaje, BufferedWriter writer) {
        try {
            String timestamp = "[" + LocalDateTime.now().format(formatter) + "] ";
            writer.write(timestamp + mensaje + "\n");
            writer.flush();
        } catch (IOException e) {
            System.out.println("Error al registrar: " + e.getMessage());
        }
    }

    /**
     * Registra el inicio de la simulación con los detalles iniciales.
     *
     * @param nombreEmpresa       Nombre de la empresa.
     * @param nombrePiscifactoria Nombre de la piscifactoría inicial.
     * @param pecesPorTipo        Detalles de los peces iniciales.
     * @param monedasIniciales    Cantidad inicial de monedas.
     */
    public void registrarInicio(String nombreEmpresa, String nombrePiscifactoria, String pecesPorTipo,
            int monedasIniciales) {
        StringBuilder inicio = new StringBuilder();
        inicio.append("Inicio de la simulación ").append(nombreEmpresa).append(".\n");
        inicio.append("Dinero inicial: ").append(monedasIniciales).append(" monedas.\n");
        inicio.append(pecesPorTipo).append("\n");
        inicio.append("-------------------------\n");
        inicio.append("Piscifactoría inicial: ").append(nombrePiscifactoria).append(".\n");
        registrar(inicio.toString(), transcripcionWriter);
    }

    /**
     * Registra una acción específica en el archivo de transcripción.
     *
     * @param mensaje Mensaje que describe la acción.
     */
    public void registrarAccion(String mensaje) {
        registrar(mensaje, transcripcionWriter);
    }

    /**
     * Registra un mensaje en el archivo de transcripción.
     *
     * @param mensaje Mensaje a registrar.
     */
    public void registrarTranscripcion(String mensaje) {
        registrar(mensaje, transcripcionWriter);
    }

    /**
     * Registra un mensaje en el archivo de logs.
     *
     * @param mensaje Mensaje a registrar.
     */
    public void registrarLog(String mensaje) {
        registrar(mensaje, logWriter);
    }

    /**
     * Registra un mensaje con un nivel de log específico (INFO, WARNING, ERROR).
     *
     * @param nivel   Nivel del log.
     * @param mensaje Mensaje a registrar.
     */
    public void registrarLog(String nivel, String mensaje) {
        try {
            if (logWriter != null) {
                String timestamp = "[" + LocalDateTime.now().format(formatter) + "] ";
                String log = timestamp + nivel + ": " + mensaje;
                logWriter.write(log + "\n");
                logWriter.flush();
            }
        } catch (IOException e) {
            System.out.println("Error al registrar en el log: " + e.getMessage());
        }
    }

    /**
     * Registra un mensaje de error en un archivo de errores.
     *
     * @param mensaje Mensaje de error a registrar.
     */
    public void registrarError(String mensaje) {
        try (BufferedWriter errorWriter = new BufferedWriter(new FileWriter("src/logs/0_errors.log", true))) {
            String timestamp = "[" + LocalDateTime.now().format(formatter) + "] ";
            errorWriter.write(timestamp + "ERROR: " + mensaje + "\n");
        } catch (IOException e) {
            System.out.println("Error al registrar en el log de errores: " + e.getMessage());
        }
    }

    /**
     * Cierra los archivos de registro y transcripción abiertos.
     */
    public void cerrarRegistro() {
        try {
            if (transcripcionWriter != null)
                transcripcionWriter.close();
            if (logWriter != null)
                logWriter.close();
        } catch (IOException e) {
            System.out.println("Error al cerrar los registros: " + e.getMessage());
        }
    }
}
