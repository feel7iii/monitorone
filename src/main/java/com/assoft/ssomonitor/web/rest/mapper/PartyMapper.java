package com.assoft.ssomonitor.web.rest.mapper;

import com.assoft.ssomonitor.domain.Party;
import com.assoft.ssomonitor.web.rest.dto.PartyDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {})
public interface PartyMapper {

    PartyDTO cooToCooDTO(Party coo);

    List<PartyDTO> coosToCooDTOs(List<Party> coos);

    Party cooDTOToCoo(PartyDTO cooDTO);

    List<Party> cooDTOsToCoos(List<PartyDTO> cooDTOs);
}
