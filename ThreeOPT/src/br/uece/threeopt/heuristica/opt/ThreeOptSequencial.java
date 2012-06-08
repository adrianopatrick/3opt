package br.uece.threeopt.heuristica.opt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import br.uece.threeopt.heuristica.sequenciamento.Job;
import br.uece.threeopt.heuristica.sequenciamento.Maquina;
import br.uece.threeopt.heuristica.sequenciamento.Matriz;
import br.uece.threeopt.heuristica.sequenciamento.Sequencia;
import br.uece.threeopt.heuristica.sequenciamento.Tarefa;

public class ThreeOptSequencial {
	
	public List<Job> sequencia = new ArrayList<Job>(); 
	public TreeSet<Sequencia> sequenciasTestadas = new TreeSet<Sequencia>();
	public static long tempoTotal = 0;
	public int naoMelhora = 0;
	private Job[][] jobs = Matriz.getJobs();	
		
	/**
	 * Método que efetivamente implementa o 3-OPT
	 * @author patrick
	 * */
	public List<Maquina> obtemSequencias(){
		
		try {
			//define um array com a sequencia de tarefas
			Tarefa[] tarefas = new Tarefa[Matriz.getJobs()[0].length];
			
			//parte da sequencia indicada no arquivo
			for (int i = 0; i < tarefas.length; i++) {
				tarefas[i] = Matriz.getJobs()[0][i].getTarefa();
			}			
			
			Integer tempoTotalDaSequencia = calculaTempoDaSequencia(tarefas);
			
			sequenciasTestadas.add(new Sequencia(tarefas, tempoTotalDaSequencia));
			long inicio = System.nanoTime();
			obtemNovaSequenciaRandomica(tarefas);
			long fim = System.nanoTime();
			Iterator<Sequencia> i = sequenciasTestadas.iterator();
			
			while(i.hasNext()){
				System.out.println(i.next());
			}
			
			System.out.println("*** "+sequenciasTestadas.first());
			System.out.println("Tempo Total: "+(fim-inicio/1000000000));
			System.out.println(naoMelhora);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void obtemNovaSequenciaRandomica(Tarefa[] tarefas){
		
		int index1 = 0, index2 = 0, index3 = 0;
		int[] indexs = new int[3];
		int parametro = 5000;
		
		do {
			index1 = new Random().nextInt(tarefas.length-1);
			index2 = new Random().nextInt(tarefas.length-1);
			index3 = new Random().nextInt(tarefas.length-1);
			
			indexs[0] = index1;
			indexs[1] = index2;
			indexs[2] = index3;
			if(naoMelhora > parametro)
				break;
			
		} while (index1 == index2 || index1 == index3 || index2 == index3 || verificaSeRepetido(indexs, tarefas) || naoMelhora < parametro);
		System.out.println(naoMelhora);
	}
	
	public boolean verificaSeRepetido(int[] indexs, Tarefa[] tarefas){
		
		Tarefa aux1, aux2;
		
		//altera sequencia das tarefas baseado nos indices gerados randomicamente
		aux1 = tarefas[indexs[1]];
		tarefas[indexs[1]] = tarefas[indexs[0]];
		aux2 = tarefas[indexs[2]];
		tarefas[indexs[2]] = aux1;
		tarefas[indexs[0]] = aux2;
		
		Sequencia sequencia = new Sequencia(tarefas, calculaTempoDaSequencia(tarefas));
		
		//sequencia já existe no Set ou o seu tempo é inferior a algum elemento do set (garantido pelo Set e método equals de Sequencia)
		if(sequenciasTestadas.add(sequencia)) {
			System.out.println(naoMelhora);
			naoMelhora++;
			return false;
		} else {
			return true;
		}
		
	}
	
	/**
	 * Método que calcula do Tempo da sequencia informada
	 * @author patrick
	 * */
	public Integer calculaTempoDaSequencia(Tarefa[] sequencia){
		
		/* define um array de tempo do tamanho da quantidade de máquinas do problema */
		Integer[] tempo = new Integer[jobs.length];
		for (int i = 0; i < tempo.length; i++) {
			tempo[i] = 0;
		}
		
		// j define a tarefa que está sendo calculada
		int j = 0;	
		
		// navega em cada uma das tarefas, veja que a primeira coisa é alterar o valor de j
		for (int k = 0; k < sequencia.length; k++) {
			j = sequencia[k].getId();
			
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
