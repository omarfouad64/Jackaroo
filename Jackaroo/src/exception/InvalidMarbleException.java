package exception;

public class InvalidMarbleException extends InvalidSelectionException {
	public InvalidMarbleException() {
		super();
	}
	
	public InvalidMarbleException(String message) {
		super(message);
	}

}
