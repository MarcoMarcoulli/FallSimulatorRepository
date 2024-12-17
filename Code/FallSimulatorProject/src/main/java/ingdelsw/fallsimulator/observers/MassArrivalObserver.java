package ingdelsw.fallsimulator.observers;

import ingdelsw.fallsimulator.SimulationManager;

public interface MassArrivalObserver {
	void onMassArrival(SimulationManager source, boolean arrived);
}
