
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JComponent;

public class AnalogStopwatch extends JComponent {

    int w = 300, h = 300; // width, height

    Color bg = new Color(242, 242, 248), middleDot = new Color(122, 122, 128),
            ind1 = new Color(0, 0, 10, 10), ind2 = new Color(0, 0, 10, 15), // seconds indicator dots
            bgSmallCircle = new Color(0, 0, 10, 5),
            dot = new Color(0, 0, 10, 20);

    Color[] pointerCol = {new Color(184, 190, 221), new Color(128, 171, 221), new Color(63, 103, 234), new Color(143, 219, 222)};

    public AnalogStopwatch() {
        new Thread(() -> {
            while (true) {

                try {
                    repaint();
                    Thread.sleep(14);
                } catch (InterruptedException ex) {
                }
            }
        }).start();
    }

    public void renderIndicators(Graphics2D g2) {
        int counter = 2;
        for (int i = 0; i < 360; i += 6) {
            float cos = MH.cos(i);
            float sin = MH.sin(i);

            float size = (int) (w * 0.48f);
            float x = cos * size;
            float y = sin * size;
            g2.setColor(i % 15 == 0 ? ind2 : ind1);
            int s = i % 15 == 0 ? 6 : 4;
            DH.fillOval(g2, w * 0.5f + x, h * 0.5f + y, s, s, true);
        }
    }

    public void renderHs(Graphics2D g2) {
        g2.setColor(bgSmallCircle);
        float cx = w * 0.5f; // center x
        float cy = h * 0.5f + h * -0.25f; // center y
        g2.fillOval((int) (cx - 40), (int) (cy - 40), 80, 80);

        // draw center
        g2.setColor(dot);
        g2.fillOval((int) (cx - 4), (int) (cy - 4), 8, 8);

        float hs = 360f * (Main.dsw.time[0] / 12f);
        hs -= 90;
        float cos = MH.cos(hs);
        float sin = MH.sin(hs);

        g2.setColor(pointerCol[0]);
        DH.drawLine(g2, cx + cos * 7, cy + sin * 7, cx + cos * 35, cy + sin * 35, 2, false);
    }

    public void renderMins(Graphics2D g2) {
        g2.setColor(bgSmallCircle);
        float cx = w * 0.5f + w * -0.22f; // center x
        float cy = h * 0.5f + h * 0.15f; // center y
        g2.fillOval((int) (cx - 50), (int) (cy - 50), 100, 100);

        // draw center
        g2.setColor(dot);
        g2.fillOval((int) (cx - 4), (int) (cy - 4), 8, 8);

        float mins = 360f * (Main.dsw.time[1] / 60f);
        mins -= 90;
        float cos = MH.cos(mins);
        float sin = MH.sin(mins);

        g2.setColor(pointerCol[1]);
        DH.drawLine(g2, cx + cos * 7, cy + sin * 7, cx + cos * 42, cy + sin * 42, 2, false);
    }

    public void renderSecs(Graphics2D g2) {
        float secs = 360f * (Main.dsw.time[2] / 60f);
        secs -= 90;
        float cos = MH.cos(secs);
        float sin = MH.sin(secs);

        g2.setColor(pointerCol[2]);
        DH.drawLine(g2, w * 0.5f + cos * 16, h * 0.5f + sin * 16, w * 0.5f + cos * (w / 2 * 0.9f), h * 0.5f + sin * (h / 2 * 0.9f), 4, false);
    }

    public void renderMs(Graphics2D g2) {
        g2.setColor(bgSmallCircle);
        float cx = w * 0.5f + w * 0.22f; // center x
        float cy = h * 0.5f + h * 0.15f; // center y
        g2.fillOval((int) (cx - 50), (int) (cy - 50), 100, 100);

        // draw center
        g2.setColor(dot);
        g2.fillOval((int) (cx - 4), (int) (cy - 4), 8, 8);

        float ms = 360f * (Main.dsw.time[3] / 1000f);
        ms -= 90;
        float cos = MH.cos(ms);
        float sin = MH.sin(ms);

        g2.setColor(pointerCol[3]);
        DH.drawLine(g2, cx + cos * 7, cy + sin * 7, cx + cos * 42, cy + sin * 42, 2, false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        int w = getWidth(), h = getHeight();

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(bg);
        g2.fillOval(0, 0, w, h);

        g2.setColor(middleDot);
        g2.fillOval(w / 2 - 8, h / 2 - 8, 16, 16);

        renderIndicators(g2);

        renderHs(g2);
        renderMins(g2);
        renderMs(g2);

        renderSecs(g2);
    }

    public Color getBg() {
        return bg;
    }

    public void setBg(Color bg) {
        this.bg = bg;
    }

    public Color getMiddleDot() {
        return middleDot;
    }

    public void setMiddleDot(Color middleDot) {
        this.middleDot = middleDot;
    }

    public Color getInd1() {
        return ind1;
    }

    public void setInd1(Color ind1) {
        this.ind1 = ind1;
    }

    public Color getInd2() {
        return ind2;
    }

    public void setInd2(Color ind2) {
        this.ind2 = ind2;
    }

    public Color getBgSmallCircle() {
        return bgSmallCircle;
    }

    public void setBgSmallCircle(Color bgSmallCircle) {
        this.bgSmallCircle = bgSmallCircle;
    }

    public Color getDot() {
        return dot;
    }

    public void setDot(Color dot) {
        this.dot = dot;
    }

    public Color[] getPointerCol() {
        return pointerCol;
    }

    public void setPointerCol(Color[] pointerCol) {
        this.pointerCol = pointerCol;
    }
    
    
}
