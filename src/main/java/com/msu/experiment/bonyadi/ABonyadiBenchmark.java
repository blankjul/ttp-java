package com.msu.experiment.bonyadi;

import java.util.List;

import com.msu.NSGAIIFactory;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;

public abstract class ABonyadiBenchmark extends AExperiment {


	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		algorithms.add(NSGAIIFactory.createNSGAIIBuilder("NSGAII-[OPT-RANDOM]-[OX-HUX]-[SWAP-BF]").create());
		//algorithms.add(new OnePlusOneEA());
		//algorithms.add(new RandomLocalSearch());
	}


}
