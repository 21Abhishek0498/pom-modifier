package com.falcon.pommodifierservice.parser;

import com.falcon.pommodifierservice.dto.Method;
import com.falcon.pommodifierservice.dto.TestClassBuilder;
import com.github.javaparser.ast.CompilationUnit;

import java.io.IOException;
import java.util.List;

public interface ParseFile {
    TestClassBuilder startParsing(String fileName) throws IOException;

    List<Method> getAllMethodOfSourceClass(CompilationUnit cu);

    CompilationUnit getCompilationUnit(String fileName) throws IOException;
}
