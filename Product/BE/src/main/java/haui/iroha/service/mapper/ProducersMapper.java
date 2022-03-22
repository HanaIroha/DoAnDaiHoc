package haui.iroha.service.mapper;

import haui.iroha.domain.Producers;
import haui.iroha.service.dto.ProducersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Producers} and its DTO {@link ProducersDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProducersMapper extends EntityMapper<ProducersDTO, Producers> {}
