package com.skagit.utils.navigation;

import java.util.Comparator;

import com.skagit.utils.Constants0;
import com.skagit.utils.NumericalRoutines;

public class LatLngArrayStatics {
	final public static int _NDigitsForLatOrLng = 7;
	final public static String _StandardFormat = String.format("%%.%df", _NDigitsForLatOrLng);

	public static Comparator<double[]> _ByLatThenLng = (latLngArray0A, latLngArray1A) -> {
		if (latLngArray0A == latLngArray1A)
			return 0;
		if (latLngArray0A == null)
			return -1;
		if (latLngArray1A == null)
			return 1;
		/** They're not the same and neither is null. */
		final double[] latLngArray0B = normalizeLatLngArray(latLngArray0A);
		final double[] latLngArray1B = normalizeLatLngArray(latLngArray1A);
		final double lat0 = latLngArray0B[0];
		final double lat1 = latLngArray1B[0];
		if (lat0 != lat1)
			return lat0 < lat1 ? -1 : 1;
		final double lng0 = latLngArray0B[1];
		final double lng1 = latLngArray1B[1];
		return lng0 < lng1 ? -1 : (lng0 > lng1 ? 1 : 0);
	};

	public static boolean equalLatLngArrays(final double[] latLngArray0, final double[] latLngArray1) {
		return _ByLatThenLng.compare(latLngArray0, latLngArray1) == 0;
	}

	static public boolean isValid(final double[] latLngArray) {
		final double lat = latLngArray[0];
		if (-90d <= lat && lat <= 90d) {
			final double lng = latLngArray[1];
			if (-180d <= lng && lng < 180d)
				return true;
		}
		return false;
	}

	/** Radians routines; no rounding. */
	public static double getInRange0_2Pi(final double dIn) {
		return NumericalRoutines.generalMod(dIn, Constants0._2Pi);
	}

	public static double getInRangePi_Pi(final double dIn) {
		if (-Math.PI <= dIn && dIn < Math.PI)
			return dIn;
		return NumericalRoutines.generalMod(dIn + Math.PI, Constants0._2Pi) - Math.PI;
	}

	/** End of Radians routines, but still no rounding. */
	public static double getInRange180_180(final double dIn) {
		if (-180d <= dIn && dIn < 180d)
			return dIn;
		return NumericalRoutines.generalMod(dIn + 180d, 360d) - 180d;
	}

	public static double getInRange0_360(final double dIn) {
		return NumericalRoutines.generalMod(dIn, 360d);
	}

	public static double degsToEast180_180(final double baseDegs, final double degs) {
		return getInRange180_180(degs - baseDegs);
	}

	/** We return the input if there are no changes. */
	public static double[] normalizeLatLngArray(final double[] latLngArray) {
		double lat = latLngArray[0];
		double lng = latLngArray[1];
		if (Double.isNaN(lat) || Double.isNaN(lng))
			return new double[] { Double.NaN, Double.NaN };
		lat = getInRange180_180(lat);
		lng = getInRange180_180(lng);
		/**
		 * lat is now between -180 and 180. If it's between -90 and 90, there's no
		 * problem. Handle the problem.
		 */
		if (lat < -90d || lat > 90d) {
			if (lng >= 0d) {
				lng -= 180d;
			} else {
				lng += 180d;
			}
			lat = (lat < -90d ? -180d : 180d) - lat;
		}
		if (lat == 90d || lat == -90d) {
			lng = 0d;
		}
		if (latLngArray[0] == lat && latLngArray[1] == lng)
			return latLngArray;
		return new double[] { lat, lng };

	}

}
