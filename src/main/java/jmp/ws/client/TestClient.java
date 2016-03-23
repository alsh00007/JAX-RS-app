package jmp.ws.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;


public class TestClient {
    private static final int port = 8080;
    private static final String uri = "/JAX-RS-app-1.0-SNAPSHOT/rest/TicketsWebService";
    private static final String url = "http://localhost:" + port + uri;


    public static void main(String[] args) {
        TestClient testClient = new TestClient();
        int ticketId = testClient.booking();
        testClient.getting(ticketId);
        testClient.pay(ticketId);
        testClient.back(ticketId);

        int ticketId2 = testClient.booking();
        testClient.getting(ticketId2);
        testClient.pay(ticketId2);
        testClient.back(ticketId2);
    }

    private Integer booking() {
        Client client = Client.create();
        WebResource webResource = client.resource(url + "/book");

        Form form = new Form();
        form.add("departureCity", "Riga");
        form.add("departureDate", "2016.07.20 13:20");
        form.add("firstName", "Eric");
        form.add("secondName", "Cartman");
        form.add("thirdName", "Sidorovich");
        form.add("birthDay", "1948.07.20");

        ClientResponse response = webResource.accept("text/plain")
                .post(ClientResponse.class, form);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }

        String output = response.getEntity(String.class);

        System.out.println("Output from Server .... \n");
        System.out.println("Ticket id : " + output);
        return Integer.valueOf(output);
    }

    private void getting(int ticketId) {

        Client client = Client.create();
        WebResource webResource = client.resource(url + "/get/" + ticketId);

        ClientResponse response = webResource.accept("text/plain")
                .get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }

        String output = response.getEntity(String.class);

        System.out.println("Output from Server .... \n");
        System.out.println(output);
    }

    private void pay(int ticketId) {

        Client client = Client.create();
        WebResource webResource = client.resource(url + "/pay/" + ticketId);

        ClientResponse response = webResource.accept("text/plain")
                .get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }

        String output = response.getEntity(String.class);

        System.out.println("Output from Server .... \n");
        System.out.println(output);
    }

    private void back(int ticketId) {

        Client client = Client.create();
        WebResource webResource = client.resource(url + "/back/" + ticketId);

        ClientResponse response = webResource.accept("text/plain")
                .get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }

        String output = response.getEntity(String.class);

        System.out.println("Output from Server .... \n");
        System.out.println(output);
    }

}
