package com.bcknd.blogapp.articles;

import com.bcknd.blogapp.articles.dtos.CreateArticleRequest;
import com.bcknd.blogapp.articles.dtos.UpdateArticleRequest;
import com.bcknd.blogapp.users.UsersRepository;
import com.bcknd.blogapp.users.UsersService;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

@Service
public class ArticlesService {
    private ArticlesRepository articlesRepository;
    private UsersRepository usersRepository;

    public ArticlesService(ArticlesRepository articlesRepository, UsersRepository usersRepository) {
        this.articlesRepository = articlesRepository;
        this.usersRepository = usersRepository;
    }

    public Iterable<ArticleEntity> getAllArticles() {
        return articlesRepository.findAll();
    }

    public ArticleEntity getArticleBySlug(String slug) {
        var article = articlesRepository.findBySlug(slug);
        if (article == null) {
            throw new ArticleNotFoundException(slug);
        }
        return article;
    }

    public ArticleEntity createArticle(CreateArticleRequest a, Long authorId) {
        var author = usersRepository.findById(authorId).orElseThrow(() -> new UsersService.UserNotFoundException(authorId));

        return articlesRepository.save(ArticleEntity.builder()
                .title(a.getTitle())
                // TODO: create a proper slugification function
                .slug(a.getTitle().toLowerCase().replaceAll("\\s+", "-"))
                .body(a.getBody())
                .subtitle(a.getSubtitle())
                .author(author)
                .build()
        );
    }

    public ArticleEntity updateArticle(Long articleId, UpdateArticleRequest a) {
        var article = articlesRepository.findById(articleId).orElseThrow(() -> new ArticleNotFoundException(articleId));

        if (a.getTitle() != null) {
            article.setTitle(a.getTitle());
            article.setSlug(a.getTitle().toLowerCase().replaceAll("\\s+", "-"));
        }

        if (a.getBody() != null) {
            article.setBody(a.getBody());
        }

        if (a.getSubtitle() != null) {
            article.setSubtitle(a.getSubtitle());
        }

        return articlesRepository.save(article);
    }


    static class ArticleNotFoundException extends IllegalArgumentException {
        public ArticleNotFoundException(Long articleId) {
            super("Article with id: "+ articleId + " not found.");
        }    public ArticleNotFoundException(String slug) {
            super("Article "+ slug + " not found.");
        }
    }

}
