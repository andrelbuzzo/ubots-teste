package com.invext.test.service;

import com.invext.test.dto.SupportTicketDto;
import com.invext.test.enums.TicketStatus;
import com.invext.test.enums.TicketType;
import com.invext.test.model.SupportTicket;
import com.invext.test.model.User;
import com.invext.test.repository.SupportTicketRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class SupportTicketService {

	private final int TICKET_LIMIT = 3;

	private final SupportTicketRepository supportTicketRepository;

	public SupportTicketService(SupportTicketRepository supportTicketRepository) {
		this.supportTicketRepository = supportTicketRepository;
	}

	public List<SupportTicket> findAll() {
		return supportTicketRepository.findAll();
	}

	public List<SupportTicket> findAllByOwner(User owner){
		return supportTicketRepository.findAllByOwner(owner);
	}

	public Optional<SupportTicket> findById(long id) {
		return supportTicketRepository.findById(id);
	}

	public SupportTicket save(SupportTicketDto supportTicket) {
		SupportTicket ticket = convertToEntity(supportTicket);
		return supportTicketRepository.save(ticket);
	}

	public SupportTicket save(SupportTicket supportTicket) {
		return supportTicketRepository.save(supportTicket);
	}

	public SupportTicket convertToEntity(SupportTicketDto dto){
		return new SupportTicket(dto);
	}

	public void deleteById(long id) {
		supportTicketRepository.deleteById(id);
	}

	public void deleteAll() {
		supportTicketRepository.deleteAll();
	}

	public List<SupportTicket> findByTicketStatus(TicketStatus status){
		return supportTicketRepository.findByTicketStatus(status);
	}

	public List<SupportTicket> findByTicketStatusIsCreatedAndOwnerIsNull(TicketStatus status){
		return supportTicketRepository.findByTicketStatusAndOwnerIsNull(status);
	}

	public List<SupportTicket> findByTicketType(TicketType type){
		return supportTicketRepository.findByTicketType(type);
	}

	public boolean haveBandwidth(User owner){
		int numberOfTickets = supportTicketRepository.findAllByOwner(owner).size();
		return numberOfTickets < TICKET_LIMIT;
	}

}
