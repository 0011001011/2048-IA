package interfa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;

//par habitude je l'ai appelée threads, mais il était plus
//simple je trouve d'utiliser les callable pour renvoyer directement le score

public class threads implements Callable <Double> {

	public String[] mMov = {"UP","DOWN","LEFT","RIGHT"};
	public ArrayList<int[][]> potentiel;
	public int Ni=-1;
	int depth;
	grille g = new grille(4,2);
	int nbGrille;
	int N;
	boolean mort=false;

	//on crée un threads à partir d'un arraylist de grilles potentiel enfant de notre grille de départ
	public threads(ArrayList<int[][]> potentiel, int Ni){
		this.potentiel=potentiel;
		this.Ni=Ni;
		N=0;
	}

	public threads(int[][] grid, int Ni){

		this.Ni=Ni;
		N=0;
		depth=0;
	}


	//au cas où la direction ne donne aucun enfant possible (direction non admissible)
	public threads(int i) {
		mort=true;
	}

	//méthode de scorage de notre objet représentant les enfants possibles de la grille dans une certaine direction
	public double scorageG(int[][]tab){
		if (tab!=null){

			grille g = new grille(4,2);
			g.setg(tab);
			int[][] tFree;
			int nbFree;
			double max=-3;
			double[] moyenne=new double[4];
			for (int i=0;i<4;i++){
				if (g.control(mMov[i])==true) {
					g.mov(mMov[i]);
					tFree =g.free();
					nbFree=tFree[0][0];

					for (int j=0;j<nbFree;j++){
						g.add((int)2, tFree[j+1]);
						moyenne[i]+=g.score();
						g.add((int)0, tFree[j+1]);
					}
					moyenne[i]/=nbFree;	
					max=Math.max(max, moyenne[i]);
				}
			}
			return max;}
		else return -1;
	}

	//itérer à la génération d'au-dessus notre tableau des grilles possibles
	public ArrayList<int[][]> iterate(){

		ArrayList <int[][]> DeepPotentiel = new ArrayList<int[][]>();
		int sizePot = potentiel.size();
		grille G=new grille(4,2);

		for (int N=0;N<sizePot;N++){

			G.setg(potentiel.get(N));
			DeepPotentiel.addAll(tabEnfnt(G));
		}
		return DeepPotentiel;
	}





	//notre méthode expectedmax (en fait on fait une moyenne des carrés)
	//                          (après benchs ^2 est la meilleure fonction)
	public double critereDir(ArrayList<int[][]> L){
		if (L!=null){



			
			double score = 0;
			double critere;
			
			grille G = new grille (4,2);

			critere = 0;
			//boolean prudence =false;
			
			int tFree[][];
			int nbFree;
			int cc=0;
			
			double moyenne=0000000;

			for (int i=0;i<L.size();i++){

				G.setg(L.get(i));

				double maX=-1;
				for (int j=0;j<4;j++){

					if (G.control(mMov[j])==true) {


						G.mov(mMov[j]);
						tFree =G.free();
						nbFree=tFree[0][0];
						int c=0;
						critere=0;
						for (int h=0;h<nbFree;h++){

							G.add((int)2, tFree[h+1]);
							score=G.score();
							G.add((int)0, tFree[h+1]);
							//min=Math.min(min, score);
							//max=Math.max(max, score);
							critere+= (score);
							c++;
							
						}
						//critere/=c;
						maX=Math.max(critere/c, maX);
						//scores[j]=critere;
					}
				}
				moyenne+=Math.pow(maX,2);
				cc++;
				//maxTmp=Math.max(maxTmp, critere);
				//c++;
			}
			//critere=maxTmp;




			//Arrays.sort(scores);
			//mediane = scores[L.size()/2];
			
			moyenne = moyenne/cc;

			//System.out.println("mediane : "+mediane);

			//if (prudence ==true){return max;}
			return moyenne ;
		}
		else return (double)-1;
	}


	//cette méthode crée des arrayLists de possibles grilles à partir d'une grille donnée en entrée
	
	public Collection<? extends int[][]> tabEnfnt(grille gril){	

		int[][] tG = gril.copy();				
		int[][] tFree;										 
		int nbFree;											
		ArrayList<int[][]> selection= new ArrayList<int[][]>();

		
		for (int l=0;l<4;l++){
			int[][] ttG = gril.copy();

			if (gril.control(mMov[l])==true) {


				gril.mov(mMov[l]);
				tFree =gril.free();
				nbFree=tFree[0][0];

				for (int h=0;h<nbFree;h++){
					gril.add((int)2, tFree[h+1]);
					int [][] t2G =gril.copy();
					selection.add(t2G);
					gril.add((int)0, tFree[h+1]);
					
				}
			}	

			gril.setg(ttG);

		}
		gril.setg(tG);
		return selection;
	}







	//dans notre méthode principale nous allons générer des enfants (une génération de plus)
	//tant que nous n'avons pas générer un nombre limite de grilles en mémoire déterminé par Ni que nous donne l'IA (avec choixIt)
	
	public Double call() throws Exception {



		depth=0;
		int Z=-1;
		Double score;
		if (potentiel.size()<=0){
			score = (double)-1;
			return score;
		}

		int N=potentiel.size();


		do{
			depth++;
			N=potentiel.size();

			if (potentiel!=null){
				potentiel=iterate();}
			if (Z==N){ 
				return new Double (critereDir(potentiel));
			}
			Z=N;

		} while (N<Ni);

		
		score=critereDir(potentiel);

		return score;
	}





}




