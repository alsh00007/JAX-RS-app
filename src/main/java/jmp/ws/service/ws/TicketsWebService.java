package jmp.ws.service.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import jmp.ws.model.Ticket;
import jmp.ws.service.TicketService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/TicketsWebService")
public class TicketsWebService {
    private TicketService ticketService = new TicketService();

    @POST
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/book")
    public Response bookTicket(@FormParam("departureCity") String departureCity, @FormParam("departureDate") String departureDate,
                               @FormParam("firstName") String firstName, @FormParam("secondName") String secondName,
                               @FormParam("thirdName") String thirdName, @FormParam("birthDay") String birthDay) {

        Ticket ticket = ticketService.searchAvailableTicket(departureCity, departureDate);
        ticketService.bookTicket(ticket, firstName, secondName, thirdName, birthDay);

        String output = String.valueOf(ticket.getTicketId());
        return Response.status(200).entity(output).build();
    }


    @GET
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/get/{id}")
    public Response getTicket(@PathParam("id") String id) {
        String output = "";
        Ticket ticket = ticketService.getTicket(id);
        if (ticket != null) {
            output = toJson(ticket);
        }
        return Response.status(200).entity(output).build();

    }

    private String toJson(Ticket ticket) {
        String json = "Error";
        try {
            json = new ObjectMapper().writeValueAsString(ticket);
        } catch (Exception e) {
        }
        return json;
    }

    @GET
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/pay/{id}")
    public Response pay(@PathParam("id") String id) {

        ticketService.payTicket(id);
        String output = "Ticket is paid";
        return Response.status(200).entity(output).build();

    }


    @GET
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/back/{id}")
    public Response back(@PathParam("id") String id) {

        ticketService.backTicket(id);
        String output = "Ticket is backed";
        return Response.status(200).entity(output).build();

    }



}
