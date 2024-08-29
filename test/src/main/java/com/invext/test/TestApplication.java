package com.invext.test;

import com.invext.test.enums.TicketStatus;
import com.invext.test.enums.TicketType;
import com.invext.test.model.SupportTicket;
import com.invext.test.model.User;
import com.invext.test.service.SupportTicketService;
import com.invext.test.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService, SupportTicketService supportTicketService) {
		return args -> {  // inserting data after application is up
			User user_cartao1;
			User user_cartao2;
			User user_cartao3;
			User user_emprestimo;
			User user_outros;

			if (userService.findAllByRole(TicketType.CARTAO).isEmpty()) {
				user_cartao1 = userService.save(new User("User Cartão 1", "cartao1@invext.com", "123456", TicketType.CARTAO));
				user_cartao2 = userService.save(new User("User Cartão 2", "cartao2@invext.com", "123456", TicketType.CARTAO));
				user_cartao3 = userService.save(new User("User Cartão 3", "cartao3@invext.com", "123456", TicketType.CARTAO));
				user_emprestimo = userService.save(new User("User Empréstimo", "emprestimo@invext.com", "123456", TicketType.EMPRESTIMO));
				user_outros = userService.save(new User("User Outros Assunto", "outros@invext.com", "123456", TicketType.OUTROS));

				if (supportTicketService.findAll().isEmpty()) {
					supportTicketService.save(
							SupportTicket.builder().owner(user_cartao1).ticketType(TicketType.CARTAO)
									.ticketStatus(TicketStatus.CREATED).build());

					supportTicketService.save(
							SupportTicket.builder().owner(user_cartao2).ticketType(TicketType.CARTAO)
									.ticketStatus(TicketStatus.CREATED).build());

					supportTicketService.save(
							SupportTicket.builder().owner(user_cartao3).ticketType(TicketType.CARTAO)
									.ticketStatus(TicketStatus.CREATED).build());

					supportTicketService.save(
							SupportTicket.builder().owner(user_emprestimo).ticketType(TicketType.EMPRESTIMO)
									.ticketStatus(TicketStatus.CREATED).build());

					supportTicketService.save(
							SupportTicket.builder().owner(user_outros).ticketType(TicketType.OUTROS)
									.ticketStatus(TicketStatus.CREATED).build());
				}
			}
		};
	}

}
