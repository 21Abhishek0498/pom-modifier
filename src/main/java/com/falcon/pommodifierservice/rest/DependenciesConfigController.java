package com.falcon.pommodifierservice.rest;

import com.falcon.pommodifierservice.constants.Constants;
import com.falcon.pommodifierservice.scanner.ClassScannerServiceImpl;
import com.falcon.pommodifierservice.service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/autoJ")
public class DependenciesConfigController {

    @Autowired
    private ServiceImpl serviceImpl;
    @Autowired
    private ClassScannerServiceImpl classScannerService;

    @RequestMapping("/home")
    public String home(Model model) {
        // Add the 'time' attribute to the model
        model.addAttribute("time", LocalDateTime.now());

        // Return the view name
        return "home";
    }

    @GetMapping("/pom-path")
    public String getDependencies(@RequestParam(name = "pathToPom", required = true) String pathToPom, Model model) {
        try {
            model.addAttribute("result", serviceImpl.getJavaVersionAndSpringVersion(pathToPom));
            return "result"; // Assuming you have a Thymeleaf template named "result.html"
        } catch (Exception e) {
            model.addAttribute("error", "Error processing POM file: " + e.getMessage());
            return "error"; // Assuming you have a Thymeleaf template named "error.html"
        }
    }

    @GetMapping(value = "/check-and-add")
    public ResponseEntity<String> checkAndAddDependency(@RequestParam(name = "pathToPom", required = true) String pathToPom) {
        try {
            return new ResponseEntity<>(serviceImpl.checkAndAdd(pathToPom), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error processing request: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/get-test-classes")
    public ResponseEntity<List<String>> getTestClasses(@RequestParam(name = "Directory-Path", required = true) String directoryPath) {
        try {
            return new ResponseEntity<List<String>>(classScannerService.dtoIdentifier(directoryPath), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(Collections.singletonList("Error processing request: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}




