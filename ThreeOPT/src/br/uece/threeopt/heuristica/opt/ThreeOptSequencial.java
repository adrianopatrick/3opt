package br.uece.threeopt.heuristica.opt;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import br.uece.threeopt.heuristica.sequenciamento.Job;
import br.uece.threeopt.heuristica.sequenciamento.Maquina;
import br.uece.threeopt.heuristica.sequenciamento.Matriz;
import br.uece.threeopt.heuristica.sequenciamento.Sequencia;
import br.uece.threeopt.heuristica.sequenciamento.Tarefa;
import br.uece.threeopt.utils.Sequenciador;

public class ThreeOptSequencial {
	
	public TreeSet<Sequencia> sequenciasTestadas = new TreeSet<Sequencia>();
	public static long tempoTotal = 0;
	public int naoMelhora = 0;
	private Job[][] jobs = Matriz.getJobs();
	private int parametro = 100000; //valor varia de solução a solução: refactory
	private int count;
		
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
			Sequencia seqInicial = new Sequencia(tarefas, tempoTotalDaSequencia);
			sequenciasTestadas.add(seqInicial);
			
			long inicio = System.nanoTime();
			executaShuffle(tarefas.clone());
			long fim = System.nanoTime();
			
			System.out.println("*** "+sequenciasTestadas.first());
			System.out.println("Tempo Total: "+(fim-inicio));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void executaThreeOPT(Tarefa[] tarefas){
		
		int index1 = 0, index2 = 0, index3 = 0;
		int[] indexs = new int[3];
		
		//gerador de novas sequencias de tarefas até todas as condições serem satisfeitas
		do {
			index1 = new Random().nextInt(tarefas.length);
			index2 = new Random().nextInt(tarefas.length);
			index3 = new Random().nextInt(tarefas.length);
			
			indexs[0] = index1;
			indexs[1] = index2;
			indexs[2] = index3;
			
		} while (index1 == index2 || index1 == index3 || index2 == index3 || 
				index2 == index1+1 || index3 == index2+1 ||  
				verificaSeRepetido(indexs, tarefas.clone()) || naoMelhora < parametro);
		
	}
	
	public void executaShuffle(Tarefa[] tarefas){
				
		List<Tarefa> listas = Arrays.asList(tarefas);
		while(count < 100000) {
			Collections.shuffle(listas);
			tarefas = (Tarefa[]) listas.toArray();
			verificaSeRepetidoShuffle(tarefas);
			listas = Arrays.asList(tarefas);
			count++;
		}
		
	}
	
	public boolean verificaSeRepetido(int[] indexs, Tarefa[] tarefas){
		
		List<Tarefa[]> tarefasGeradas = Sequenciador.geraSequenciasPossiveis(indexs, tarefas);
		
		for (Tarefa[] tarefas2 : tarefasGeradas) {
			Sequencia sequencia = new Sequencia(tarefas2, calculaTempoDaSequencia(tarefas2));
			//só adiciona se sequencia ainda não existir no Set (garantido pelo Set e método equals de Sequencia)
			if(sequenciasTestadas.add(sequencia)) {
				naoMelhora++;
			} 
			
		}
		
		if(naoMelhora >= parametro)
			return false;
		else
			return true;
		
	}
	
	public boolean verificaSeRepetidoShuffle(Tarefa[] tarefas){	
		
		Sequencia sequencia = new Sequencia(tarefas, calculaTempoDaSequencia(tarefas));
		
		//só adiciona se sequencia ainda não existir no Set (garantido pelo Set e método equals de Sequencia)
		if(sequenciasTestadas.add(sequencia)) {
			//System.out.println(naoMelhora);
			naoMelhora++;
			return false;
		} 
		return true;
		
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
