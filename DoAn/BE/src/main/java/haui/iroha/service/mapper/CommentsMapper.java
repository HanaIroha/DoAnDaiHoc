package haui.iroha.service.mapper;

import haui.iroha.domain.Comments;
import haui.iroha.service.dto.CommentsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Comments} and its DTO {@link CommentsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CommentsMapper extends EntityMapper<CommentsDTO, Comments> {}
