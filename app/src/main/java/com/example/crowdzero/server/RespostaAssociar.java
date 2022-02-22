package com.example.crowdzero.server;

public class RespostaAssociar {
    private boolean success;
    private String message;
    private DataInstiuicao data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataInstiuicao getData() {
        return data;
    }

    public void setData(DataInstiuicao data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RespostaAssociar{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
