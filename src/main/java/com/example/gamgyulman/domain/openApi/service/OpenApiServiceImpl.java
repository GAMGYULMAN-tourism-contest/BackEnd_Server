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
    private static final String MOBILE_OS = "ETC";
    private static final String MOBILE_APP = "YOURSJEJU";
    private static final String TYPE = "json";
    private static final String AREA_CODE = "39";

    @Value("${open-api.service-key.decode}")
    private String decodedKey;

    @Value("${open-api.service-key.encode}")
    private String encodedKey;

    // Base Url English
    @Value("${open-api.uri.base.english}")
    private String englishUrl;

    // 지역 기반 Endpoint
    @Value("${open-api.endpoint.local-base}")
    private String localBaseUrl;

    // 키워드 검색 Endpoint
    @Value("${open-api.endpoint.keyword-search}")
    private String keywordSearchUrl;

    @Override
    public TravelInfoResponseDTO.DefaultTravelInfoListDTO getTravels(String keyword, int page, int size) {
        if (keyword == null || keyword.isEmpty()) {
            return getDefaultTravel(page, size);
        }
        else {
            return searchKeyword(keyword, page, size);
        }
    }

    @Override
    public TravelInfoResponseDTO.DefaultTravelInfoListDTO getDefaultTravel(int page, int size) {
        WebClient webClient = openApiWebClient.getEnglishTravelWebClient();

        String url = englishUrl + localBaseUrl;

        // 제주의 관광지 가져오기
        // TODO: 어떤 것으로 가져올지 한 번 상의해보기
        URI uri = UriComponentsBuilder.fromUriString(url)
                .queryParam("numOfRows", size)
                .queryParam("pageNo", page)
                .queryParam("MobileOS", MOBILE_OS)
                .queryParam("MobileApp", MOBILE_APP)
                .queryParam("_type", TYPE)
                .queryParam("arrange", "Q")
                .queryParam("contentTypeId", "76")
                .queryParam("areaCode", AREA_CODE)
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

    @Override
    public TravelInfoResponseDTO.DefaultTravelInfoListDTO searchKeyword(String keyword, int page, int size) {
        WebClient webClient = openApiWebClient.getEnglishTravelWebClient();

        String url = englishUrl + keywordSearchUrl;

        URI uri = UriComponentsBuilder.fromUriString(url)
                .queryParam("numOfRows", size)
                .queryParam("pageNo", page)
                .queryParam("MobileOS", MOBILE_OS)
                .queryParam("MobileApp", MOBILE_APP)
                .queryParam("_type", TYPE)
                .queryParam("arrange", "O")
                .queryParam("keyword", keyword)
                .queryParam("areaCode", AREA_CODE)
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

    /**
     * 문자열 응답을 OpenApiInfoDTO로 매핑하기
     * @param response WebClient의 문자열 응답
     * @return OpenApiResponseDTO.OpenApiInfoListDTO
     */
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

    /**
     * OpenApiResponseDTO.OpenApiInfoListDTO를 TravelInfoResponseDTO.DefaultTravelInfoListDTO로 매핑
     * @param dto OpenApiResponseDTO.OpenApiInfoListDTO
     * @return TravelInfoResponseDTO.DefaultTravelInfoListDTO
     */
    private TravelInfoResponseDTO.DefaultTravelInfoListDTO mappedWithTravelInfoDTO(OpenApiResponseDTO.OpenApiInfoListDTO dto) {
        return TravelInfoResponseDTO.DefaultTravelInfoListDTO.from(dto);
    }
}
