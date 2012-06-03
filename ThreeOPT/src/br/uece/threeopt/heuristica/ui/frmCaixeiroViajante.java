package br.uece.threeopt.heuristica.ui;

import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Label;
import java.awt.TextField;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

import br.uece.threeopt.heuristica.caixeiroviajante.Caminho;
import br.uece.threeopt.heuristica.caixeiroviajante.Celula;
import br.uece.threeopt.heuristica.caixeiroviajante.Ponto;
import br.uece.threeopt.heuristica.opt.ThreeOpt;
import br.uece.threeopt.utils.ArquivoUtils;

public class frmCaixeiroViajante extends JFrame {

	Label label1 = null;
	TextField txtArquivo = null;
	JPanel panel = null;
	JPanel panel2 = null;
	JLabel lblN = null;
	JLabel lblOtima = null;
	File file = null;
	Celula[][] matriz = null;
	int valorZoom = 100;
	JSlider slider = null;
	Ponto[] pontos = null;
	
	public frmCaixeiroViajante() {
		inicializaComponentes();
		this.setExtendedState(MAXIMIZED_BOTH);
	}
	
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo inicializa os componentes existentes no JFrame
	 */
	private void inicializaComponentes() {

		this.setTitle("Caixeiro Viajante - Guloso com n partidas");
		panel = new javax.swing.JPanel();
		label1 = new Label();
		label1.setText("Arquivo");
		txtArquivo = new TextField(50);
		txtArquivo.setEditable(false);
		txtArquivo.setSize(20, 100);
		Button btnAbreArquivo = new Button("Selecionar arquivo");
		Button btnGeraSolucao = new Button("Gerar solução");
		panel.add(label1);
		panel.add(txtArquivo);
		panel.add(btnAbreArquivo);
		panel.add(btnGeraSolucao);
		
		this.getContentPane().add(panel, java.awt.BorderLayout.NORTH);
		
		panel2 = new javax.swing.JPanel();
		panel2.setBackground(Color.LIGHT_GRAY);
		this.getContentPane().add(panel2, java.awt.BorderLayout.CENTER);
		panel2.setLayout(null);
		
		JLabel label2 = new JLabel();
		label2.setText("n: ");
		label2.setBounds(10, 10, 20, 20);
		panel2.add(label2);
		
		lblN = new JLabel();
		lblN.setBounds(30, 10, 100, 20);
		panel2.add(lblN);
		
		JLabel label3 = new JLabel();
		label3.setText("Solução Menor Caminho: ");
		label3.setBounds(10, 30, 100, 20);
		panel2.add(label3);
		
		lblOtima = new JLabel();
		lblOtima.setBounds(100, 30, 500, 20);
		panel2.add(lblOtima);
		
			
		slider = new JSlider(0, 200, 100);
		slider.setBorder(BorderFactory.createTitledBorder("Zoom"));
	    slider.setMajorTickSpacing(50);
	    slider.setMinorTickSpacing(25);
	    slider.setPaintTicks(true);
	    slider.setPaintLabels(true);
	    
	    slider.addChangeListener(  
	            new ChangeListener() {  
	            
	            public void stateChanged(ChangeEvent evt)  
	            {  
	            	if (!slider.getValueIsAdjusting())
	            	{
	            		valorZoom = slider.getValue();
	            		desenhaTrajetoria(pontos);
	            	}
	            }  
	         }); 
	    panel2.add(slider);
		
		btnAbreArquivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evento) {
            	btnAbreArquivoActionPerformed(evento);
            }
        });
		
		btnGeraSolucao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evento) {
            	btnGeraSolucaoArquivoActionPerformed(evento);
            }
        });
	}
	
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo obtem o arquivo 
	 * @param evento
	 */
	private void btnAbreArquivoActionPerformed(java.awt.event.ActionEvent evento) {
		
			JFileChooser fc = new JFileChooser();
			fc.setAcceptAllFileFilterUsed(false);
			
			fc.addChoosableFileFilter(new FileFilter() {
				
				@Override
				public String getDescription() {
					return null;
				}
				
				@Override
				public boolean accept(File f) {
					if (f.isDirectory()) { return true; }

			        String extension = getExtension(f);
			        if (extension != null) {
			            if (extension.equals("txt")) {
			                    return true;
			            } else { return false; }
			        }
			        return false;
				}
			});	
			
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
	            
				file = fc.getSelectedFile();
	            txtArquivo.setText(file.getPath());
	        }
			
			ArquivoUtils au = new ArquivoUtils();
			matriz = au.lerArquivo(file);
	}
	
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo trata se o arquivo eh valido e gera a solucao caso seja
	 * @param evento
	 */
	private void btnGeraSolucaoArquivoActionPerformed(java.awt.event.ActionEvent evento) {
		
		if (file == null) {
			JOptionPane.showMessageDialog(this, "Antes de gerar a solução, é necessário escolher um arquivo.");
		} else {
			geraSolucao();
		}
	}
	
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo desenha as coordenadas x e y de acordo com o tamanho do painel
	 * @param grafico
	 */
	private void desenhaCoordenada(java.awt.Graphics2D g) {
		
		g.setColor(Color.DARK_GRAY);
		g.drawLine(0, panel2.getHeight()/2, panel2.getWidth(), panel2.getHeight()/2);
		g.drawLine(panel2.getWidth()/2, 0, panel2.getWidth()/2, panel2.getWidth()/2);
		
		slider.setBounds(panel2.getWidth() - 200, panel2.getHeight() - 70, 200, 70);
	}
	
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo limpa o que foi desenhado no painel
	 * @param grafico
	 */
	private void limpaTela(java.awt.Graphics2D g) {
		
		panel2.paintAll(g);
	}
	
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo plota os pontos no grafico
	 * @param grafico
	 */
	private void desenhaPontos(java.awt.Graphics2D g) {
			
			double zoom = getZoom();
			g.setColor(Color.BLACK);
			if (matriz != null) {
				for(int i = 0; i < matriz.length; i++) {
					if (matriz[i][0].getOrigem().getCoordX() != null && matriz[i][0].getOrigem().getCoordY() != null)
						g.fill(new Ellipse2D.Double((matriz[i][0].getOrigem().getCoordX()*zoom + panel2.getWidth()/2 - 2), (panel2.getHeight()/2 - matriz[i][0].getOrigem().getCoordY()*zoom - 2), 4, 4));
				}
			}
	}
	
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo desenha a trajetoria entre os pontos de acordo com a solucao obtida
	 * @param grafico
	 * @param ciclo
	 */
	private void desenhaTrajetoria(Ponto[] ciclo) {
		
		Graphics2D g = (Graphics2D)panel2.getGraphics();
		limpaTela(g);
		desenhaCoordenada(g);
		desenhaPontos(g);
				
		Ponto origem = null;
		Ponto destino = null;
		
		for (int i = 0; i < ciclo.length - 1; i++) {
			origem = ciclo[i];
			destino = ciclo[i+1];
			
			System.out.print(ciclo[i].getId() + " - ");
			
			if (origem.getCoordX() != null && origem.getCoordY() != null && destino.getCoordX() != null && destino.getCoordY() != null) {
				double zoom = getZoom();
				g.setColor(Color.BLUE); 
				g.drawLine((int)(origem.getCoordX()*zoom + panel2.getWidth()/2), (int)(panel2.getHeight()/2 - origem.getCoordY()*zoom), (int)(destino.getCoordX()*zoom + panel2.getWidth()/2), (int)(panel2.getHeight()/2 - destino.getCoordY()*zoom));
			}
		}
		
		System.out.println();
	}
	
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo obtem o valor de proporcao do zoom
	 * @return valor de proporcao do zoom
	 */
	private double getZoom() {
	
		double valor = 1;
		if (valorZoom >= 0 && valorZoom < 25) { valor = 0.1; }
		if (valorZoom >= 25 && valorZoom < 50) { valor = 0.25; }
		if (valorZoom >= 50 && valorZoom < 75) { valor = 0.5; }
		if (valorZoom >= 75 && valorZoom < 100) { valor = 0.75; }
		if (valorZoom >= 100 && valorZoom < 125) { valor = 1; }
		if (valorZoom >= 125 && valorZoom < 150) { valor = 2; }
		if (valorZoom >= 150 && valorZoom < 175) { valor = 3; }
		if (valorZoom >= 175 && valorZoom < 200) { valor = 4; }
		if (valorZoom >= 200) { valor = 5; }
		return valor;
	}
	
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo obtem a extensao do arquivo
	 * @param arquivo
	 * @return extensao do arquivo
	 */
	private String getExtension(File f) {
		
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
	
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo gera o grafico com os pontos de acordo
	 */
	private synchronized void geraSolucao() {
		
		try {
			Double distancia = 0.0;
			lblN.setText(matriz.length+"");
			lblOtima.setText(distancia+"");
							
			List<Caminho> caminhos = new ThreeOpt().obtemCaminhos(matriz);
			
			for (Caminho caminho : caminhos) {
				 
				desenhaTrajetoria(caminho.getPonto());
				lblOtima.setText(caminho.getDistancia().toString());
				System.out.println(caminho.getDistancia());
				Thread.sleep(300);
			}
			System.out.print("Tempo total: " + ThreeOpt.tempoTotal + " ms");
		}
		catch(InterruptedException e) {}	
	}
}
