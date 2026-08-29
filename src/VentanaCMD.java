import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VentanaCMD extends JFrame{

    private JTextArea areaSalidaTexto;
    private JTextField campoEntradaTexto;
    private JLabel labelPrompt;
    private File directorioRaiz;
    private File directorioActual;
    private boolean modoEscritura = false;

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
                procesarComando(comandoIngresado);
            }
            campoEntradaTexto.requestFocusInWindow();
        });

        areaSalidaTexto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                campoEntradaTexto.requestFocusInWindow();
            }
        });
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
        return "C:\\CMD_ProgramaciónII" + rutaRelativa + ">";
    }

    public void actualizarPrompt() {
        labelPrompt.setText(conseguirRutaPrompt());

    }

    public void imprimir(String texto) {
        areaSalidaTexto.append(texto + "\n");
        areaSalidaTexto.setCaretPosition(areaSalidaTexto.getDocument().getLength());
    }



}