/*
 * observer interface for mass arrival and simulation stopping
 */
package ingdelsw.fallsimulator.observers;

import ingdelsw.fallsimulator.SimulationManager;

public interface MassArrivalObserver {
	//method fo handling mass arrival to endpoint (arrived = true) or mass stopping along the path (arrived = false)
	void onMassArrival(SimulationManager source, boolean arrived);
}
