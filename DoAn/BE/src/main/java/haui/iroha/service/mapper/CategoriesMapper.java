package haui.iroha.service.mapper;

import haui.iroha.domain.Categories;
import haui.iroha.service.dto.CategoriesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Categories} and its DTO {@link CategoriesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoriesMapper extends EntityMapper<CategoriesDTO, Categories> {}
