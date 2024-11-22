package utn.dacs.ms.bff.exceptions;

public abstract class GenericException extends RuntimeException {

	private static final long serialVersionUID = -7946077471150012041L;
	
	private final IEnumError error;
    private Object[] params;

    public GenericException(IEnumError error) {
        super(error.message());
        this.error = error;
    }

    public GenericException(IEnumError error, Object... params) {
        super(error.message());
        this.error = error;
        this.params = params;
    }

    public Object[] getParams() {
        return params;
    }

    public IEnumError getError() {
        return error;
    }
}
