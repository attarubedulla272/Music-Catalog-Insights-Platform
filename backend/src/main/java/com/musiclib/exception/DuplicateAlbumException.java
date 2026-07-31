package com.musiclib.exception;

public class DuplicateAlbumException extends RuntimeException {
    public DuplicateAlbumException(String message) {
        super(message);
    }
}
