package haui.iroha.service.mapper;

import haui.iroha.domain.Banners;
import haui.iroha.service.dto.BannersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Banners} and its DTO {@link BannersDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BannersMapper extends EntityMapper<BannersDTO, Banners> {}
