package jsa.endpoint;

import lombok.Getter;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public final class APIEndpoint {

	/**
	 * e.g. http://mydomain.com/api
	 */
	private @Getter String httpAddress;

	public APIEndpoint(String httpAddress) {
		if (httpAddress.endsWith("/")) {
			httpAddress = httpAddress.substring(0, httpAddress.length() - 1);
		}
		this.httpAddress = httpAddress;
	}

	public String context(String contextPath) {
		if (!contextPath.startsWith("/")) {
			contextPath = "/" + contextPath;
		}
		return httpAddress + contextPath;
	}

}
