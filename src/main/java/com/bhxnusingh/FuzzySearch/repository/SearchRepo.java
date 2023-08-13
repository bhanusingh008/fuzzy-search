package com.bhxnusingh.FuzzySearch.repository;

import java.util.List;

public interface SearchRepo {

    List<Object> findByText(String text);
}
