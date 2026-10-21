import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.util.Random;

/**
 * PROG209 - Event Driven Programming
 * Assignment 1: Basic Interactive GUI  (Style 2 - "Gradient Theme Studio")
 *
 * Inheritance : GradientPanel extends JPanel (custom painted background)
 * Constructor : GradientPanel(Color, Color) and SwingAssignment()
 * ImageIcon   : window icon + logo shown above the welcome message
 * Interaction : type your own text, random quote, switch colour theme
 */
public class SwingAssignment {

    // TODO: replace with your own full name
    private static final String STUDENT_NAME = "Your Name";

    // Required variables (names fixed by the brief)
    private JFrame frame;
    private JPanel panel;
    private JLabel label;

    // Extra components
    private JTextField input;

    // Theme data: {top colour, bottom colour}
    private static final String[] THEME_NAMES = {"Ocean", "Midnight", "Forest", "Royal", "Plum"};
    private static final Color[][] THEMES = {
        {new Color(0x0F2027), new Color(0x2C5364)},
        {new Color(0x141E30), new Color(0x35577D)},
        {new Color(0x134E5E), new Color(0x2E8B57)},
        {new Color(0x1E3C72), new Color(0x2A5298)},
        {new Color(0x41295A), new Color(0x8E4585)}
    };
    private static final String[] QUOTES = {
        "Code is like humour. When you have to explain it, it is bad.",
        "First, solve the problem. Then, write the code.",
        "Events drive the interface - the user is in control.",
        "Practice makes a programmer."
    };
    private int themeIndex = 0;
    private final Random random = new Random();

    /* ---------- INHERITANCE: GradientPanel IS-A JPanel ---------- */
    static class GradientPanel extends JPanel {
        private Color top;
        private Color bottom;

        // Constructor: stores the two gradient colours
        GradientPanel(Color top, Color bottom) {
            super(new GridBagLayout());
            this.top = top;
            this.bottom = bottom;
        }

        // Changes the background and repaints
        void setColors(Color top, Color bottom) {
            this.top = top;
            this.bottom = bottom;
            repaint();
        }

        // Overrides JPanel to paint a vertical gradient
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setPaint(new GradientPaint(0, 0, top, 0, getHeight(), bottom));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
        }
    }

    /* ---------- CONSTRUCTOR: builds the GUI ---------- */
    public SwingAssignment() {
        // JFrame (frame)
        frame = new JFrame("Java Swing Assignment");
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ImageIcon: own application icon
        ImageIcon icon = new ImageIcon("icon.png");
        if (icon.getIconWidth() > 0) {
            frame.setIconImage(icon.getImage());
        }

        // JPanel (panel) - an instance of the subclass GradientPanel
        panel = new GradientPanel(THEMES[0][0], THEMES[0][1]);

        // Logo using ImageIcon (scaled)
        JLabel logo = new JLabel();
        if (icon.getIconWidth() > 0) {
            logo.setIcon(new ImageIcon(icon.getImage().getScaledInstance(96, 96, Image.SCALE_SMOOTH)));
        }

        // JLabel (label)
        label = new JLabel("Welcome to Java Swing First Individual Assignment by " + STUDENT_NAME);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        label.setForeground(Color.WHITE);

        // Controls
        input = new JTextField(28);
        input.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JButton applyBtn = makeButton("Apply Text");
        JButton quoteBtn = makeButton("Random Quote");
        JButton themeBtn = makeButton("Next Theme");

        applyBtn.addActionListener(e -> applyText());
        input.addActionListener(e -> applyText());          // Enter key also works
        quoteBtn.addActionListener(e -> label.setText(QUOTES[random.nextInt(QUOTES.length)]));
        themeBtn.addActionListener(e -> nextTheme());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        buttons.setOpaque(false);
        buttons.add(applyBtn);
        buttons.add(quoteBtn);
        buttons.add(themeBtn);

        // Layout inside panel
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.insets = new Insets(10, 10, 10, 10);
        c.gridy = 0; panel.add(logo, c);
        c.gridy = 1; panel.add(label, c);          // label added to panel
        c.gridy = 2; panel.add(input, c);
        c.gridy = 3; panel.add(buttons, c);

        frame.add(panel);                          // panel added to frame
        frame.setVisible(true);
    }

    private JButton makeButton(String text) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setPreferredSize(new Dimension(140, 36));
        return b;
    }

    // Change the text of the JLabel using what the user typed
    private void applyText() {
        String text = input.getText().trim();
        if (!text.isEmpty()) {
            label.setText(text);
            input.setText("");
        }
    }

    // Change the background of the JPanel
    private void nextTheme() {
        themeIndex = (themeIndex + 1) % THEMES.length;
        ((GradientPanel) panel).setColors(THEMES[themeIndex][0], THEMES[themeIndex][1]);
        frame.setTitle("Java Swing Assignment - " + THEME_NAMES[themeIndex] + " theme");
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }
        SwingUtilities.invokeLater(SwingAssignment::new);
    }
}
