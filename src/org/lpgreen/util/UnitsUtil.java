package org.lpgreen.util;

import java.util.*;

/**
 * UnitsUtil contains Units related utility methods, such as the Units conversion
 * 
 * Creation date: Mar. 1, 2012
 * Last modify date: Mar. 28, 2012
 * 
 * @author  Jiaxun Stephen Yu
 * @version 1.0
 */

public class UnitsUtil {

	// The conversion factors between two units. These values will be
	// retrieved from a database eventually.
	//
	// Source http://en.wikipedia.org/wiki/Conversion_of_units#Length
	// All units use the international definitions as the first choice
    // followed by the US customary definitions.
	//
	// Note: 1 mile = 5280 ft = 1760 yd = 63360 in
	//       1 acre = 4840 sq yd = 4046.8564224 sq m
    //       1 cup (US customary) = 8 fl oz ≡ 1/16 gal
	//
	private static final double LengthConvFactors[][] = {
	             /*in*/        /*ft*/         /*yd*/           /*mi*/          /*mm*/        /*cm*/        /*m */      /*km*/
	/*in*/{           1d,         1/12d,         1/36d,    1/(6.336e+4d),     2.54e+1d,        2.54d,     2.54e-2d,  2.54e-5d },
	/*ft*/{          12d,            1d,          1/3d,     1/(5.28e+3d),    3.048e+2d,    3.048e+1d,    3.048e-1d, 3.048e-4d },
	/*yd*/{          36d,            3d,            1d,     1/(1.76e+3d),    9.144e+2d,    9.144e+1d,    9.144e-1d, 9.144e-4d },
	/*mi*/{    6.336e+4d,      5.28e+3d,      1.76e+3d,               1d, 1.609344e+6d, 1.609344e+5d, 1.609344e+3d, 1.609344d },
	/*mm*/{ 1/(2.54e+1d), 1/(3.048e+2d), 1/(9.144e+2d), 1/(1.609344e+6d),           1d,        1e-1d,        1e-3d,     1e-6d },
	/*cm*/{      1/2.54d, 1/(3.048e+1d), 1/(9.144e+1d), 1/(1.609344e+5d),          10d,           1d,        1e-2d,     1e-5d },
	/*m */{ 1/(2.54e-2d), 1/(3.048e-1d), 1/(9.144e-1d), 1/(1.609344e+3d),        1e+3d,        1e+2d,           1d,     1e-3d },
	/*km*/{ 1/(2.54e-5d), 1/(3.048e-4d), 1/(9.144e-4d),    1/(1.609344d),        1e+6d,        1e+5d,        1e+3d,        1d },
	};

	private static final double AreaConvFactors[][] = {
	              /*in^2*/          /*ft^2*/           /*yd^2*/            /*mi^2*/            /*mm^2*/         /*cm^2*/          /*m^2 */          /*km^2*/             /*acre*/
	/*in^2*/{              1d,     1/(1.44e+2d),     1/(1.296e+3d),   1/(4.0144896e+9d),       6.4516e+2d,          6.4516d,       6.4516e-4d,      6.4516e-10d,      1/(6.27264e+6d) },
	/*ft^2*/{        1.44e+2d,               1d,              1/9d,     1/(2.78784e+7d),     9.290304e+4d,     9.290304e+2d,     9.290304e-2d,     9.290304e-8d,        1/(4.356e+4d) },
	/*yd^2*/{       1.296e+3d,               9d,                1d,      1/(3.0976e+6d),    8.3612736e+5d,    8.3612736e+3d,    8.3612736e-1d,    8.3612736e-7d,         1/(4.84e+3d) },
	/*mi^2*/{   4.0144896e+9d,      2.78784e+7d,        3.0976e+6d,                  1d,  2.58998811e+12d,  2.58998811e+10d,   2.58998811e+6d,      2.58998811d,    (3.0976e+3d)/4.84 },
	/*mm^2*/{  1/(6.4516e+2d), 1/(9.290304e+4d), 1/(8.3612736e+5d), 1/(2.58998811e+12d),               1d,            1e-2d,            1e-6d,           1e-12d, 1/(4.0468564224e+9d) },
	/*cm^2*/{       1/6.4516d, 1/(9.290304e+2d), 1/(8.3612736e+3d), 1/(2.58998811e+10d),            1e+2d,               1d,            1e-4d,           1e-10d, 1/(4.0468564224e+7d) },
	/*m^2 */{  1/(6.4516e-4d), 1/(9.290304e-2d), 1/(8.3612736e-1d),  1/(2.58998811e+6d),            1e+6d,            1e+4d,               1d,            1e-6d, 1/(4.0468564224e+3d) },
	/*km^2*/{ 1/(6.4516e-10d), 1/(9.290304e-8d), 1/(8.3612736e-7d),       1/2.58998811d,           1e+12d,           1e+10d,            1e+6d,               1d, 1/(4.0468564224e-3d) },
	/*acre*/{     6.27264e+6d,        4.356e+4d,          4.84e+3d,   4.84/(3.0976e+3d), 4.0468564224e+9d, 4.0468564224e+7d, 4.0468564224e+3d, 4.0468564224e-3d,                   1d },
	};

	private static final double VolumeConvFactors[][] = {
	               /*in^3*/         /*ft^3*/           /*yd^3*/             /*cm^3*/           /*m^3*/            /*tbsp*/             /*tsp*/              /*oz_fl*/          /*pt_fl*/            /*qt_fl*/         /*gal_fl*/            /*cup*/              /*ml*/              /*l */              /*kl*/          /*ton_fr*/
	/* in^3 */{             1d,   5.787037037e-4d,    2.14334705e-5d,     1.6387064e+1d,     1.6387064e-5d,          1.1082251d,  3.32467532467532d,   5.54112554113e-1d,   3.4632034632e-2d,    1.7316017316e-2d,   4.329004329e-3d,   6.9264069264e-2d,      1.6387064e+1d,      1.6387064e-2d,      1.6387064e-5d,   1/(6.912e+4d) },
	/* ft^3 */{      1.728e+3d,                1d,     3.7037037e-2d,  2.8316846592e+4d,  2.8316846592e-2d,    1.9150129875e+3d,     5.74503896e+3d,     9.575064935e+2d,   5.9844155844e+1d,    2.9922077922e+1d,       7.48051948d, 1.196883116883e+2d,       2.831685e+4d,       2.831685e+1d,       2.831685e-2d,         2.5e-2d },
	/* yd^3 */{     4.6656e+4d,               27d,                1d, 7.64554857984e+5d, 7.64554857984e-1d,   5.17053506631e+4d,    1.551160519e+5d,    2.5852675325e+4d,      1.6157922e+3d,  8.078961038961e+2d, 2.01974025974e+2d, 3.231584415584e+3d,        7.64559e+5d,        7.64559e+2d,        7.64559e-1d,        6.75e-1d },
	/* cm^3 */{  6.1023744e-2d,   3.531466667e-5d,   1.307950619e-6d,                1d,             1e-6d, 6.7628045397969e-2d, 2.028841361596e-1d, 3.3814022701843e-2d, 2.113376418865e-3d, 1.0566882094326e-3d, 2.64172052358e-4d,  4.22675283773e-3d,                 1d,              1e-3d,              1e-6d,  8.82866668e-7d },
	/* m^3  */{  6.1023744e+4d,   3.531466667e+1d,      1.307950619d,             1e+6d,                1d, 6.7628045397969e+4d, 2.028841361596e+5d, 3.3814022701843e+4d, 2.113376418865e+3d, 1.0566882094326e+3d, 2.64172052358e+2d,  4.22675283773e+3d,              1e+6d,              1e+3d,                 1d,  8.82866668e-1d },
	/* tbsp */{  9.0234375e-1d,     5.2218967e-4d,     1.9340358e-5d, 1.47867647825e+1d, 1.47867647825e-5d,                  1d,                 3d,                1/2d,              1/32d,               1/64d,       3.90625e-3d,              1/16d, 1.478676478125e+1d, 1.478676478125e-2d, 1.478676478125e-5d, 1.305474175e-5d },
	/* tsp  */{  3.0078125e-1d,   1.740632234e-4d,  6.4467860527e-6d,      4.928921595d,   4.928921595e-6d,                1/3d,                 1d,                1/6d,              1/96d,              1/192d,      1/(7.68e+2d),              1/48d,     4.92892159375d,  4.92892159375e-3d,  4.92892159375e-6d, 4.351580585e-6d },
	/* oz_fl*/{     1.8046875d,    1.04437934e-3d,    3.86807163e-5d, 2.95735295625e+1d, 2.95735295625e-5d,                  2d,                 6d,                  1d,              1/16d,               1/32d,        7.8125e-3d,               1/8d,  2.95735295625e+1d,  2.95735295625e-2d,  2.95735295625e-5d,  2.61094835e-5d },
	/* pt_fl*/{     2.8875e+1d, 1.67100694444e-2d,   6.188914609e-4d,    4.73176473e+2d,    4.73176473e-4d,                 32d,                96d,                 16d,                 1d,                1/2d,              1/8d,                 2d,     4.73176473e+2d,    4.73176473e-1d,      4.73176473e-4d, 4.177517361e-4d },
	/* qt_fl*/{      5.775e+1d, 3.34201388889e-2d, 1.23778292181e-3d,    9.46352946e+2d,    9.46352946e-4d,                 64d,               192d,                 32d,                 2d,                  1d,              1/4d,                 4d,     9.46352946e+2d,    9.46352946e-1d,      9.46352946e-4d, 8.355034722e-4d },
	/*gal_fl*/{       2.31e+2d, 1.33680555556e-1d, 4.95113168724e-3d,   3.785411784e+3d,   3.785411784e-3d,            2.56e+2d,           7.68e+2d,            1.28e+2d,                 8d,                  4d,                1d,                16d,    3.785411784e+3d,      3.785411784d,     3.785411784e-3d, 3.342013889e-3d },
	/* cup  */{    1.44375e+1d, 8.35503472222e-3d,  3.0944573045e-4d,   2.365882365e+2d,   2.365882365e-4d,                 16d,                48d,                  8d,               1/2d,                1/4d,             1/16d,                 1d,    2.365882365e+2d,   2.365882365e-1d,     2.365882365e-4d, 2.088758681e-4d },
	/* ml   */{ 6.10237441e-2d,    3.53146667e-5d,  1.3079435334e-6d,                1d,             1e-6d,      6.76280454e-2d,  2.02884136211e-1d,       3.3814017e-2d,      2.1133764e-3d,       1.0566882e-3d, 2.64172052358e-4d,  4.22675283773e-3d,                 1d,             1e-3d,               1e-6d, 8.828665618e-7d },
	/* l    */{ 6.10237441e+1d,    3.53146667e-2d,  1.3079435334e-3d,             1e+3d,             1e-3d,      6.76280454e+1d,  2.02884136211e+2d,       3.3814017e+1d,         2.1133764d,          1.0566882d, 2.64172052358e-1d,     4.22675283773d,              1e+3d,                1d,               1e-3d, 8.828665618e-4d },
	/* kl   */{ 6.10237441e+4d,    3.53146667e+1d,     1.3079435334d,             1e+6d,                1d,      6.76280454e+4d,  2.02884136211e+5d,       3.3814017e+4d,      2.1133764e+3d,       1.0566882e+3d, 2.64172052358e+2d,  4.22675283773e+3d,              1e+6d,             1e+3d,                  1d, 8.828665618e-1d },
	/*ton_fr*/{      6.912e+4d,               40d,            40/27d, 1.13267386368e+6d,    1.13267386368d,      7.66005195e+4d,    2.298015584e+5d,     3.830025974e+4d,  2.39376623376e+3d,   1.19688311688e+3d,   2.992207792e+2d, 4.787532467532e+3d,       1.132674e+6d,      1.132674e+3d,           1.132674d,              1d },
	};

	private static final double MassConvFactors[][] = {
	               /*oz*/            /*lb*/           /*t_s*/           /*t_l*/            /*mg*/            /*g */            /*kg*/            /*t */
	/*oz*/ {                1d,            1/16d,      3.125e-5d,      1/(3.584e+4d), 2.8349523125e+4d, 2.8349523125e+1d, 2.8349523125e-2d, 2.8349523125e-5d },
	/*lb*/ {               16d,               1d,          5e-4d,       1/(2.24e+3d),    4.5359237e+5d,    4.5359237e+2d,    4.5359237e-1d,    4.5359237e-4d },
	/*t_s*/{           3.2e+4d,            2e+3d,             1d,             25/28d,    9.0718474e+8d,    9.0718474e+5d,    9.0718474e+2d,    9.0718474e-1d },
	/*t_l*/{         3.584e+4d,         2.24e+3d,          1.12d,                 1d, 1.0160469088e+9d, 1.0160469088e+6d, 1.0160469088e+3d,    1.0160469088d },
	/*mg*/ { 3.52739619488e-5d, 2.2046226218e-6d, 1.10231131e-9d, 9.84206527611e-10d,               1d,            1e-3d,            1e-6d,            1e-9d },
	/*g */ { 3.52739619488e-2d, 2.2046226218e-3d, 1.10231131e-6d,  9.84206527611e-7d,            1e+3d,               1d,            1e-3d,            1e-6d },
	/*kg*/ { 3.52739619488e+1d,    2.2046226218d, 1.10231131e-3d,  9.84206527611e-4d,            1e+6d,            1e+3d,               1d,            1e-3d },
	/*t */ { 3.52739619488e+4d, 2.2046226218e+3d,    1.10231131d,  9.84206527611e-1d,            1e+9d,            1e+6d,            1e+3d,               1d },
	};

	private static final double TimeConvFactors[][] = {
	          /*ms*/    /*s */    /*min*/      /*h */       /*d */
	/*ms*/ {       1d,    1e-3d, 1/(6e+4d), 1/(3.6e+6d), 1/(8.64e+7d) },
	/*s */ {    1e+3d,       1d,     1/60d, 1/(3.6e+3d), 1/(8.64e+4d) },
	/*min*/{    6e+4d,      60d,        1d,       1/60d, 1/(1.44e+3d) },
	/*h */ {  3.6e+6d,  3.6e+3d,       60d,          1d,        1/24d },
	/*d */ { 8.64e+7d, 8.64e+4d,  1.44e+3d,         24d,           1d },
	};

	private static final double CurrencyConvFactors[][] = {
	          /*CNY*/  /*USD*/  /*GBP*/  /*EUR*/   /*JPY*/
	/*CNY*/{       1d, 0.1583d, 0.0997d, 0.1196d,  13.2042d },
	/*USD*/{  6.3207d,      1d, 0.6301d, 0.7556d,  83.4500d },
	/*GBP*/{ 10.0286d, 1.5871d,      1d, 1.2003d, 132.3960d },
	/*EUR*/{  8.3654d, 1.3235d, 0.8339d,      1d, 110.4060d },
	/*JPY*/{  0.0757d, 0.0120d, 0.0076d, 0.0091d,        1d },
	};

	// Name of the length units to which conversion can be applied.
	// These names will be retrieved from a database eventually.
	//
	public static final String[] lengthUnits = {
		"in", "ft", "yd", "mi", "mm", "cm", "m", "km"
	};

	// Name of the area units to which conversion can be applied.
	// These names will be retrieved from a database eventually.
	//
	public static final String[] areaUnits = {
		"sq in", "sq ft", "sq yd", "sq mi", "sq mm", "sq cm", "sq m", "sq km", "acre"
	};

	// Name of the volume units to which conversion can be applied.
	// These names will be retrieved from a database eventually.
	//
	public static final String[] volumeUnits = {
		"cu in", "cu ft", "cu yd", "cu cm", "cu m", "tbsp", "tsp", "fl oz", "pt", "qt", "gal", "cup", "ml", "l", "kl", "fr ton"
	};

	// Name of the mass units to which conversion can be applied.
	// These names will be retrieved from a database eventually.
	//
	public static final String[] massUnits = {
		"oz", "lb", "sh tn", "ton", "mg", "g", "kg", "t"
	};

	// Name of the time interval units to which conversion can be applied.
	// These names will be retrieved from a database eventually.
	//
	public static final String[] timeUnits = {
		"ms", "s", "min", "h", "d"
	};

	// Name of the currency interval units to which conversion can be applied.
	// These names will be retrieved from a database eventually.
	//
	public static final String[] currencyUnits = {
		"CNY", "USD", "GBP", "EUR", "JPY"
	};

	// The epsilon that the input quantity is considered to be 0
	//
	public static final double unitEpsilon = 1e-6;

	// The length units conversion map
	//
	private Map<String, Map<String, Double>> lengthConv;

	// The area units conversion map
	//
	private Map<String, Map<String, Double>> areaConv;

	// The volume units conversion map
	//
	private Map<String, Map<String, Double>> volumeConv;

	// The mass units conversion map
	//
	private Map<String, Map<String, Double>> massConv;

	// The time units conversion map
	//
	private Map<String, Map<String, Double>> timeConv;

	// The currency units conversion map
	//
	private Map<String, Map<String, Double>> currencyConv;

	// Constructor
	//
	public UnitsUtil() {

		// Create the length conversion map
		lengthConv = createConvMap(lengthUnits, LengthConvFactors);

		// Create the area conversion map
		areaConv = createConvMap(areaUnits, AreaConvFactors);

		// Create the volume conversion map
		volumeConv = createConvMap(volumeUnits, VolumeConvFactors);

		// Create the mass conversion map
		massConv = createConvMap(massUnits, MassConvFactors);

		// Create the time interval conversion map
		timeConv = createConvMap(timeUnits, TimeConvFactors);

		// Create the currency interval conversion map
		currencyConv = createConvMap(currencyUnits, CurrencyConvFactors);
	}
	
	// Caller sets the CurrencyConv
	public void SetCurrencyConv(Map<String, Map<String, Double>> currencyConv) {
		this.currencyConv = currencyConv;
	}

	// Create a conversion map
	//
	private Map<String, Map<String, Double>> createConvMap(String[] units, double[][] convFactors) { 
		Map<String, Map<String, Double>> convMap = new HashMap<String, Map<String, Double>>();
		for (String unit1 : units) {
			int i1 = getUnitIndexInUnits(unit1, units);
			if (i1 == -1)
				continue;
			Map<String, Double> conv = new HashMap<String, Double>();
			for (String unit2 : units) {
				int i2 = getUnitIndexInUnits(unit2, units);
				if (i2 == -1)
					continue;
				conv.put(unit2, convFactors[i1][i2]);
			}
			convMap.put(unit1, conv);
		}
		return convMap;
	}

	// Helper to retrieve the index of a unit in the array of units name
	//
	private static int getUnitIndexInUnits(String unit, String[] units) {
		for (int i = 0; i < units.length; i++)
			if (unit.equals(units[i]))
				return i;
		return -1;
	}

	// Helper to output a separate line on the console for debugging purpose
	//
	private static void outputLineSep() {
		System.out.format("============================================================\n");
	}

	// Helper to show the units conversion factors
	//
	private static void showConvFactors(Map<String, Map<String, Double>> unitConv) {
		Iterator<Map.Entry<String, Map<String, Double>>> iter1 = unitConv.entrySet().iterator();
		while (iter1.hasNext()) {
			Map.Entry<String, Map<String, Double>> entry1 = iter1.next();
			String unit1 = entry1.getKey();
			Iterator<Map.Entry<String, Double>> iter2 = entry1.getValue().entrySet().iterator();
			while (iter2.hasNext()) {
				Map.Entry<String, Double> entry2 = iter2.next();
				String unit2 = entry2.getKey();
				Double factor = entry2.getValue();
				System.out.format("%s => %s: %f\n", unit1, unit2, factor);
			}
		}
	}

	// Show the length units conversion factors
	//
	public void showLengthConvFactors() {
		showConvFactors(lengthConv);
	}

	// Show the area units conversion factors
	//
	public void showAreaConvFactors() {
		showConvFactors(areaConv);
	}

	// Show the volume units conversion factors
	//
	public void showVolumeConvFactors() {
		showConvFactors(volumeConv);
	}

	// Show the mass units conversion factors
	//
	public void showMassConvFactors() {
		showConvFactors(massConv);
	}

	// Show the time interval units conversion factors
	//
	public void showTimeConvFactors() {
		showConvFactors(timeConv);
	}

	// Show the currency interval units conversion factors
	//
	public void showCurrencyConvFactors() {
		showConvFactors(currencyConv);
	}

	// Helper to validate the conversion units - debugging
	//
	private static void validateConvUnits(String[] units, Map<String, Map<String, Double>> unitConv) {
		double c1 = 0d;
		double c2 = 0d;
		for (String unit1 : units) {
			for (String unit2 : units) {
				try {
					c1 = convertUnits(unit1, unit2, 1, unitConv);
					c2 = convertUnits(unit2, unit1, 1, unitConv);
				}
				catch (UnitsException e) {
					e.printStackTrace(System.out);
				}
				System.out.format("%s <=> %s: (%g, %g); round trip %.10f\n", unit1, unit2, c1, c2, c1*c2);
			}
		}
	}

	// Helper to validate the conversion maps - debugging
	//
	// The validation equation is a/b * b/a ~= 1.0
	//
	private void validateConvMaps() {

		// Validate the length conversion factors
		System.out.format("Validate the length conversion factors\n");
		outputLineSep();
		validateConvUnits(lengthUnits, lengthConv);
		outputLineSep();

		// Validate the area conversion factors
		System.out.format("Validate the area conversion factors\n");
		outputLineSep();
		validateConvUnits(areaUnits, areaConv);
		outputLineSep();

		// Validate the volume conversion factors
		System.out.format("Validate the volume conversion factors\n");
		outputLineSep();
		validateConvUnits(volumeUnits, volumeConv);
		outputLineSep();

		// Validate the mass conversion factors
		System.out.format("Validate the mass conversion factors\n");
		outputLineSep();
		validateConvUnits(massUnits, massConv);
		outputLineSep();

		// Validate the time interval conversion factors
		System.out.format("Validate the time conversion factors\n");
		outputLineSep();
		validateConvUnits(timeUnits, timeConv);
		outputLineSep();

		// Validate the currency interval conversion factors
		System.out.format("Validate the currency conversion factors\n");
		outputLineSep();
		validateConvUnits(currencyUnits, currencyConv);
		outputLineSep();
	}

	// Helper to convert units
	//
	private static double convertUnits(String srcUnit, String dstUnit, double quantity, Map<String, Map<String, Double>> unitConv)
		throws UnitsException {
		Map<String, Double> dstMap = unitConv.get(srcUnit);
		if (dstMap == null) {
			throw new UnitsException("unit " + srcUnit + " not found");
		}
		Double factor = dstMap.get(dstUnit);
		if (factor == null) {
			throw new UnitsException("unit " + dstUnit + " not found");
		}
		return quantity * factor;
	}

	// The API for converting length units
	//
	public double convertLengthUnits(String srcUnit, String dstUnit, double quantity) throws UnitsException {
		return convertUnits(srcUnit, dstUnit, quantity, lengthConv);
	}

	// The API for converting area units
	//
	public double convertAreaUnits(String srcUnit, String dstUnit, double quantity) throws UnitsException {
		return convertUnits(srcUnit, dstUnit, quantity, areaConv);
	}

	// The API for converting volume units
	//
	public double convertVolumeUnits(String srcUnit, String dstUnit, double quantity) throws UnitsException {
		return convertUnits(srcUnit, dstUnit, quantity, volumeConv);
	}

	// The API for converting mass units
	//
	public double convertMassUnits(String srcUnit, String dstUnit, double quantity) throws UnitsException {
		return convertUnits(srcUnit, dstUnit, quantity, massConv);
	}

	// The API for converting time interval units
	//
	public double convertTimeUnits(String srcUnit, String dstUnit, double quantity) throws UnitsException {
		return convertUnits(srcUnit, dstUnit, quantity, timeConv);
	}

	// The API for converting currency interval units
	//
	public double convertCurrencyUnits(String srcUnit, String dstUnit, double quantity) throws UnitsException {
		return convertUnits(srcUnit, dstUnit, quantity, currencyConv);
	}

	// The API for converting a unit
	//
	public double convertToNewUnit(String srcUnit, String dstUnit, double quantity) throws UnitsException {
		try {
			// No measuring unit
			if (srcUnit == null || srcUnit.isEmpty() || dstUnit == null || dstUnit.isEmpty()) {
				return quantity;
			}
			// Check the price unit against the length units
			else if (getUnitIndexInUnits(srcUnit, lengthUnits) != -1 &&
					getUnitIndexInUnits(dstUnit, lengthUnits) != -1) {
				return convertLengthUnits(srcUnit, dstUnit, quantity);
			}
			// Check the price unit against the area units
			else if (getUnitIndexInUnits(srcUnit, areaUnits) != -1 &&
					getUnitIndexInUnits(dstUnit, areaUnits) != -1) {
				return convertAreaUnits(srcUnit, dstUnit, quantity);
			}
			// Check the price unit against the volume units
			else if (getUnitIndexInUnits(srcUnit, volumeUnits) != -1 &&
					getUnitIndexInUnits(dstUnit, volumeUnits) != -1) {
				return convertVolumeUnits(srcUnit, dstUnit, quantity);
			}
			// Check the price unit against the mass units
			else if (getUnitIndexInUnits(srcUnit, massUnits) != -1 &&
					getUnitIndexInUnits(dstUnit, massUnits) != -1) {
				return convertMassUnits(srcUnit, dstUnit, quantity);
			}
			// Check the price unit against the time interval units
			else if (getUnitIndexInUnits(srcUnit, timeUnits) != -1 &&
					getUnitIndexInUnits(dstUnit, timeUnits) != -1) {
				return convertTimeUnits(srcUnit, dstUnit, quantity);
			}
			// Unrecognized unit
			else {
				throw new UnitsException("Unrecognized unit pair " + srcUnit + " and " + dstUnit);
			}
		}
		catch (UnitsException e) {
			throw e;
		}
	}

	// Helper to determine the priceList items are in the same unit group.
	//
	private boolean isListOfUnitPricesInSameUnitGroup(List<? extends UnitPrice> priceList, String[] units) {
		for (int i = 0; i < priceList.size(); i++) {
			UnitPrice price;
			price = priceList.get(i);
			if (units == null) {
				if (price.getUnit() != null && !price.getUnit().isEmpty()) {
					return false;
				}
			}
			else if (price.getUnit() == null || price.getUnit().isEmpty() ||
					getUnitIndexInUnits(price.getUnit(), units) == -1) {
				return false;
			}
		}
		return true;
	}

	// Helper to determine the smallest unit among the package prices.
	// Based on the smallest unit, it calculates the package prices
	// quantity and unit price in the smallest unit and the base currency.
	//
	private void calculateQuantityPriceInUnitMin(List<? extends UnitPrice> priceList, String stdUnit,
		Map<String, Map<String, Double>> unitConv, String baseCurrency) throws UnitsException
	{
		try {
			double c_unit = Double.MAX_VALUE;
			String unitMin = stdUnit;
			UnitPrice price;
			int i = 0;

			// Iterate the list of product to determine the smallest unit
			for (i = 0; i < priceList.size(); i++) {
				price = priceList.get(i);

				// Price must not be less than the -unitEpsilon
				if (price.getPrice() < -unitEpsilon)
					throw new UnitsException("Price of a UnitPrice must not be negative");
				// Quantity must not be less than the -unitEpsilon
				if (price.getQuantity() < -unitEpsilon)
					throw new UnitsException("Quantity of a UnitPrice must not be negative");

				// Determine the conversion value to the standard measuring unit
				if (stdUnit != null) {
					double f_unit = convertUnits(price.getUnit(), stdUnit, 1, unitConv);
					if (f_unit < c_unit) {
						c_unit = f_unit;
						unitMin = price.getUnit();
					}
				}
			}

			// Determine each product quantity and price in the smallest
			// measuring unit and the base currency unit
			for (i = 0; i < priceList.size(); i++) {
				price = priceList.get(i);
				price.setNewUnit(stdUnit == null ? null : unitMin);
				price.setNewCurrency(baseCurrency);

				// The quantity must not be zero
				if (Math.abs(price.getQuantity()) <= unitEpsilon) {
					// When quantity is zero, if the price is also zero, this
					// is the case of 0/0. Make the price in the smallest unit
					// also zero. Otherwise, the case becomes n/0 where n>0.
					// Make the price in the smallest unit MAX_DOUBLE.
					price.setNewQuantity(price.getQuantity());
					if (Math.abs(price.getPrice()) <= unitEpsilon) {
						price.setNewPrice(0d);
					}
					else {
						price.setNewPrice(Double.MAX_VALUE);
					}
				}
				else {
					// Convert quantity to the smallest unit and compute price
					// per smallest unit in the new smallest currency unit.
					if (stdUnit == null || stdUnit.isEmpty()) {
						price.setNewQuantity(price.getQuantity());
					}
					else {
						price.setNewQuantity(convertUnits(price.getUnit(), unitMin, price.getQuantity(), unitConv));
					}
					price.setNewPrice(convertUnits(price.getCurrency(), baseCurrency, price.getPrice(), currencyConv));
					price.setNewUnitPrice(price.getNewPrice() / price.getNewQuantity());
				}
			}
		}
		catch (UnitsException e) {
			throw e;
		}
	}

	// The API for sorting the input package price list in unit price order in
	// the input base currency
	//
	public void sortListOfUnitPrices(List<? extends UnitPrice> priceList, String baseCurrency) throws Exception {
		// Make sure the input list is not empty
		if (priceList.size() == 0) {
			throw new Exception("Input price list is empty!");
		}

		// Make sure the base currency is valid
		if (getUnitIndexInUnits(baseCurrency, currencyUnits) == -1) {
			throw new Exception("Input " + baseCurrency + " is not valid!");
		}

		UnitPrice price = priceList.get(0);

		try {
			// No measuring unit
			if (price.getUnit() == null || price.getUnit().isEmpty()) {
				if (isListOfUnitPricesInSameUnitGroup(priceList, null) == false)
					throw new Exception("Price list items cannot have measuring unit");
				calculateQuantityPriceInUnitMin(priceList, null, null, baseCurrency);
			}
			// Check the price unit against the length units
			else if (getUnitIndexInUnits(price.getUnit(), lengthUnits) != -1) {
				if (isListOfUnitPricesInSameUnitGroup(priceList, lengthUnits) == false)
					throw new Exception("Price list items are not in the same unit group");
				calculateQuantityPriceInUnitMin(priceList, "m", lengthConv, baseCurrency);
			}
			// Check the price unit against the area units
			else if (getUnitIndexInUnits(price.getUnit(), areaUnits) != -1) {
				if (isListOfUnitPricesInSameUnitGroup(priceList, areaUnits) == false)
					throw new Exception("Price list items are not in the same unit group");
				calculateQuantityPriceInUnitMin(priceList, "sq m", areaConv, baseCurrency);
			}
			// Check the price unit against the volume units
			else if (getUnitIndexInUnits(price.getUnit(), volumeUnits) != -1) {
				if (isListOfUnitPricesInSameUnitGroup(priceList, volumeUnits) == false)
					throw new Exception("Price list items are not in the same unit group");
				calculateQuantityPriceInUnitMin(priceList, "cu m", volumeConv, baseCurrency);
			}
			// Check the price unit against the mass units
			else if (getUnitIndexInUnits(price.getUnit(), massUnits) != -1) {
				if (isListOfUnitPricesInSameUnitGroup(priceList, massUnits) == false)
					throw new Exception("Price list items are not in the same unit group");
				calculateQuantityPriceInUnitMin(priceList, "g", massConv, baseCurrency);
			}
			// Check the price unit against the time interval units
			else if (getUnitIndexInUnits(price.getUnit(), timeUnits) != -1) {
				if (isListOfUnitPricesInSameUnitGroup(priceList, timeUnits) == false)
					throw new Exception("Price list items are not in the same unit group");
				calculateQuantityPriceInUnitMin(priceList, "s", timeConv, baseCurrency);
			}
			// Unrecognized unit
			else {
				throw new UnitsException("Unrecognized unit " + price.getUnit() + "!");
			}
			Collections.sort(priceList);
		}
		catch (UnitsException e) {
			throw e;
		}
	}

	// Helper to determine the quantityList items are in the same unit group.
	//
	private boolean isListOfUnitQuantitiesInSameUnitGroup(List<? extends UnitQuantity> quantityList, String[] units) {
		for (int i = 0; i < quantityList.size(); i++) {
			UnitQuantity quantity;
			quantity = quantityList.get(i);
			if (units == null) {
				if (quantity.getUnit() != null && !quantity.getUnit().isEmpty()) {
					return false;
				}
			}
			else if (quantity.getUnit() == null || quantity.getUnit().isEmpty() ||
					getUnitIndexInUnits(quantity.getUnit(), units) == -1) {
				return false;
			}
		}
		return true;
	}

	// Helper to determine the smallest unit among the package quantities.
	// Based on the smallest unit, it calculates the new quantity based on
	// the smallest unit.
	//
	private void calculateQuantityInUnitMin(List<? extends UnitQuantity> quantityList, String stdUnit,
		Map<String, Map<String, Double>> unitConv) throws UnitsException
	{
		try {
			double c_unit = Double.MAX_VALUE;
			String unitMin = stdUnit;
			UnitQuantity quantity;
			int i = 0;

			// Iterate the list of product to determine the smallest unit
			for (i = 0; i < quantityList.size(); i++) {
				quantity = quantityList.get(i);

				// Quantity must be greater than or equal to the -unitEpsilon
				if (quantity.getQuantity() < -unitEpsilon)
					throw new UnitsException("Quantity of a UnitQuantity must not be negative");

				// Determine the conversion value to the standard measuring unit
				if (stdUnit != null) {
					double f_unit = convertUnits(quantity.getUnit(), stdUnit, 1, unitConv);
					if (f_unit < c_unit) {
						c_unit = f_unit;
						unitMin = quantity.getUnit();
					}
				}
			}

			// Determine each product quantity in the smallest measuring unit
			for (i = 0; i < quantityList.size(); i++) {
				quantity = quantityList.get(i);
				quantity.setNewUnit(stdUnit == null ? null : unitMin);

				// The quantity must not be zero
				if (Math.abs(quantity.getQuantity()) <= unitEpsilon ||
					stdUnit == null || stdUnit.isEmpty()) {
					quantity.setNewQuantity(quantity.getQuantity());
				}
				else {
					// Convert quantity to the smallest unit
					quantity.setNewQuantity(convertUnits(quantity.getUnit(), unitMin, quantity.getQuantity(), unitConv));
				}
			}
		}
		catch (UnitsException e) {
			throw e;
		}
	}

	// The API for converting the input quantity list to a quantity list in new unit
	//
	public void convertListOfUnitQuantities(List<? extends UnitQuantity> quantityList) throws Exception {
		// Make sure the input list is not empty
		if (quantityList.size() == 0) {
			throw new Exception("Input quantity list is empty!");
		}

		UnitQuantity quantity = quantityList.get(0);

		try {
			// No measuring unit
			if (quantity.getUnit() == null || quantity.getUnit().isEmpty()) {
				if (isListOfUnitQuantitiesInSameUnitGroup(quantityList, null) == false)
					throw new Exception("Quantity list items cannot have measuring unit");
				calculateQuantityInUnitMin(quantityList, null, null);
			}
			// Check the price unit against the length units
			else if (getUnitIndexInUnits(quantity.getUnit(), lengthUnits) != -1) {
				if (isListOfUnitQuantitiesInSameUnitGroup(quantityList, lengthUnits) == false)
					throw new Exception("Quantity list items are not in the same unit group");
				calculateQuantityInUnitMin(quantityList, "m", lengthConv);
			}
			// Check the price unit against the area units
			else if (getUnitIndexInUnits(quantity.getUnit(), areaUnits) != -1) {
				if (isListOfUnitQuantitiesInSameUnitGroup(quantityList, areaUnits) == false)
					throw new Exception("Quantity list items are not in the same unit group");
				calculateQuantityInUnitMin(quantityList, "sq m", areaConv);
			}
			// Check the price unit against the volume units
			else if (getUnitIndexInUnits(quantity.getUnit(), volumeUnits) != -1) {
				if (isListOfUnitQuantitiesInSameUnitGroup(quantityList, volumeUnits) == false)
					throw new Exception("Quantity list items are not in the same unit group");
				calculateQuantityInUnitMin(quantityList, "cu m", volumeConv);
			}
			// Check the price unit against the mass units
			else if (getUnitIndexInUnits(quantity.getUnit(), massUnits) != -1) {
				if (isListOfUnitQuantitiesInSameUnitGroup(quantityList, massUnits) == false)
					throw new Exception("Quantity list items are not in the same unit group");
				calculateQuantityInUnitMin(quantityList, "g", massConv);
			}
			// Check the price unit against the time interval units
			else if (getUnitIndexInUnits(quantity.getUnit(), timeUnits) != -1) {
				if (isListOfUnitQuantitiesInSameUnitGroup(quantityList, timeUnits) == false)
					throw new Exception("Quantity list items are not in the same unit group");
				calculateQuantityInUnitMin(quantityList, "s", timeConv);
			}
			// Unrecognized unit
			else {
				throw new UnitsException("Unrecognized unit " + quantity.getUnit() + "!");
			}
		}
		catch (UnitsException e) {
			throw e;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UnitsUtil unitConv = new UnitsUtil();
		System.out.println("Length conversion factors");
		unitConv.showLengthConvFactors();
		System.out.println("Area conversion factors");
		unitConv.showAreaConvFactors();
		System.out.println("Volume conversion factors");
		unitConv.showVolumeConvFactors();
		System.out.println("Mass conversion factors");
		unitConv.showMassConvFactors();
		System.out.println("Time interval conversion factors");
		unitConv.showTimeConvFactors();
		System.out.println("Currency interval conversion factors");
		unitConv.showCurrencyConvFactors();
		unitConv.validateConvMaps();
		try {
			Double a = unitConv.convertLengthUnits("in", "cm", 201);
			System.out.println("201 in = " + a.toString() + " cm");
		}
		catch (UnitsException e) {
			e.printStackTrace(System.out);
		}
		try {
			Double a = unitConv.convertAreaUnits("sq m", "sq ft", 99.8);
			System.out.println("99.8 sq m = " + a.toString() + " sq ft");
		}
		catch (UnitsException e) {
			e.printStackTrace(System.out);
		}
		try {
			Double a = unitConv.convertVolumeUnits("gal", "cup", 2.5);
			System.out.println("2.5 gal = " + a.toString() + " cup");
		}
		catch (UnitsException e) {
			e.printStackTrace(System.out);
		}
		try {
			Double a = unitConv.convertMassUnits("lb", "kg", 50);
			System.out.println("50 lb = " + a.toString() + " kg");
		}
		catch (UnitsException e) {
			e.printStackTrace(System.out);
		}
		try {
			Double a = unitConv.convertTimeUnits("h", "s", 2.5);
			System.out.println("2.5 h = " + a.toString() + " s");
		}
		catch (UnitsException e) {
			e.printStackTrace(System.out);
		}
		List<UnitPrice> priceList = new ArrayList<UnitPrice>();
		priceList.add(new UnitPriceImpl("lb", 2, 20, "USD"));
		priceList.add(new UnitPriceImpl("oz", 10, 3.6, "CNY"));
		priceList.add(new UnitPriceImpl("kg", 1, 18, "EUR"));
		try {
			unitConv.sortListOfUnitPrices(priceList, "CNY");
		}
		catch (Exception e){
			e.printStackTrace(System.out);
		}
		for (int i = 0; i < priceList.size(); i++) {
			System.out.println(priceList.get(i).toString());
		}
		try {
			unitConv.sortListOfUnitPrices(priceList, "USD");
		}
		catch (Exception e){
			e.printStackTrace(System.out);
		}
		for (int i = 0; i < priceList.size(); i++) {
			System.out.println(priceList.get(i).toString());
		}
		List<UnitPrice> priceList2 = new ArrayList<UnitPrice>();
		priceList2.add(new UnitPriceImpl(null, 25, 60, "USD"));
		priceList2.add(new UnitPriceImpl(null, 10, 300, "CNY"));
		priceList2.add(new UnitPriceImpl(null, 8, 18, "EUR"));
		try {
			unitConv.sortListOfUnitPrices(priceList2, "CNY");
		}
		catch (Exception e){
			e.printStackTrace(System.out);
		}
		for (int i = 0; i < priceList2.size(); i++) {
			System.out.println(priceList2.get(i).toString());
		}
		try {
			unitConv.sortListOfUnitPrices(priceList2, "USD");
		}
		catch (Exception e){
			e.printStackTrace(System.out);
		}
		for (int i = 0; i < priceList2.size(); i++) {
			System.out.println(priceList2.get(i).toString());
		}
		UnitPriceImpl badPrice = new UnitPriceImpl("m", 5, 25, "JPY");
		priceList.add(badPrice);
		try {
			unitConv.sortListOfUnitPrices(priceList, "JPN");
		}
		catch (Exception e){
			e.printStackTrace(System.out);
		}
		priceList.remove(badPrice);
		priceList.add(new UnitPriceImpl("g", -5, 25, "GBP"));
		try {
			unitConv.sortListOfUnitPrices(priceList, "GBP");
		}
		catch (Exception e){
			e.printStackTrace(System.out);
		}
		priceList.add(new UnitPriceImpl("g", 5, -25, "CNY"));
		try {
			unitConv.sortListOfUnitPrices(priceList, "CNY");
		}
		catch (Exception e){
			e.printStackTrace(System.out);
		}
		List<UnitQuantity> quantityList = new ArrayList<UnitQuantity>();
		quantityList.add(new UnitQuantityImpl("lb", 2));
		quantityList.add(new UnitQuantityImpl("oz", 10));
		quantityList.add(new UnitQuantityImpl("kg", 1));
		try {
			unitConv.convertListOfUnitQuantities(quantityList);
		}
		catch (Exception e){
			e.printStackTrace(System.out);
		}
		for (int i = 0; i < quantityList.size(); i++) {
			System.out.println(quantityList.get(i).toString());
		}
	}
}
