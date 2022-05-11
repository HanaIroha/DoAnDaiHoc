package haui.iroha.service.mapper;

import haui.iroha.domain.ProductDetails;
import haui.iroha.service.dto.ProductDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductDetails} and its DTO {@link ProductDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductDetailsMapper extends EntityMapper<ProductDetailsDTO, ProductDetails> {}
