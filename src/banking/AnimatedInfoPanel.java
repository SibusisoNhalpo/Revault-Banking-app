package banking;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

public class AnimatedInfoPanel extends JPanel {

    private int bar1Height = 80;
    private int bar2Height = 120;
    private int bar3Height = 60;

    private int delta1 = 2;
    private int delta2 = 3;
    private int delta3 = 2;

    private float glowPulse = 0f;
    private int glowDirection = 1;

    public AnimatedInfoPanel() {
        setOpaque(true);
        setBackground(Color.WHITE);

        Timer timer = new Timer(35, e -> updateAnimation());
        timer.start();
    }

    private void updateAnimation() {

        // ★ FIXED: Bars now increase/decrease properly
        bar1Height += delta1;
        bar2Height += delta2;
        bar3Height += delta3;

        if (bar1Height > 230 || bar1Height < 50) delta1 *= -1;
        if (bar2Height > 230 || bar2Height < 50) delta2 *= -1;
        if (bar3Height > 230 || bar3Height < 50) delta3 *= -1;

        // Neon breathing effect
        glowPulse += glowDirection * 0.05f;
        if (glowPulse >= 1f || glowPulse <= 0f)
            glowDirection *= -1;

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawProfessionalGradientWave(g2);
        drawTitle(g2, getWidth());
        drawNeonBars(g2);

        g2.dispose();
    }

    // -------------------------------------------------------------
    // 1. PROFESSIONAL GRADIENT WAVE BACKGROUND
    // -------------------------------------------------------------
    private void drawProfessionalGradientWave(Graphics2D g2) {

        int w = getWidth();
        int h = getHeight();

        // Subtle premium banking gradient
        GradientPaint gp = new GradientPaint(
                0, 0, new Color(245, 245, 245),
                w, h, new Color(225, 225, 225)
        );
        g2.setPaint(gp);
        g2.fillRect(0, 0, w, h);

        // Smooth wave path (premium, soft, not childish)
        Path2D wave = new Path2D.Float();
        wave.moveTo(0, h * 0.65);

        wave.curveTo(
                w * 0.25, h * 0.60,
                w * 0.35, h * 0.75,
                w * 0.50, h * 0.70
        );

        wave.curveTo(
                w * 0.70, h * 0.63,
                w * 0.85, h * 0.80,
                w, h * 0.68
        );

        wave.lineTo(w, h);
        wave.lineTo(0, h);
        wave.closePath();

        // Subtle deep navy wave
        g2.setColor(new Color(20, 30, 60, 55));
        g2.fill(wave);
    }

    // -------------------------------------------------------------
    // 2. TITLE
    // -------------------------------------------------------------
    private void drawTitle(Graphics2D g2, int panelWidth) {
        String title = "Banking Activity Overview";

        g2.setFont(new Font("SansSerif", Font.BOLD, 22));
        g2.setColor(new Color(30, 30, 30));

        FontMetrics fm = g2.getFontMetrics();
        int x = (panelWidth - fm.stringWidth(title)) / 2;

        g2.drawString(title, x, 45);
    }

    // -------------------------------------------------------------
    // 3. NEON BARS WITH ANIMATION
    // -------------------------------------------------------------
    private void drawNeonBars(Graphics2D g2) {

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        int barWidth = 140;
        int spacing = 70;

        int totalWidth = (barWidth * 3) + (spacing * 2);
        int startX = (panelWidth - totalWidth) / 2;
        int yBase = panelHeight - 70;

        int[] heights = {bar1Height, bar2Height, bar3Height};
        int[] xPos = {
                startX,
                startX + barWidth + spacing,
                startX + (barWidth + spacing) * 2
        };

        String[] labels = {"Withdrawals", "Deposits", "Transfers"};

        // Banking colors
        Color[] fillColors = {
                new Color(40, 110, 200),   // Blue
                new Color(15, 160, 180),   // Teal
                new Color(20, 35, 90)      // Navy
        };

        // Neon glow colors
        Color[] neonGlow = {
                new Color(40, 160, 255, 140),
                new Color(0, 230, 220, 140),
                new Color(90, 120, 255, 140)
        };

        g2.setFont(new Font("SansSerif", Font.BOLD, 17));
        FontMetrics fm = g2.getFontMetrics();

        for (int i = 0; i < 3; i++) {

            int h = heights[i];
            int x = xPos[i];

            // -- Neon Glow (breathing pulse) --
            int glowSize = (int) (20 + glowPulse * 20);
            g2.setColor(neonGlow[i]);
            g2.fillRoundRect(
                    x - glowSize / 2,
                    yBase - h - glowSize / 2,
                    barWidth + glowSize,
                    h + glowSize,
                    28, 28
            );

            // Bar shadow
            g2.setColor(new Color(0, 0, 0, 35));
            g2.fillRoundRect(x + 4, yBase - h + 10, barWidth, h, 25, 25);

            // Bar fill
            g2.setColor(fillColors[i]);
            g2.fillRoundRect(x, yBase - h, barWidth, h, 25, 25);

            // Neon outline (dynamic)
            g2.setStroke(new BasicStroke(4f));
            Color outline = new Color(
                    neonGlow[i].getRed(),
                    neonGlow[i].getGreen(),
                    neonGlow[i].getBlue(),
                    (int) (120 + glowPulse * 120)
            );
            g2.setColor(outline);
            g2.drawRoundRect(x, yBase - h, barWidth, h, 25, 25);

            // Label
            int lw = fm.stringWidth(labels[i]);
            int lx = x + (barWidth - lw) / 2;

            g2.setColor(new Color(40, 40, 40));
            g2.drawString(labels[i], lx, yBase + 35);
        }
    }
}