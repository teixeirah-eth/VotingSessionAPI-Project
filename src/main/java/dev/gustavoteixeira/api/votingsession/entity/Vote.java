package dev.gustavoteixeira.api.votingsession.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "votes")
@Getter
@Setter
@Builder
public class Vote {

    @Id
    private String id;
    private String associate;
    private String choice;
    private String agendaId;

}
