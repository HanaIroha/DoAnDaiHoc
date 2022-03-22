package haui.iroha.web.rest;

import static haui.iroha.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import haui.iroha.IntegrationTest;
import haui.iroha.domain.Comments;
import haui.iroha.repository.CommentsRepository;
import haui.iroha.service.dto.CommentsDTO;
import haui.iroha.service.mapper.CommentsMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CommentsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CommentsResourceIT {

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final Long DEFAULT_ID_PRODUCT = 1L;
    private static final Long UPDATED_ID_PRODUCT = 2L;

    private static final Long DEFAULT_ID_PARENT_COMMENT = 1L;
    private static final Long UPDATED_ID_PARENT_COMMENT = 2L;

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/comments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{idComment}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommentsMockMvc;

    private Comments comments;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comments createEntity(EntityManager em) {
        Comments comments = new Comments()
            .login(DEFAULT_LOGIN)
            .idProduct(DEFAULT_ID_PRODUCT)
            .idParentComment(DEFAULT_ID_PARENT_COMMENT)
            .content(DEFAULT_CONTENT)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return comments;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comments createUpdatedEntity(EntityManager em) {
        Comments comments = new Comments()
            .login(UPDATED_LOGIN)
            .idProduct(UPDATED_ID_PRODUCT)
            .idParentComment(UPDATED_ID_PARENT_COMMENT)
            .content(UPDATED_CONTENT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return comments;
    }

    @BeforeEach
    public void initTest() {
        comments = createEntity(em);
    }

    @Test
    @Transactional
    void createComments() throws Exception {
        int databaseSizeBeforeCreate = commentsRepository.findAll().size();
        // Create the Comments
        CommentsDTO commentsDTO = commentsMapper.toDto(comments);
        restCommentsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commentsDTO)))
            .andExpect(status().isCreated());

        // Validate the Comments in the database
        List<Comments> commentsList = commentsRepository.findAll();
        assertThat(commentsList).hasSize(databaseSizeBeforeCreate + 1);
        Comments testComments = commentsList.get(commentsList.size() - 1);
        assertThat(testComments.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testComments.getIdProduct()).isEqualTo(DEFAULT_ID_PRODUCT);
        assertThat(testComments.getIdParentComment()).isEqualTo(DEFAULT_ID_PARENT_COMMENT);
        assertThat(testComments.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testComments.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testComments.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void createCommentsWithExistingId() throws Exception {
        // Create the Comments with an existing ID
        comments.setIdComment(1L);
        CommentsDTO commentsDTO = commentsMapper.toDto(comments);

        int databaseSizeBeforeCreate = commentsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommentsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Comments in the database
        List<Comments> commentsList = commentsRepository.findAll();
        assertThat(commentsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllComments() throws Exception {
        // Initialize the database
        commentsRepository.saveAndFlush(comments);

        // Get all the commentsList
        restCommentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=idComment,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].idComment").value(hasItem(comments.getIdComment().intValue())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].idProduct").value(hasItem(DEFAULT_ID_PRODUCT.intValue())))
            .andExpect(jsonPath("$.[*].idParentComment").value(hasItem(DEFAULT_ID_PARENT_COMMENT.intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(sameInstant(DEFAULT_UPDATED_AT))));
    }

    @Test
    @Transactional
    void getComments() throws Exception {
        // Initialize the database
        commentsRepository.saveAndFlush(comments);

        // Get the comments
        restCommentsMockMvc
            .perform(get(ENTITY_API_URL_ID, comments.getIdComment()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.idComment").value(comments.getIdComment().intValue()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN))
            .andExpect(jsonPath("$.idProduct").value(DEFAULT_ID_PRODUCT.intValue()))
            .andExpect(jsonPath("$.idParentComment").value(DEFAULT_ID_PARENT_COMMENT.intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.updatedAt").value(sameInstant(DEFAULT_UPDATED_AT)));
    }

    @Test
    @Transactional
    void getNonExistingComments() throws Exception {
        // Get the comments
        restCommentsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewComments() throws Exception {
        // Initialize the database
        commentsRepository.saveAndFlush(comments);

        int databaseSizeBeforeUpdate = commentsRepository.findAll().size();

        // Update the comments
        Comments updatedComments = commentsRepository.findById(comments.getIdComment()).get();
        // Disconnect from session so that the updates on updatedComments are not directly saved in db
        em.detach(updatedComments);
        updatedComments
            .login(UPDATED_LOGIN)
            .idProduct(UPDATED_ID_PRODUCT)
            .idParentComment(UPDATED_ID_PARENT_COMMENT)
            .content(UPDATED_CONTENT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        CommentsDTO commentsDTO = commentsMapper.toDto(updatedComments);

        restCommentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, commentsDTO.getIdComment())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commentsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Comments in the database
        List<Comments> commentsList = commentsRepository.findAll();
        assertThat(commentsList).hasSize(databaseSizeBeforeUpdate);
        Comments testComments = commentsList.get(commentsList.size() - 1);
        assertThat(testComments.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testComments.getIdProduct()).isEqualTo(UPDATED_ID_PRODUCT);
        assertThat(testComments.getIdParentComment()).isEqualTo(UPDATED_ID_PARENT_COMMENT);
        assertThat(testComments.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testComments.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testComments.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingComments() throws Exception {
        int databaseSizeBeforeUpdate = commentsRepository.findAll().size();
        comments.setIdComment(count.incrementAndGet());

        // Create the Comments
        CommentsDTO commentsDTO = commentsMapper.toDto(comments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, commentsDTO.getIdComment())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Comments in the database
        List<Comments> commentsList = commentsRepository.findAll();
        assertThat(commentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchComments() throws Exception {
        int databaseSizeBeforeUpdate = commentsRepository.findAll().size();
        comments.setIdComment(count.incrementAndGet());

        // Create the Comments
        CommentsDTO commentsDTO = commentsMapper.toDto(comments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Comments in the database
        List<Comments> commentsList = commentsRepository.findAll();
        assertThat(commentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamComments() throws Exception {
        int databaseSizeBeforeUpdate = commentsRepository.findAll().size();
        comments.setIdComment(count.incrementAndGet());

        // Create the Comments
        CommentsDTO commentsDTO = commentsMapper.toDto(comments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommentsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commentsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Comments in the database
        List<Comments> commentsList = commentsRepository.findAll();
        assertThat(commentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCommentsWithPatch() throws Exception {
        // Initialize the database
        commentsRepository.saveAndFlush(comments);

        int databaseSizeBeforeUpdate = commentsRepository.findAll().size();

        // Update the comments using partial update
        Comments partialUpdatedComments = new Comments();
        partialUpdatedComments.setIdComment(comments.getIdComment());

        partialUpdatedComments.idProduct(UPDATED_ID_PRODUCT).content(UPDATED_CONTENT);

        restCommentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComments.getIdComment())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComments))
            )
            .andExpect(status().isOk());

        // Validate the Comments in the database
        List<Comments> commentsList = commentsRepository.findAll();
        assertThat(commentsList).hasSize(databaseSizeBeforeUpdate);
        Comments testComments = commentsList.get(commentsList.size() - 1);
        assertThat(testComments.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testComments.getIdProduct()).isEqualTo(UPDATED_ID_PRODUCT);
        assertThat(testComments.getIdParentComment()).isEqualTo(DEFAULT_ID_PARENT_COMMENT);
        assertThat(testComments.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testComments.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testComments.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateCommentsWithPatch() throws Exception {
        // Initialize the database
        commentsRepository.saveAndFlush(comments);

        int databaseSizeBeforeUpdate = commentsRepository.findAll().size();

        // Update the comments using partial update
        Comments partialUpdatedComments = new Comments();
        partialUpdatedComments.setIdComment(comments.getIdComment());

        partialUpdatedComments
            .login(UPDATED_LOGIN)
            .idProduct(UPDATED_ID_PRODUCT)
            .idParentComment(UPDATED_ID_PARENT_COMMENT)
            .content(UPDATED_CONTENT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restCommentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComments.getIdComment())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComments))
            )
            .andExpect(status().isOk());

        // Validate the Comments in the database
        List<Comments> commentsList = commentsRepository.findAll();
        assertThat(commentsList).hasSize(databaseSizeBeforeUpdate);
        Comments testComments = commentsList.get(commentsList.size() - 1);
        assertThat(testComments.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testComments.getIdProduct()).isEqualTo(UPDATED_ID_PRODUCT);
        assertThat(testComments.getIdParentComment()).isEqualTo(UPDATED_ID_PARENT_COMMENT);
        assertThat(testComments.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testComments.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testComments.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingComments() throws Exception {
        int databaseSizeBeforeUpdate = commentsRepository.findAll().size();
        comments.setIdComment(count.incrementAndGet());

        // Create the Comments
        CommentsDTO commentsDTO = commentsMapper.toDto(comments);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, commentsDTO.getIdComment())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Comments in the database
        List<Comments> commentsList = commentsRepository.findAll();
        assertThat(commentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchComments() throws Exception {
        int databaseSizeBeforeUpdate = commentsRepository.findAll().size();
        comments.setIdComment(count.incrementAndGet());

        // Create the Comments
        CommentsDTO commentsDTO = commentsMapper.toDto(comments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Comments in the database
        List<Comments> commentsList = commentsRepository.findAll();
        assertThat(commentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamComments() throws Exception {
        int databaseSizeBeforeUpdate = commentsRepository.findAll().size();
        comments.setIdComment(count.incrementAndGet());

        // Create the Comments
        CommentsDTO commentsDTO = commentsMapper.toDto(comments);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommentsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(commentsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Comments in the database
        List<Comments> commentsList = commentsRepository.findAll();
        assertThat(commentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteComments() throws Exception {
        // Initialize the database
        commentsRepository.saveAndFlush(comments);

        int databaseSizeBeforeDelete = commentsRepository.findAll().size();

        // Delete the comments
        restCommentsMockMvc
            .perform(delete(ENTITY_API_URL_ID, comments.getIdComment()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Comments> commentsList = commentsRepository.findAll();
        assertThat(commentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
