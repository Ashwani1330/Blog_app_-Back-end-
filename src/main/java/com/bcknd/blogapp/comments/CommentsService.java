package com.bcknd.blogapp.comments;

import com.bcknd.blogapp.users.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class CommentsService {
    private CommentsRepository commentsRepository;

    public CommentsService(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }


}