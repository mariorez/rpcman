package ecommerce;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.logging.Logger;

import static ecommerce.ProductInfoGrpc.ProductInfoBlockingStub;
import static ecommerce.ProductInfoGrpc.newBlockingStub;
import static ecommerce.ProductInfoOuterClass.Product;
import static ecommerce.ProductInfoOuterClass.ProductID;

public class RpcMan {

    private static final Logger logger = Logger.getLogger(RpcMan.class.getName());

    public static void main(String[] args) throws InterruptedException {

        ManagedChannel channel = ManagedChannelBuilder
                .forTarget("localhost:50051")
                .usePlaintext() // Channels are secure by default (via SSL/TLS). Disable TLS to avoid needing certificates.
                .build();

        ProductInfoBlockingStub stub = newBlockingStub(channel);

        ProductID productID = stub.addProduct(
                Product.newBuilder()
                        .setName("Samsung S10")
                        .setDescription("Smart phone")
                        .setPrice(700.0f)
                        .build());

        logger.info("Product ID: " + productID.getValue() + " added successfully.");

        Product product = stub.getProduct(productID);

        logger.info("Product: " + product.toString());

        channel.shutdown();
    }
}
