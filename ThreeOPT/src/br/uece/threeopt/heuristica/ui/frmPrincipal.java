package br.uece.threeopt.heuristica.ui;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class frmPrincipal extends JFrame {

	public frmPrincipal() {
		inicializaComponentes();
		this.setExtendedState(MAXIMIZED_BOTH);
	}
	
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo incializa os componentes do formulario
	 */
	private void inicializaComponentes() {
		
		this.setSize(500, 500);
		this.setLocationRelativeTo(null);
		this.setTitle("Caixeiro Viajante");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JMenuBar menu = new JMenuBar();
		JMenu menu1 = new JMenu();
		menu1.setText("Arquivo");
		menu.add(menu1);
		
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("Caixeiro Viajante");
		menu1.add(menuItem);				
		
		JMenuItem menuItem2 = new JMenuItem();
		menuItem2.setText("Sequenciamento");
		menu1.add(menuItem2);			
		
		setJMenuBar(menu); 
		
		menuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evento) {
            	menuItemActionPerformed(evento);
            }
        });
		
		menuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evento) {
            	menuItem2ActionPerformed(evento);
            }
        });
	}
		
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo chama o formulario do Caixeiro Viajante
	 * @param evento
	 */
	private void menuItemActionPerformed(java.awt.event.ActionEvent evento) {
		
		frmCaixeiroViajante form = new frmCaixeiroViajante();
		form.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
		form.setSize(500, 500);
		form.setLocationRelativeTo(null);
		form.setVisible(true);
	}
	
	/**
	 * @author raquel silveira e paulo alberto
	 * @version 1.0
	 * Este metodo chama o formulario do JSSP
	 * @param evento
	 */
	private void menuItem2ActionPerformed(java.awt.event.ActionEvent evento) {
		
		frmJSSP form = new frmJSSP();
		form.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
		form.setSize(500, 500);
		form.setLocationRelativeTo(null);
		form.setVisible(true);
	}
}
