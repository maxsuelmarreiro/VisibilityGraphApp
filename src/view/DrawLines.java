package view;

import grafoapp.Aresta;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class DrawLines {

    private ArrayList<Aresta> arestas;
    private ArrayList<Aresta> arestasTemp;
    private JFrame mainMap;
    
    public DrawLines() {
        initComponents();
    }

    private void initComponents() {

        mainMap = new JFrame();
        mainMap.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //mainMap.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMap.setLocationRelativeTo(null);
        mainMap.setVisible(true);

        int xx = 35;
        int cst = 500;

        int baseline = 200;

//        poly = new Polygon(xPoly, yPoly, xPoly.length);
//        poly1 = new Polygon(xPoly1, yPoly1, xPoly1.length);
        JPanel p = new JPanel() {
            @Override
            protected void paintComponent(Graphics gg) {
                super.paintComponent(gg);
                Graphics2D g = (Graphics2D) gg.create();
                //g.drawPolygon(poly);
                //g.drawPolygon(poly1);
                
                g.setStroke(new BasicStroke(2,BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                for (int i = 0; i < arestas.size(); i++) {
                    g.drawLine(((int) arestas.get(i).getV1().getX() * xx), cst - (int) arestas.get(i).getV1().getY() * xx,
                            ((int) arestas.get(i).getV2().getX() * xx), cst - (int) arestas.get(i).getV2().getY() * xx);
                }
                for (int i = 0; i < arestasTemp.size(); i++) {
                    g.setColor(Color.PINK);
                    g.drawLine(((int) arestasTemp.get(i).getV1().getX() * xx), cst - (int) arestasTemp.get(i).getV1().getY() * xx,
                            ((int) arestasTemp.get(i).getV2().getX() * xx), cst - (int) arestasTemp.get(i).getV2().getY() * xx);
                }
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(900, 700);
            }
        };

        mainMap.add(p);

        mainMap.pack();
        mainMap.setVisible(true);

    }


    public void setAresta(ArrayList<Aresta> a, ArrayList<Aresta> b) {
        this.arestas = a;
        this.arestasTemp = b;
    }
}
