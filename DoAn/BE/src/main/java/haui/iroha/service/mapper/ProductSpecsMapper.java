package haui.iroha.service.mapper;

import haui.iroha.domain.ProductSpecs;
import haui.iroha.service.dto.ProductSpecsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductSpecs} and its DTO {@link ProductSpecsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductSpecsMapper extends EntityMapper<ProductSpecsDTO, ProductSpecs> {}
