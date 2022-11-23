import java.io.FileNotFoundException;
import java.io.IOException;

public class Jogo {
    public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException {
        Placar pontuacao = new Placar("pontuacao.csv",10);
        FecheACaixa jogo = new FecheACaixa(FecheACaixa.leNome());
        if (pontuacao.tem()) pontuacao.mostrar();
        jogo.rodar(pontuacao);
    }
}