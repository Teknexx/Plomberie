/* Code par TekneX */
/* https://github.com/Teknexx */
/* Version Java : 16 ou plus*/
/* Encodage : UTF-8 */
//class Case, liée à la pile

package progPure;

public class Case {
	
	public int i;
	public int j;
	public int orientation;
	public int orientationprecedent;	
	
	Case(int i, int j, int orientation,int orientationprecedent){
		this.i = i;
		this.j = j;
		this.orientation = orientation;
		this.orientationprecedent=orientationprecedent;
	}
	
}
