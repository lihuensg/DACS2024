package utn.dacs.ms.bff.exceptions;

public class BffException extends GenericException {

	private static final long serialVersionUID = -8240990866638394860L;

	public BffException(IEnumError error) {
        super(error);
    }

    public BffException(IEnumError error, Object... params) {
        super(error, params);
    }
}
