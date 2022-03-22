package haui.iroha.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import haui.iroha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommentsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommentsDTO.class);
        CommentsDTO commentsDTO1 = new CommentsDTO();
        commentsDTO1.setIdComment(1L);
        CommentsDTO commentsDTO2 = new CommentsDTO();
        assertThat(commentsDTO1).isNotEqualTo(commentsDTO2);
        commentsDTO2.setIdComment(commentsDTO1.getIdComment());
        assertThat(commentsDTO1).isEqualTo(commentsDTO2);
        commentsDTO2.setIdComment(2L);
        assertThat(commentsDTO1).isNotEqualTo(commentsDTO2);
        commentsDTO1.setIdComment(null);
        assertThat(commentsDTO1).isNotEqualTo(commentsDTO2);
    }
}
