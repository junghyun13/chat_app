package com.ll.chatApp.domain.article.article.service;
import com.ll.chatApp.domain.article.article.entity.Article;
import com.ll.chatApp.domain.article.article.repository.ArticleRepository;
import com.ll.chatApp.domain.article.articleComment.entity.ArticleComment;
import com.ll.chatApp.domain.member.member.entity.Member;
import com.ll.chatApp.global.rsData.RsData;
//import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    @Transactional
    public RsData<Article> write(Long memberId, String title, String content) {
        Article article = Article.builder()
                .author(Member.builder().id(memberId).build())
                .title(title)
                .content(content)
                .build();
        articleRepository.save(article);
        return RsData.of("200", "글 작성 성공", article);
    }

    public Optional<Article> findById(Long id) {
        return articleRepository.findById(id);
    }

    @Transactional
    public void modify(Article article, String title, String content) {
        article.setTitle(title);
        article.setContent(content);
        //articleRepository.save(article); Transcational 처리하면 save하지 않아도 알아서 Update쿼리가 날라감 (Dirty Checking)
    }

    @Transactional
    public void modifyComment(ArticleComment comment, String commentBody) {
        comment.setBody(commentBody);
    }
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Page<Article> search(List<String> kwTypes, String kw, Pageable pageable) {


        return articleRepository.search(kwTypes, kw, pageable);
    }
}