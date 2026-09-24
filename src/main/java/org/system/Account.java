package org.system;

public class Account {
    private String name;
    private String email;
    private double balance;

    Account(String name, String email, double balance){
        this.name = name;
        this.email = email;
        this.balance = balance;
    }
    public double getBalance() {
        return balance;
    }
    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }
}
