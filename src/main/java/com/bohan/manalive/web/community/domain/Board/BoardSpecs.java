package com.bohan.manalive.web.community.domain.Board;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Getter
@RequiredArgsConstructor
public class BoardSpecs {

    public enum SearchKey {
        TITLE("title"),
        CONTENT("content");

        private final String value;

        SearchKey(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static Specification<Board> searchWith(Map<SearchKey, Object> searchKeyword) {
        return (Specification<Board>) ((root, query, builder) -> {
            List<Predicate> predicate = getPredicateWithKeyword(searchKeyword, root, builder);
            return builder.and(predicate.toArray(new Predicate[0]));
        });
    }

    private static List<Predicate> getPredicateWithKeyword(Map<SearchKey, Object> searchKeyword, Root<Board> root, CriteriaBuilder builder) {
        List<Predicate> predicate = new ArrayList<>();
        for(SearchKey key : searchKeyword.keySet()) {
            switch (key) {
                case TITLE:
                case CONTENT:
//                    predicate.add(builder.equal(
//                            root.get(key.value),searchKeyword.get(key)
//                    ));
                        predicate.add(builder.like(root.get(key.value),"%" + searchKeyword.get(key) +"%"));
                    break;

            }

        }
        return predicate;
    }
    //    @Nullable
    //    Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder);

}
