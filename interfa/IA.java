package interfa;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.ArrayList;

public class IA  {

	grille g;

	double[] bench;
	int lastProfondeur=0;
	int lastRandomiz=-1;
	@SuppressWarnings("rawtypes")
	ArrayList [][] lastPrev=null;
	int lastMov;
	int lastH;
	int Ni=2;

	public String[] mMov = {"UP","DOWN","LEFT","RIGHT"};

	public IA(grille g) {
		this.g=g;

		bench =  new double[4];
		//A.fV();
	}



	public int choixIt(){		//choisir jusqu'à quelle profondeur l'IA doit calculer
		int c= g.count(0);		//en nombre de grilles mémorisées par direction avant une denière itération de génération d'enfants
		
		if (g.sum()<2048){return 0;}
		 else{
			 if( c<1 ){return 1000;}
			 if( c<2 ){return 100;}
			 if( c<4 ){return 40;}
		 }


		//de fait en testant quand la grille est complètement pleine cela permet d'aller
		//jusqu'à (maximum observé ever) (durant moins de 3 secondes) 15 coups en avance
		 return 32; 

	}




	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String choix() throws ExecutionException{	//répondre un string de choix de direction face à une grille


		if (lastPrev==null){
			ArrayList[] potentiel=grillePoss();



			int Ni=choixIt();


			//1


			//on crée 4 callables qui vont renvoyer un score pour chaque direction
			//chacun d'entre eux va itérer une direction suffisamment longtemps (dépend de choixIt)
			//avant de calculer sur cet ensemble des possibles un score

			Callable<Double> [] callables = new Callable[4];
			callables[0] =new threads(potentiel[0],Ni);
			callables [1] =new threads(potentiel[1],Ni);
			callables[2] =new threads(potentiel[2],Ni);
			callables [3] =new threads(potentiel[3],Ni);

			//on crée notre pool de callable
			ExecutorService submit = Executors.newFixedThreadPool(4);

			//on récupère nos scores
			double[] bench = executeCallables(submit,callables);



			int[] prefMov= new int[4];
			double z;

			for (int j=0;j<4;j++){
				z=-5;
				for (int i=0;i<4;i++){
					if (z<bench[i]){
						z=bench[i];
						prefMov[j]=i;
					}
				}	
				bench[prefMov[j]]=-6-j;
			}
			int i=0;

			//puis on renvoie dans l'ordre la préférence de choix (on vérifie au cas où que le mouvement est bien possible (inutile de fait)

			for (int j=0;j<4;j++){
				if (g.control(mMov[prefMov[0]])){return mMov[prefMov[i]];}
			}
		}

		return "STOP";

	}



	//on génère les grilles enfants possibles pour cette direction
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList[] grillePoss(){				//l'objet renvoyé est sale, mais il me semble la meilleure

		int[][] tmpGrid = g.copy();				//façon de représenter les matrices des valeurs possibles au rang suivant
		int[][] tFree;										//en tant que tableau des mouvements possibles d'un tableau de toutes les 
		int nbFree;											//possibilités selon les cases vides, des matrices des valeurs
		//On aurait pu stocker des grilles mais la manipulation est ainsi plus légère

		ArrayList<int[][]> H = new ArrayList<int[][]>();
		ArrayList<int[][]> B = new ArrayList<int[][]>();
		ArrayList<int[][]> G = new ArrayList<int[][]>();
		ArrayList<int[][]> D = new ArrayList<int[][]>();
		ArrayList[] selection = {H,B,G,D};

		for (int l=0;l<4;l++){

			if (g.control(mMov[l])) {

				int [][]ttG = g.copy();

				g.mov(mMov[l]);
				tFree =g.free();
				nbFree=tFree[0][0];

				for (int h=0;h<nbFree;h++){

					g.add(2, tFree[h+1]);
					int [][] t2G =g.copy();
					selection[l].add(t2G);
					g.add(0, tFree[h+1]);
				}
				g.setg(ttG);

			}
			else selection[l]=new ArrayList<Object>();
		}
		g.setg(tmpGrid); 									
		return selection;
	}




	//récupération des scores de nos callables
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static double[] executeCallables(final ExecutorService service, Callable[] callables) throws ExecutionException{

		//Future <List> future; 

		Double tmp0 =(double)-2;
		Double tmp1=(double)-2;
		Double tmp2=(double)-2;
		Double tmp3=(double)-2;

		Future<Double> future0 =service.submit(callables[0]);
		Future<Double> future1 =service.submit(callables[1]);
		Future<Double> future2=service.submit(callables[2]);
		Future<Double> future3 =service.submit(callables[3]);


		try {
			tmp0=  (future0.get());} 
		catch (InterruptedException e) {e.printStackTrace();}

		try {
			tmp1= (future1.get());} 
		catch (InterruptedException e) {e.printStackTrace();}
		try {
			tmp2= (future2.get());} 
		catch (InterruptedException e) {e.printStackTrace();}

		try {
			tmp3= (future3.get());} 
		catch (InterruptedException e) {e.printStackTrace();}


		service.shutdown();
		double[] a = {tmp0,tmp1,tmp2,tmp3};

		return a;
	}



}


















