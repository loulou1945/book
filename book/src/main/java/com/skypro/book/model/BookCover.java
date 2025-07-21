package com.skypro.book.model;

import jakarta.persistence.*;

@Entity
public class BookCover {
    @Id
    @GeneratedValue
    private long id;

    private String filePath; //путь файла
    private long fileSize; // размер файла

    private String mediaType; //тип файла

    @Lob
    private byte[] preview; //обложка уменьшенная в размере, будет хранится в БД

    @OneToOne
    private Book book;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public byte[] getPreview() {
        return preview;
    }

    public void setPreview(byte[] preview) {
        this.preview = preview;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
