package videojuegos.libreria;

import java.awt.Component;

import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.Vector;

/**
 * Manejador de pol�ticas personalizadas de recorrido de foco en ventanas.
 * 
 * @author Oracle
 */
public class MiFocusTraversalPolicy extends FocusTraversalPolicy {
	/**
	 * Vector con los componentes ordenados.
	 */
	private Vector<Component> order;

	/**
	 * Crea una nueva pol�tica de recorrido de foco.
	 * 
	 * @param order
	 *            Vector con los componentes ordenados que recibir�n el foco.
	 */
	public MiFocusTraversalPolicy(Vector<Component> order) {
		this.order = new Vector<Component>(order.size());
		this.order.addAll(order);
	}

	/**
	 * Obtiene el siguiente componente del ciclo.
	 * 
	 * @param focusCycleRoot
	 *            Ciclo de foco.
	 * @param aComponent
	 *            El componente a partir del cual se obtendr� el siguiente.
	 * @return El componente siguiente del ciclo.
	 */
	public Component getComponentAfter(Container focusCycleRoot,
			Component aComponent) {
		int idx = (order.indexOf(aComponent) + 1) % order.size();
		return order.get(idx);
	}

	/**
	 * Obtiene el componente anterior del ciclo.
	 * 
	 * @param focusCycleRoot
	 *            Ciclo de foco.
	 * @param aComponent
	 *            El componente a partir del cual se obtendr� el anterior.
	 * @return El componente anterior del ciclo.
	 */
	public Component getComponentBefore(Container focusCycleRoot,
			Component aComponent) {
		int idx = order.indexOf(aComponent) - 1;
		if (idx < 0) {
			idx = order.size() - 1;
		}
		return order.get(idx);
	}

	/**
	 * Obtiene el componente por defecto para recibir el foco.
	 * 
	 * @param focusCycleRoot
	 *            Ciclo de foco.
	 * @return El primer componente a recibir el foco.
	 */
	public Component getDefaultComponent(Container focusCycleRoot) {
		return order.get(0);
	}

	/**
	 * Obtiene el �ltimo componente del ciclo.
	 * 
	 * @param focusCycleRoot
	 *            Ciclo de foco.
	 * @return El �ltimo componente a recibir el foco.
	 */
	public Component getLastComponent(Container focusCycleRoot) {
		return order.lastElement();
	}

	/**
	 * Obtiene el primer componente del ciclo.
	 * 
	 * @param focusCycleRoot
	 *            Ciclo de foco.
	 * @return El primer componente a recibir el foco.
	 */
	public Component getFirstComponent(Container focusCycleRoot) {
		return order.get(0);
	}
}
