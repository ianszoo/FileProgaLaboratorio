import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;

public class VentanaCMD extends JFrame{

    private JTextArea areaSalidaTexto;
    private JTextField campoEntradaTexto;
    private JLabel labelPrompt;
    private File directorioRaiz;
    private File directorioActual;
    private boolean modoEscritura = false;
    private CMDArchivos archivos;

    private List<String> bufferTexto;
    private String archivoActual;
    private String comandoActual;

    public VentanaCMD() {
        setTitle("Símbolo del sistema simulado - Equipo 4");
        setMinimumSize(new Dimension(700,450));
        setSize(900,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

    Color cFondo = new Color (12,12,12);
    setForeground(cFondo);
    Color cTexto = new Color (20,220,20);
    Font fuenteTexto = new Font ("Consolas",Font.PLAIN,18);

    areaSalidaTexto = new JTextArea();
    areaSalidaTexto.setBackground(cFondo);
    areaSalidaTexto.setForeground(cTexto);
    areaSalidaTexto.setFont(fuenteTexto);
    areaSalidaTexto.setEditable(false);
    areaSalidaTexto.setLineWrap(true);
    areaSalidaTexto.setWrapStyleWord(true);

    JScrollPane scroll = new JScrollPane(areaSalidaTexto);
    scroll.setBorder(null);
    scroll.getVerticalScrollBar().setBackground(cFondo);
    add(scroll, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(cFondo);

    arrancarEntorno();

        labelPrompt = new JLabel(conseguirRutaPrompt());
        labelPrompt.setForeground(cTexto);
        labelPrompt.setFont(fuenteTexto);
        labelPrompt.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

        campoEntradaTexto = new JTextField();
        campoEntradaTexto.setBackground(cFondo);
        campoEntradaTexto.setForeground(cTexto);
        campoEntradaTexto.setFont(fuenteTexto);
        campoEntradaTexto.setCaretColor(cTexto);
        campoEntradaTexto.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        panelInferior.add(labelPrompt, BorderLayout.WEST);
        panelInferior.add(campoEntradaTexto, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
        campoEntradaTexto.addActionListener((ActionEvent e) -> {
            String comandoIngresado = campoEntradaTexto.getText();
            campoEntradaTexto.setText("");
            if (!comandoIngresado.trim().isEmpty()) {
                imprimir(conseguirRutaPrompt() + comandoIngresado);
                procesarComando();
            }
            campoEntradaTexto.requestFocusInWindow();
        });

        areaSalidaTexto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                campoEntradaTexto.requestFocusInWindow();
            }
        });

        setVisible(true);
    }

    private void arrancarEntorno() {
        directorioRaiz = new File(System.getProperty("user.dir"), "CMD");
        if (!directorioRaiz.exists()) {
            directorioRaiz.mkdirs();
        }
        directorioActual = directorioRaiz;
    }

    private String conseguirRutaPrompt() {
        if (modoEscritura) {
            return "> ";
        }
        String rutaRelativa = directorioActual.getAbsolutePath().substring(directorioRaiz.getAbsolutePath().length());
        return "C:\\CMD_ProgramacionII" + rutaRelativa + ">";
    }

    public void actualizarPrompt() {
        labelPrompt.setText(conseguirRutaPrompt());

    }

    public void imprimir(String texto) {
        areaSalidaTexto.append(texto + "\n");
        areaSalidaTexto.setCaretPosition(areaSalidaTexto.getDocument().getLength());
    }

    private void procesarComando(){
        String entrada = campoEntradaTexto.getText().trim();

        if (modoEscritura){
            if(entrada.equalsIgnoreCase("EXIT")) {
                modoEscritura = false;
                String resultado = "";

                if (comandoActual.equalsIgnoreCase("wr")) {
                    resultado = archivos.wr(archivoActual, bufferTexto);
                } else if (comandoActual.equalsIgnoreCase("ap")) {
                    resultado = archivos.ap(archivoActual, bufferTexto);
                }

                imprimir(resultado);
                actualizarPrompt();

            } else {
                bufferTexto.add(entrada);
            }
            return;
        }
        String[] partes = entrada.split("\\s+");
        String comando = partes[0].toLowerCase();
        String parametro1 = partes.length > 1 ? partes[1] : "";
        String parametro2 = partes.length > 2 ? partes[2] : "";

        switch (comando) {
            case "rd":
                imprimir(archivos.rd(parametro1));
                break;
            case "wr":
            case "ap":
                if (parametro1.isEmpty()) {
                    imprimir("Error: Debe agregar un nombre de archivo.");
                    break;
                }
                modoEscritura = true;
                comandoActual = comando;
                archivoActual = parametro1;
                bufferTexto = new java.util.ArrayList<>();
                imprimir("Modo de edición. Ingrese un texto. Escriba EXIT en mayúsculas para guardar y salir.");
                actualizarPrompt();
                break;

            case "copy":
                imprimir(archivos.copy(parametro1, parametro2));
                break;

            case "info":
                imprimir(archivos.info(parametro1));
                break;

            case "find":
                imprimir(archivos.find(parametro1));
                break;

            case "tree":
                imprimir(archivos.tree());
                break;

            case "help":
                imprimir(archivos.help());
                break;

            case "cls":
                areaSalidaTexto.setText("");
                break;

            case "exit":
                System.exit(0);
                break;

            default:
                imprimir("'" + partes[0] + "' no es un comando válido.");
                break;
        }
    }


}