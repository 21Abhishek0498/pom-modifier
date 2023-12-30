package com.falcon.pommodifierservice.generator;


import com.falcon.pommodifierservice.dto.TestClassBuilder;

import java.io.IOException;
import java.util.List;

public interface Generator {
    List<TestClassBuilder> generate(String sourceCodePath) throws IOException;
}
