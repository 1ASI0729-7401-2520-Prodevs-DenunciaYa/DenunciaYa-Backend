package com.denunciayabackend.community.domain.services;

import com.denunciayabackend.community.domain.model.commands.CreateCommentCommand;
import com.denunciayabackend.community.domain.model.commands.DeleteCommentCommand;

public interface CommentCommandService {
    Long handle(CreateCommentCommand command);
    void handle(DeleteCommentCommand command);
}
