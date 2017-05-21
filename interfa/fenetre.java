package interfa;

import javax.swing.JFrame;
import java.awt.event.*;
import java.util.concurrent.ExecutionException;

public class fenetre extends JFrame implements KeyListener,Runnable{

	
	private static final long serialVersionUID = 1L;
	private gameplay gpl; //= new grille(4);
	private Panneau pan;
	static boolean auto;
	private Thread thread;
	private IA ia;
	
	//la fenêtre a globalement pour rôle d'implémenter KeyListeners pour les controles et de générer un nouveau gameplay quand il le faut (début et fin de partie)
	//elle instancie une IA si espace est appuyé
	
	public fenetre() 
	{
		int tailleGrille = 4;
		int base =2;

		setTitle ("2048");
		setSize(500,500);
		addKeyListener (this);
		setVisible(true);

		gpl = new gameplay(tailleGrille,base);
		ia = new IA(gpl.getG());
		//affiche.r(gpl.getG());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pan = new Panneau(gpl);
		this.setContentPane(pan);

		auto=false;
		thread = new Thread(this);
		thread.start();

		
	}


	

	public void keyPressed (KeyEvent e){
		gameplay.insertCoin=false;
		switch (e.getKeyCode()){
		case KeyEvent.VK_DOWN:
			gpl.mov("DOWN");
			pan.setGpl(gpl);

			pan.removeAll();
			pan.repaint();
			//affiche.r(gpl.getG());
			break;
		case KeyEvent.VK_UP:
			gpl.mov("UP");
			//affiche.r(gpl.getG());
			
			pan.setGpl(gpl);

			pan.removeAll();
			pan.repaint();break;

		case KeyEvent.VK_LEFT:
			gpl.mov("LEFT");
			//affiche.r(gpl.getG());
			pan.setGpl(gpl);

			pan.removeAll();
			pan.repaint();
			break;

		case KeyEvent.VK_RIGHT:
			gpl.mov("RIGHT");
			//affiche.r(gpl.getG());
			pan.setGpl(gpl);

			pan.removeAll();
			pan.repaint();
			break;
		case KeyEvent.VK_BACK_SPACE:
			gpl.back();
			this.setContentPane(pan);
			//affiche.r(gpl.getG());
			break;

		case KeyEvent.VK_SPACE:
			auto=!auto;
			if (auto){ia=new IA(gpl.getG());};
			this.setContentPane(pan);
			//affiche.r(gpl.getG());
			break;

		case KeyEvent.VK_UNDERSCORE:
			gpl.newgame(4, 2);
			this.setContentPane(pan);
			//affiche.r(gpl.getG());
			break;
			
		case KeyEvent.VK_ENTER:
			gameplay.insertCoin=false;
			break;
		}

	}



	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}


	@Override
	public void run() {				//le thread de la fenêtre qui gère à un certain rafraichissement les mouvements de l'ia
		int c=0;
		double s=0;
		while(true){
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
				while (auto){
					try {
						Thread.sleep(5);
					} catch (InterruptedException ee) {
						
						ee.printStackTrace();
					}
					try {
						String choix = ia.choix();
						if (choix=="STOP"){				//si l'IA s'arrête elle recommencera et au final donnera une moyenne de ses scores
							s+=gpl.getG().sum();
							System.out.println(gpl.getG().sum()+" "+c);
							
							c++;
							System.out.println("moyenne 2 : "+(s/c));
							if (c<60){gpl=new gameplay(4,2);}
							ia = new IA(gpl.getG());
							
						}
						gpl.mov(choix);
					} catch (ExecutionException e) {
						
						e.printStackTrace();
					}
					
					pan.setGpl(gpl);

					pan.removeAll();
					pan.repaint();

					
				}
			
		}
	}
}
