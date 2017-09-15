package org.jagan.rsws.messenger.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Link {

	private String url;
	private String rel;
	
	public Link() {
		super();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}
	
}
