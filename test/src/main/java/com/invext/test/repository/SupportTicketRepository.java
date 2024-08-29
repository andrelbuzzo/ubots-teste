package com.invext.test.repository;

import com.invext.test.enums.TicketStatus;
import com.invext.test.enums.TicketType;
import com.invext.test.model.SupportTicket;
import com.invext.test.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {

	List<SupportTicket> findByTicketStatus(TicketStatus status);

	List<SupportTicket> findByTicketStatusAndOwnerIsNull(TicketStatus status);

	List<SupportTicket> findByTicketType(TicketType type);

	List<SupportTicket> findAllByOwner(User owner);

}
