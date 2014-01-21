package jsa;

/**
 *
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class NotImplementedException extends Exception {

	private static final long serialVersionUID = 5345309342134086660L;

	public NotImplementedException() {
	}

	public NotImplementedException(String message) {
		super(message);
	}

	public NotImplementedException(Class<?> ifc) {
		super(String.format("No implementation specified for %s", ifc));
	}

	public NotImplementedException(Exception exception) {
		super(exception);
	}
}
