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
package com.liferay.faces.bridge;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.BridgeDefaultViewNotSpecifiedException;
import javax.portlet.faces.BridgeException;
import javax.portlet.faces.BridgeUninitializedException;


/**
 * @author  Neil Griffin
 */
public class BridgeImpl implements Bridge {

	// Private Data Members
	private boolean initialized = false;
	private BridgePhaseFactory bridgePhaseFactory;
	private PortletConfig portletConfig;

	public void destroy() {
		initialized = false;
	}

	public void doFacesRequest(ActionRequest actionRequest, ActionResponse actionResponse)
		throws BridgeDefaultViewNotSpecifiedException, BridgeUninitializedException, BridgeException {

		checkNull(actionRequest, actionResponse);

		if (initialized) {
			BridgePhase bridgePhase = bridgePhaseFactory.getBridgeActionPhase(actionRequest, actionResponse,
					portletConfig);
			bridgePhase.execute();
		}
		else {
			throw new BridgeUninitializedException();
		}
	}

	public void doFacesRequest(EventRequest eventRequest, EventResponse eventResponse)
		throws BridgeUninitializedException, BridgeException {

		checkNull(eventRequest, eventResponse);

		if (initialized) {
			BridgePhase bridgePhase = bridgePhaseFactory.getBridgeEventPhase(eventRequest, eventResponse,
					portletConfig);
			bridgePhase.execute();
		}
		else {
			throw new BridgeUninitializedException();
		}

	}

	public void doFacesRequest(RenderRequest renderRequest, RenderResponse renderResponse)
		throws BridgeDefaultViewNotSpecifiedException, BridgeUninitializedException, BridgeException {

		checkNull(renderRequest, renderResponse);
		
		if (initialized) {
			BridgePhase bridgePhase = bridgePhaseFactory.getBridgeRenderPhase(renderRequest, renderResponse,
					portletConfig);
			bridgePhase.execute();
		}
		else {
			throw new BridgeUninitializedException();
		}

	}

	public void doFacesRequest(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws BridgeUninitializedException, BridgeException {

		checkNull(resourceRequest, resourceResponse);

		if (initialized) {
			BridgePhase bridgePhase = bridgePhaseFactory.getBridgeResourcePhase(resourceRequest, resourceResponse,
					portletConfig);
			bridgePhase.execute();
		}
		else {
			throw new BridgeUninitializedException();
		}

	}

	public void init(PortletConfig portletConfig) throws BridgeException {
		StringBuilder logMessage = new StringBuilder();
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss,SSS");
		String timestamp = dateFormat.format(Calendar.getInstance().getTime());
		logMessage.append(timestamp);
		logMessage.append(BridgeConstants.CHAR_SPACE);
		logMessage.append("INFO");
		logMessage.append(BridgeConstants.CHAR_SPACE);
		logMessage.append(BridgeConstants.CHAR_SPACE);
		logMessage.append("[BridgeImpl] Initializing");
		logMessage.append(BridgeConstants.CHAR_SPACE);
		logMessage.append(getTitle());
		logMessage.append(BridgeConstants.CHAR_SPACE);
		logMessage.append(getVersion());
		System.out.println(logMessage.toString());
		this.initialized = true;
		this.portletConfig = portletConfig;
		BridgeFactoryFinder.setPortletConfig(portletConfig);
		bridgePhaseFactory = (BridgePhaseFactory) BridgeFactoryFinder.getFactory(BridgePhaseFactory.class);
	}

	protected void checkNull(PortletRequest portletRequest, PortletResponse portletResponse) {

		// Null check required by the TCK.
		if (portletRequest == null) {
			throw new NullPointerException("portletRequest was null");
		}

		// Null check required by the TCK.
		if (portletResponse == null) {
			throw new NullPointerException("portletResponse was null");
		}
	}

	public String getTitle() {
		return BridgeImpl.class.getPackage().getImplementationTitle();
	}

	public String getVersion() {
		return BridgeImpl.class.getPackage().getImplementationVersion();
	}
}
