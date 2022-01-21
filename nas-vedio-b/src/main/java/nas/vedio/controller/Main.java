package nas.vedio.controller;

import nas.vedio.service.Init;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@RestController
public class Main {
    @Autowired
    Init init;
    @GetMapping("/initAll")
    public String initAll(String path){
        return init.initAll(path,true)+"";
    }
}