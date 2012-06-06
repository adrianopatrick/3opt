package br.uece.threeopt.heuristica.sequenciamento;

import java.io.File;

import br.uece.threeopt.utils.ArquivoUtils;
/**
 * <p>
 * Mantem uma referência estática na memória da matriz
 * para que não seja necessário fazer i/o novamente
 * Cada vez que o usuário selecionar um novo path
 * uma nova instancia de Matriz deve ser criado para sobrescrever o 
 * método getArquivo.
 * </p>
 * <br>
 * Matriz jobs = new Matriz() { <br>
 *&nbsp;&nbsp;&nbsp;&nbsp; public File getArquivo() { <br>
 *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;return new File("path escolhido pelo usuário"); <br>
 *&nbsp;&nbsp;&nbsp;&nbsp; } <br>
 * }; <br>
 * 
 * @author patrick 
 
 * */
public class Matriz {
	
	private static Job[][] jobs;
	
	public Matriz() {
		jobs = ArquivoUtils.lerArquivoJSSP(getArquivo());
	}
	
	public static Job[][] getJobs(){		
		return jobs;		
	}
	
	public File getArquivo(){
		return new File("");
	}
	
	
	
}
