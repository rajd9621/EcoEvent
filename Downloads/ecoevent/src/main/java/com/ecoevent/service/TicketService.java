package com.ecoevent.service;

import com.ecoevent.entity.Registration;
import com.ecoevent.entity.Ticket;
import com.ecoevent.repository.TicketRepository;
import com.ecoevent.util.QRCodeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TicketService {

    private final TicketRepository ticketRepository;
    private final QRCodeUtil qrCodeUtil;

    public TicketService(TicketRepository ticketRepository, QRCodeUtil qrCodeUtil) {
        this.ticketRepository = ticketRepository;
        this.qrCodeUtil = qrCodeUtil;
    }

    public Ticket generateTicket(Registration registration) {
        Ticket ticket = Ticket.builder()
                .ticketCode("ECO-" + System.currentTimeMillis())
                .registration(registration)
                .used(false)
                .build();

        try {
            String qrBase64 = qrCodeUtil.generateQRCodeBase64(ticket.getTicketCode());
            ticket.setQrCodeBase64(qrBase64);
        } catch (Exception e) {
            // QR generation is optional; ticket still works with code
        }

        return ticketRepository.save(ticket);
    }

    public Ticket findById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    public Ticket findByCode(String code) {
        return ticketRepository.findByTicketCode(code)
                .orElseThrow(() -> new RuntimeException("Invalid ticket code"));
    }

    public Ticket findByRegistration(Long registrationId) {
        return ticketRepository.findByRegistrationId(registrationId)
                .orElseThrow(() -> new RuntimeException("Ticket not found for registration"));
    }

    public Ticket markAsUsed(String ticketCode) {
        Ticket ticket = findByCode(ticketCode);
        ticket.setUsed(true);
        ticket.setUsedAt(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }
}
