package exception;

public class CannotDiscardException extends ActionException {
	public CannotDiscardException() {
		super();
	}
	
	public CannotDiscardException(String message) {
		super(message);
	}

}
