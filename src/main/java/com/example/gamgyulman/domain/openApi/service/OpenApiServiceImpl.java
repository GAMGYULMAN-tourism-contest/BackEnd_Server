package com.example.gamgyulman.domain.openApi.service;

import com.example.gamgyulman.domain.openApi.components.OpenApiWebClient;
import com.example.gamgyulman.domain.openApi.dto.OpenApiResponseDTO;
import com.example.gamgyulman.domain.openApi.dto.TravelInfoResponseDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenApiServiceImpl implements OpenApiService {

    private final OpenApiWebClient openApiWebClient;

    @Value("${open-api.service-key.decode}")
    private String decodedKey;

    @Value("${open-api.service-key.encode}")
    private String encodedKey;

    @Value("${open-api.uri.local-base}")
    private String localBaseUrl;

    @Override
    public TravelInfoResponseDTO.DefaultTravelInfoListDTO getDefaultTravel(int page, int size) {
        WebClient webClient = openApiWebClient.getEnglishTravelWebClient();

        // 제주의 관광지 가져오기
        URI uri = UriComponentsBuilder.fromUriString(localBaseUrl)
                .queryParam("numOfRows", size)
                .queryParam("pageNo", page)
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "YOURSJEJU")
                .queryParam("_type", "json")
                .queryParam("arrange", "Q")
                .queryParam("contentTypeId", "76")
                .queryParam("areaCode", "39")
                .queryParam("serviceKey", encodedKey)
                .build(true)
                .toUri();

        String response = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        OpenApiResponseDTO.OpenApiInfoListDTO openApiInfoListDTO = mappedOpenApiInfoDTO(response);
        return mappedWithTravelInfoDTO(openApiInfoListDTO);
    }

    private OpenApiResponseDTO.OpenApiInfoListDTO mappedOpenApiInfoDTO(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(response).path("response").path("body");
            JsonNode items = jsonNode.path("items");
            OpenApiResponseDTO.OpenApiInfoListDTO result = objectMapper.convertValue(jsonNode, OpenApiResponseDTO.OpenApiInfoListDTO.class);
            if (items.has("item")) {
                OpenApiResponseDTO.OpenApiInfoListDTO infoList = objectMapper.convertValue(items, OpenApiResponseDTO.OpenApiInfoListDTO.class);
                result.setItem(infoList.getItem());
            }
            return result;
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()));
        }

        return null;
    }

    private TravelInfoResponseDTO.DefaultTravelInfoListDTO mappedWithTravelInfoDTO(OpenApiResponseDTO.OpenApiInfoListDTO dto) {
        return TravelInfoResponseDTO.DefaultTravelInfoListDTO.from(dto);
    }
}
