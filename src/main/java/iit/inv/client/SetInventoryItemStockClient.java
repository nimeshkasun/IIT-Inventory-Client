package iit.inv.client;

import iit.inv.grpc.generated.*;
import iit.inv.ns.NameServiceClient;
import io.grpc.ConnectivityState;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.io.IOException;
import java.util.Scanner;

public class SetInventoryItemStockClient {
    private ManagedChannel channel = null;
    InventoryServiceGrpc.InventoryServiceBlockingStub clientStub = null;
    String host = null;
    int port = -1;
    public static final String NAME_SERVICE_ADDRESS = "http://localhost:2379";

    public SetInventoryItemStockClient() throws InterruptedException, IOException {
        fetchServerDetails();
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
        channel.getState(true);
    }

    private void closeConnection() {
        channel.shutdown();
    }

    private void processUserRequests() throws InterruptedException, IOException {
        while (true) {
            Scanner userInput = new Scanner(System.in);
            System.out.println("\nEnter Inventory ID to add :");
            int inventoryId = Integer.parseInt(userInput.nextLine().trim());

            System.out.println("\nEnter Inventory Stock Value to save :");
            int value = Integer.parseInt(userInput.nextLine().trim());

            String lockName = "setInv";

            System.out.println("Requesting server to check the inventory for " + inventoryId);
            SetInventoryItemStockRequest request = SetInventoryItemStockRequest.newBuilder()
                    .setLockName(lockName)
                    .setItemId(inventoryId)
                    .setValue(value)
                    .build();

            ConnectivityState state = channel.getState(true);
            while (state != ConnectivityState.READY) {
                System.out.println("Service unavailable, looking for a service provider..");
                fetchServerDetails();
                initializeConnection();
                Thread.sleep(5000);
                state = channel.getState(true);
            }

            System.out.println("Connection Initialized!");
            SetInventoryItemStockResponse response = clientStub.setInventoryItemStock(request);
            System.out.printf("Inventory update status: " + response.getStatus());
            Thread.sleep(1000);
            break;
        }
    }

    protected void fetchServerDetails() throws IOException, InterruptedException {
        NameServiceClient client = new NameServiceClient(NAME_SERVICE_ADDRESS);
        NameServiceClient.ServiceDetails serviceDetails = client.findService("InventoryService");
        host = serviceDetails.getIPAddress();
        port = serviceDetails.getPort();
    }
}
