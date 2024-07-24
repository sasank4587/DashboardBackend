package com.example.Triveni.impl;

import com.example.Triveni.collections.ProductInvoice;
import com.example.Triveni.request.AddProductInvoiceRequest;
import com.example.Triveni.request.ProductInvoiceRequest;
import com.example.Triveni.response.DashBoardResponse;
import com.example.Triveni.response.InvoiceResponse;
import com.example.Triveni.response.ProductInvoiceResponse;
import com.example.Triveni.respositories.ProductInvoiceRepository;
import com.example.Triveni.service.ProductService;
import com.opencsv.CSVWriter;
import com.opencsv.ICSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInvoiceRepository productInvoiceRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public List<ProductInvoiceResponse> getAllProducts() {
        List<ProductInvoice> productInvoiceList = productInvoiceRepository.findAll();
        return productInvoiceList.parallelStream().map(productInvoice -> ProductInvoiceResponse.builder()
                .invoiceId(productInvoice.getInvoiceId())
                .invoiceDate(productInvoice.getInvoiceDate())
                .vendorName(productInvoice.getVendorName())
                .productName(productInvoice.getProductName())
                .brandName(productInvoice.getBrandName())
                .productSize(productInvoice.getProductSize())
                .productQuantity(productInvoice.getProductQuantity())
                .expirationDate(productInvoice.getExpirationDate())
                .build()).collect(Collectors.toList());
    }

    @Override
    public Page<ProductInvoice> getFilteredProducts(String invoiceId, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by("expirationDate").ascending());
        if(StringUtils.isEmpty(invoiceId)) {
            return productInvoiceRepository.findAll(pageable);
        } else{
            return productInvoiceRepository.findByInvoiceId(invoiceId, pageable);
        }
    }

    @Override
    public List<ProductInvoiceResponse> getNearExpiryProducts() {
        Date startDate = new Date(System.currentTimeMillis());
        Date endDate = new Date(startDate.getTime() + (1000l * 60 * 60 * 24 * 100));
        List<ProductInvoiceResponse> productInvoiceResponseList =
                getListOfProductsExpiringBetweenDates(startDate, endDate);
        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy");
        String sDate = sdf.format(startDate);
        writeToFile(productInvoiceResponseList, sDate);
        return productInvoiceResponseList;
    }

    @Override
    public List<ProductInvoiceResponse> getProductsByInvoiceId(String invoiceId) {
        List<ProductInvoice> productInvoiceList = productInvoiceRepository.findByInvoiceId(invoiceId);
        return productInvoiceList.parallelStream()
                .map(this::convertToProductInvoiceResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceResponse> getOpenInvoiceIds() {
        List<String> invoiceIds = productInvoiceRepository.findDistinctInvoiceIdsByStatus("OPEN");
        return invoiceIds.parallelStream().map(s -> {
            return InvoiceResponse.builder().invoiceId(s).build();
        }).collect(Collectors.toList());
    }

    @Override
    public void addProductInvoices(ProductInvoiceRequest productInvoiceRequest) {
        if (Objects.nonNull(productInvoiceRequest)) {
            ProductInvoice productInvoice = convertProductInvoice(productInvoiceRequest);
            String status = productInvoiceRequest.getStatus();
            productInvoiceRepository.save(productInvoice);
            if ("CLOSE".equalsIgnoreCase(status)) {
                List<ProductInvoice> existingProductInvoiceList =
                        productInvoiceRepository.findByInvoiceId(productInvoiceRequest.getInvoiceId());
                existingProductInvoiceList.parallelStream().forEach(productInvoiceObject -> productInvoiceObject.setInvoiceStatus(status));
                productInvoiceRepository.saveAll(existingProductInvoiceList);
            }
        }
    }

//    @Override
//    public void addProductInvoices(AddProductInvoiceRequest addProductInvoiceRequest) {
//        if (!CollectionUtils.isEmpty(addProductInvoiceRequest.getProductInvoiceRequestList())) {
//            List<ProductInvoiceRequest> productInvoiceRequestList =
//                    addProductInvoiceRequest.getProductInvoiceRequestList();
//            String status = addProductInvoiceRequest.getStatus();
//            if ("CLOSE".equalsIgnoreCase(status)) {
//                List<ProductInvoice> existingProductInvoiceList =
//                        productInvoiceRepository.findByInvoiceId(productInvoiceRequestList.get(0).getInvoiceId());
//                existingProductInvoiceList.parallelStream().forEach(productInvoice -> productInvoice.setInvoiceStatus(status));
//                productInvoiceRepository.saveAll(existingProductInvoiceList);
//            } else {
//                List<ProductInvoice> productInvoiceList = productInvoiceRequestList.parallelStream()
//                        .map(productInvoiceRequest -> convertProductInvoice(productInvoiceRequest, status))
//                        .collect(Collectors.toList());
//                productInvoiceRepository.saveAll(productInvoiceList);
//            }
//        }
//    }

    @Override
    public DashBoardResponse getExpiryDashBoard() {
        Date startDate = new Date(System.currentTimeMillis());
        Date after1Week = new Date(startDate.getTime() + (1000L * 60 * 60 * 24 * 7));
        Date after2Week = new Date(startDate.getTime() + (1000L * 60 * 60 * 24 * 14));
        Date after3Week = new Date(startDate.getTime() + (1000L * 60 * 60 * 24 * 21));
        Date after4Week = new Date(startDate.getTime() + (1000L * 60 * 60 * 24 * 28));
        Date after10Days = new Date(startDate.getTime() + (1000L * 60 * 60 * 24 * 10));
        Date after20Days = new Date(startDate.getTime() + (1000L * 60 * 60 * 24 * 20));
        Date after30Days = new Date(startDate.getTime() + (1000L * 60 * 60 * 24 * 30));
        Date after40Days = new Date(startDate.getTime() + (1000L * 60 * 60 * 24 * 40));
        List<ProductInvoiceResponse> listOfProductToExpireInWeek1 =
                getListOfProductsExpiringBetweenDates(startDate, after1Week);
        List<ProductInvoiceResponse> listOfProductToExpireInWeek2 =
                getListOfProductsExpiringBetweenDates(after1Week, after2Week);
        List<ProductInvoiceResponse> listOfProductToExpireInWeek3 =
                getListOfProductsExpiringBetweenDates(after2Week, after3Week);
        List<ProductInvoiceResponse> listOfProductToExpireInWeek4 =
                getListOfProductsExpiringBetweenDates(after3Week, after4Week);
        List<ProductInvoiceResponse> listOfProductToExpireIn10Days =
                getListOfProductsExpiringBetweenDates(startDate, after10Days);
        List<ProductInvoiceResponse> listOfProductToExpireIn20Days =
                getListOfProductsExpiringBetweenDates(startDate, after20Days);
        List<ProductInvoiceResponse> listOfProductToExpireIn30Days =
                getListOfProductsExpiringBetweenDates(startDate, after30Days);
        List<ProductInvoiceResponse> listOfProductToExpireIn40Days =
                getListOfProductsExpiringBetweenDates(startDate, after40Days);
        return DashBoardResponse.builder()
                .listOfProductToExpireInWeek1(listOfProductToExpireInWeek1)
                .listOfProductToExpireInWeek2(listOfProductToExpireInWeek2)
                .listOfProductToExpireInWeek3(listOfProductToExpireInWeek3)
                .listOfProductToExpireInWeek4(listOfProductToExpireInWeek4)
                .listOfProductToExpireIn10Days(listOfProductToExpireIn10Days)
                .listOfProductToExpireIn20Days(listOfProductToExpireIn20Days)
                .listOfProductToExpireIn30Days(listOfProductToExpireIn30Days)
                .listOfProductToExpireIn40Days(listOfProductToExpireIn40Days)
                .build();
    }

    private ProductInvoice convertProductInvoice(ProductInvoiceRequest productInvoiceRequest) {
        ProductInvoice productInvoice = new ProductInvoice();
        productInvoice.setInvoiceId(productInvoiceRequest.getInvoiceId());
        productInvoice.setInvoiceDate(productInvoiceRequest.getInvoiceDate());
        productInvoice.setVendorName(productInvoiceRequest.getVendorName());
        productInvoice.setProductName(productInvoiceRequest.getProductName());
        productInvoice.setBrandName(productInvoiceRequest.getBrandName());
        productInvoice.setProductSize(productInvoiceRequest.getProductSize());
        productInvoice.setProductQuantity(productInvoiceRequest.getProductQuantity());
        productInvoice.setExpirationDate(productInvoiceRequest.getExpirationDate());
        productInvoice.setInvoiceStatus(productInvoiceRequest.getStatus());
        return productInvoice;
    }

    private List<ProductInvoiceResponse> getListOfProductsExpiringBetweenDates(Date startDate, Date endDate) {
        List<ProductInvoice> productInvoiceList = productInvoiceRepository.findByExpirationDateBetween(startDate,
                endDate);
        return productInvoiceList.parallelStream()
                .map(this::convertToProductInvoiceResponse)
                .collect(Collectors.toList());
    }

    private ProductInvoiceResponse convertToProductInvoiceResponse(ProductInvoice productInvoice) {
        return ProductInvoiceResponse.builder()
                .invoiceId(productInvoice.getInvoiceId())
                .invoiceDate(productInvoice.getInvoiceDate())
                .vendorName(productInvoice.getVendorName())
                .productName(productInvoice.getProductName())
                .brandName(productInvoice.getBrandName())
                .productSize(productInvoice.getProductSize())
                .productQuantity(productInvoice.getProductQuantity())
                .expirationDate(productInvoice.getExpirationDate())
                .build();
    }

    private void writeToFile(List<ProductInvoiceResponse> productInvoiceResponseList, String date) {
        ICSVWriter csvWriter = null;
        String fileName = "product_list_" + date + ".csv";
        try {
            csvWriter = new CSVWriter(new FileWriter(fileName));
            String[] header = {"invoiceId", "invoiceDate", "vendorName", "brandName", "productName", "productSize",
                    "productQuantity", "expirationDate"};
            csvWriter.writeNext(header, true);
            for (ProductInvoiceResponse productInvoiceResponse : productInvoiceResponseList) {
                String[] row = {
                        productInvoiceResponse.getInvoiceId(),
                        productInvoiceResponse.getInvoiceDate().toString(),
                        productInvoiceResponse.getVendorName(),
                        productInvoiceResponse.getBrandName(),
                        productInvoiceResponse.getProductName(),
                        productInvoiceResponse.getProductSize(),
                        productInvoiceResponse.getProductQuantity().toString(),
                        productInvoiceResponse.getExpirationDate().toString(),
                };
                csvWriter.writeNext(row, true);
            }
        } catch (IOException e) {
            System.err.println("Error writing the CSV file: " + e);
        } finally {
            if (csvWriter != null) {
                try {
                    csvWriter.close();
                    emailService.mailWithAttachment(fileName);
                } catch (IOException e) {
                    System.err.println("Error closing the writer: " + e);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
