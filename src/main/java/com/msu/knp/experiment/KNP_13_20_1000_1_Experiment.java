package com.msu.knp.experiment;

public class KNP_13_20_1000_1_Experiment extends AbstractKNPExperiment {

	@Override
	protected Integer[][] getItems() {
		return new Integer[][] { { 234, 114 }, { 39, 19 }, { 1053, 873 }, { 351, 291 }, { 585, 485 }, { 78, 38 }, { 117, 97 }, { 234, 194 }, { 312, 152 },
				{ 156, 76 }, { 156, 76 }, { 273, 133 }, { 351, 291 }, { 351, 291 }, { 312, 152 }, { 468, 388 }, { 156, 76 }, { 585, 485 }, { 468, 388 },
				{ 1053, 873 } };
	}

	@Override
	protected Integer[] getOptimum() {
		return new Integer[] {1, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0};
	}

	@Override
	protected Integer getMaxWeight() {
		return 873;
	}

}


