package scorbot.src;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class ColaCircularConcurrente<T> {
	
	private Object[] array;
	private int inicio, fin, nElementos;
	private Semaphore elementoDisponible, espacioLibre, exMutNElementos;
	
	
	public ColaCircularConcurrente(int tamano) {
		array = new Object[tamano];
		nElementos=0;
		inicio=0;
		fin=0;
		elementoDisponible = new Semaphore(0);
		espacioLibre = new Semaphore(tamano);
		exMutNElementos = new Semaphore(1);
		
	}
	
	/**
	 * Comprueba si la cola esta vacia
	 * @return true si esta vacia, false en caso contrario
	 */
	public boolean estaVacia() {
		return nElementos==0;
	}
	/**
	 * Comprueba si la cola esta llena 
	 * @return true si esta llena, false en caso contrario
	 */
	
	public boolean estaLlena() {
		return nElementos==array.length;
	}
	/**
	 * Añade un objeto al final de la cola en caso de no estar llena
	 * @param Object el objeto a encolar
	 * @return true si se ha insertado correctamente, false en caso contrario
	 */
	public boolean encolar(T objeto) {
		//if(estaLlena())return false;
		
		
		try {
			espacioLibre.acquire();			//WAIT EL
		} catch (InterruptedException e) {
			return false;
		}
		
		array[fin=(fin+1)%array.length]=objeto; //<--encolar
		try {
			exMutNElementos.acquire();
		} catch (InterruptedException e) {
			return false;					//WAIT EXMUT
		}
		nElementos++;
		exMutNElementos.release();			//SIGNAL EXMUT
		elementoDisponible.release();		//SIGNAL ED
		return true;
		

	}
	/**
	 * Consime el primer elemento de la cola en caso de no estar vacia
	 * @return el objeto consumido si existe, o null en caso contrario
	 */
	public T consumir(){
		T objeto=null;
		if(estaVacia()) return objeto;
		try {
			elementoDisponible.acquire();	//WAIT ED
		} catch (InterruptedException e) {
			return objeto;
		}
		
		
		objeto=(T)array[inicio];
		array[inicio=(inicio+1)%array.length]=null;	//<--suprimir
		try {
			exMutNElementos.acquire();
		} catch (InterruptedException e) {
			return null;					//WAIT EXMUT
		}
		nElementos--;
		exMutNElementos.release();			//SIGNAL EXMUT
		espacioLibre.release();				//SIGNAL EL
		return (T)objeto;
		
		
	}
	
	public T ultimo() {
		try {
			elementoDisponible.acquire();	//WAIT ED
		} catch (InterruptedException e) {
			return null;
		}
		elementoDisponible.release();
		return (T) array[fin];
		

	}
	
	
}
