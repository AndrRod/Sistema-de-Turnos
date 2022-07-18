package com.turnosRegistro.shift.record.config;

import com.turnosRegistro.shift.record.formsAndResponses.MessagePagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@Component
public class PaginationMessageHandler {
    @Autowired
    private MessageHandler messageHandler;
    public MessagePagination message(Page page, List<Object> dtoPageList, HttpServletRequest request){
        List <Object> content = dtoPageList;
        String nextPath = null;
        String prevPath = null;
        if(!page.isLast()) nextPath= request.getRequestURL().toString() + "?page="+(page.getNumber()+1);
        if(!page.isFirst()) prevPath = request.getRequestURL().toString() +"?page=" + (page.getNumber()-1);
        if(page.getContent().isEmpty()) content = Collections.singletonList(messageHandler.message("page.empty", null));
        return new MessagePagination(content, HttpStatus.OK.value(), nextPath, prevPath);
    }
}
