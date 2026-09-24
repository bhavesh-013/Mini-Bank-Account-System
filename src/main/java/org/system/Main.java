package org.system;

import java.util.Scanner;

public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        while(true){
            System.out.println("\n===== MINI BANK =====");

            System.out.println("1. Create Account");
            System.out.println("2. View Account");
            System.out.println("3. Deposit Money");
            System.out.println("4. Withdraw Money");
            System.out.println("5. View Transaction History");
            System.out.println("6. Delete Account");
            System.out.println("7. Exit");

            System.out.print("\nEnter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            AccountDAO dao = new AccountDAO();
            switch(choice){
                case 1:
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();

                    System.out.print("Enter initial amt: ");
                    Double balance = sc.nextDouble();

                    Account account = new Account(name, email, balance);
                    dao.createAccount(account);

                    break;

                case 2:
                    System.out.print("Enter Account ID: ");
                    int id = sc.nextInt();
                    dao.getAccount(id);
                    break;

                case 3:
                    System.out.print("Enter Account ID: ");
                    int ID = sc.nextInt();
                    System.out.print("Deposit amount: ");
                    int amt = sc.nextInt();
                    dao.deposit(ID, amt);
                    break;

                case 4:
                    System.out.print("Enter Account ID: ");
                    int accID = sc.nextInt();
                    System.out.print("Withdraw amount: ");
                    int W_amt = sc.nextInt();
                    dao.withdraw(accID, W_amt);
                    break;

                case 5:
                    System.out.print("Enter Account ID: ");
                    int accountId = sc.nextInt();
                    dao.getTransactionHistory(accountId);
                    break;

                case 6:
                    System.out.print("Enter Account ID: ");
                    int deleteAccountId = sc.nextInt();
                    dao.deleteAccount(deleteAccountId);
                    break;

                case 7:
                    System.out.println("Thank you for using Mini Bank!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}