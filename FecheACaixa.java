import java.util.Scanner;
import java.io.FileNotFoundException; 
import java.io.IOException;

public class FecheACaixa {
    private String nome;
    private int pontos;
    private boolean[] tabuleiro;
    private boolean[] ponteiros;
    private int[] dados;
    private int lados;
    private int valor;
    private boolean jogo;
    private String acao;
    
    /**
     * Construtor do objeto FecheACaixa, tem atributos padrão que podem ser alterados tendo acesso a classe.
     */
    public FecheACaixa(String n) {
        nome = n;
        pontos = 0;
        int nCasas = 9;
        tabuleiro = new boolean[nCasas];
        ponteiros = new boolean[nCasas];
        dados = new int[2];
        lados = 6;
        valor = 0;
        jogo = true;
        acao = null;
    }

    public static String leNome() {
        Scanner in = new Scanner(System.in);
        System.out.print("Insira seu nome: ");
        return in.nextLine().replace(";",",");
    }
    
    /**
     * O metodo principal do FecheACaixa, onde acontece todo o jogo e outros metodos da classe sao chamados.
     * @param Placar pontuacao recebe o objeto de placar que sera usado para ser lido e armazenar o placar do jogo.
     */
    public void rodar(Placar pontuacao) throws InterruptedException, FileNotFoundException, IOException {
        System.out.println("Boas-vindas ao jogo Feche a Caixa!\n");
        Scanner in = new Scanner(System.in);
        System.out.println();

        while(jogo) {
            mostraTab();
            
            acao(in);

            if (!validarAcao()) { 
                Thread.sleep(1500); //Tempo de espera apos a mensagem de erro
            }
            else { //A acao informada eh valida para seguir com o jogo:
                switch(acao) {
                    case "L": //Lancar os dados, nao podem ter dados "ativos"
                        if (valor == 0) {
                            dados();
                            valor = dados[0] + dados[1];
                        }
                        else {
                            System.out.println("Voce ja tem dados rolados.");
                            Thread.sleep(2000);
                        }
                        System.out.println();
                        if (temOpcao()) {break;}
                        else {
                            System.out.print("Voce obteve ");
                            if (dados[1] == 0)
                                System.out.printf("o dado [%d]. ",dados[0]);
                            else
                                System.out.printf("os dados [%d] e [%d]. ", dados[0], dados[1]);
                            System.out.println("Nao existem opcoes de casas a serem fechadas.");
                        } 
                    case "P": //Passar os dados atuais e somar seus valores a pontuacao
                        if (valor != 0) {
                            System.out.println("Somando dados a sua pontuacao.");
                            System.out.println();
                            pontos += valor;
                            valor = 0;
                            dados[0] = 0;
                            dados[1] = 0;
                        }
                        else {
                            System.out.println("Voce deve rolar dados antes.");
                        }
                        Thread.sleep(1500);
                        System.out.println();
                        break;
                    case "F": //Fechar as casas marcadas, devem ter a soma igual a soma dos dados
                        if (valor != 0) {
                            if (fechaCasa()) {
                                System.out.println("Casa(s) fechada(s) com sucesso!");
                                valor = 0;
                                dados[0] = 0;
                                dados[1] = 0;
                                for(int i = 0; i < ponteiros.length; i++) {
                                    ponteiros[i] = false;
                                }
                            }
                            else {
                                System.out.println("A soma das casas nao coincide com a soma dos dados.");
                            }
                        }
                        else {
                            System.out.println("Voce deve rolar seus dados antes.");
                        }
                        Thread.sleep(1500);
                        System.out.println();
                        break;
                    case "S": //Vai encerrar o jogo
                        System.out.println("O jogo ira acabar.");
                        jogo = false;
                        break;
                    default: //marcar, ou desmarcar, um ponteiro, eh de uma casa ainda nao fechada
                        int casa = (Integer.parseInt(acao)) - 1;
                        if (ponteiros[casa]) ponteiros[casa] = false;
                        else ponteiros[casa] = true;
                        break;
                }
                if (verificarFim()) {
                    System.out.printf("Parabens! Voce finalizou o jogo.\nSua pontuacao: %d\n\n", pontos);
                    jogo = false;
                    if (pontuacao.adicionaPontuacao(nome, pontos)) {
                        System.out.println("Sua pontuacao entrou no placar, Parabens!");
                        pontuacao.salvarArquivo();
                    }
                    else {
                        System.out.println("Sua pontuacao nao entrou no placar.");
                    }
                    pontuacao.mostrar();
                }
            }
        }
        in.close();
        System.out.println();
        System.out.print("Jogo Finalizado. Obrigado por Jogar!");
    }

    /**
     * O metodo que imprime o tabuleiro de jogo, bem como as informacoes do jogador e seu estado atual.
     */
    public void mostraTab() {
        System.out.printf("Jogador(a):   %s\n", nome);
        System.out.printf("Pontuação:    %d\n", pontos);
        for (int i = 0; i < tabuleiro.length; i++) {
            System.out.print("[");
            if (!tabuleiro[i]) System.out.print(i+1);
            else System.out.print("X");
            System.out.print("]");
        }
        System.out.println();
        for (int i = 0; i < ponteiros.length; i++) {
            if (ponteiros[i]) System.out.print(" ^ ");
            else System.out.print(" . ");
        }
        System.out.printf("\n\n");
        if (dados[0] != 0) {
            System.out.printf("Dados: [%d]", dados[0]);
            if (dados[1] != 0)
                System.out.printf("[%d]", dados[1]);
            System.out.print("\n\n");
        }
    }

    /**
     * Metodo que faz a leitura da acao desejada do jogador, alem de transformar certos valores para saidas validas.
     * @param Scanner in recebe a classe do Scanner que ira ler a acao.
     */
    public void acao(Scanner in) {
        System.out.print("[L]ançar, [P]assar, [F]echar, [S]air, ou uma casa a ser marcada/desmarcada: ");
        acao = in.nextLine().toUpperCase().trim();
        System.out.println();
        switch(acao) {
            case "LANCAR":
            case "LANÇAR":
                acao = "L";
                break;
            case "PASSAR":
                acao = "P";
                break;
            case "FECHAR":
                acao = "F";
                break;
            case "SAIR":
                acao = "S";
                break;
            default:
                break;
        }
    }

    /**
     * Metodo que recebe a acao previamente lida do usuario e valida ela, bem como transforma em inteiro se for o
     * caso, alem de exportar diferentes tipos de mensagem dependendo do "erro" do usuario ao indicar sua acao.
     * @return boolean Retorna um booleano onde true quer dizer que o usuario inseriu uma acao valida.
     */
    public boolean validarAcao() throws InterruptedException {
        if (ehNumerico(acao)) {
            int casa = Integer.parseInt(acao);
            System.out.println();
            if (casa >= 1 && casa <= 9) {
                if (tabuleiro[casa-1]) {
                    System.out.println("Esta casa ja esta fechada!\n");
                    return false;
                }
                else {
                    return true;
                }
            }
            else {
                System.out.println("Insira uma casa válida.");
                System.out.println();
                return false;
            }
        }
        else {
            switch(acao) {
                case "L":
                case "P":
                case "F":
                case "S":
                    return true;
                default:
                    System.out.println("Insira uma ação válida.");
                    return false;
            }
        }
    }

    /**
     * Metodo simples que avalia se uma String tem um valor que eh um inteiro.
     * @param String linha a String que sera avaliada como numerica ou nao.
     * @return boolean Retorna um booleano onde true indica que o conteudo da String eh um inteiro.
     */
    public boolean ehNumerico(String linha) {
        if (linha == null) {
            return false;
        }
        try {
            int n = Integer.parseInt(linha);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     *  O metodo que gera os dados para o jogo, usando os dados como um vetor de duas casas e avaliando se deve
     *  rodar 1 ou 2 dados.
     */
    public void dados() {
        dados[0] = rolaDado(lados);
        if (doisDados())
            dados[1] = rolaDado(lados);
        else
            dados[1] = 0;
    }

    /**
     * Metodo do objeto que avalia o tabuleiro e verifica se todas casas com valores maiores que um unico dado estao
     * fechadas, se estiverem, retorna false.
     * @return boolean Retorna um booleano que eh true se dois dados devem ser rolados ainda.
     */
    public boolean doisDados() {
        for (int i = lados; i < tabuleiro.length; i++) {
            if (!tabuleiro[i]) return true;
        }
        return false;
    }

    /**
     * Metodo que gera um valor de dado.
     * @param int lados quantos lados tem o dado que sera rolado.
     * @return int Retorna um valor inteiro entre 1 e o valor informado (um dado de "tantos" lados).
     */
    public int rolaDado(int lados) {
        return (int)(Math.random() * lados) + 1;
    }

    /**
     * Um metodo que passa por um loop de verificacoes para tentar encontrar alguma combinacao de casas abertas no
     * tabuleiro para a soma atual dos dados, se encontrar, retorna true.
     * @return boolean Retorna um booleano onde true diz que existe uma combinacao de casas para os dados atuais.
     */
    public boolean temOpcao() {
        if (valor <= 9 && !tabuleiro[valor-1]) {
            return true;
        }
        else {
            for (int m = 1; m < 5; m++) {
                for (int n = m+1; n <= 9 && m+n <= 12; n++) {
                    if (n+m==valor && !tabuleiro[n-1] && !tabuleiro[m-1]) return true;
                    for (int o = n+1; o <= 9 && m+n+o <= 12; o++) {
                        if (n+m+o==valor && !tabuleiro[n-1] && !tabuleiro[m-1] && !tabuleiro[o-1]) return true;
                        for (int p = o+1; p <= 6 && m+n+o+p <= 12; p++) {
                            if (n+m+o+p==valor && !tabuleiro[n-1] && !tabuleiro[m-1] && !tabuleiro[o-1] && !tabuleiro[p-1]) return true;
                        }
                    }
                }
            }
            return false;
        }
    }

    /**
     * Um metodo que soma o valor das casas que os ponteiros estao apontado e compara com a soma dos dados,
     * se coincidirem, as casas apontadas sao fechadas.
     * @return boolean Retorna um booleano que diz se a(s) casa(s) foram fechadas ou nao, true para sim.
     */
    public boolean fechaCasa() {
        int valorPonteiros = 0;
        for(int i = 0; i < tabuleiro.length; i++) {
            if (ponteiros[i]) valorPonteiros += i+1; //Soma os valores das casas que os ponteiros estao apontando
        }
        if (valorPonteiros == valor) { //Se a soma dos ponteiros for igual a soma dos dados...
            for(int i = 0; i < tabuleiro.length; i++) {
                if (ponteiros[i]) tabuleiro[i] = true; //...entao todas as casas dos ponteiros serao fechadas
            }
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Um metodo que verifica o fim do jogo, ou seja, todas as casas do tabuleiro estarem fechadas.
     * @return boolean Retorna um booleano onde se for false quer dizer que o jogo ainda nao acabou.
     */
    public boolean verificarFim() {
        for(int i = 0; i < tabuleiro.length; i++) {
            if (!tabuleiro[i]) return false; //Se qualquer casa estiver aberta, quer dizer que o jogo ainda nao acabou
        }
        return true;
    }
}
