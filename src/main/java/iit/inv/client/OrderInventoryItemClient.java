package iit.inv.client;

import iit.inv.grpc.generated.InventoryServiceGrpc;
import iit.inv.grpc.generated.OrderInventoryItemRequest;
import iit.inv.grpc.generated.OrderInventoryItemResponse;
import iit.inv.ns.NameServiceClient;
import io.grpc.ConnectivityState;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.io.IOException;
import java.util.Scanner;

public class OrderInventoryItemClient {
    private ManagedChannel channel = null;
    InventoryServiceGrpc.InventoryServiceBlockingStub clientStub = null;
    String host = null;
    int port = -1;
    public static final String NAME_SERVICE_ADDRESS = "http://localhost:2379";

    public OrderInventoryItemClient() throws InterruptedException, IOException {
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
            System.out.println("\nEnter Inventory ID to ORDER:");
            int inventoryId = Integer.parseInt(userInput.nextLine().trim());

            System.out.println("\nEnter Inventory Stocks required for the ORDER:");
            int value = Integer.parseInt(userInput.nextLine().trim());

            String lockName = "orderInv";

            System.out.println("Requesting server to place the order the inventory for " + inventoryId);
            OrderInventoryItemRequest request = OrderInventoryItemRequest.newBuilder()
                    .setLockName(lockName)
                    .setItemIdForOrder(inventoryId)
                    .setValueForOrder(value)
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
            OrderInventoryItemResponse response = clientStub.orderInventoryItem(request);

            System.out.println("\n");
            System.out.println("---------------------------------------------------------------------------");
            System.out.println("Inventory update status: " + response.getStatus());

            if(response.getStatus()) {
                System.out.println("Order has been placed to the Item " + response.getItemId() + " for " + response.getValue());
            }else{
                System.out.println("Order couldn't be placed to the Item " + response.getItemId() + " for " + response.getValue());
            }
            System.out.println("---------------------------------------------------------------------------");

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
