package haui.iroha.service.mapper;

import haui.iroha.domain.Orders;
import haui.iroha.service.dto.OrdersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Orders} and its DTO {@link OrdersDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrdersMapper extends EntityMapper<OrdersDTO, Orders> {}
