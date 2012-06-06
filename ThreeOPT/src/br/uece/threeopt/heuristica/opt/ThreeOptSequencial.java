package br.uece.threeopt.heuristica.opt;

import java.util.ArrayList;
import java.util.List;

import br.uece.threeopt.heuristica.sequenciamento.Job;
import br.uece.threeopt.heuristica.sequenciamento.Maquina;
import br.uece.threeopt.heuristica.sequenciamento.Matriz;

public class ThreeOptSequencial {
	
	public List<Job> sequencia = new ArrayList<Job>(); 
	public static long tempoTotal = 0;
	public int naoMelhora = 0;
	private Job[][] jobs = Matriz.getJobs();	
		
	/**
	 * Método que efetivamente implementa o 3-OPT
	 * @author patrick
	 * */
	public List<Maquina> obtemSequencias(){
		
		try {
			//parte da sequencia indicada no arquivo
			Job[] sequenciaInicial = Matriz.getJobs()[0];
			
			Integer tempoTotalDaSequencia = calculaTempoDaSequencia(sequenciaInicial);
			
			//implementa 3-OPT
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * Método que calcula do Tempo da sequencia informada
	 * @author patrick
	 * */
	public Integer calculaTempoDaSequencia(Job[] sequencia){
		
		/* define um array de tempo do tamanho da quantidade de máquinas do problema */
		Integer[] tempo = new Integer[jobs.length];
		for (int i = 0; i < tempo.length; i++) {
			tempo[i] = 0;
		}
		
		// j define a tarefa que está sendo calculada
		int j = 0;	
		
		// navega em cada uma das tarefas, veja que a primeira coisa é alterar o valor de j
		for (int k = 0; k < sequencia.length; k++) {
			j = sequencia[k].getTarefa().getId();
			
			//o tempo da primeira máquina sempre será a soma do tempo anterior com o tempo da tarefa j na máquina 0
			tempo[0] += jobs[0][j].getTempo();
			
			// navega por todas as máquinas calculando a distribuição das tarefas em cada uma das máquinas
			for (int i = 1; i < tempo.length; i++) {
				//se o tempo da máquina atual já é maior que o tempo da máquina anterior, apenas pego o tempo da tarefa e adiciono na máquina
				if(tempo[i] > tempo[i-1])
					tempo[i] += jobs[i][j].getTempo();
				//senão o tempo da máquina atual será o tempo da máquina anterior mais o tempo da tarefa na máquina atual
				else
					tempo[i] = tempo[i - 1] + jobs[i][j].getTempo();
			}
			
		}
		//o tempo total será o tempo final da última máquina		
		return tempo[tempo.length - 1];
	}
	
}
