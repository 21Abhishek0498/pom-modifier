package com.falcon.pommodifierservice.service;


import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;


@Service
public class ServiceImpl {


    public String readPomFile(String pathOfFile) throws Exception {
        Model model = readModel(pathOfFile);
        return model.toString();
    }


    public String getJavaVersion(String pathOfFile) throws Exception {
        Model model = readModel(pathOfFile);
        return model.getProperties().getProperty("java.version");
    }

    public String getSpringBootVersion(String pathOfFile) throws Exception {
        Model model = readModel(pathOfFile);
        return model.getParent().getVersion();
    }

    private Model readModel(String path) throws Exception {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        return reader.read(new FileReader(path));
    }
}


