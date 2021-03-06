/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.liferay.faces.bridge.context;

import com.liferay.faces.bridge.config.BridgeConfigConstants;


/**
 * This class is intended to be used as a {@link BridgeContextWrapper} around the default BridgeContext implementation.
 * Its purpose is to handle special cases of the TCK.
 *
 * @author  Neil Griffin
 */
public class BridgeContextTCKImpl extends BridgeContextWrapper {

	// Private Data Members
	private BridgeContext wrappedBridgeContext;

	public BridgeContextTCKImpl(BridgeContext bridgeContext) {
		this.wrappedBridgeContext = bridgeContext;
		setCurrentInstance(this);
	}

	/**
	 * This method overrides the default values of various configuration options in order to prevent individual test
	 * failures in the TCK.
	 */
	@Override
	public String getInitParameter(String name) {

		String value = super.getInitParameter(name);

		if (BridgeConfigConstants.PARAM_OPTIMIZE_PORTLET_NAMESPACE1.equals(name) ||
				(BridgeConfigConstants.PARAM_OPTIMIZE_PORTLET_NAMESPACE2.equals(name))) {
			value = Boolean.FALSE.toString();
		}
		else if (BridgeConfigConstants.PARAM_MANAGE_INCONGRUITIES.equals(name)) {
			value = Boolean.FALSE.toString();
		}
		else if (BridgeConfigConstants.PARAM_RENDER_REDIRECT_ENABLED.equals(name)) {
			value = Boolean.TRUE.toString();
		}

		return value;
	}

	@Override
	public BridgeContext getWrapped() {
		return wrappedBridgeContext;
	}

}
