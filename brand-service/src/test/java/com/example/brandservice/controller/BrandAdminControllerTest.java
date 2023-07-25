package com.example.brandservice.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.brandservice.dto.BrandAccountRequestDto;
import com.example.brandservice.dto.BrandRequestDto;
import com.google.gson.Gson;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BrandAdminControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @Order(1)
    public void createBrand() throws Exception {
        //given
        BrandRequestDto brandRequestDto = BrandRequestDto.builder()
            .name("브랜드명")
            .depositAmount(10000)
            .adminId(1L)
            .isActive(Boolean.TRUE)
            .build();
        String content = new Gson().toJson(brandRequestDto);

        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.post("/admin/brand")
            .content(content)
            .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isOk())
            .andDo(print())
            .andDo(document("create-brand",
                requestFields(
                    fieldWithPath("name").description("브랜드 이름"),
                    fieldWithPath("depositAmount").optional().description("예치금 금액"),
                    fieldWithPath("adminId").description("브랜드 담당자"),
                    fieldWithPath("isActive").optional().description("브랜드 활성화 여부")
                ),
                responseFields(
                    fieldWithPath("code").description("결과코드"),
                    fieldWithPath("message").description("메시지"),
                    fieldWithPath("data").description("데이터"),
                    fieldWithPath("data.brandId").type(JsonFieldType.NUMBER).description("브랜드 고유번호"),
                    fieldWithPath("data.name").description("브랜드 이름"),
                    fieldWithPath("data.depositAmount").type(JsonFieldType.NUMBER).description("예치금 금액"),
                    fieldWithPath("data.adminId").description("브랜드 담당자"),
                    fieldWithPath("data.isActive").type(JsonFieldType.BOOLEAN).description("브랜드 활성화 여부")
                )
            ));
    }

    @Test
    @Order(2)
    public void createBrandAccount() throws Exception {
        //given
        BrandAccountRequestDto requestDto = BrandAccountRequestDto.builder()
            .loginId("test")
            .password("1234")
            .brandId(1L)
            .accountNumber("1122")
            .bankName("은행명")
            .holderName("이체자명")
            .build();

        String content = new Gson().toJson(requestDto);

        //when
        ResultActions actions = mockMvc.perform(RestDocumentationRequestBuilders.post("/admin/brandAccount")
            .content(content)
            .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isOk())
            .andDo(print())
            .andDo(document("create-brand-account",
                requestFields(
                    fieldWithPath("loginId").description("로그인 아이디"),
                    fieldWithPath("password").description("비밀번호"),
                    fieldWithPath("brandId").description("브랜드"),
                    fieldWithPath("accountNumber").description("계좌 번호"),
                    fieldWithPath("bankName").description("은행 이름"),
                    fieldWithPath("holderName").description("예치금 예치자 이름")
                ),
                responseFields(
                    fieldWithPath("code").description("결과코드"),
                    fieldWithPath("message").description("메시지"),
                    fieldWithPath("data").description("데이터"),
                    fieldWithPath("data.brandAccountId").type(JsonFieldType.NUMBER).description("브랜드 계정 고유번호"),
                    fieldWithPath("data.loginId").description("로그인 아이디"),
                    fieldWithPath("data.bankInfo").description("은행 정보"),
                    fieldWithPath("data.bankInfo.accountNumber").description("계좌 번호"),
                    fieldWithPath("data.bankInfo.bankName").description("은행 이름"),
                    fieldWithPath("data.bankInfo.holderName").description("예치금 예치자 이름")
                )
            ));
    }

}