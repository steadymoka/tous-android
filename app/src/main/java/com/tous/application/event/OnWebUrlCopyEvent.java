package com.tous.application.event;


import com.moka.framework.util.OttoUtil;


public class OnWebUrlCopyEvent implements OttoUtil.Event {

	private String copyUrl;

	public OnWebUrlCopyEvent( String copyUrl ) {

		this.copyUrl = copyUrl;
	}

	public String getCopyUrl() {

		return copyUrl;
	}

}
