package src;
import javax.swing.*;

import java.awt.*;

public class ForestGUI extends JFrame {
    private JPanel[][] cells; 
    private JLabel[][] cellLabels; 
    private JTextArea logArea;  
    public static final int SIZE = Main.SIZE;

    public ForestGUI() {
        setTitle("Simulação de Detecção de Incêndios");
        setSize(1200, 800); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel forestPanel = new JPanel();
        forestPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        cells = new JPanel[SIZE][SIZE];
        cellLabels = new JLabel[SIZE][SIZE];

        for (int col = 1; col <= SIZE; col++) {
            gbc.gridx = col;
            gbc.gridy = 0;
            JLabel label = new JLabel(String.valueOf(col - 1), SwingConstants.CENTER);
            forestPanel.add(label, gbc);
        }

        for (int i = 0; i < SIZE; i++) {
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            JLabel rowLabel = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            forestPanel.add(rowLabel, gbc);

            for (int j = 0; j < SIZE; j++) {
                gbc.gridx = j + 1;
                gbc.gridy = i + 1;
                cells[i][j] = new JPanel();
                cells[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY)); 
                cellLabels[i][j] = new JLabel();
                cellLabels[i][j].setFont(new Font("Arial", Font.BOLD, 12)); 
                cellLabels[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                cellLabels[i][j].setVerticalAlignment(SwingConstants.CENTER);

                updateCell(i, j, Main.forest[i][j]);
                cells[i][j].setPreferredSize(new Dimension(20, 20)); 
                cells[i][j].add(cellLabels[i][j]); 
                forestPanel.add(cells[i][j], gbc); 
            }
        }

        logArea = new JTextArea(30, 40); 
        logArea.setEditable(false); 
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane logScrollPane = new JScrollPane(logArea);
        logScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, forestPanel, logScrollPane);
        splitPane.setDividerLocation(700);
        
        add(splitPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public void updateCell(int x, int y, char state) {
        switch (state) {
            case '-': // Área livre
                cells[x][y].setBackground(Color.GREEN);
                cellLabels[x][y].setText("-"); 
                break;
            case 'T': // Célula monitorada
                cells[x][y].setBackground(Color.BLUE);
                cellLabels[x][y].setText("T"); 
                break;
            case '@': // Incêndio ativo
                cells[x][y].setBackground(Color.RED);
                cellLabels[x][y].setText("@"); 
                break;
            case '/': // Célula queimada
                cells[x][y].setBackground(Color.BLACK);
                cellLabels[x][y].setForeground(Color.WHITE); 
                cellLabels[x][y].setText("/"); 
                break;
        }
    }

    public void refreshForest() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                updateCell(i, j, Main.forest[i][j]);
            }
        }
        repaint(); 
    }

    public void addLog(String log) {
        logArea.append(log + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength()); 
    }
}
