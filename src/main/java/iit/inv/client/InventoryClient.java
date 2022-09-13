package iit.inv.client;

import iit.inv.ns.NameServiceClient;

import java.io.IOException;

public class InventoryClient {
    //public static final String NAME_SERVICE_ADDRESS = "http://localhost:2379";
    public static String host = null;
    public static int port = -1;

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

        new CheckInventoryItemStockClient();
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