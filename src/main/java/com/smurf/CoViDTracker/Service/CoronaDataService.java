package com.smurf.CoViDTracker.Service;


import com.smurf.CoViDTracker.model.Report;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaDataService {

    private String DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<Report> reportList = new ArrayList<>();

    public List<Report> getReportList() {
        return reportList;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * * ")
    public void fetchData() throws IOException, InterruptedException {

        List<Report> newReportList = new ArrayList<>();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(DATA_URL))
                .build();
        HttpResponse<String> stringHttpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(stringHttpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            Report reportInstance = new Report();
            reportInstance.setCity(record.get("Province/State"));
            reportInstance.setCountry(record.get("Country/Region"));
            reportInstance.setConfirms(Integer.parseInt(record.get(record.size()-1)));
            reportInstance.setLastDay(Integer.parseInt(record.get(record.size()-1))-Integer.parseInt(record.get(record.size()-2)));
            newReportList.add(reportInstance);
        }
        this.reportList = newReportList;
    }

}
