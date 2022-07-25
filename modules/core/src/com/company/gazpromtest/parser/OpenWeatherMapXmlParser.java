package com.company.gazpromtest.parser;

import com.company.gazpromtest.dto.WeatherDataDto;
import com.company.gazpromtest.dto.WeatherDto;
import com.company.gazpromtest.exception.WeatherParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class OpenWeatherMapXmlParser implements Parser<WeatherDataDto> {

    private static final Logger log = LoggerFactory.getLogger(OpenWeatherMapXmlParser.class);

    @Override
    public WeatherDataDto parse(String xml, String city) {
        List<WeatherDto> weatherList = new ArrayList<>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            try (StringReader stringReader = new StringReader(xml)) {
                Document parse = db.parse(new InputSource(stringReader));
                NodeList timeNodeList = parse.getElementsByTagName("time");
                NodeList temperatureNodeList = parse.getElementsByTagName("temperature");
                NodeList feelsLikeNodeList = parse.getElementsByTagName("feels_like");
                NodeList pressureNodeList = parse.getElementsByTagName("pressure");
                NodeList humidityNodeList = parse.getElementsByTagName("humidity");
                NodeList symbolNodeList = parse.getElementsByTagName("symbol");
                for (int temp = 0; temp < timeNodeList.getLength(); temp++) {
                    Element timeElement = (Element)timeNodeList.item(temp);
                    Element temperatureElement = (Element)temperatureNodeList.item(temp);
                    Element feelsLikeElement = (Element)feelsLikeNodeList.item(temp);
                    Element pressureElement = (Element)pressureNodeList.item(temp);
                    Element humidityElement = (Element)humidityNodeList.item(temp);
                    Element symbolElement = (Element)symbolNodeList.item(temp);
                    String from = timeElement.getAttribute("from");
                    String to = timeElement.getAttribute("to");
                    String tmp = temperatureElement.getAttribute("value");
                    String feelsLike = feelsLikeElement.getAttribute("value");
                    String pressure = pressureElement.getAttribute("value");
                    String humidity = humidityElement.getAttribute("value");
                    String description = symbolElement.getAttribute("name");
                    weatherList.add(
                            new WeatherDto(tmp, feelsLike,pressure, humidity,description, from ,to)
                    );

                }
            }
        } catch (Exception e) {
            log.error("Xml parsing error, xml{}", xml, e);
            throw new WeatherParseException(e.getMessage());
        }
        return new WeatherDataDto(city, weatherList);
    }
}
