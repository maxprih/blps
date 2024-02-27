package org.maxpri.blps.service;

import org.maxpri.blps.exception.ArticleNotFoundException;
import org.maxpri.blps.exception.ImageNotFoundException;
import org.maxpri.blps.repository.imageRepository.ImageRepository;
import org.maxpri.blps.model.entity.imageEntity.ArticleImage;
import org.maxpri.blps.repository.articleRepository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author max_pri
 */
@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository, ArticleRepository articleRepository) {
        this.imageRepository = imageRepository;
        this.articleRepository = articleRepository;
    }

    @Transactional(transactionManager = "myTransactionManager")
    public Long addImageToArticle(MultipartFile multipartFile, Long articleId) throws IOException {
        if (!articleRepository.existsById(articleId)) {
            throw new ArticleNotFoundException(articleId);
        }

        ArticleImage articleImage = ArticleImage.builder()
                .image(multipartFile.getBytes())
                .articleId(articleId)
                .build();
        return imageRepository.save(articleImage).getId();
    }

    public List<Long> getImagesForArticle(Long articleId) {
        return imageRepository.findAllByArticleId(articleId)
                .stream()
                .map(ArticleImage::getId)
                .collect(Collectors.toList());
    }

    public byte[] getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ImageNotFoundException(id))
                .getImage();
    }
}
