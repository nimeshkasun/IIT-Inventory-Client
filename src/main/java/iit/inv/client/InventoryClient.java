package iit.inv.client;

public class InventoryClient {
    public static void main(String[] args) throws InterruptedException {
        String host = null;
        int port = -1;
        if (args.length != 2) {
            System.out.println("Usage CheckInventoryStockClient <host> <port>");
            System.exit(1);
        }
        host = args[0];
        port = Integer.parseInt(args[1].trim());

        new CheckInventoryItemStockClient(host, port);

    }
}




/*
 * Commands:
 *
 * Clean Install: mvn clean install
 *
 * Open CMD/ PowerShell > Run:
 * > cd IdeaProjects\IIT-Inventory-Client
 * Run Server: java -jar target/IIT-Inventory-Client-1.0-SNAPSHOT-jar-with-dependencies.jar localhost 11436
 *
 *
 * */