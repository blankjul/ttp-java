package com.msu.tsp.scenarios.impl;

import java.util.Arrays;

import com.msu.thief.model.tour.StandardTour;
import com.msu.thief.model.tour.Tour;
import com.msu.tsp.scenarios.ACoordinateScenario;

public class D198 extends ACoordinateScenario {
	@Override
	public Tour<?> getOptimal() {
		return new StandardTour(Arrays.asList(28, 29, 27, 33, 32, 35, 36, 31, 30, 37, 44, 53, 60, 65, 73, 81, 74, 61, 52, 45, 51, 64, 80, 75, 76, 77, 63, 49, 50, 46, 38, 34,
				47, 48, 62, 78, 79, 90, 91, 104, 113, 112, 111, 105, 106, 110, 109, 108, 107, 166, 167, 181, 182, 180, 176, 175, 172, 173, 174, 177, 179, 183,
				184, 178, 193, 194, 197, 196, 195, 186, 185, 192, 191, 190, 187, 188, 189, 170, 171, 165, 164, 163, 162, 150, 149, 144, 143, 136, 128, 127, 126,
				169, 125, 124, 168, 123, 137, 139, 133, 131, 130, 129, 132, 135, 134, 140, 142, 141, 146, 145, 148, 147, 152, 151, 161, 160, 159, 158, 157, 156,
				155, 154, 153, 138, 122, 119, 118, 117, 116, 115, 120, 121, 114, 103, 102, 101, 100, 92, 93, 94, 95, 89, 88, 96, 97, 87, 82, 69, 66, 59, 54, 43,
				55, 58, 67, 71, 72, 83, 86, 98, 99, 85, 84, 70, 68, 57, 56, 42, 41, 40, 12, 11, 10, 9, 8, 7, 4, 3, 2, 5, 6, 1, 0, 39, 13, 14, 15, 16, 17, 18,
				19, 20, 23, 24, 25, 26, 21, 22 ));
	}

	@Override
	protected double[][] getCoordinates() {
		return new double[][] { { 0, 0 }, { 551.2, 996.4 }, { 627.4, 996.4 }, { 703.6, 996.4 }, { 703.6, 1047.2 }, { 627.4, 1047.2 }, { 551.2, 1047.2 },
			{ 881.4, 1352 }, { 932.2, 1352 }, { 957.6, 1352 }, { 983, 1352 }, { 1008.4, 1352 }, { 1033.8, 1352 }, { 1313.2, 1123.4 }, { 1287.8, 1098 },
			{ 1287.8, 996.4 }, { 1313.2, 996.4 }, { 1465.6, 996.4 }, { 1516.4, 996.4 }, { 1592.6, 996.4 }, { 1592.6, 1098 }, { 1516.4, 1098 },
			{ 1465.6, 1098 }, { 1567.2, 1123.4 }, { 1592.6, 1148.8 }, { 1567.2, 1174.2 }, { 1541.8, 1174.2 }, { 1491, 1174.2 }, { 1440.2, 1174.2 },
			{ 1465.6, 1199.6 }, { 1414.8, 1225 }, { 1440.2, 1225 }, { 1491, 1225 }, { 1516.4, 1225 }, { 1592.6, 1250.4 }, { 1465.6, 1250.4 },
			{ 1440.2, 1250.4 }, { 1389.4, 1250.4 }, { 1541.8, 1275.8 }, { 1160.8, 1123.4 }, { 1160.8, 1225 }, { 1262.4, 1301.2 }, { 1287.8, 1301.2 },
			{ 1338.6, 1301.2 }, { 1414.8, 1301.2 }, { 1491, 1301.2 }, { 1541.8, 1301.2 }, { 1643.4, 1301.2 }, { 1668.8, 1326.6 }, { 1618, 1326.6 },
			{ 1567.2, 1326.6 }, { 1516.4, 1326.6 }, { 1465.6, 1326.6 }, { 1414.8, 1326.6 }, { 1338.6, 1326.6 }, { 1313.2, 1326.6 }, { 1237, 1326.6 },
			{ 1237, 1352 }, { 1313.2, 1352 }, { 1338.6, 1352 }, { 1414.8, 1352 }, { 1465.6, 1352 }, { 1694.2, 1352 }, { 1618, 1377.4 }, { 1516.4, 1377.4 },
			{ 1414.8, 1377.4 }, { 1338.6, 1377.4 }, { 1313.2, 1377.4 }, { 1237, 1377.4 }, { 1357.7, 1390.1 }, { 1237, 1402.8 }, { 1313.2, 1402.8 },
			{ 1338.6, 1402.8 }, { 1414.8, 1402.8 }, { 1465.6, 1402.8 }, { 1567.2, 1402.8 }, { 1592.6, 1402.8 }, { 1618, 1402.8 }, { 1694.2, 1428.2 },
			{ 1668.8, 1428.2 }, { 1541.8, 1428.2 }, { 1440.2, 1428.2 }, { 1414.8, 1428.2 }, { 1338.6, 1428.2 }, { 1262.4, 1428.2 }, { 1237, 1453.6 },
			{ 1338.6, 1453.6 }, { 1414.8, 1453.6 }, { 1465.6, 1453.6 }, { 1491, 1453.6 }, { 1668.8, 1453.6 }, { 1694.2, 1453.6 }, { 1618, 1479 },
			{ 1592.6, 1479 }, { 1567.2, 1479 }, { 1516.4, 1479 }, { 1440.2, 1479 }, { 1414.8, 1479 }, { 1338.6, 1479 }, { 1262.4, 1479 },
			{ 1592.6, 1504.4 }, { 1618, 1504.4 }, { 1668.8, 1529.8 }, { 1694.2, 1529.8 }, { 1745, 1529.8 }, { 1821.2, 1529.8 }, { 1846.6, 1529.8 },
			{ 1948.2, 1529.8 }, { 1922.8, 1555.2 }, { 1897.4, 1555.2 }, { 1872, 1555.2 }, { 1821.2, 1555.2 }, { 1795.8, 1555.2 }, { 1770.4, 1555.2 },
			{ 1770.4, 1656.8 }, { 1795.8, 1656.8 }, { 1821.2, 1656.8 }, { 1872, 1656.8 }, { 1897.4, 1656.8 }, { 1922.8, 1656.8 }, { 1808.5, 1694.9 },
			{ 1757.7, 1694.9 }, { 1884.7, 1733 }, { 1999, 1733 }, { 2075.2, 1733 }, { 2113.3, 1733 }, { 2176.8, 1733 }, { 2236.5, 1733 },
			{ 2176.8, 1783.8 }, { 2126, 1783.8 }, { 2100.6, 1783.8 }, { 2100.6, 1809.2 }, { 2126, 1809.2 }, { 2100.6, 1834.6 }, { 2126, 1834.6 },
			{ 2151.4, 1834.6 }, { 2236.5, 1847.3 }, { 1999, 1847.3 }, { 1884.7, 1847.3 }, { 2100.6, 1860 }, { 2126, 1860 }, { 2100.6, 1885.4 },
			{ 2126, 1885.4 }, { 2176.8, 1885.4 }, { 2151.4, 1910.8 }, { 2126, 1910.8 }, { 2100.6, 1910.8 }, { 2100.6, 1936.2 }, { 2126, 1936.2 },
			{ 2176.8, 1936.2 }, { 2227.6, 1936.2 }, { 2126, 1961.6 }, { 2100.6, 1961.6 }, { 1795.8, 1987 }, { 1821.2, 1987 }, { 1846.6, 1987 },
			{ 1872, 1987 }, { 1897.4, 1987 }, { 1948.2, 1987 }, { 2056.2, 1987 }, { 2100.6, 1987 }, { 2126, 1987 }, { 2253, 1987 }, { 2303.8, 1987 },
			{ 2380, 1987 }, { 2405.4, 1987 }, { 2024.4, 1402.8 }, { 2151.4, 1402.8 }, { 2075.2, 1707.6 }, { 2176.8, 1707.6 }, { 2350.8, 1733 },
			{ 2350.8, 1847.3 }, { 3652.1, 1010.3 }, { 3725.7, 1010.3 }, { 3725.7, 1086.5 }, { 3652.1, 1086.5 }, { 3726.2, 1148.8 }, { 3802.4, 1148.8 },
			{ 3853.2, 1148.8 }, { 3802.4, 1174.2 }, { 3700.8, 1174.2 }, { 3605.6, 1199.6 }, { 3700.8, 1199.6 }, { 3802.4, 1199.6 }, { 3853.2, 1199.6 },
			{ 4028.3, 1310.3 }, { 3952.1, 1310.3 }, { 3728.3, 1310.3 }, { 3652.1, 1310.3 }, { 3652.1, 1386.5 }, { 3728.3, 1386.5 }, { 3952.1, 1386.5 },
			{ 4028.3, 1386.5 }, { 3853.2, 1123.4 }, { 3952.1, 1086.5 }, { 4028.3, 1086.5 }, { 4028.3, 1010.3 }, { 3952.1, 1010.3 } };
	}
	
	

}