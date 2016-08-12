/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import grafoapp.Aresta;
import grafoapp.Grafo;
import grafoapp.Vertice;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.SwingUtilities;

/**
 *
 * @author '-Maxsuel
 */
public class GraphView extends javax.swing.JPanel {

    public static Grafo grafo = new Grafo();
    public ArrayList<Aresta> arestas = grafo.getArestas();
    public ArrayList<Aresta> arestasTemp = grafo.getArestasTemp();

    public ArrayList<Aresta> arestasMenorCaminho = new ArrayList<>();

    public ArrayList<Vertice> PontosClick = new ArrayList<>();

    Graphics2D g;
    Graphics gg;

    public void menorCaminho(String caminho) {
        ArrayList<String> dados = new ArrayList<>();

        StringTokenizer st = new StringTokenizer(caminho);
        while (st.hasMoreTokens()) {
            dados.add(st.nextToken());
        }

        for (int i = 0; i < dados.size(); i++) {
            System.out.println(dados.size() - 1 + " // " + i);
            Aresta aresta0 = new Aresta();
            if (i == dados.size() - 1) {

            } else {
                aresta0.setV1(grafo.buscarVertice(Integer.valueOf(dados.get(i))));
                aresta0.setV2(grafo.buscarVertice(Integer.valueOf(dados.get(i + 1))));
                arestasMenorCaminho.add(aresta0);
            }
        }

        repaint();
    }

    String inserirObstaculo = "";
    String inserirObstaculoINTERFACE = "";
    int qtdLadosObstaculo = 0;

    public void reiniciar() {
        grafo = new Grafo();
        arestas = grafo.getArestas();
        arestasTemp = grafo.getArestasTemp();
        arestasMenorCaminho = new ArrayList<>();

        PontosClick.removeAll(PontosClick);
    }

    int xx = 1;
    int cst = 600; //plot
    Random random = new Random();

    public int getCst() {
        return cst;
    }

    public void setCst(int cst) {
        this.cst = cst;
    }

    @Override
    protected void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        g = (Graphics2D) gg.create();

        g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.setColor(Color.decode("#6A71E6"));
        for (int i = 0; i < arestasTemp.size(); i++) {
            g.drawLine(((int) arestasTemp.get(i).getV1().getX() * xx), cst - (int) arestasTemp.get(i).getV1().getY() * xx,
                    ((int) arestasTemp.get(i).getV2().getX() * xx), cst - (int) arestasTemp.get(i).getV2().getY() * xx);
        }

        g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.setColor(Color.BLACK);
        //g.drawLine(random.nextInt(500), random.nextInt(500), random.nextInt(1000), random.nextInt(1000));
        for (int i = 0; i < arestas.size(); i++) {
            g.drawLine(((int) arestas.get(i).getV1().getX() * xx), cst - (int) arestas.get(i).getV1().getY() * xx,
                    ((int) arestas.get(i).getV2().getX() * xx), cst - (int) arestas.get(i).getV2().getY() * xx);
        }

        if (arestasMenorCaminho != null) {
            g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.setColor(Color.decode("#FF5534"));
            for (int i = 0; i < arestasMenorCaminho.size(); i++) {
                g.drawLine(((int) arestasMenorCaminho.get(i).getV1().getX() * xx), cst - (int) arestasMenorCaminho.get(i).getV1().getY() * xx,
                        ((int) arestasMenorCaminho.get(i).getV2().getX() * xx), cst - (int) arestasMenorCaminho.get(i).getV2().getY() * xx);
            }
        }

        //draw pontos
        for (Vertice p : PontosClick) {
            g.fillOval((int) (p.getX() * xx) - 5, (int) (cst - p.getY() * xx) - 5, 10, 10);
            //g.drawLine((int)p.getX()*xx, (int) (cst-p.getY()*xx), 0, 0);
        }
    }

    public void setAresta(ArrayList<Aresta> a, ArrayList<Aresta> b) {
        this.arestas = a;
        this.arestasTemp = b;
    }

    public GraphView() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    double clickX = e.getX();
                    double clickY = (cst - e.getY());
                    inserirObstaculo = inserirObstaculo + " " + (clickX) + " " + (clickY);
                    inserirObstaculoINTERFACE = inserirObstaculoINTERFACE + " (" + (clickX) + "," + clickY + ")";
                    jLabel1.setText(inserirObstaculoINTERFACE);
                    qtdLadosObstaculo++;

                    Vertice v = new Vertice();
                    v.setX(clickX);
                    v.setY(clickY);
                    PontosClick.add(v);
                    repaint();

                } else if (SwingUtilities.isRightMouseButton(e)) {
                    String enviar = qtdLadosObstaculo + "" + inserirObstaculo;
                    System.out.println(enviar);
                    grafo.inserirObstaculo(new Scanner("" + enviar));
                    qtdLadosObstaculo = 0;
                    inserirObstaculo = "";
                    inserirObstaculoINTERFACE = "";
                    jLabel1.setText("");

                    //PontosClick.removeAll(PontosClick);
                    repaint();
                }
            }
        });

        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this
     * code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jColorChooser1 = new javax.swing.JColorChooser();
        jLabel1 = new javax.swing.JLabel();

        jLabel1.setText("  ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 536, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JColorChooser jColorChooser1;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
