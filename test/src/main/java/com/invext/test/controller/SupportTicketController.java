package com.invext.test.controller;

import com.invext.test.dto.SupportTicketDto;
import com.invext.test.enums.TicketStatus;
import com.invext.test.enums.TicketType;
import com.invext.test.model.SupportTicket;
import com.invext.test.model.User;
import com.invext.test.service.SupportTicketService;
import com.invext.test.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/api")
public class SupportTicketController {

	private final SupportTicketService supportTicketService;
	private final UserService userService;

	public SupportTicketController(SupportTicketService supportTicketService, UserService userService) {
		this.supportTicketService = supportTicketService;
		this.userService = userService;
	}

	@GetMapping("/tickets/all")
	public ResponseEntity<List<SupportTicket>> getAllTickets() {
		try {
			List<SupportTicket> tickets = supportTicketService.findAll(); // returns all tickets

			if (tickets.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(tickets, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/tickets")
	public ResponseEntity<List<SupportTicket>> getMyTickets(Authentication authentication) {
		try {
			User owner = userService.getUser(authentication);
			List<SupportTicket> tickets = supportTicketService.findAllByOwner(owner);

			if (tickets.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(tickets, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/tickets/{id}")
	public ResponseEntity<SupportTicket> getTicketById(@PathVariable("id") long id) {
		Optional<SupportTicket> supportTicket = supportTicketService.findById(id);

		if (supportTicket.isPresent()) {
			return new ResponseEntity<>(supportTicket.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/tickets")
	public ResponseEntity<SupportTicket> createTicket(@RequestBody(required = false) SupportTicketDto dto) {
		try {
			TicketType ticketType = Optional.ofNullable(dto).isPresent() ? dto.ticketType() : TicketType.OUTROS;

			SupportTicket ticket = SupportTicket.builder()
					.ticketStatus(TicketStatus.CREATED)
					.ticketType(ticketType)
					.build();
			supportTicketService.save(ticket);
			return new ResponseEntity<>(ticket, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/tickets/{id}")
	public ResponseEntity updateTicket(@PathVariable("id") long id,
	                                   @RequestBody SupportTicketDto supportTicket,
	                                   Authentication authentication) {
		Optional<SupportTicket> ticketData = supportTicketService.findById(id);

		if (ticketData.isPresent()) {

			SupportTicket ticket = ticketData.get();
			User owner = userService.getUser(authentication);

			if (ticket.getTicketType() != owner.getRole()) {
				return ResponseEntity
						.status(HttpStatus.CONFLICT)
						.body("O chamado é diferente do permitido para o usuário!");
			}

			boolean haveBandwidth = supportTicketService.haveBandwidth(owner);
			if (!haveBandwidth) {
				return ResponseEntity
						.status(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED)
						.body("O usuário está atendendo o limite de chamados simultâneos permitidos!");
			}

			if (supportTicket.ticketType() != null) {
				ticket.setTicketType(supportTicket.ticketType());
			}
			if (supportTicket.ticketStatus() != null) {
				ticket.setTicketStatus(supportTicket.ticketStatus());
			}
			ticket.setOwner(owner);
			supportTicketService.save(ticket);
			return new ResponseEntity<>(ticket, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/tickets/{id}")
	public ResponseEntity<HttpStatus> deleteTicket(@PathVariable("id") long id) {
		try {
			supportTicketService.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/tickets")
	public ResponseEntity<HttpStatus> deleteAllTickets() {
		try {
			supportTicketService.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/tickets/status")
	public ResponseEntity<List<SupportTicket>> findByStatus(@RequestParam TicketStatus status) {
		try {
			List<SupportTicket> ticketList = supportTicketService.findByTicketStatus(status);

			if (ticketList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(ticketList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/tickets/type")
	public ResponseEntity<List<SupportTicket>> findByType(@RequestParam TicketType type) {
		try {
			List<SupportTicket> ticketList = supportTicketService.findByTicketType(type);

			if (ticketList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(ticketList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
