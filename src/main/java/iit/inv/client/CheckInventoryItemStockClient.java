package iit.inv.client;

import iit.inv.grpc.generated.CheckInventoryItemStockRequest;
import iit.inv.grpc.generated.CheckInventoryItemStockResponse;
import iit.inv.grpc.generated.InventoryServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;

public class CheckInventoryItemStockClient {
    private ManagedChannel channel = null;
    InventoryServiceGrpc.InventoryServiceBlockingStub clientStub = null;
    String host = null;
    int port = -1;

    public CheckInventoryItemStockClient(String host, int port) throws InterruptedException {
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
            CheckInventoryItemStockRequest request = CheckInventoryItemStockRequest.newBuilder()
                    .setItemId(inventoryId)
                    .build();
            System.out.println("request: " + request);
            CheckInventoryItemStockResponse response = clientStub.checkInventoryItemStock(request);
            System.out.println("response: " + response);
            System.out.printf("Available stock is " + response.getItemStock() + " pcs");
            Thread.sleep(1000);
        }
    }

}
