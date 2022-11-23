import java.util.Scanner;

/**
 * Escreva uma descrição da classe Vetor aqui.
 * 
 * @author (seu nome) 
 * @version (um número da versão ou uma data)
 */
public class Vetor
{
    public static void main(String[] args) {
        int[] vetor = {2,6,84,1,6,3,53,2};
        mostra(vetor);
        ordenarCrescente(vetor, vetor.length);
        mostra(vetor);
    }
    
    public static void preenche(int[] v, int n) {
        preenche(v, v.length, n);
    }
    
    public static void preenche(int [] v, int t, int n) {
        for (int i = 0; i < t; i++) {
            v[i] = n;
        }
    }
    
    public static long soma(int[] v) {
        long res = 0;
        for (int i = 0; i < v.length; i++) {
            res += v[i];
        }
        return res;
    }
    /*
    public static int med(int[] v) {
        if (v.length > 0)
            return (double)soma(v)/v.length;
        return 0;
    }
    */
    public static int menor(int[] v) {
        return menor(v, v.length);
    }
    
    public static int menor(int[] v, int t) {
        int menor = v[0];
        for (int i = 0; i < t; i++) {
            if (v[i] < menor)
                menor = v[i];
        }
        return menor;
    }
    
    public static int maior(int [] v) {
        return maior(v, v.length);
    }
    
    public static int maior(int[] v, int t) {
        int maior = v[0];
        for (int i = 0; i < t; i++) {
            if (v[i] > maior)
                maior = v[i];
        }
        return maior;
    }
    
    public static boolean busca(int[] v, int n) {
        return busca(v, v.length, n);
    }
    
    public static boolean busca(int[] v, int t, int n) {
        for (int i = 0; i < t; i++) {
            if (v[i] == n)
                return true;
        }
        return false;
    }
    
    public static int conta(int[] v,int n) {
        return conta(v, v.length, n);
    }
    
    public static int conta(int[] v, int t, int n) {
        int c = 0;
        for (int i = 0; i < t; i++) {
             if (v[i] == c)
                 c++;
        }
        return c;
    }
    
    public static void le(Scanner in, int[] v) {
        le(in, v, v.length);
    }
    
    public static void le(Scanner in, int[] v, int t) {
        for (int i = 0; i < t; i++) {
            v[i] = in.nextInt();
        }
    }
    
    public static void mostra(int[] v) {
        mostra(v, v.length);
    }
    
    public static void mostra(int[] v, int t) {
        for (int i = 0; i < t; i++) {
            System.out.printf("%d ", v[i]);
        }
        System.out.println();
    }
    
    public static int somaRec(int[] v) {
        return somaRec(v, v.length);
    }
    
    public static int somaRec(int[] v, int t) {
        if (t == 1)
            return v[0];
        return v[t-1] + somaRec(v, t-1);
    }
    
    public static void ordenarCrescente(int[] v, int t) {
        if (t > 1) {
            int iMaior = cade(v, t, maior(v,t));
            if (t-1 != iMaior) {
                int aux = v[t-1];
                v[t-1] = v[iMaior];
                v[iMaior] = aux;
            }   
            mostra(v);
            ordenarCrescente(v, t-1);
        }
    }
    
    public static int cade(int[] v, int t, int n) {
        for (int i = 0; i < t; i++) {
            if (v[i] == n)
                return i;
        }
        return 0;
    }
}
