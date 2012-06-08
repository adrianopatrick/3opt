package br.uece.threeopt.heuristica.sequenciamento;

import java.util.Arrays;


public class Sequencia implements Comparable<Sequencia>{
	
	private Tarefa[] tarefas;
	private Integer tempo;
	
	public Sequencia(Tarefa[] tarefas, Integer tempo) {
		this.tarefas = tarefas;
		this.tempo = tempo;
	}
	
	public Tarefa[] getTarefas() {
		return tarefas;
	}
	public void setTarefas(Tarefa[] tarefas) {
		this.tarefas = tarefas;
	}
	public Integer getTempo() {
		return tempo;
	}
	public void setTempo(Integer tempo) {
		this.tempo = tempo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tempo == null) ? 0 : tempo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sequencia other = (Sequencia) obj;
		if (tempo == null) {
			if (other.tempo != null)
				return false;
		} else if (!tempo.equals(other.tempo)){
			if(tempo > other.tempo)
				return false;
			else
				return true;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Sequencia [tarefas=" + Arrays.toString(tarefas) + ", tempo="
				+ tempo + "]";
	}

	@Override
	public int compareTo(Sequencia o) {
		return tempo.compareTo(o.getTempo());
	}
	
}
