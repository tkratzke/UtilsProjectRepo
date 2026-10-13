package com.skagit.utils.randomx;

import com.skagit.util.cdf.BivariateNormalCdf;
import com.skagit.util.myLogger.MyLogger;

public class WeightedPairDataAcc extends WeightedPairDataAcc0 {
	@Override
	public BivariateNormalCdf createBivariateNormalCdf(final MyLogger logger) {
		/** Following line sets _mean and _var. */
		getVar();
		final double xBar = _mean[0];
		final double yBar = _mean[1];
		final double sigmaXSq = _var[0][0];
		final double sigmaYSq = _var[1][1];
		final double sigmaXY = _var[0][1];
		final BivariateNormalCdf cdf = new BivariateNormalCdf(logger, xBar, yBar, sigmaXSq, sigmaYSq, sigmaXY,
				/* warnOnDegenerate= */true);
		return cdf;
	}
}
