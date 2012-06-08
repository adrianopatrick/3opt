package br.uece.threeopt.heuristica.sequenciamento;

import java.io.File;

import br.uece.threeopt.heuristica.opt.ThreeOptSequencial;

public class Fabrica {
	
	public static void main(String[] args) {
		
		@SuppressWarnings("unused")
		Matriz jobs = new Matriz() {
			public File getArquivo() {
				return new File("/home/patrick/workspace/ThreeOPT/arquivos/instanciasSequenciamento/Car5.txt");
			}
		};
		
		ThreeOptSequencial tps = new ThreeOptSequencial();
		tps.obtemSequencias();		
		
	}
	
}
