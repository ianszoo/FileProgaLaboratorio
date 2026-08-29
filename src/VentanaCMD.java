import javax.swing.*;
import java.awt.*;
import java.io.File;

public class VentanaCMD extends JFrame{

    private JTextArea areaSalidaTexto;
    private JTextField campoEntrada;
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

        setVisible(true);
    }
}
