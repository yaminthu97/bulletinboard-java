package com.bulletinBoard.system.persistance.dao.post.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bulletinBoard.system.persistance.dao.post.PostDao;
import com.bulletinBoard.system.persistance.entity.Post;

/**
 * <h2>PostDaoImpl Class</h2>
 * <p>
 * Implementation Class of PostDao
 * </p>
 * 
 * @author 
 *
 */
@Repository
@Transactional
public class PostDaoImpl implements PostDao {

    /**
     * <h2>TABLE_NAME</h2>
     * <p>
     * Table Name of Post Entity
     * </p>
     */
    private static final String TABLE_NAME = "Post";

    /**
     * <h2>SELECT_STMT</h2>
     * <p>
     * Common SELECT Statement of Post Entity
     * </p>
     */
    private static final String SELECT_STMT = "FROM " + TABLE_NAME;

    /**
     * <h2>COUNT_STMT</h2>
     * <p>
     * Common Statement of count
     * </p>
     */
    private static final String COUNT_STMT = "SELECT Count(id) FROM " + TABLE_NAME;

    /**
     * <h2>sessionFactory</h2>
     * <p>
     * SessionFactory To Create Session For Database
     * </p>
     */
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * <h2>insert</h2>
     * <p>
     * Insert Post
     * </p>
     * 
     * @param post PostForm
     */
    @Override
    public void dbInsertPost(Post post) {
        this.getSession().save(post);
    }

    /**
     * <h2>getAll</h2>
     * <p>
     * Get All Posts
     * </p>
     * 
     * @param offset int
     * @param limit  int
     * @return List<Post>
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Post> dbGetPosts(int offset, int limit) {
        StringBuilder stmt = new StringBuilder(SELECT_STMT);
        stmt.append(" WHERE isActive = true");
        stmt.append(" ORDER BY created_at DESC");
        return this.getSession().createQuery(stmt.toString()).setFirstResult(offset).setMaxResults(limit).list();
    }

    /**
     * <h2>dbGetUserPosts</h2>
     * <p>
     * Get User's Posts
     * </p>
     * 
     * @param offset int
     * @param limit  int
     * @param userId int
     * @return List<Post>
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Post> dbGetUserPosts(int offset, int limit, int userId, boolean includePublicPosts) {
        StringBuilder stmt = new StringBuilder(SELECT_STMT);
        stmt.append(" WHERE user_Id = :userId");
        if (includePublicPosts) {
            stmt.append(" OR isActive = true");
        }
        stmt.append(" ORDER BY created_at DESC");
        return this.getSession().createQuery(stmt.toString()).setParameter("userId", userId).setFirstResult(offset)
                .setMaxResults(limit).list();
    }

    /**
     * <h2>getByActiveStatus</h2>
     * <p>
     * Get Active Posts
     * </p>
     * 
     * @return List<Post>
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Post> dbGetPostsByActiveStatus(int offset, int limit) {
        StringBuilder stmt = new StringBuilder(SELECT_STMT);
        stmt.append(" WHERE isActive = true");
        stmt.append(" ORDER BY created_at DESC");
        return this.getSession().createQuery(stmt.toString()).setFirstResult(offset).setMaxResults(limit).list();
    }

    /**
     * <h2>getByTitle</h2>
     * <p>
     * Get Posts By Title
     * </p>
     *
     * @param title String
     * @return List<Post>
     */
    @SuppressWarnings("unchecked")
    public List<Post> dbGetPostsByTitle(String title) {
        StringBuilder stmt = new StringBuilder(SELECT_STMT);
        stmt.append(" WHERE title = :title");
        stmt.append(" ORDER BY id");
        return this.getSession().createQuery(stmt.toString()).setParameter("title", title).list();
    }

    /**
     * <h2>dbGetPublicPostsByTitleAndAuthorName</h2>
     * <p>
     * Get Public Posts by Title and Author Name
     * </p>
     * 
     * @param offset     int
     * @param limit      int
     * @param title      String
     * @param authorName String
     * @return List<Post>
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Post> dbGetPublicPostsByTitleAndAuthorName(int offset, int limit, String title, String authorName) {
        StringBuilder stmt = new StringBuilder(SELECT_STMT);
        stmt.append(" AS P");
        stmt.append(" LEFT JOIN FETCH P.user AS U");
        stmt.append(" WHERE P.title LIKE :postTitle");
        stmt.append(" AND (U.name LIKE :authorName OR U.email LIKE :authorName)");
        stmt.append(" AND P.isActive = " + true);
        stmt.append(" ORDER BY P.created_at DESC");
        return this.getSession().createQuery(stmt.toString()).setParameter("postTitle", "%" + title + "%")
                .setParameter("authorName", "%" + authorName + "%").setFirstResult(offset).setMaxResults(limit).list();
    }

    /**
     * <h2>dbGetPostById</h2>
     * <p>
     * Get Post By ID
     * </p>
     *
     * @param id
     * @return Post
     */
    @Override
    public Post dbGetPostById(int id) {
        return this.getSession().get(Post.class, id);
    }

    /**
     * <h2>getCount</h2>
     * <p>
     * Get Number Of All Posts
     * </p>
     * 
     * @return int
     */
    @Override
    public int dbGetPostCount() {
        Long count = (Long) this.getSession().createQuery(COUNT_STMT).uniqueResult();
        return count.intValue();
    }

    /**
     * <h2>dbGetPublicPostsByTitleAndAuthorName</h2>
     * <p>
     * Get Public Posts by Title and Author Name
     * </p>
     * 
     * @param postTitle  String
     * @param authorName String
     * @return int
     */
    @Override
    public int dbGetPublicPostsByTitleAndAuthorName(String postTitle, String authorName) {
        StringBuilder stmt = new StringBuilder("SELECT Count(P.id) FROM " + TABLE_NAME);
        stmt.append(" AS P");
        stmt.append(" LEFT OUTER JOIN P.user AS U");
        stmt.append(" WHERE P.title LIKE :postTitle");
        stmt.append(" AND (U.name LIKE :authorName OR U.email LIKE :authorName)");
        stmt.append(" AND isActive = " + true);
        stmt.append(" ORDER BY P.created_at DESC");
        Long count = (Long) this.getSession().createQuery(stmt.toString())
                .setParameter("postTitle", "%" + postTitle + "%").setParameter("authorName", "%" + authorName + "%")
                .uniqueResult();
        return count.intValue();
    }

    /**
     * <h2>dbGetUserPostCount</h2>
     * <p>
     * Get User Post Count
     * </p>
     * 
     * @param includePublicPosts
     * @return
     */
    @Override
    public int dbGetUserPostCount(int userId, boolean includePublicPosts) {
        StringBuilder stmt = new StringBuilder(COUNT_STMT);
        stmt.append(" WHERE user_id = :userId");
        if (includePublicPosts) {
            stmt.append(" OR isActive = true");
        }
        return ((Long) this.getSession().createQuery(stmt.toString()).setParameter("userId", userId).uniqueResult())
                .intValue();
    }

    /**
     * <h2>dbGetPostCountByActiveStatus</h2>
     * <p>
     * Get Total Number of Active Post
     * </p>
     * 
     * @return
     */
    @Override
    public int dbGetPostCountByActiveStatus() {
        StringBuilder stmt = new StringBuilder(COUNT_STMT);
        stmt.append(" WHERE isActive = true");
        Long count = (Long) this.getSession().createQuery(stmt.toString()).uniqueResult();
        return count.intValue();
    }

    /**
     * <h2>update</h2>
     * <p>
     * Update Post
     * </p>
     * 
     * @param post PostForm
     */
    @Override
    public void dbUpdatePost(Post post) {
        Post oldPost = this.dbGetPostById(post.getId());
        if (post.getUser() == null) {
            post.setUser(oldPost.getUser());
        }
        this.getSession().merge(post);
    }

    /**
     * <h2>delete</h2>
     * <p>
     * Delete Post By ID
     * </p>
     * 
     */
    @Override
    public void dbDeletePost(int id) {
        Post post = this.dbGetPostById(id);
        this.getSession().delete(post);
    }

    /**
     * <h2>getSession</h2>
     * <p>
     * Get Current Session
     * </p>
     *
     * @return Session
     */
    private Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }
}
