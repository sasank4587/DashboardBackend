package com.example.Triveni.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashBoardResponse {
    List<ProductInvoiceResponse> listOfProductToExpireInWeek1;
    List<ProductInvoiceResponse> listOfProductToExpireInWeek2;
    List<ProductInvoiceResponse> listOfProductToExpireInWeek3;
    List<ProductInvoiceResponse> listOfProductToExpireInWeek4;
    List<ProductInvoiceResponse> listOfProductToExpireIn10Days;
    List<ProductInvoiceResponse> listOfProductToExpireIn20Days;
    List<ProductInvoiceResponse> listOfProductToExpireIn30Days;
    List<ProductInvoiceResponse> listOfProductToExpireIn40Days;

}
