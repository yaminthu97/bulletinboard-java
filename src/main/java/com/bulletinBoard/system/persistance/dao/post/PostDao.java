package com.bulletinBoard.system.persistance.dao.post;

import java.util.List;

import com.bulletinBoard.system.persistance.entity.Post;

/**
 * <h2>PostDao Class</h2>
 * <p>
 * Process for Displaying PostDao
 * </p>
 * 
 * @author 
 *
 */
public interface PostDao {

    /**
     * <h2>dbInsertPost</h2>
     * <p>
     * Insert Post
     * </p>
     *
     * @param post Post
     */
    public void dbInsertPost(Post post);

    /**
     * <h2>dbGetPosts</h2>
     * <p>
     * Get All Post
     * </p>
     *
     * @param offset int
     * @param limit  int
     * @return List<Post>
     */
    public List<Post> dbGetPosts(int offset, int limit);

    /**
     * <h2>dbGetUserPosts</h2>
     * <p>
     * Get User Posts
     * </p>
     *
     * @param offset             int
     * @param limit              int
     * @param userId             int
     * @param includePublicPosts boolean
     * @return List<Post>
     */
    public List<Post> dbGetUserPosts(int offset, int limit, int userId, boolean includePublicPosts);

    /**
     * <h2>dbGetPostByActiveStatus</h2>
     * <p>
     * Get Active Posts
     * </p>
     *
     * @return List<Post>
     */
    public List<Post> dbGetPostsByActiveStatus(int offset, int limit);

    /**
     * <h2>dbGetPostsByTitle</h2>
     * <p>
     * Get Posts By Title
     * </p>
     *
     * @param title String
     * @return List<Post>
     */
    public List<Post> dbGetPostsByTitle(String title);

    /**
     * <h2>dbGetPublicPostsByTitleAndAuthorName</h2>
     * <p>
     * Get Public Posts By Title and Author Name
     * </p>
     *
     * @param offset     int
     * @param limit      int
     * @param title      String
     * @param authorName String
     * @return List<Post>
     */
    public List<Post> dbGetPublicPostsByTitleAndAuthorName(int offset, int limit, String title, String authorName);

    /**
     * <h2>dbGetPostById</h2>
     * <p>
     * Get Post By ID
     * </p>
     *
     * @param id
     * @return Post
     */
    public Post dbGetPostById(int id);

    /**
     * <h2>getCount</h2>
     * <p>
     * Get Total Number of Post
     * </p>
     *
     * @return int
     */
    public int dbGetPostCount();

    /**
     * <h2>dbGetPublicPostsByTitleAndAuthorName</h2>
     * <p>
     * Get Total Number of Public Posts by Title and Author's Name
     * </p>
     *
     * @param postTitle  String
     * @param authorName String
     * @return int
     */
    public int dbGetPublicPostsByTitleAndAuthorName(String postTitle, String authorName);

    /**
     * <h2>dbGetUsesrPostCount</h2>
     * <p>
     * Get Total Number of User Post including Public Posts
     * </p>
     *
     * @param includePublicPosts boolean
     * @return int
     */
    public int dbGetUserPostCount(int userId, boolean includePublicPosts);

    /**
     * <h2>dbGetPostCountByActiveStatus</h2>
     * <p>
     * Get Total Number of Active Post
     * </p>
     *
     * @return int
     */
    public int dbGetPostCountByActiveStatus();

    /**
     * <h2>dbUpdatePost</h2>
     * <p>
     * Update Post
     * </p>
     *
     * @param post Post
     */
    public void dbUpdatePost(Post post);

    /**
     * <h2>dbDeletePost</h2>
     * <p>
     * Delete Post By ID
     * </p>
     *
     * @param id int
     */
    public void dbDeletePost(int id);
}
