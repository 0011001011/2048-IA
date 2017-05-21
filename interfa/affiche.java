package interfa;

public class affiche {
	
	
	//simplement l'affichage  en console au cas où
	public static void r (grille G){

		System.out.println(Integer.toString(G.getg()[3][0])+ " | "+ Integer.toString(G.getg()[3][1])+ " | " +Integer.toString(G.getg()[3][2])+" | "+Integer.toString(G.getg()[3][3]));
		System.out.println(Integer.toString(G.getg()[2][0])+ " | "+ Integer.toString(G.getg()[2][1])+ " | "+ Integer.toString(G.getg()[2][2])+" | "+Integer.toString(G.getg()[2][3]));
		System.out.println(Integer.toString(G.getg()[1][0])+ " | "+ Integer.toString(G.getg()[1][1])+ " | "+ Integer.toString(G.getg()[1][2])+" | "+Integer.toString(G.getg()[1][3]));
		System.out.println(Integer.toString(G.getg()[0][0])+ " | "+ Integer.toString(G.getg()[0][1])+ " | "+ Integer.toString(G.getg()[0][2])+" | "+Integer.toString(G.getg()[0][3]));
		
		System.out.println(G.score());
		System.out.println();
	}

}
