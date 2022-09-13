package iit.inv.client;

import iit.inv.ns.NameServiceClient;

import java.io.IOException;
import java.util.Scanner;

public class InventoryClient {
    public static String host = null;
    public static int port = -1;

    private void userMenu() throws InterruptedException, IOException{

        Scanner userInput = new Scanner(System.in);
        System.out.println("\n");
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("------------------- Select an Option (Enter the number) -------------------");
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("(1) New Inventory Item");
        System.out.println("(2) Check Inventory Stock");
        System.out.println("(3) Order Inventory Item");
        System.out.println("(4) Exit System");
        System.out.println("---------------------------------------------------------------------------");
        System.out.print("Selection: ");
        int selection = Integer.parseInt(userInput.nextLine().trim());

        switch (selection){
            case 1:
                new SetInventoryItemStockClient();
                userMenu();
            case 2:
                new CheckInventoryItemStockClient();
                userMenu();
            case 3:
                new OrderInventoryItemClient();
                userMenu();
            case 4:
                System.out.println("---------------------------------------------------------------------------");
                System.out.println("---------------------------- Have a nice day! -----------------------------");
                System.out.println("---------------------------------------------------------------------------");
                break;
        }

    }

    public static void main(String[] args) throws InterruptedException, IOException {
        /*
        //Removed for NameService
        String host = null;
        int port = -1;
        if (args.length != 2) {
            System.out.println("Usage CheckInventoryStockClient <host> <port>");
            System.exit(1);
        }
        host = args[0];
        port = Integer.parseInt(args[1].trim());
        */
        //fetchServerDetails();

        InventoryClient inventoryClient = new InventoryClient();
        inventoryClient.userMenu();

    }



}




/*
 * Commands:
 *
 * Clean Install: mvn clean install
 *
 * Open CMD/ PowerShell > Run:
 * > cd IdeaProjects\IIT-Inventory-Client
 * Run Server: java -jar target/IIT-Inventory-Client-1.0-SNAPSHOT-jar-with-dependencies.jar
 *
 *
 * */