package grafoapp;

import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author '-Maxsuel
 */
public class Grafo {

    private int ID_AUTO_OBSTACULO = 0;
    private int ID_AUTO_VERTICE = 2;

    private ArrayList<Vertice> vertices;
    private ArrayList<Aresta> arestas;
    private ArrayList<Aresta> arestasTemp;

    private ArrayList<Aresta> menorCaminho;

    private boolean orientado;

    public Grafo() {
        this.vertices = new ArrayList<>();
        this.arestas = new ArrayList<>();
        this.arestasTemp = new ArrayList<>();
        ID_AUTO_OBSTACULO = 0;
        ID_AUTO_VERTICE = 2;
    }

    public void grafoVisibilidade() {
        long tempoInicio = System.currentTimeMillis();

        ArrayList<Vertice> tempVertice = getVertices();
        ArrayList<Aresta> tempArestas = getArestas();

        ArrayList<Vertice> tempLista = new ArrayList<>();

        for (Vertice ver1 : tempVertice) {
            for (Vertice ver2 : tempVertice) {
                if (ver1.getId() != ver2.getId()) {
                    if (ver1.getIdObstaculo() != ver2.getIdObstaculo()) {
                        int haObstaculos = 0;
                        for (Aresta aresta : tempArestas) {
                            if ((interseccaoRetas(ver1.getX(), ver1.getY(), ver2.getX(), ver2.getY(), aresta.getV1().getX(), aresta.getV1().getY(), aresta.getV2().getX(), aresta.getV2().getY()))) {

                                //IF ( Vertice1(X,Y) == Aresta1(X,Y) || Vertice1(X,Y) == Aresta2(X,Y) || 
                                //     Vertice2(X,Y) == Aresta1(X,Y) || Vertice2(X,Y) == Aresta2(X,Y))
                                if (((ver1.getX() == aresta.getV1().getX() && ver1.getY() == aresta.getV1().getY()) || (ver1.getX() == aresta.getV2().getX() && ver1.getY() == aresta.getV2().getY()) || (ver2.getX() == aresta.getV1().getX() && ver2.getY() == aresta.getV1().getY()) || (ver2.getX() == aresta.getV2().getX() && ver2.getY() == aresta.getV2().getY()))) {
                                } else {
                                    haObstaculos++;
                                }
                            } else {

                            }
                        }
                        if (haObstaculos == 0) {
                            //inserirAresta(ver1.getId(), ver2.getId());
                            tempLista.add(ver1);
                            tempLista.add(ver2);
                        }
                    } else {
                        //verificar se é adjacente 
                    }
                }
            }
        }
        for (int m = 0; m < tempLista.size(); m += 2) {
            inserirArestaTemp(tempLista.get(m).getId(), tempLista.get(m + 1).getId());
        }
        System.out.println("Tempo Total: " + (System.currentTimeMillis() - tempoInicio));
    }

    public boolean interseccaoRetas(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {

        return Line2D.linesIntersect(x1, y1, x2, y2, x3, y3, x4, y4);

    }

    public boolean verticeExiste(int v) {
        for (Vertice vertice : vertices) {
            if (vertice.getId() == v) {
                return true;
            }
        }
        return false;
    }

    public boolean arestaExiste(int v1, int v2) {
        for (Aresta aresta : arestas) {
            if ((aresta.getV1().getId() == v1 && aresta.getV2().getId() == v2) || (aresta.getV1().getId() == v2 && aresta.getV2().getId() == v1)) {
                return true;
            }
        }
        for (Aresta aresta : arestasTemp) {
            if ((aresta.getV1().getId() == v1 && aresta.getV2().getId() == v2) || (aresta.getV1().getId() == v2 && aresta.getV2().getId() == v1)) {
                return true;
            }
        }
        return false;
    }

    public boolean inserirVertice(int idVertice, int idObstaculo, double xVertice, double yVertice) {
        if (!verticeExiste(idVertice)) {
            Vertice addVertice = new Vertice();
            addVertice.setId(idVertice);
            addVertice.setIdObstaculo(idObstaculo);
            addVertice.setX(xVertice);
            addVertice.setY(yVertice);
            //addVertice.setPeso(pesoVertice);
            vertices.add(addVertice);
            return true;
        }
        return false;
    }

    public boolean inserirVertice(int idVertice, double xVertice, double yVertice) {
        if (!verticeExiste(idVertice)) {
            Vertice addVertice = new Vertice();
            addVertice.setId(idVertice);
            addVertice.setIdObstaculo(ID_AUTO_OBSTACULO);
            ID_AUTO_OBSTACULO++;
            addVertice.setX(xVertice);
            addVertice.setY(yVertice);
            //addVertice.setPeso(pesoVertice);
            vertices.add(addVertice);
            return true;
        }
        return false;
    }

    public boolean editVertice(int ID, double xVertice, double yVertice) {
        if (verticeExiste(ID)) {
            buscarVertice(ID).setX(xVertice);
            buscarVertice(ID).setY(yVertice);
            buscarVertice(ID).setAdjacente(new ArrayList<>());
            grafoVisibilidade();
            return true;
        }
        return false;
    }

    public boolean inserirAresta(int v1, int v2) {
        if (verticeExiste(v1) && verticeExiste(v2)) {
            if (!(arestaExiste(v1, v2))) {
                Vertice vrc1 = buscarVertice(v1);
                Vertice vrc2 = buscarVertice(v2);

                vrc1.getAdjacente().add(vrc2);
                vrc2.getAdjacente().add(vrc1);

                Aresta addAresta = new Aresta();
                addAresta.setV1(vrc1);
                addAresta.setV2(vrc2);
                //calcular tamanho da aresta (Distância entre dois pontos)
                double peso = Math.sqrt(Math.pow(vrc2.getX() - vrc1.getX(), 2) + Math.pow(vrc2.getY() - vrc1.getY(), 2));
                addAresta.setPeso(peso);
                arestas.add(addAresta);
                return true;
            }
        }
        return false;
    }

    public boolean inserirArestaTemp(int v1, int v2) {
        if (verticeExiste(v1) && verticeExiste(v2)) {
            if (!(arestaExiste(v1, v2))) {
                Vertice vrc1 = buscarVertice(v1);
                Vertice vrc2 = buscarVertice(v2);

                vrc1.getAdjacente().add(vrc2);
                vrc2.getAdjacente().add(vrc1);

                Aresta addAresta = new Aresta();
                addAresta.setV1(vrc1);
                addAresta.setV2(vrc2);
                //calcular tamanho da aresta
                double peso = Math.sqrt(Math.pow(vrc2.getX() - vrc1.getX(), 2) + Math.pow(vrc2.getY() - vrc1.getY(), 2));
                addAresta.setPeso(peso);
                arestasTemp.add(addAresta);
                return true;
            }
        }
        return false;
    }

    public void inserirObstaculo() {
        Vertice verticePrimeiro = null;
        Vertice verticeUltimo = null;
        Vertice verticeAnterior = null;
        Vertice verticeAtual = null;

        Scanner ler = new Scanner(System.in);
        System.out.print("Quantidade de lados:");
        int qtdLados = ler.nextInt();
        int idObstaculo = ID_AUTO_OBSTACULO;
        ID_AUTO_OBSTACULO++;
        for (int i = 1; i <= qtdLados; i++) {
            System.out.println("VERTICE " + i + " de " + qtdLados);
            int idVertice = ID_AUTO_VERTICE;
            ID_AUTO_VERTICE++;
            System.out.println("ID do Vertice: " + idVertice);
            System.out.print("X: ");
            double xVertice = ler.nextDouble();
            System.out.print("Y: ");
            double yVertice = ler.nextDouble();

            if (inserirVertice(idVertice, idObstaculo, xVertice, yVertice)) {
            } else {
                System.out.println("Ops!. Algo errado. Parece que esse ID já existe.");
                i--;
            }

            if (i == 1) {
                verticeAtual = buscarVertice(idVertice);
                verticePrimeiro = buscarVertice(idVertice);
            } else if (i == qtdLados) {
                verticeUltimo = buscarVertice(idVertice);
                inserirAresta(verticeAtual.getId(), verticeUltimo.getId());
                inserirAresta(verticePrimeiro.getId(), verticeUltimo.getId());
            } else {
                verticeAnterior = verticeAtual;
                verticeAtual = buscarVertice(idVertice);
                inserirAresta(verticeAtual.getId(), verticeAnterior.getId());
            }
            System.out.println("");
        }
    }

    public void inserirObstaculo(Scanner scanner) {
        Vertice verticePrimeiro = null;
        Vertice verticeUltimo = null;
        Vertice verticeAnterior = null;
        Vertice verticeAtual = null;

        Scanner ler = scanner;
        int qtdLados = ler.nextInt();
        int idObstaculo = ID_AUTO_OBSTACULO;
        ID_AUTO_OBSTACULO++;
        for (int i = 1; i <= qtdLados; i++) {
            int idVertice = ID_AUTO_VERTICE++;
            double xVertice = Double.parseDouble(ler.next());
            double yVertice = Double.parseDouble(ler.next());

            if (inserirVertice(idVertice, idObstaculo, xVertice, yVertice)) {
            } else {
                System.out.println("Ops!. Algo errado. Parece que esse ID já existe. (" + (idVertice) + ")");
                i--;
            }

            if (i == 1) {
                verticeAtual = buscarVertice(idVertice);
                verticePrimeiro = buscarVertice(idVertice);
            } else if (i == qtdLados) {
                verticeUltimo = buscarVertice(idVertice);
                inserirAresta(verticeAtual.getId(), verticeUltimo.getId());
                inserirAresta(verticePrimeiro.getId(), verticeUltimo.getId());
            } else {
                verticeAnterior = verticeAtual;
                verticeAtual = buscarVertice(idVertice);
                inserirAresta(verticeAtual.getId(), verticeAnterior.getId());
            }
        }
    }

    public String menorCaminho() {
        imprimirGrafo();
        menorcaminho.WeightedGraph grafoMenorCaminho = new menorcaminho.WeightedGraph();
        String caminho = grafoMenorCaminho.menorCaminho(this);
        System.out.println("Menor caminho: " + caminho);

        return caminho;
//        grafoMenorCaminho.busca("1");
//        String menorCaminho = grafoMenorCaminho.imprimirCaminho("0");
//        System.out.println("");
//        System.out.println(menorCaminho);
//        new Scanner(menorCaminho);
    }

    public Vertice buscarVertice(int idVertice) {
        for (Vertice vertice : vertices) {
            if (vertice.getId() == idVertice) {
                return vertice;
            }
        }
        return null;
    }

    public void imprimirGrafo() {
        vertices.stream().map((vertice) -> {
            Vertice v = vertice;
            System.out.print(vertice.getId() + "(" + vertice.getX() + "," + vertice.getY() + ")" + "->");

            for (int i = 0; i < v.getAdjacente().size(); i++) {
                System.out.print(v.getAdjacente().get(i).getId() + "(" + v.getAdjacente().get(i).getX() + "," + v.getAdjacente().get(i).getY() + ") - ");
            }
            return vertice;
        }).forEach((_item) -> {
            System.out.println("");
        });
    }

    public String[][] imprimeMatrizdeIncidencia(Grafo g) {
        String[][] matriz = new String[getVertices().size() + 1][getArestasTemp().size() + 1];
        if (!orientado) {
            matriz[0][0] = "X  ";
            for (int i = 0; i < getVertices().size(); i++) {
                matriz[i + 1][0] = Integer.toString(getVertices().get(i).getId());
            }
            for (int i = 0; i < getArestas().size(); i++) {
                matriz[0][i + 1] = getArestas().get(i).getV1().getId() + "-" + getArestas().get(i).getV2().getId() + "[" + getArestas().get(i).getPeso() + "]";
            }
            for (int i = 0; i < getVertices().size(); i++) {
                for (int j = 0; j < getArestas().size(); j++) {
                    if (matriz[i + 1][0].equals(Integer.toString(getArestas().get(j).getV1().getId()))) {
                        matriz[i + 1][j + 1] = "   1 ";
                    } else {
                        if (matriz[i + 1][0].equals(Integer.toString(getArestas().get(j).getV2().getId()))) {
                            matriz[i + 1][j + 1] = "  -1 ";
                        } else {
                            matriz[i + 1][j + 1] = "   0 ";
                        }
                    }

                }

            }
            for (int i = 0; i < getVertices().size() + 1; i++) {
                for (int j = 0; j < getArestas().size() + 1; j++) {
                    System.out.print(" " + matriz[i][j] + "  ");
                }
                System.out.println("");
            }

        }
        if (orientado) {
            matriz[0][0] = "X";
            for (int i = 0; i < getVertices().size(); i++) {
                matriz[i + 1][0] = Integer.toString(getVertices().get(i).getId());
            }
            for (int i = 0; i < getArestasTemp().size(); i++) {
                matriz[0][i + 1] = getArestasTemp().get(i).getV1().getId() + "-" + getArestasTemp().get(i).getV2().getId() + "[" + getArestasTemp().get(i).getPeso() + "]";
            }

            for (int i = 0; i < getVertices().size(); i++) {
                for (int j = 0; j < getArestasTemp().size(); j++) {
                    if (matriz[i + 1][0].equals(Integer.toString(getArestasTemp().get(j).getV1().getId())) || matriz[i + 1][0].equals(Integer.toString(getArestasTemp().get(j).getV2().getId()))) {
                        matriz[i + 1][j + 1] = "  1";
                    } else {
                        matriz[i + 1][j + 1] = "  0";
                    }
                }
            }
            for (int i = 0; i < getVertices().size() + 1; i++) {
                for (int j = 0; j < getArestasTemp().size() + 1; j++) {
                    System.out.print("  " + matriz[i][j] + "   ");
                }
                System.out.println("");
            }
        }
        return matriz;
    }

    public void povoarGrafo(String path) {
        ArrayList<String> dados = new ArrayList<>();
        String linha;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(path));
            StringTokenizer dado = null;
            String m = "";
            while ((linha = reader.readLine()) != null) {
                dado = new StringTokenizer(linha, "-> :(),");
                while (dado.hasMoreTokens()) {
                    m = m + dado.nextToken() + " ";
                    //dados.add(dado.nextToken());
                }
                //System.out.println(m);
                inserirObstaculo(new Scanner(m));
                m = "";
            }
            reader.close();

        } catch (IOException | NumberFormatException e) {
        }
    }

    public void reiniciar() {
        vertices = new ArrayList<Vertice>();
        arestas = new ArrayList<Aresta>();
        arestasTemp = new ArrayList<Aresta>();
    }

    public ArrayList<Vertice> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Vertice> vertices) {
        this.vertices = vertices;
    }

    public ArrayList<Aresta> getArestas() {
        return arestas;
    }

    public void setArestas(ArrayList<Aresta> arestas) {
        this.arestas = arestas;
    }

    public boolean isOrientado() {
        return orientado;
    }

    public void setOrientado(boolean orientado) {
        this.orientado = orientado;
    }

    public ArrayList<Aresta> getArestasTemp() {
        return arestasTemp;
    }

    public void setArestasTemp(ArrayList<Aresta> arestasTemp) {
        this.arestasTemp = arestasTemp;
    }

    public ArrayList<Aresta> getMenorCaminho() {
        return menorCaminho;
    }

    public void setMenorCaminho(ArrayList<Aresta> menorCaminho) {
        this.menorCaminho = menorCaminho;
    }

}
