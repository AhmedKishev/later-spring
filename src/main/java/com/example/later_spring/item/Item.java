package com.example.later_spring.item;

import com.example.later_spring.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@Table(name = "items", schema = "public")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User user;

    @Column
    private String url;

    @Column(name = "resolved_url")
    private String resolvedUrl;

    @Column(name = "mime_type")
    private String mimeType;

    private String title;

    @Column(name = "has_image")
    private boolean hasImage;

    @Column(name = "has_video")
    private boolean hasVideo;

    private boolean unread = true;

    @Column(name = "date_resolved")
    private Instant dateResolved;

    @ElementCollection
    @CollectionTable(name = "tags", joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "name")
    private Set<String> tags = new HashSet<>();
}