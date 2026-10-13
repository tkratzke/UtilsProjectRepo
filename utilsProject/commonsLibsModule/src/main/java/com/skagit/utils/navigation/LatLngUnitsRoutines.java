package com.skagit.utils.navigation;

public class LatLngUnitsRoutines {

	final protected static int _NaNUnits = Integer.MIN_VALUE;
	final public static int _DegsToNewUnits = 10000000;
	final public static int _QuarterCircleOfNewUnits;
	final public static int _HalfCircleOfNewUnits;
	final public static long _FullCircleOfNewUnits;
	final public static int _NDigitsForLatOrLng = 7;
	final public static String _StandardFormat;
	/** Note: _FullCircleOfNewUnits > Integer.MAX_VALUE. */

	static {
		_QuarterCircleOfNewUnits = _DegsToNewUnits * 90;
		_HalfCircleOfNewUnits = _DegsToNewUnits * 180;
		_FullCircleOfNewUnits = _DegsToNewUnits * 360L;
		final int nDigitsForNewUnits = getNDigitsForNewUnits();
		_StandardFormat = String.format("%%.%df", nDigitsForNewUnits);
	}

	public static int getNDigitsForNewUnits() {
		return (int) Math.ceil(Math.log10(_DegsToNewUnits));
	}

	public static int normalizeUnits180_180I(final long uIn) {
		if (-_HalfCircleOfNewUnits <= uIn && uIn < _HalfCircleOfNewUnits)
			return (int) uIn;
		if (uIn < -_HalfCircleOfNewUnits) {
			final long u = -(-uIn % _FullCircleOfNewUnits);
			if (u < -_HalfCircleOfNewUnits)
				return (int) (u + _FullCircleOfNewUnits);
			else
				return (int) u;
		} else {
			final long u = uIn % _FullCircleOfNewUnits;
			if (u >= _HalfCircleOfNewUnits)
				return (int) (u - _FullCircleOfNewUnits);
			else
				return (int) u;
		}
	}

	public static double simpleUToD180_180I(final int u) {
		final double d = ((double) u) / _DegsToNewUnits;
		return d;
	}

	public static int degsToUnits180_180I(final double dIn) {
		if (Double.isNaN(dIn))
			return _NaNUnits;
		final double d = LatLngArrayStatics.getInRange180_180(dIn);
		return simpleDToU180_180I(d);
	}

	public static int simpleDToU180_180I(final double d) {
		final int u = (int) Math.round(d * _DegsToNewUnits);
		return u;
	}

	public static double unitsToDegs180_180I(int u) {
		if (u == _NaNUnits)
			return Double.NaN;
		u = normalizeUnits180_180I(u);
		return simpleUToD180_180I(u);
	}

	public static double roundToLattice180_180I(final double dIn) {
		final double d = LatLngArrayStatics.getInRange180_180(dIn);
		final int u = simpleDToU180_180I(d);
		return simpleUToD180_180I(u);
	}

	public static int unitsToEast180_180I(final int baseUnits, final int units) {
		final long diffUnits = (long) units - baseUnits;
		final int u = normalizeUnits180_180I(diffUnits);
		return u;
	}

	public static long normalizeUnits0_360L(final long uIn) {
		if (0L <= uIn && uIn < _FullCircleOfNewUnits)
			return uIn;
		if (uIn < 0) {
			final long u = -(-uIn % _FullCircleOfNewUnits);
			if (u == 0)
				return 0;
			return _FullCircleOfNewUnits - u;
		}
		return uIn % _FullCircleOfNewUnits;
	}

	public static double simpleUToD0_360L(final long u) {
		final double d = ((double) u) / _DegsToNewUnits;
		return d;
	}

	public static long simpleDToU0_360L(final double d) {
		final long u = Math.round(d * _DegsToNewUnits);
		return u;
	}

	public static double unitsToDegs0_360L(long u) {
		if (u == _NaNUnits)
			return Double.NaN;
		u = normalizeUnits0_360L(u);
		return simpleUToD0_360L(u);
	}

	public static long degsToUnits0_360L(final double dIn) {
		if (Double.isNaN(dIn))
			return _NaNUnits;
		final double d = LatLngArrayStatics.getInRange0_360(dIn);
		return simpleDToU0_360L(d);
	}

	public static double roundToLattice0_360L(final double dIn) {
		final double d = LatLngArrayStatics.getInRange0_360(dIn);
		final long u = simpleDToU0_360L(d);
		return simpleUToD0_360L(u);
	}

	public static long unitsToEast0_360L(final long baseUnits, final long units) {
		final long diffUnits = units - baseUnits;
		final long u = normalizeUnits0_360L(diffUnits);
		return u;
	}

	public static double degsToEast0_360L(final double baseDegs, final double degs) {
		final double diff = LatLngArrayStatics.getInRange0_360(degs - baseDegs);
		final double d = roundToLattice0_360L(diff);
		if (degs != baseDegs && d == 0d)
			return 360d;
		return d;
	}

	public static int[] getNormalizedUnits(final int latUIn, final int lngUIn) {
		int latU = normalizeUnits180_180I(latUIn);
		int lngU = lngUIn;
		/**
		 * The latitude's between -180 and 180. We want it between -90 and 90.
		 */
		if (latU < -_QuarterCircleOfNewUnits || latU > _QuarterCircleOfNewUnits) {
			if (lngU < 0) {
				lngU += _HalfCircleOfNewUnits;
			} else {
				lngU -= _HalfCircleOfNewUnits;
			}
			if (latU < -_QuarterCircleOfNewUnits) {
				latU = -_HalfCircleOfNewUnits - latU;
			} else {
				latU = _HalfCircleOfNewUnits - latU;
			}
		}
		if (latU == _QuarterCircleOfNewUnits || latU == -_QuarterCircleOfNewUnits) {
			lngU = 0;
		} else {
			lngU = normalizeUnits180_180I(lngU);
		}
		return new int[] { latU, lngU };
	}

}
