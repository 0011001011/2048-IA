package interfa;

import java.util.Arrays;

public class grille { 			//grille est en fait une simple matrice à laquelle on ajoute des fonctions pour bouger/apparaitre du random, etc..
	private int[][] grid;
	private int N;
	private int M;
	private int lastRandom;		//indice du dernier chiffre aléatoire ajouté à la grille
	int c=0;				


	public int getLastRandom() {  
		return lastRandom;
	}

	public double score(){						//notre critére de base : pondération 4000 3000 2000 1000 sur la ligne 1
		//return (16^5 -this.count (0)^3);		//400 300 200 100 sur la ligne 2, etc..
		
		double [][]sum= {{0,0},{0,0},{0,0},{0,0}};
		
		
		


		//on calcule en fait les 8 serpentins possibles
		//il y a clairement moyen de factoriser le code..
		
		
		for (int i=0;i<4;i++){
			sum[0][0]+=grid[0][i]*(4-i)*1000;
			sum[0][0]+=grid[2][i]*(4-i)*10;
			sum[2][0]+=grid[0][i]*(i+1)*1000;
			sum[2][0]+=grid[2][i]*(1+i)*10;
		}
		for (int i=0;i<4;i++){
			sum[0][0]+=grid[1][3-i]*(4-i)*100;
			sum[0][0]+=grid[3][3-i]*(4-i);
			sum[2][0]+=grid[1][3-i]*(i+1)*100;
			sum[2][0]+=grid[3][3-i]*(i+1);
		}
		
		
		
		for (int i=0;i<4;i++){
			sum[0][1]+=grid[i][0]*(4-i)*1000;
			sum[0][1]+=grid[i][2]*(4-i)*10;
			sum[2][1]+=grid[i][0]*(1+i)*1000;
			sum[2][1]+=grid[i][2]*(1+i)*10;
		}
		for (int i=0;i<4;i++){
			sum[0][1]+=grid[3-i][1]*(4-i)*100;
			sum[0][1]+=grid[3-i][3]*(4-i);
			sum[2][1]+=grid[3-i][1]*(1+i)*100;
			sum[2][1]+=grid[3-i][3]*(1+i);
		}
		
		
		
		
		for (int i=0;i<4;i++){
			sum[1][0]+=grid[3][3-i]*(4-i)*1000;
			sum[1][0]+=grid[1][3-i]*(4-i)*10;
			sum[3][0]+=grid[3][3-i]*(i+1)*1000;
			sum[3][0]+=grid[1][3-i]*(1+i)*10;
		}
		for (int i=0;i<4;i++){
			sum[1][0]+=grid[2][3-i]*(4-i)*100;
			sum[1][0]+=grid[0][3-i]*(4-i);
			sum[3][0]+=grid[2][3-i]*(i+1)*100;
			sum[3][0]+=grid[0][3-i]*(i+1);
		}
		
		

		for (int i=0;i<4;i++){
			sum[1][1]+=grid[3-i][3]*(4-i)*1000;
			sum[1][1]+=grid[3-i][1]*(4-i)*10;
			sum[3][1]+=grid[3-i][3]*(1+i)*1000;
			sum[3][1]+=grid[3-i][1]*(1+i)*10;
		}
		for (int i=0;i<4;i++){
			sum[1][1]+=grid[i][2]*(4-i)*100;
			sum[1][1]+=grid[i][0]*(4-i);
			sum[3][1]+=grid[i][2]*(1+i)*100;
			sum[3][1]+=grid[i][0]*(1+i);
		}
		
		
		
		// autre critère à utiliser en complément (multiplication pas le score par exemple
		/*
		int somme = 0;
		for (int i=0;i<4;i++){
			for (int j=0;j<4;j++){
				int temp=0;
				int[][] tab = {{i+1, j} , {i-1, j} , {i,j+1} , {i,j-1} };
				int k =0; 
				
				while ( k<4 && 0<=tab[k][0] && tab[k][0]<=3 && 0<=tab[k][1] && tab[k][1]<=3  ){
					temp = temp  + grid[tab[k][0]][tab[k][1]];
					k++;
				}
				int tempbis = 0;
				if(i+1>3){
					tempbis = tempbis+temp*grid[i][j];}
				if(j+1>3){
					tempbis = tempbis+temp*grid[i][j];}
				if(i-1<0){
					tempbis = tempbis+temp*grid[i][j];}
				if(j-1<0){
					tempbis = tempbis+temp*grid[i][j];}
					
				somme = somme + (temp+tempbis)*grid[i][j];
			}
		}
		*/
		
		
		
		
		//on récupère le meilleur score de serpentin
		//pour considérer toutes les directions également
		
		double score=Math.max( Math.max(Math.max(sum[0][1],sum[0][0]), Math.max(sum[1][0], sum[1][1])),
				Math.max(Math.max(sum[2][0],sum[2][1]), Math.max(sum[3][0], sum[3][1])));
		
		//puis on multiplie pas ça par la racine du nombres de cases vides
		
		return Math.pow((count(0)+1),.4)*score;//*(count(0)+1);//*Math.sqrt(sum2()/(max()^2));//*Math.pow((sommeSami+2)/max()^2*2,.2);//*Math.pow(sommeSami+2,.1) ;//Math.pow((1+count(0)/16),.5));

	}
	
	public int max(){
		int o=0;
		for (int i=0;i<N;i++){
			for (int j=0;j<N;j++){
				o=Math.max(o, grid[i][j]);
			}
		}
		return o;
	}
	
	public double sum() {
		double sum=0;
		for (int i=0;i<N;i++){
			for (int j=0;j<N;j++){
				sum+=grid[i][j];
			}
		}
		return sum;
	}
	public double sum2() {  //sommedescarrés
		double sum=0;
		for (int i=0;i<N;i++){
			for (int j=0;j<N;j++){
				sum+=grid[i][j]*grid[i][j];
			}
		}
		return sum;
	}

	public grille (int N, int M){
		this.N=N;
		this.M=M;
		grid = new int[N][N];
		for (int i = 0; i< N; i++){
			for (int j = 0; j< N; j++){
				grid[i][j]=0;
			}
		}



	}
	public int getN(){
		return this.N;
	}

	public int[][] getg(){
		return this.grid;
	}

	public void setg(int[][] a){
		this.grid=a;
	}

	public void mov(String s){
		switch(s){
		case "DOWN":
			this.DOWN();
			break;
		case "UP":
			this.UP();
			break;
		case "LEFT":
			this.LEFT();
			break;
		case "RIGHT":
			this.RIGHT();
			break;
		}
	}



	public void DOWN (){      
		for (int j =0; j<N;j++){                              // on fait colonne par colonne
			for (int i=0; i<N; i++){   //on va faire tomber une case non-nulle sur la case du bas i
				int h=0;
				while(grid[h+i][j]==0){			            //on compte
					h++;
					if (h+i>=N) break;	
				}
				if(h!=0){
					for (int k=0; k+h+i<N;k++){              //puis on dégage les zéros
						grid[i+k][j]=grid[i+h+k][j];
						grid[i+h+k][j]=0;
						
					}
				}
				h=1;
				//break s'il ne reste au-dessus que des zéros
				if (h+i>=N) break;                  //on compte cb de cases entre celle du bas i et la prochaine non-nulle (au moins 1)
				while (grid[i+h][j]==0 && h<N-i){  // en remontant la colonne
					h++;
					if(h+i>=N) break;
				}

				if (h+i<N && grid[i][j]==grid[i+h][j] && grid[i+h][j]!=0) {   // on fusionne et gaffe aux trous
					
					grid[i][j]*=M;
					grid[i+h][j]=0;


				}
			}
		}
		/*
		for (int j =0; j<N;j++){          //méthode plus économe en calcul (a priori?) éventuellement à implémenter
			for (int i =N-1; i>0; i--){   //pour les autres directions
				if (grid[i-1][j]==0){
					grid[i-1][j]=grid[i][j];
					grid[i][j]=0;
				}
			}
			for (int i=0;i<N-1;i++){
				if (grid[i][j]==grid[i+1][j]){
					grid[i][j]*=M;
					grid[i+1][j]=0;
				}
			}
			for (int i =N-1; i>0; i--){
				if (grid[i-1][j]==0){
					grid[i-1][j]=grid[i][j];
					grid[i][j]=0;
				}
			}
		}
		 */
	}



	public void LEFT (){
		for (int j =0; j<N;j++){       
			for (int i=0; i<N; i++){   
				int h=0;
				while(grid[j][h+i]==0){		
					h++;
					if (h+i>=N) break;	
				}
				if(h!=0){
					for (int k=0; k+h+i<N;k++){
						grid[j][i+k]=grid[j][i+h+k];
						grid[j][i+h+k]=0;
						
					}
				}
				h=1;

				if (h+i>=N) break;
				while (grid[j][i+h]==0 && h<N-i){        
					h++;
					if(h+i>=N) break;
				}

				if (h+i<N && grid[j][i]==grid[j][i+h] && grid[j][i+h]!=0) {   // on fusionne et gaffe aux trous
					
					grid[j][i]*=M;
					grid[j][i+h]=0;


				}
			}
		}
	}


	public void UP (){
		for (int j =0; j<N;j++){                              
			for (int i=N-1; i>=0; i--){   
				int h=0;
				while(grid[i-h][j]==0){		
					h++;
					if (i-h<0) break;	
				}
				if(h!=0){
					for (int k=0; i-k-h>=0;k++){
						grid[i-k][j]=grid[i-h-k][j];
						grid[i-h-k][j]=0;
						
					}
				}
				h=1;

				if (i-h<0) break;
				while (grid[i-h][j]==0 && h<=i){    
					h++;
					if(i-h<0) break;
				}

				if (i-h>=0 && grid[i][j]==grid[i-h][j] && grid[i-h][j]!=0) {  
					
					
					
					grid[i][j]*=M;
					grid[i-h][j]=0;


				}
			}
		}
	}


	public void RIGHT (){
		for (int j =0; j<N;j++){                      
			for (int i=N-1; i>=0; i--){   
				int h=0;
				while(grid[j][i-h]==0){		
					h++;
					if (i-h<0) break;	
				}
				if(h!=0){
					for (int k=0; i-k-h>=0;k++){
						grid[j][i-k]=grid[j][i-h-k];
						grid[j][i-h-k]=0;
						
					}
				}
				h=1;

				if (i-h<0) break;
				while (grid[j][i-h]==0 && h<=i){     
					h++;
					if(i-h<0) break;
				}

				if (i-h>=0 && grid[j][i]==grid[j][i-h] && grid[j][i-h]!=0) {  
					;
					grid[j][i]*=M;
					grid[j][i-h]=0;


				}
			}
		}
	}


	//compter le nombre d'occurrence de val dans la grille
	public int count(int val){
		int c=0;
		for (int i=0; i<N;i++){
			for (int j=0;j<N;j++){
				if (grid[i][j]==val) c++;
			}
		}
		return c;
	}

	public void random(){ //génération des nombre aléatoirement dans la grille
		int [][] tab =this.free();
		int c = tab[0][0];
		c= (int) Math.floor(Math.random()*c);
		if (Math.random()>.9){
			this.grid[tab[c+1][0]][tab[c+1][1]]=M*M;
		}
		else {
			this.grid[tab[c+1][0]][tab[c+1][1]]=M;
		}
		lastRandom = c;
	}

	public int[][] free(){				//renvoit un tableau des coordonnées des cases vides
		int[][] tab = new int[this.N*N+1][2]; 
		int c=1;
		for (int i=0; i<this.N; i++){
			for (int j=0; j<this.N;j++){
				if (this.grid[i][j]==0){
					tab[c][0]=i;
					tab[c][1]=j;
					c++;
				}
			}
		}
		tab[0][0]=c-1;
		return tab;
	}

	public void add(int a, int [] ij){
		this.grid[ij[0]][ij[1]]=a;
	}


	public boolean control(String s){  //vérifier si direction admissible
		if (s=="STOP"){return false;}
		
		int[][] h = new int [N][N]; 
		for (int i=0;i<N;i++){
			for (int j=0;j<N;j++){
				h[i][j]=this.grid[i][j];
			}
		}
		this.mov(s);
		boolean c= true;
		for (int i=0;i<N;i++){
			for (int j=0;j<N;j++){
				c=c&&(h[i][j]==grid[i][j]);
			}
		}

		if (c){
			return false;
		}
		else { this.grid=h; return true; }
	}

	public int[][] copy(){	//un clonage de grille
		int [][] co = new int[grid.length][];
		for(int i = 0; i < grid.length; i++)
		{
			int[] aMatrix = grid[i];
			int   aLength = aMatrix.length;
			co[i] = new int[aLength];
			co[i]=Arrays.copyOf(grid[i],grid[i].length);
		}
		return co;
	}
	
	public grille(int N,int M, Object o){
		this.N=N;
		this.M=M;
	}

	

}