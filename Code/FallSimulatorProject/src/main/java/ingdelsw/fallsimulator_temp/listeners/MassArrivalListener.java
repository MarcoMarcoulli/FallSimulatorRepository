package ingdelsw.fallsimulator.listeners;

import ingdelsw.fallsimulator.SimulationManager;

public interface MassArrivalListener {
	void onMassArrival(SimulationManager source, boolean arrived);
}
