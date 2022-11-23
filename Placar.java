import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Placar {
    private int nLinhas;
    private int tamPlacar;
    private String[] nomes;
    private int[] pontos;
    
    /**
     * Construtor do objeto da classe Placar.
     * @param String arquivo O nome do arquivo de onde sera lido o placar e salvo depois do jogo.
     */
    public Placar(String arquivo, int tam) throws FileNotFoundException {
        nLinhas = 0;
        int nPlacar = 10;
        tamPlacar = tam;
        nomes = new String[tamPlacar];
        pontos = new int[tamPlacar];

        File arquivoPontos = new File(arquivo);
        if (arquivoPontos.exists()) {
            Scanner inFile = new Scanner(arquivoPontos);
            while(inFile.hasNextLine()) {
                String linha = inFile.nextLine();
                String[] campos = linha.split(";");
                if(campos.length != 2) {
                    System.out.println("> Placar: arquivo "+arquivo+" invalido...");
                    inFile.close();
                    return;
                }
                else {
                    nomes[nLinhas] = campos[0].trim();
                    pontos[nLinhas] = Integer.parseInt(campos[1].trim());
                    nLinhas++;
                }
            }
            inFile.close();
        }
    }

    /**
     * Metodo que server para mostrar o placar.
     */
    public void mostrar() throws FileNotFoundException {
        System.out.println("\n----------Placar----------");
        System.out.printf("%-18s%s","Jogador:","Pontos:\n");
        for (int i = 0; i < nLinhas; i++) {
            System.out.printf("%2d. %-15s- %3d\n", i+1, nomes[i], pontos[i]);
        }
        System.out.println("--------------------------\n");
    }

    /**
     * Metodo que avalia a existencia do placar.
     * @return boolean Retorna um booleano que diz se existe alguma pontuacao atualmente no placar.
     */
    public boolean tem() {
        if (nLinhas != 0)
            return true;
        else
            return false;
    }
   
    /**
     * Metodo que adiciona o nome a pontuacao de um jogador ao placar.
     * @param String nome O nome do jogador.
     * @param int pontosNovos A pontuacao do jogador que deve, ou nao, ser adicionada.
     * @return boolean Retorna um booleano onde true indica que a pontuacao passou a compor o placar.
     */
    public boolean adicionaPontuacao(String nome, int pontosNovos) {
        if (tem()) {
            for (int i = 0; i < tamPlacar && i < nLinhas+1; i++) {
                if (pontosNovos < pontos[i]) {
                    for (int j = nLinhas; j > i; j--) {
                        if (j != tamPlacar) {
                            nomes[j] = nomes[j-1];
                            pontos[j] = pontos[j-1];
                        }
                    }
                    nomes[i] = nome;
                    pontos[i] = pontosNovos;
                    return true;
                }
            }
        }
        else {
            nomes[nLinhas] = nome;
            pontos[nLinhas] = pontosNovos;
            System.out.printf("%s - %d", nomes[0], pontos[0]);
            nLinhas++;
            return true;
        }
        return false;
    }

    /**
     * Metodo que cria um arquivo de texto e salva o placar nele.
     */
    public void salvarArquivo() throws FileNotFoundException, IOException {
        FileWriter arquivo = new FileWriter("pontuacao.csv");
        PrintWriter novoArquivo = new PrintWriter(arquivo);
        for (int i = 0; i < nLinhas; i++) {
            novoArquivo.printf("%s;%d",nomes[i],pontos[i]);
            if (i != nLinhas-1) novoArquivo.println();
        }
        novoArquivo.close();
    }
}
