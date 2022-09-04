package iit.inv.client;

import iit.inv.grpc.generated.CheckInventoryStockRequest;
import iit.inv.grpc.generated.CheckInventoryStockResponse;
import iit.inv.grpc.generated.InventoryServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;

public class CheckInventoryStockClient {
    private ManagedChannel channel = null;
    InventoryServiceGrpc.InventoryServiceBlockingStub clientStub = null;
    String host = null;
    int port = -1;

    /**public static void main(String[] args) throws InterruptedException {
        String host = null;
        int port = -1;
        if (args.length != 2) {
            System.out.println("Usage CheckInventoryStockClient <host> <port>");
            System.exit(1);
        }
        host = args[0];
        port = Integer.parseInt(args[1].trim());
        CheckInventoryStockClient client = new CheckInventoryStockClient(host, port);
        client.initializeConnection();
        try {
            client.processUserRequests();
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
        }
        client.closeConnection();
    }**/

    public CheckInventoryStockClient(String host, int port) throws InterruptedException {
        this.host = host;
        this.port = port;
        this.initializeConnection();
        this.processUserRequests();
        this.closeConnection();
    }

    private void initializeConnection() {
        System.out.println("Initializing Connecting to server at " + host + ":" + port);
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        clientStub = InventoryServiceGrpc.newBlockingStub(channel);
        System.out.println("Connection Initialized!");
    }

    private void closeConnection() {
        channel.shutdown();
    }

    private void processUserRequests() throws InterruptedException {
        while (true) {
            Scanner userInput = new Scanner(System.in);
            System.out.println("\nEnter Inventory ID to check the stock :");
            int inventoryId = Integer.parseInt(userInput.nextLine().trim());
            System.out.println("Requesting server to check the inventory for " + inventoryId);
            CheckInventoryStockRequest request = CheckInventoryStockRequest.newBuilder()
                    .setInventoryId(inventoryId)
                    .build();
            System.out.println("request: " + request);
            CheckInventoryStockResponse response = clientStub.checkInventoryStock(request);
            System.out.println("response: " + response);
            System.out.printf("Available stock is " + response.getInventory() + " pcs");
            Thread.sleep(1000);
        }
    }

}
