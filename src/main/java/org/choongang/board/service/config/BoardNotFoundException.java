package org.choongang.board.service.config;

import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.AlertException;
import org.springframework.http.HttpStatus;

public class BoardNotFoundException extends AlertException {

    BoardNotFoundException(){
        super(Utils.getMessage("NotFound.board", "errors"), HttpStatus.NOT_FOUND);
    }
}
