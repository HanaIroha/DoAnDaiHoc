package haui.iroha.service.mapper;

import haui.iroha.domain.Images;
import haui.iroha.service.dto.ImagesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Images} and its DTO {@link ImagesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ImagesMapper extends EntityMapper<ImagesDTO, Images> {}
