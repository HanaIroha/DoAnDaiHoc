package haui.iroha.service.mapper;

import haui.iroha.domain.OrderDetails;
import haui.iroha.service.dto.OrderDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderDetails} and its DTO {@link OrderDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrderDetailsMapper extends EntityMapper<OrderDetailsDTO, OrderDetails> {}
