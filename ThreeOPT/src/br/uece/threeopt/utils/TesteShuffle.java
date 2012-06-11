package br.uece.threeopt.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import br.uece.threeopt.heuristica.sequenciamento.Tarefa;

public class TesteShuffle {
	
	public static void main(String[] args) {
		
		Tarefa[] lista = new Tarefa[10];
		for (int i = 0; i < lista.length; i++) {
			lista[i] = new Tarefa(new Random().nextInt(9));			
		}
		
		printaLista(lista);
		
		List<Tarefa> tarefas = Arrays.asList(lista);
		
		Collections.shuffle(tarefas);
		
		lista = (Tarefa[]) tarefas.toArray();
		
		System.out.println();
		printaLista(lista);
		
		tarefas = Arrays.asList(lista);
		
		Collections.shuffle(tarefas);
		
		lista = (Tarefa[]) tarefas.toArray();
		
		System.out.println();
		printaLista(lista);
		
		
	}

	private static void printaLista(Tarefa[] lista) {
		for (int i = 0; i < lista.length; i++) {
			System.out.print(lista[i].getId()+" -> ");			
		}
	}

}
