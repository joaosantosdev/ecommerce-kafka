package br.com.ourmind.ecommerce.order.models;

public class Email {

    private final String subject, body;

    public Email(String subject, String body) {
        this.subject = subject;
        this.body = body;
    }
}
