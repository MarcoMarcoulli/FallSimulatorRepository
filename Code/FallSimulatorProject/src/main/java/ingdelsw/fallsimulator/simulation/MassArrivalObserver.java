/*
 * observer interface for mass arrival and simulation stopping
 */
package ingdelsw.fallsimulator.simulation;

public interface MassArrivalObserver {
	//method for handling mass arrival to endpoint (arrived = true) or mass stopping along the path (arrived = false)
	void onMassArrival(SimulationManager source, boolean arrived);
}
