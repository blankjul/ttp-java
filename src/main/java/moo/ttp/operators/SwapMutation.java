package moo.ttp.operators;

import moo.ttp.util.Rnd;
import moo.ttp.util.Util;

public class SwapMutation {


	public static <T> void mutate(T[] obj, int a, int b) {
		Util.swap(obj, a, b);
	}
	
	public static <T> void mutate(T[] obj) {
		// search for two random points -> do not swap the first city (always 0)
		int a = Rnd.rndInt(1, obj.length-1);
		int b = Rnd.rndInt(1, obj.length-1);
		Util.swap(obj, a, b);
	}

}
