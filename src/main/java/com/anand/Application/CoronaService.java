package com.anand.Application;

import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.type.LocalDateTimeType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class CoronaService {
    CoronaRepository coronaRepository;

    public CoronaService(CoronaRepository coronaRepository) {
        this.coronaRepository = coronaRepository;
    }
    public void save(Corona corona){
        coronaRepository.save(corona);
    }
    @Scheduled(cron = "0 4 * * *")
    public void populateDatabase() throws IOException {
        URL url = new URL("https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/06-06-2020.csv");
        HttpURLConnection huc = (HttpURLConnection) url.openConnection();
        int responseCode = huc.getResponseCode();
        if(responseCode == 200){
            log.info("--successfully connected to github--");
            CSVReader reader = null;
            try{

                BufferedReader input = new BufferedReader(new InputStreamReader(huc.getInputStream()),8192);
                reader = new CSVReader(input);
                String[] line;
                int i =0;
                while ((line = reader.readNext()) !=null){
                    if(i==0){
                        i++;
                        continue;
                    }
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                    Corona corona = new Corona();
                    corona.setLastUpdate(LocalDateTime.parse(line[4],formatter));
                    corona.setConfirmed(Long.valueOf(line[7]));
                    corona.setRecovered(Long.valueOf(line[9]));
                    corona.setActive(Long.valueOf(line[10]));
                    corona.setCombinedKey(line[11]);

                    List<Corona> coronaList = findByCombinedKey(corona.getCombinedKey());
                    if(!coronaList.isEmpty()){
                        corona.setConfirmedChanges(corona.getConfirmed()- coronaList.get(coronaList.size() - 1).getConfirmed());
                        corona.setRecoveredChanges(corona.getRecovered()- coronaList.get(coronaList.size() - 1).getRecovered());
                        corona.setActiveChanges(corona.getActive()- coronaList.get(coronaList.size() - 1).getActive());
                    }
                    coronaRepository.save(corona);
                    log.info(corona.toString());




                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (reader!=null){
                    try {
                        reader.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }

        }


    }
    public void populateDatabase2() throws IOException {
        URL url = new URL("https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_daily_reports/07-06-2020.csv");
        HttpURLConnection huc = (HttpURLConnection) url.openConnection();
        int responseCode = huc.getResponseCode();
        if(responseCode == 200){
            log.info("--successfully connected to github--");
            CSVReader reader = null;
            try{

                BufferedReader input = new BufferedReader(new InputStreamReader(huc.getInputStream()),8192);
                reader = new CSVReader(input);
                String[] line;
                int i =0;
                while ((line = reader.readNext()) !=null){
                    if(i==0){
                        i++;
                        continue;
                    }
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                    Corona corona = new Corona();
                    corona.setLastUpdate(LocalDateTime.parse(line[4],formatter));
                    corona.setConfirmed(Long.valueOf(line[7]));
                    corona.setRecovered(Long.valueOf(line[9]));
                    corona.setActive(Long.valueOf(line[10]));
                    corona.setCombinedKey(line[11]);

                    List<Corona> coronaList = findByCombinedKey(corona.getCombinedKey());
                    if(!coronaList.isEmpty()){
                        corona.setConfirmedChanges(corona.getConfirmed()- coronaList.get(coronaList.size() - 1).getConfirmed());
                        corona.setRecoveredChanges(corona.getRecovered()- coronaList.get(coronaList.size() - 1).getRecovered());
                        corona.setActiveChanges(corona.getActive()- coronaList.get(coronaList.size() - 1).getActive());
                    }
                    coronaRepository.save(corona);
                    log.info(corona.toString());




                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (reader!=null){
                    try {
                        reader.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }

        }


    }

    private List<Corona> findByCombinedKey(String combinedKey) {
        return coronaRepository.findByCombinedKey(combinedKey);
    }

    public List<Corona> findByLastUpdate(LocalDate localDate) {
        return coronaRepository.findByLastUpdateBetween(LocalDateTime.of(localDate, LocalTime.MIN),LocalDateTime.of(localDate,LocalTime.MAX));

    }
}
