package com.example.autoforum.favorite;

import com.example.autoforum.post.Post;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "favorite")

public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = false)
    private int id;

    @JoinColumn(name="user_id", nullable = false)
    private int userId;

    @JoinColumn(name = "post_id", nullable = false)
    private int postId;



    public Favorite() {
    }

}
