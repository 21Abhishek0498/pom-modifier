package com.falcon.pommodifierservice.validator;


import com.falcon.pommodifierservice.exceptions.PathDoesNotExistsException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SourcePathValidator
{
    /**
     * @param sourceCodePath
     */

    public void validate(String sourceCodePath) {
        Path codePath = Paths.get(sourceCodePath);
        if(!Files.exists(codePath))
            throw new PathDoesNotExistsException(sourceCodePath);
    }
}
