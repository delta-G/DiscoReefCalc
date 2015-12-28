/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DosingComputerGUI;

import dosingcomputer.WaterTestRecord;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;
import javax.swing.JPanel;

/**
 *
 * @author Dave
 */
public class DatedGraphPanel extends JPanel {

    private static final int P_WIDTH = 1000;
    private static final int P_HEIGHT = 850;
    private static final int Y_BOTTOM_BORDER_GAP = 200;
    private static final int Y_TOP_BORDER_GAP = 50;
    private static final int X_BORDER_GAP = 50;
    private static final Color BACKGROUND_COLOR = Color.black;
    private static final Color LINE_COLOR = Color.green;
    private static final Color FORGROUND_COLOR = Color.white;
    private static final Stroke GRAPH_STROKE = new BasicStroke(3f);
    private static final int GRAPH_POINT_WIDTH = 12;
    private static final int Y_HATCH_CNT = 10;
    private TreeMap<Long, Double> data;
    private Long xMin;
    private Long xMax;
    private Double yMin;
    private Double yMax;

    public DatedGraphPanel(ArrayList<WaterTestRecord> adata) {

        data = new TreeMap<>();


        for (WaterTestRecord wtr : adata) {
            data.put(wtr.getTime(), wtr.getResult());
        }
        xMin = data.firstKey();
        xMax = data.lastKey();

        yMin = data.get(xMin);
        yMax = data.get(xMax);

        for (Double d : data.values()) {
            if (d < yMin) {
                yMin = d;
            }
            if (d > yMax) {
                yMax = d;
            }
        }
    }

    public DatedGraphPanel(TreeMap<Long, Double> adata) {
        this.data = adata;
        xMin = data.firstKey();
        xMax = data.lastKey();

        yMin = data.get(xMin);
        yMax = data.get(xMax);

        for (Double d : data.values()) {
            if (d < yMin) {
                yMin = d;
            }
            if (d > yMax) {
                yMax = d;
            }
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(BACKGROUND_COLOR);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setColor(FORGROUND_COLOR);

        // xScal in pixels per millisecond
        Double xScale = ((double) getWidth() - 2 * X_BORDER_GAP) / (xMax - xMin);
        // yScale in pixels per mM
        Double yScale = ((double) getHeight() - (Y_BOTTOM_BORDER_GAP + Y_TOP_BORDER_GAP)) / (yMax - yMin);

        ArrayList<Point> points = new ArrayList<>();
        for (Long lo : data.keySet()) {
            int x1 = (int) ((lo - xMin) * xScale) + X_BORDER_GAP;
            int y1 = (int) ((data.get(lo) - yMin) * yScale) + Y_TOP_BORDER_GAP;
            points.add(new Point(x1, y1));
        }

        g2.drawLine(X_BORDER_GAP, getHeight() - Y_BOTTOM_BORDER_GAP, X_BORDER_GAP, Y_TOP_BORDER_GAP);
        g2.drawLine(X_BORDER_GAP, getHeight() - Y_BOTTOM_BORDER_GAP, getWidth() - X_BORDER_GAP, getHeight() - Y_BOTTOM_BORDER_GAP);

        //  y hatches
        for (int i = 0; i < Y_HATCH_CNT; i++) {
            int x0 = X_BORDER_GAP;
            int x1 = GRAPH_POINT_WIDTH + X_BORDER_GAP;
            int y0 = getHeight() - (((i + 1) * ((getHeight() - (Y_BOTTOM_BORDER_GAP + Y_TOP_BORDER_GAP)) / Y_HATCH_CNT)) + Y_BOTTOM_BORDER_GAP);
            int y1 = y0;
            
            Double labelVal = (((i + 1) * ((yMax - yMin) / Y_HATCH_CNT)) + yMin);
            String labelStr = String.format("%.2f", labelVal);
            FontMetrics metrics = g2.getFontMetrics();
            int labelWidth = metrics.stringWidth(labelStr);
            g2.drawString(labelStr, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            
            g2.drawLine(x0, y0, x1, y1);
        }

        // x hatches

        for (Long lo : data.keySet()) {

            int x0 = (int) (((lo - xMin) * xScale) + X_BORDER_GAP);
            int x1 = x0;
            int y0 = getHeight() - Y_BOTTOM_BORDER_GAP;
            int y1 = y0 - GRAPH_POINT_WIDTH;

            String labelStr = new Date(lo).toString();
            FontMetrics metrics = g2.getFontMetrics();
            int labelWidth = metrics.stringWidth(labelStr);
            AffineTransform trans = new AffineTransform();
            trans.rotate(Math.toRadians(-90), 0, 0);
            Font oldFont = g2.getFont();
            Font rotatedFont = g2.getFont().deriveFont(trans);
            g2.setFont(rotatedFont);
            g2.drawString(labelStr, x0 + (metrics.getHeight() / 2) - 3, y0 + labelWidth + 5);
            g2.setFont(oldFont);
            
            g2.drawLine(x0, y0, x1, y1);

        }

        Stroke oldStroke = g2.getStroke();
        g2.setColor(LINE_COLOR);
        g2.setStroke(GRAPH_STROKE);

        for (int i = 0; i < points.size() - 1; i++) {
            int x0 = points.get(i).x;
            int y0 = points.get(i).y;
            int x1 = points.get(i + 1).x;
            int y1 = points.get(i + 1).y;
            g2.drawLine(x0, y0, x1, y1);
        }
        g2.setStroke(oldStroke);

    }
}
