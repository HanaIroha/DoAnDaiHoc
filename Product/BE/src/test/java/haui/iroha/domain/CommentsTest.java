package haui.iroha.domain;

import static org.assertj.core.api.Assertions.assertThat;

import haui.iroha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommentsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comments.class);
        Comments comments1 = new Comments();
        comments1.setIdComment(1L);
        Comments comments2 = new Comments();
        comments2.setIdComment(comments1.getIdComment());
        assertThat(comments1).isEqualTo(comments2);
        comments2.setIdComment(2L);
        assertThat(comments1).isNotEqualTo(comments2);
        comments1.setIdComment(null);
        assertThat(comments1).isNotEqualTo(comments2);
    }
}
