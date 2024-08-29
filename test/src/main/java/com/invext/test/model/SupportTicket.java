package com.invext.test.model;

import com.invext.test.dto.SupportTicketDto;
import com.invext.test.enums.TicketStatus;
import com.invext.test.enums.TicketType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "support_tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SupportTicket extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@JoinColumn(name = "owner_id")
	@ManyToOne
	private User owner;

	@Enumerated(EnumType.STRING)
	private TicketType ticketType = TicketType.OUTROS;

	@Enumerated(EnumType.STRING)
	private TicketStatus ticketStatus = TicketStatus.CREATED;

	public SupportTicket(SupportTicketDto dao) {
		this.owner = dao.owner();
		this.ticketType = dao.ticketType();
	}

}
