package jmp.ws.service;

import jmp.ws.model.Passenger;
import jmp.ws.model.Ticket;
import jmp.ws.model.TicketStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class TicketService {

    private final static Set<Ticket> TICKET_STORAGE = new HashSet<>();

    private interface TicketIdGenerator {
        int getNextId();
    }

    private int lastUsedId;
    private SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");

    private TicketIdGenerator ticketIdGenerator = new TicketIdGenerator() {
        @Override
        public int getNextId() {
            return ++lastUsedId;
        }
    };

    public Ticket searchAvailableTicket(final String departureCity, String departureDate) {
        Ticket ticket = new Ticket();
        ticket.setTicketId(ticketIdGenerator.getNextId());
        ticket.setDepartureCity(departureCity);
        ticket.setArrivalCity("Minsk");
        ticket.setPrice(15);
        try {
            ticket.setDepartureDate(simpleDateTimeFormat.parse(departureDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            ticket.setArrivalDate(simpleDateTimeFormat.parse("2016.08.22 15:30"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ticket;

    }

    public Ticket bookTicket(final Ticket ticket, final String firstName, String secondName, String thirdName, String birthDay) {
        Passenger passenger = new Passenger();
        passenger.setFirstName(firstName);
        passenger.setSecondName(secondName);
        passenger.setThirdName(thirdName);
        ticket.setPassenger(passenger);
        ticket.setTicketStatus(TicketStatus.RESERVED);
        try {
            passenger.setBirthDay(simpleDateFormat.parse(birthDay));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TICKET_STORAGE.add(ticket);
        return ticket;

    }


    public Ticket getTicket(String id) {
        final Integer ticketId = Integer.valueOf(id);

        for (final Ticket ticket : TICKET_STORAGE) {
            if (ticketId.equals(ticket.getTicketId())) {
                return ticket;
            }
        }
        return null;
    }

    public void payTicket(String id) {
        final Ticket ticket = getTicket(id);
        ticket.setTicketStatus(TicketStatus.PAID);
    }

    public void backTicket(String id) {
        final Ticket ticket = getTicket(id);
        TICKET_STORAGE.remove(ticket);
    }

}
