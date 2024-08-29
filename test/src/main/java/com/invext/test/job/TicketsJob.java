package com.invext.test.job;

import com.invext.test.enums.TicketStatus;
import com.invext.test.model.User;
import com.invext.test.service.SupportTicketService;
import com.invext.test.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
@Log4j2
public class TicketsJob {

	private final SupportTicketService supportTicketService;
	private final UserService userService;

	public TicketsJob(SupportTicketService supportTicketService, UserService userService) {
		this.supportTicketService = supportTicketService;
		this.userService = userService;
	}

	@Scheduled(cron = "${cron}") // runs every 1 minute
	public void assignOwnerToTickets() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		log.info("- Job running at: " + LocalDateTime.now().format(formatter) + " -");

		var unnasignedTickets = supportTicketService.findByTicketStatusIsCreatedAndOwnerIsNull(TicketStatus.CREATED);
		log.info("unnasignedTickets: " + unnasignedTickets.size());

		if (unnasignedTickets.size() > 0) {
			unnasignedTickets.forEach(ticket -> {
				var role = ticket.getTicketType();
				log.info("TicketType and User Role: " + role);

				var userList = userService.findAllByRole(role);
				if (userList.size() > 0) {
					log.info("UserList size: " + userList.size());
					log.info("Ticket ID: " + ticket.getId());

					User randomUser = userList.get(new Random().nextInt(userList.size()));

					boolean haveBandwidth = supportTicketService.haveBandwidth(randomUser);
					if (haveBandwidth) {
						log.info("User ID assigned: " + randomUser.getId());
						ticket.setOwner(randomUser);
						ticket.setTicketStatus(TicketStatus.IN_PROGRESS);
						supportTicketService.save(ticket);
					} else {
						log.info("User don't have bandwitdh! On next run it will try to assign the ticket to another user.");
					}
				}
			});
		}

	}

}
