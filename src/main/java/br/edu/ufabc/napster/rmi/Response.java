package br.edu.ufabc.napster.rmi;

import java.io.Serializable;

public class Response implements Serializable {

    private static final long serialVersionUID = 123123123;
    public String message;
    public String from;
    public String to;
    public String metadata;


    public Response(String message, String from, String to) {
        this.message = message;
        this.from = from;
        this.to = to;
    }

    public String toString() {
        return "Message: " + this.message + "\n" +
                "From: " + this.from + "\n" +
                "To: " + this.to + "\n";
    }
}
