package sda.catalogue.sdacataloguerestapi.core.utils;

import sda.catalogue.sdacataloguerestapi.modules.WebApp.Repositories.WebAppRepository;
import sda.catalogue.sdacataloguerestapi.modules.mobileapp.repository.MobileAppRepository;

import java.time.LocalDate;

public class GenerateAssetNumber {
    public static String generateAssetNumber(String assetCode, long countData) {
        return assetCode + "/" + LocalDate.now().getYear() + "/" + String.format("%03d", countData);
    }

//        public static String generateAssetNumber(String assetCode, WebAppRepository webAppRepository, MobileAppRepository mobileAppRepository) {
//            String baseAssetNumber = assetCode + "/" + LocalDate.now().getYear();
//            long count = 1;
//            String assetNumber = baseAssetNumber + "/" + String.format("%03d", count);
//
//            while (webAppRepository.existsByAssetNumber(assetNumber)) {
//                count++;
//                assetNumber = baseAssetNumber + "/" + String.format("%03d", count);
//            }
//
//            return assetNumber;
//        }

}
