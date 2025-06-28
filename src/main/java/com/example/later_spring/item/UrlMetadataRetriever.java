package com.example.later_spring.item;

import java.time.Instant;

public interface UrlMetadataRetriever {
    interface UrlMetadata {
        String getNormalUrl();

        String getResolvedUrl();

        String getMimeType();

        String getTitle();

        boolean isHasImage();

        boolean isHasVideo();

        Instant getDateResolved();
    }

    public UrlMetadata retrieve(String urlString);
}
