package banking;

import java.util.Scanner;

public class UI {

    public static void show() {
     try(Scanner sc = new Scanner(System.in)) {

         boolean outer = true;
         boolean inner = true;

         AccountService accServ = new AccountService();

         while (outer) {
             Menu menu = new InitialMenu();
             menu.printMenu();
             switch (sc.nextInt()) {
                 case 1:
                     accServ.createAccount();
                     break;
                 case 2:
                     System.out.println("\nEnter your card number:");
                     System.out.print(">");
                     String inCard = sc.next();
                     System.out.println("Enter your PIN:");
                     System.out.print(">");
                     String inPIN = sc.next();
                     System.out.println();

                     if(accServ.isLogInSuccess(inCard,inPIN)) {
                         System.out.println("You have successfully logged in!\n");
                         menu = new LoggedMenu();
                         while (inner) {
                             menu.printMenu();
                             switch (sc.nextInt()) {
                                 case 1:
                                     System.out.printf("\nBalance: %d\n\n", accServ.getAccount(inCard).getBalance().intValue());
                                     break;
                                 case 2:
                                     System.out.println("Enter amount to add: ");
                                     System.out.print(">");
                                     accServ.addIncome(inCard, sc.nextBigDecimal());
                                     break;
                                 case 3:
                                     System.out.println("Enter card number to transfer to: ");
                                     System.out.print(">");
                                     String cardTo = sc.next();
                                     System.out.println("Enter amount: ");
                                     System.out.print(">");
                                     accServ.doTransfer(inCard, cardTo, sc.nextBigDecimal());
                                     break;
                                 case 4:
                                     accServ.deleteAccount(inCard);
                                     break;
                                 case 5:
                                     System.out.print("You have successfully logged out!\n");
                                     inner = false;
                                     break;
                                 case 0:
                                     inner = false;
                                     outer = false;
                                     break;
                             }
                         }
                     }
                     else {
                         System.out.println("Wrong card number or PIN");
                     }

                     System.out.println();
                     break;
                 case 0:
                     System.out.println("\nBye!");
                     outer = false;
                     break;
             }
             System.out.println();
         }
     }
    }
}
