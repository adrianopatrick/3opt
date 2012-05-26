package br.uece.threeopt.heuristica.caixeiroviajante;

import java.util.List;

import br.uece.threeopt.utils.Mathematica;

/**
 * @author patrick cunha
 * @version 1.0
 * Classe responsável por criar a matriz de adjacencia
 * apartir de um List de Ponto ou de uma vetor com 
 * valores de metade da matriz
 * */
public class MatrizAdjacencias {
	
	
	public static Celula[][] geraMatrizAdjacenciasApartirDePontos(List<Ponto> pontos){
		
		Celula matriz[][] = new Celula[pontos.size()][pontos.size()];
		
		for (int i = 0; i < pontos.size(); i++) {
			for (int j = 0; j < pontos.size(); j++) {
				if(i != j)
					matriz[i][j] = Mathematica.calculaDistancia(pontos.get(i), pontos.get(j));
				else
					matriz[i][j] = new Celula(pontos.get(i), pontos.get(j), 0.0);
			}
		}
		
		return matriz;
	}
	
	public static void geraMatrizAdjacenciasApartirDeMatrizIncompleta(Celula[][] matriz){
		
		for (int i = 0; i < matriz.length - 1; i++) {
			for (int j = i + 1; j < matriz.length; j++) {
				matriz[j][i] = new Celula(matriz[i][j].getDestino(), matriz[i][j].getOrigem(), matriz[i][j].getDistancia());
			}			
		}
	}
	
	public static Celula[][] clone(Celula[][] matriz){
		Celula[][] novaMatriz = new Celula[matriz.length][matriz.length];
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz.length; j++) {
				novaMatriz[i][j] = new Celula(matriz[i][j].getDestino(), matriz[i][j].getOrigem(), matriz[i][j].getDistancia());
			}			
		}
		return novaMatriz;
	}

}
