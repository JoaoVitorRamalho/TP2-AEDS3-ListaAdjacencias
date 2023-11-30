import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Array;
import java.util.*;
public class Grafo {

    private int Grafo[][];

    private int Cordenada[][];

    private String Nomes[];

    private int vertices;

    private int arestas;

    private boolean direcionado = false;

    public int getVertices() {
        return vertices;
    }

    public void setVertices(int vertices) {
        this.vertices = vertices;
    }

    public int getArestas() {
        return arestas;
    }

    public void setArestas(int arestas) {
        this.arestas = arestas;
    }

    public boolean isDirecionado() {
        return direcionado;
    }

    public void setDirecionado(boolean direcionado) {
        this.direcionado = direcionado;
    }

    public void printNomes(){
        for(String i : Nomes){
            System.out.println(i);
        }
    }

    public void setNomes(int vertice, String nome){
        Nomes[vertice] = nome;
    }

    public int getMatNomes(String nome){
        for(int i = 0; i < vertices; i ++){
            if(Nomes[i].equals(nome)){
                return i;
            }
        }
        return -1;
    }

    public void lerArquivo(String nomeArquivo){
        try {
            File arquivoGrafo = new File(nomeArquivo);
            Scanner e = new Scanner(arquivoGrafo);
            String direcionado = e.nextLine();
            if(direcionado.equals("nao")){
                this.direcionado = false;
            }else{
                this.direcionado = true;
            }
            vertices = e.nextInt();
            Grafo = new int[vertices][vertices];
            for(int i = 0; i<vertices;i++){
                for (int j = 0; j<vertices; j++){
                    Grafo[i][j] = -1;
                }
            }
            Cordenada = new int[2][vertices];
            Nomes = new String[vertices];
            for(int i = 0; i<vertices;i++){
                e.nextInt();
                Cordenada[0][i] = e.nextInt();
                Cordenada[1][i] = e.nextInt();
                e.skip(" ");
                Nomes[i] = e.nextLine();
            }
            arestas = e.nextInt();
            for(int i = 0; i<arestas;i++){
                int origem = e.nextInt();
                int destino = e.nextInt();
                if(this.direcionado){
                    Grafo[origem][destino] = e.nextInt();
                }else {
                    int peso = e.nextInt();
                    Grafo[origem][destino] = peso;
                    Grafo[destino][origem] = peso;
                }
            }
            e.close();
        } catch (FileNotFoundException e) {
            System.out.println("ERRO!");
            e.printStackTrace();
        }
    }

    public void exportaGrafo(){
        try {
            File arquivo = new File("./grafo.txt");
            arquivo.createNewFile();
        } catch(IOException e) {
            System.out.println("Falha");
        }

        try {
            FileWriter arquivo = new FileWriter("./grafo.txt");
            if(direcionado){
                arquivo.write("sim\n");
            }else{
                arquivo.write("nao\n");
            }
            arquivo.write(vertices + "\n");
            for(int i = 0; i < vertices; i++){
                arquivo.write(i+" "+Cordenada[0][i]+" "+Cordenada[1][i]+" "+Nomes[i]+"\n");
            }
            arquivo.write(arestas+"\n");
            if(direcionado) {
                for (int i = 0; i < vertices; i++) {
                    for (int j = 0; j < vertices; j++) {
                        if (Grafo[i][j] == -1) {
                            continue;
                        } else {
                            arquivo.write(i + " " + j + " " + Grafo[i][j] + "\n");
                        }
                    }
                }
            }else{
                for (int i = 0; i < vertices; i++) {
                    for (int j = 0; j < vertices; j++) {
                        if (Grafo[i][j] == -1 || j>i) {
                            continue;
                        } else {
                            arquivo.write(i + " " + j + " " + Grafo[i][j] + "\n");
                        }
                    }
                }
            }
            arquivo.close();
        } catch(IOException e) {
            System.out.println("Falha");
        }
    }

    public void criarGrafoVazio(int tamanho, boolean direcionado){
        Scanner e = new Scanner(System.in);
        vertices = tamanho;
        Grafo = new int[vertices][vertices];
        Cordenada = new int[2][vertices];
        Nomes = new String[vertices];
        arestas = 0;
        this.direcionado = direcionado;
        for(int i = 0; i < vertices; i++){
            System.out.println("Digite a coordenada x: ");
            Cordenada[0][i] = e.nextInt();
            System.out.println("Digite a coordenada y: ");
            Cordenada[1][i] = e.nextInt();
            System.out.println("Digite o nome do vértice: ");
            e.skip("\n");
            Nomes[i] = e.nextLine();
            for (int j = 0; j<vertices; j++){
                Grafo[i][j] = -1;
            }
        }
    }

    public void imprimeGrafo(){
        for(int i = 0; i < vertices; i++){
            for(int j = 0; j<vertices;j++){
                if(Grafo[i][j] == -1){
                    System.out.print("X ");
                }else{
                    System.out.print(Grafo[i][j]+" ");
                }
            }
            System.out.println();
        }
    }

    public int consulta(int origem, int destino){
        return Grafo[origem][destino];
    }

    public void removeAresta(int origem, int destino) {
        if (direcionado) {
            Grafo[origem][destino] = -1;
        } else {
            Grafo[origem][destino] = -1;
            Grafo[destino][origem] = -1;
        }
    }
    public void insereAresta(int origem, int destino, int peso){
        if (direcionado) {
            Grafo[origem][destino] = peso;
        } else {
            Grafo[origem][destino] = peso;
            Grafo[destino][origem] = peso;
        }
    }

    public void alteraCords(int vertice, int x, int y){
        Cordenada[0][vertice] = x;
        Cordenada[1][vertice] = y;
    }

    public int primeiroAdjacente(int vertice){
        for(int i = 0; i<vertices;i++){
            if(Grafo[vertice][i] != -1){
                return i;
            }else{
                continue;
            }
        }
        return -1;
    }

    public  int proximoAdjacente(int origem, int destino){
        for(int i = destino+1;i<vertices;i++){
            if(Grafo[origem][i] != -1){
                return i;
            }else {
                continue;
            }
        }
        return -1;
    }

    public void listaAdjacente(int vertice){
        boolean existe = false;
        System.out.println("adjacentes: ");
        for (int i = 0; i<vertices;i++){
            if(Grafo[vertice][i] != -1){
                System.out.println(i + " ");
                existe = true;
            }else {
                continue;
            }
        }
        if(!existe){
            System.out.println("nenhuma adjacencia no grafo");
        }
    }

    public void Menu(){
        System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
        System.out.println("| 1- Importar grafo");
        System.out.println("| 2- Criar grafo vazio");
        System.out.println("| 3- Imprimir grafo");
        System.out.println("| 4- Consultar se um vértice é adjacente");
        System.out.println("| 5- Inserir aresta");
        System.out.println("| 6- Remover aresta");
        System.out.println("| 7- Editar coordenadas do vertice");
        System.out.println("| 8- Consultar primeira adjacente");
        System.out.println("| 9- Consultar proxima adjacente");
        System.out.println("| 10- Consultar lista de adjacente");
        System.out.println("| 11- Editar nome do vertice");
        System.out.println("| 12- Exportar Grafo");
        System.out.println("| 13- Busca em Largura");
        System.out.println("| 14- Busca em Profundidade");
        System.out.println("| 15- Gera Árvore Mínima");
        System.out.println("| 16- Encontrar Menor Caminho");
        System.out.println("| 17- Sair");
        System.out.println("*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*");
    }

    public void buscaEmLargura(){
        int antecessor[] = new int[vertices];
        char cor[] = new char[vertices];
        int distancia[] = new int[vertices];
        int j = 0;
        for(int i = 0; i < vertices; i++){
            antecessor[i] = -1;
            cor[i] = 'B';
            distancia[i] = Integer.MAX_VALUE;
        }
        for(int i = 0; i < vertices; i++){
            if(cor[i] == 'B'){
                visitaEmLargura(i,antecessor,cor,distancia);
            }
        }
        for(int i = 0; i < vertices; i++){
            System.out.println("Distancia vértice "+i+": "+distancia[i]);
        }
    }

    public void visitaEmLargura(int vertice, int antecessor[], char cor[], int distancia[]){
        int adjascente;
        int prox;
        cor[vertice] = 'C';
        distancia[vertice] = 0;
        Queue<Integer> fila = new LinkedList();
        fila.add(vertice);
        while(!fila.isEmpty()) {
            prox = fila.poll();
            adjascente = primeiroAdjacente(prox);
            while (adjascente != -1) {
                if (cor[adjascente] == 'B') {
                    fila.add(adjascente);
                    cor[adjascente] = 'C';
                    antecessor[adjascente] = prox;
                    distancia[adjascente] = distancia[prox] + 1;
                    adjascente = proximoAdjacente(prox, adjascente);
                    System.out.println(adjascente);
                }else{
                    adjascente = proximoAdjacente(prox, adjascente);
                }
            }
            cor[prox] = 'P';
        }
    }

    public void buscaEmProfundidade(){
        int antecessor[] = new int[vertices];
        char cor[] = new char[vertices];
        int descoberta[] = new int[vertices];
        int termino[] = new int[vertices];
        Integer tempo = 0;
        for(int i = 0; i < vertices; i++){
            descoberta[i] = -1;
            cor[i] = 'B';
        }
        for(int i = 0; i < vertices; i++){
            if(cor[i] == 'B'){
                tempo = visitaEmProfundidade(tempo, i, antecessor, cor, descoberta, termino);
            }
        }
        for(int i = 0; i < vertices; i++){
            System.out.println("Tempo de descoberta do vértice "+i+": "+descoberta[i]);
            System.out.println("Tempo de término do vértice "+i+": "+termino[i]);
        }
    }

    public int visitaEmProfundidade(Integer tempo, int vertice, int antecessor[], char cor[], int descoberta[], int termino[]){
        int adjascente;
        cor[vertice] = 'C';
        tempo++;
        descoberta[vertice] = tempo;
        adjascente = primeiroAdjacente(vertice);
        while(adjascente != -1){
            antecessor[adjascente] = vertice;
            if(cor[adjascente] == 'B'){
                tempo = visitaEmProfundidade(tempo,adjascente,antecessor,cor,descoberta,termino);
            }
            adjascente = proximoAdjacente(vertice,adjascente);
        }
        cor[vertice] = 'P';
        tempo++;
        termino[vertice] = tempo;
        return tempo;
    }

    public void arvoreMinima(){
        boolean visitado[] = new boolean[vertices];
        int soma=0;
        Queue<Par> pares = new PriorityQueue<>(new ComparaPares());
        for(int i = 0; i < vertices; i ++){
            if(Grafo[0][i] != -1){
                pares.add(new Par(Grafo[0][i],i,0));
            }
        }
        visitado[0] = true;
        while(!pares.isEmpty()) {
            Par par = pares.poll();
            if (!visitado[par.destino]) {
                soma+=par.peso;
                System.out.println("O vértice "+par.destino+" entrou na arvore minima com uma aresta de peso "+par.peso);
                for (int i = 0; i < vertices; i++) {
                    if(Grafo[par.destino][i] != -1){
                        pares.add(new Par(Grafo[par.destino][i],i,par.destino));
                    }
                }
                visitado[par.destino] = true;

            }
        }
        System.out.println("O peso total da arvore minima é "+soma);
    }

    class ComparaPares implements Comparator<Par> {
        @Override
        public int compare(Par p1, Par p2){
            if(p1.peso < p2.peso){
                return -1;
            }if(p1.peso > p2.peso){
                return 1;
            }
            return 0;
        }
    }

    public void menorCaminho(int origem, int destino){
        ArrayList<Par> pares = new ArrayList<Par>();
        boolean visitado[] =  new boolean[vertices];
        int distancia[] = new int[vertices];
        int antecessor[] =  new int[vertices];
        for(int i = 0; i < vertices; i++){
            distancia[i] = Integer.MAX_VALUE;
            antecessor[i]=-1;
        }
        visitado[origem] = true;
        distancia[origem] = 0;
        for(int i = 0; i < vertices; i++){
            if(Grafo[origem][i] != -1){
                pares.add(new Par(Grafo[origem][i],i,origem));
            }
        }

        for(int i = 1; i < vertices; i++){
            for (int j = 0; j < vertices; j++) {
                if(Grafo[i][j] != -1) {
                    pares.add(new Par(Grafo[i][j], j, i));
                }
            }
        }
        for(Par i : pares){
            if(distancia[i.destino] > i.peso + distancia[i.origem]){
                antecessor[i.destino] = i.origem;
                distancia[i.destino] = i.peso + distancia[i.origem];
            }
        }
        int prox = destino;
        Stack<Integer> caminho = new Stack<>();
        System.out.print("O menor caminho de "+origem+" até "+destino+" é: ");
        do{
            caminho.add(prox);
            prox = antecessor[prox];
        }while(prox != -1);
        while(!caminho.isEmpty()){
            System.out.print(caminho.pop()+" ");
        }
        System.out.println();
    }

}