package interfa;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;

public class Panneau extends JLabel { 

	
	private static final long serialVersionUID = 1L;
	public gameplay gpl;

	
	
	
	public Panneau (gameplay g){
		this.gpl=g;
		
	}
	

	public gameplay getGpl() {
		return gpl;
	}


	public void setGpl(gameplay gpl) {
		this.gpl = gpl;
	}

	public void paintComponent(Graphics g){
		
		//on crée un tableau des couleurs selon les chiffres
		
		Color[] CC= {new Color(150,150,150),new Color (150,150,150), new Color (150,150,150)
		,new Color(250,200,0),new Color(250,150,0),new Color(250,90,0),new Color(250,33,0)
		,new Color(250,0,70),new Color(190,0,255),new Color(0,30,255),new Color(7,111,3)
		,new Color(1,1,1),new Color(1,1,1),new Color(1,1,1),new Color(1,1,1)};

		//puis on les dessine tous
		
		int x= this.getWidth();
		int y= this.getHeight();
		for (int i=0; i<this.gpl.getN();i++){
			for (int j=0; j<this.gpl.getN();j++){
				int a= this.gpl.getG().getg()[this.gpl.getN()-j-1][i];
				if (a!=0){
					//a=(int) Math.pow(2, a);
					g.setColor(CC[(int)(Math.log(a)/Math.log(this.gpl.getmulti()))]);
					g.fillRoundRect(x/10+((x-x/5)/gpl.getN())*i, y/10+((y-y/5)/gpl.getN())*j, (int)((4*(x/5)/gpl.getN())*0.95), (int)((4*(y/5)/gpl.getN())*0.95),10,10);
					g.setColor(Color.WHITE);
					g.drawString(String.valueOf(a), x/10+((x-x/5)/gpl.getN())*i+(4*(x/5)/gpl.getN())/2, y/10+((y-y/5)/gpl.getN())*j+(4*(y/5)/gpl.getN())/2);

				}
			}
		}
	}

}
