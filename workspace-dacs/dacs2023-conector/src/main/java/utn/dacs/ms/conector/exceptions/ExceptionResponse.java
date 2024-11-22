package utn.dacs.ms.conector.exceptions;

public class ExceptionResponse {
    private Integer status;
    private String code;
    private String description;

    public ExceptionResponse(Integer status, String errorCode, String description) {
        this.status = status;
        this.code = errorCode;
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
