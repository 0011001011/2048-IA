package interfa;

import java.util.ArrayList;


public class gameplay {
	
	//le gameplay crée un jeu à partir de la grille (mais sans gérer l'affichage)
	// une méthode back est implémenté mais pour ne pas ralentir l'IA elle est commenté (oblige à stocker les grilles..)
	
	static boolean insertCoin;
	private int tailleGrille;
	private grille g;
	private int multiplicateur;
	ArrayList<int[][]> tmp= new ArrayList<int[][]>();
	private int nbCoup=0;
	int c;

	public gameplay(int N,int M){
		newgame(N,M);
		c=0;
	}

	public gameplay(int[][]i){

	}


	public void newgame (int N,int M){
		insertCoin=false;
		this.tailleGrille=N;
		this.multiplicateur=M;
		this.g=new grille (this.tailleGrille,this.multiplicateur);
		g.random();
		g.random();
		int [][]tg=g.copy();
		tmp.add(tg);
	}

	public void mov(String s) {				//gère le mouvement de la grille en faisant apparaitre le random, test de la direction et gère le gameover
		
		

			if (g.control(s)==true) {
				g.mov(s);
				g.random();
				g.copy();
				nbCoup++;
				//tmp.add(nbCoup, tg);;
			}
			else if (! (g.control("UP") || g.control("LEFT") || g.control("RIGHT") || g.control("DOWN"))) {
				System.out.println("GAME OVER");
				insertCoin=true;
				while (insertCoin){try {
					Thread.sleep(1000);
					System.out.println("insert coin(s)");
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}}

				newgame(tailleGrille, multiplicateur);
			}
		

	}

	public void back(){
		nbCoup--;
		int[][] h = tmp.get(nbCoup).clone();

		this.g.setg(h);
		this.g.random();
	}



	public ArrayList<int[][]> getTmp(){
		return tmp;
	}

	public int getnbCoup(){
		return nbCoup;
	}

	public grille getG(){
		return this.g;
	}

	public int getN(){
		return tailleGrille;
	}

	public int getmulti(){
		return multiplicateur;
	}


}
