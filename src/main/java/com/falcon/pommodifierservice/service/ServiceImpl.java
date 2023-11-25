package com.falcon.pommodifierservice.service;


import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.apache.maven.shared.invoker.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@Service
public class ServiceImpl {


    public Map<String, String> getJavaVersionAndSpringVersion(String pathOfFile) throws Exception {
        Model model = readModel(pathOfFile);
        Map<String, String> dependencies = new HashMap<>();
//      dependencies.put("Project",model.toString());
        dependencies.put("Java Version", model.getProperties().getProperty("java.version"));
        dependencies.put("Spring Boot Version", model.getParent().getVersion());
        return dependencies;
    }

    private Model readModel(String path) throws Exception {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        return reader.read(new FileReader(path));
    }

    public void checkAndAddDependencies(){


    }


    public boolean isDependencyPresent(String groupId, String artifactId,String path) throws Exception {
        return isDependencyPresentInPom(readModel(path), groupId, artifactId);
    }

    public void addDependencyToPom(String groupId, String artifactId, String version,String path) throws Exception {
        addDependencyToModel(readModel(path), groupId, artifactId, version);
        writeModel(readModel(path), path);
    }

    public void mavenBuild(String path) throws Exception {
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(new File(path, "pom.xml"));
        request.setGoals(Arrays.asList("clean", "install")); // Specify Maven goals

        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(new File(System.getenv("M2_HOME"))); // Set Maven home directory

        InvocationResult result = invoker.execute(request);

        if (result.getExitCode() != 0) {
            // Maven build failed
            throw new MavenInvocationException("Maven build failed with exit code: " + result.getExitCode(), result.getExecutionException());
        } else {
            // Maven build succeeded
            System.out.println("Maven build completed successfully.");
        }
    }





    private void writeModel(Model model, String path) throws Exception {
        MavenXpp3Writer writer = new MavenXpp3Writer();
        writer.write(new FileWriter(path), model);
    }

    private boolean isDependencyPresentInPom(Model model, String groupId, String artifactId) {
        for (Dependency dependency : model.getDependencies()) {
            if (groupId.equals(dependency.getGroupId()) && artifactId.equals(dependency.getArtifactId())) {
                return true;
            }
        }
        return false;
    }

    private void addDependencyToModel(Model model, String groupId, String artifactId, String scope) {
        Dependency newDependency = new Dependency();
        newDependency.setGroupId(groupId);
        newDependency.setArtifactId(artifactId);
        newDependency.setScope(scope);

        model.addDependency(newDependency);
    }
}



