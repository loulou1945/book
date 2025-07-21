package com.skypro.book.service;

import com.skypro.book.model.Book;
import com.skypro.book.model.BookCover;
import com.skypro.book.repositories.BookCoverRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class BookCoverService {

    @Value("${books.cover.dir.path}")
    private String coversDir;

    private final BookService bookService;
    private final BookCoverRepository bookCoverRepository;

    public BookCoverService(BookService bookService, BookCoverRepository bookCoverRepository) {
        this.bookService = bookService;
        this.bookCoverRepository = bookCoverRepository;
    }

    public void uploadCover(Long bookId, MultipartFile file) throws IOException {
        Book book = bookService.findBook(bookId);

        Path filePath = Path.of(coversDir, bookId + "." + getExtensions(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }

        BookCover bookCover = findBookCover(bookId);
        bookCover.setBook(book);
        bookCover.setFilePath(filePath.toString());
        bookCover.setFileSize(file.getSize());
        bookCover.setMediaType(file.getContentType());
        bookCover.setPreview(generateImagePreview(filePath));

        bookCoverRepository.save(bookCover);
    }

    private byte[] generateImagePreview(Path filePath) throws IOException {
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int heigh = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, heigh, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, heigh, null);
            graphics.dispose();

            ImageIO.write(preview, getExtensions(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }

    public BookCover findBookCover(Long bookId) {
        return bookCoverRepository.findByBookId(bookId).orElse(new BookCover());
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
