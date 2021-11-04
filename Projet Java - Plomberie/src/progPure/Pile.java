/* Code par TekneX */
/* https://github.com/Teknexx */
/* Version Java : 16 ou plus*/
/* Encodage : UTF-8 */
//class Pile, où s'emplient les cases

package progPure;

import java.util.Vector;

public class Pile <T>{
	Vector <T> table;
	
	Pile (){ //constructeur
		table = new Vector<T>();
	}
	
	public void Empile (T x){ //on emplile un vecteur de nombres
		table.add(table.size(), x);
	}
	
	public T SommetPile(){ //on selectionne le dernier élément sauvegardé
		return table.elementAt(table.size()-1);
	}
	
	public void Depile(){ //on depile, le dernier élément sauvegardé est suprimé, et donc l'avant dernier element devient le dernier)
		table.remove(table.size()-1);
	}
	
	public int Taille() { //pour retourner la taille du tableau
		return table.size();
	}

}
