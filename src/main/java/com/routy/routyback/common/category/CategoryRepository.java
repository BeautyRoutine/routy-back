package com.routy.routyback.common.category;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

@Component
public class CategoryRepository {

    private final Map<Integer, Category> mainCate = new TreeMap<>();
    private final Map<Integer, String> subCate = new TreeMap<>();

    public CategoryRepository() {
        // 11: 스킨케어
        mainCate.put(11, new Category("스킨케어", Map.ofEntries(
            Map.entry(11001, "스킨/토너"),
            Map.entry(11002, "에센스/세럼/앰플"),
            Map.entry(11003, "크림"),
            Map.entry(11004, "로션"),
            Map.entry(11005, "미스트/오일")
        )));
        subCate.putAll(mainCate.get(11).getSub());

        // 21: 메이크업
        mainCate.put(21, new Category("메이크업", Map.ofEntries(
            Map.entry(21001, "립메이크업"),
            Map.entry(21002, "베이스메이크업"),
            Map.entry(21003, "아이메이크업"),
            Map.entry(21004, "아이소품"),
            Map.entry(21005, "뷰티잡화")
        )));
        subCate.putAll(mainCate.get(21).getSub());

        // 31: 마스크팩
        mainCate.put(31, new Category("마스크팩", Map.ofEntries(
            Map.entry(31001, "시트팩"),
            Map.entry(31002, "패드"),
            Map.entry(31003, "페이셜팩"),
            Map.entry(31004, "코팩")
        )));
        subCate.putAll(mainCate.get(31).getSub());

        // 41: 클렌징
        mainCate.put(41, new Category("클렌징", Map.ofEntries(
            Map.entry(41001, "클렌징폼/젤"),
            Map.entry(41002, "클렌징워터/밀크"),
            Map.entry(41003, "클렌징오일/밤"),
            Map.entry(41004, "클렌징티슈/패드"),
            Map.entry(41005, "필링&스크럽")
        )));
        subCate.putAll(mainCate.get(41).getSub());

        // 51: 선케어
        mainCate.put(51, new Category("선케어", Map.ofEntries(
            Map.entry(51001, "선크림"),
            Map.entry(51002, "선스틱"),
            Map.entry(51003, "선쿠션"),
            Map.entry(51004, "선스프레이")
        )));
        subCate.putAll(mainCate.get(51).getSub());

        // 61: 맨즈케어
        mainCate.put(61, new Category("맨즈케어", Map.ofEntries(
            Map.entry(61001, "맨즈 스킨케어"),
            Map.entry(61002, "맨즈 메이크업"),
            Map.entry(61003, "헤어케어"),
            Map.entry(61004, "쉐이빙/왁싱")
        )));
        subCate.putAll(mainCate.get(61).getSub());
    }

    public String getMainCateStr(int mainNo) {
        return mainCate.containsKey(mainNo) ? mainCate.get(mainNo).getMainStr() : null;
    }

    public String getSubCateStr(int subNo) {
        return subCate.getOrDefault(subNo, null);
    }

    public Map<Integer, Category> getMainCate() {
        return mainCate;
    }

    public Map<Integer, String> getSubCate(int mainCateNo) {
        if (mainCateNo == 0) {
            // 전체 반환
            return subCate;
        } else if (mainCate.containsKey(mainCateNo)) {
            // 해당 메인 카테고리의 sub 반환
            return mainCate.get(mainCateNo).getSub();
        } else {
            // 전체 반환
            return subCate;
        }
    }


}
