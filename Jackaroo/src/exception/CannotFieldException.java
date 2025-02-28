package exception;

public class CannotFieldException extends ActionException {
	public CannotFieldException() {
		super();
	}
	
	public CannotFieldException(String message) {
		super(message);
	}

}
