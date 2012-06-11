package br.uece.threeopt.utils;

import java.util.ArrayList;
import java.util.List;

import br.uece.threeopt.heuristica.sequenciamento.Tarefa;

public class Sequenciador {
	
	public static List<Tarefa[]> geraSequenciasPossiveis(int[] indexs, Tarefa[] tarefas){
		
		List<Tarefa[]> listTarefas = new ArrayList<Tarefa[]>();
		
		int i = indexs[0]; 
		int j = ((i + 1) > (tarefas.length - 1)? 0 : i+1);
		int k = indexs[1];
		int l = ((k + 1) > (tarefas.length - 1)? 0 : k+1);
		int m = indexs[2];
		@SuppressWarnings("unused")
		int n = ((m + 1) > (tarefas.length - 1)? 0 : m+1);
		
		int[] sequenciaAtual = {j, k, l, m};
		int[][] posicoes = {{m,k,j,l}, {j, m, k, l}, {k,m,l,j},{k,j,m,l}, {k,j,l,m},	{l,j,k,m}, {m,l,j,k}, {l,k,m,j},
				{k,l,j,m}, {k,m,j,l}, {l,k,j,m}, {m,l,k,j},	{j,k,m,l}, {l,j,m,k}, {j,l,m,k}, {m,j,l,k}, {l,m,k,j},
				{j,m,l,k}, {m,j,k,l}, {m,k,l,j}, {l,m,j,k},	{j,l,k,m}, {k,l,m,j}, {j,k,l,m}};
		
		for (int p = 0; p < posicoes.length; p++) {
			Tarefa[] tars = tarefas.clone();
			int[] novaSequencia = posicoes[p];
			
			for (int q = 0; q < novaSequencia.length; q++) {
				if(novaSequencia[q] != sequenciaAtual[q]){
					tars[sequenciaAtual[q]] = tarefas[novaSequencia[q]];
				}
			}
			listTarefas.add(tars);
		}	
				
		return listTarefas;
		
	}
	
}
