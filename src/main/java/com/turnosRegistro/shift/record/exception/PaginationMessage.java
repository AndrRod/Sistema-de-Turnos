package com.turnosRegistro.shift.record.exception;

import com.turnosRegistro.shift.record.config.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@Component
public class PaginationMessage {
    @Autowired
    private MessageHandler messageHandler;
    public MessagePagination message(Page page, List<Object> dtoPageList){
        List <Object> content = dtoPageList;
        String nextPath = null;
        String prevPath = null;
        if(!page.isLast()) nextPath= "?page=" + (page.getNumber()+1);
        if(!page.isFirst()) prevPath= "?page=" + (page.getNumber()-1);
        if(page.getContent().isEmpty()) content = Collections.singletonList(messageHandler.message("page.empty", null));
        return new MessagePagination(content, HttpStatus.OK.value(), nextPath, prevPath);
    }
}
