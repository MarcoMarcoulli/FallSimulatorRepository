package ingdelsw.FallSimulator.ListenerInterfaces;

import ingdelsw.FallSimulator.SimulationManager;

public interface MassArrivalListener {
	void onMassArrival(SimulationManager source, boolean arrived);
}
