package com.anand.Application;

import com.opencsv.CSVReader;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Controller
public class MainController {
   CoronaService coronaService;

    public MainController(CoronaService coronaService) {
        this.coronaService = coronaService;
    }

    @GetMapping("/test")
    public String testMethod(Model model) throws IOException {
        model.addAttribute("test1","Hello User");
        coronaService.populateDatabase2();
        coronaService.populateDatabase();
        return "mainTemplate";
    }
    @GetMapping("/")
    public String root2(Model model) throws IOException {
        model.addAttribute("coronaData", coronaService.findByLastUpdate(LocalDate.now().minusDays(1)));
        return "mainTemplate";
    }
}

