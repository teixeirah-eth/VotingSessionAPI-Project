package dev.gustavoteixeira.api.votingsession.service.agenda;

import dev.gustavoteixeira.api.votingsession.dto.request.AgendaRequestDTO;
import dev.gustavoteixeira.api.votingsession.entity.Agenda;
import dev.gustavoteixeira.api.votingsession.exception.AgendaAlreadyExistsException;
import dev.gustavoteixeira.api.votingsession.repository.AgendaRepository;
import dev.gustavoteixeira.api.votingsession.service.AgendaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CreateAgendaTest {

    public static final String AGENDA_NAME = "Rising gasoline tax by 3%";
    public static final int AGENDA_DURATION = 60;

    @Autowired
    private AgendaService agendaService;

    @MockBean
    private AgendaRepository agendaRepository;

    @Test
    void createNewAgenda() {
        when(agendaRepository.insert(any(Agenda.class))).thenReturn(Agenda.builder().build());
        agendaService.createAgenda(getAgendaDTO());
        verify(agendaRepository, times(1)).insert(any(Agenda.class));
    }

    @Test
    void createNewAgendaWithNameThatAlreadyExistsShouldReturnAgendaAlreadyExistsException() {
        doThrow(DuplicateKeyException.class).when(agendaRepository).insert(any(Agenda.class));

        RuntimeException exception = Assertions.assertThrows(AgendaAlreadyExistsException.class,
                () -> agendaService.createAgenda(getAgendaDTO()));

        assertThat(exception).isInstanceOf(AgendaAlreadyExistsException.class);
    }

    private AgendaRequestDTO getAgendaDTO() {
        return AgendaRequestDTO.builder()
                .name(AGENDA_NAME)
                .duration(AGENDA_DURATION)
                .build();
    }

}
