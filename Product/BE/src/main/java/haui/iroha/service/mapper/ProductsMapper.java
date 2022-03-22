package haui.iroha.service.mapper;

import haui.iroha.domain.Products;
import haui.iroha.service.dto.ProductsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Products} and its DTO {@link ProductsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductsMapper extends EntityMapper<ProductsDTO, Products> {}
