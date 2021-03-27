package com.example.autoforum.post;

import com.example.autoforum.category.Category;
import com.example.autoforum.favorite.Favorite;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private Post userId;

    @OneToMany(mappedBy="postId")
    private Set<Favorite> favorites;

    @ManyToOne
    @JoinColumn(name="category_id", nullable = false)
    private Category categoryId;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
