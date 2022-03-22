package haui.iroha.service.mapper;

import haui.iroha.domain.Payments;
import haui.iroha.service.dto.PaymentsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Payments} and its DTO {@link PaymentsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PaymentsMapper extends EntityMapper<PaymentsDTO, Payments> {}
