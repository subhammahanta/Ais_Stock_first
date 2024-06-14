package com.watsoo.device.management.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.watsoo.device.management.dto.PaginationV2;

public class PaginationUtil {

	public static <T> PaginationV2<List<T>> paginate(List<T> items, int page, int pageSize, Comparator<T> comparator) {
		Collections.sort(items, comparator.reversed());

		if (pageSize == 0 && page == 0) {
			int totalItems = items.size();
			return new PaginationV2<>(0, 1, totalItems, totalItems, items);
		}

		int totalItems = items.size();
		int totalPages = (int) Math.ceil((double) totalItems / pageSize);

		int fromIndex = page * pageSize;
		int toIndex = Math.min(fromIndex + pageSize, totalItems);

		List<T> pagedItems = items.subList(fromIndex, toIndex);

		return new PaginationV2<>(page, totalPages, totalItems, pageSize, pagedItems);
	}

	public static <K, V> PaginationV2<Map<K, V>> paginateMap(Map<K, V> map, int page, int pageSize) {
		List<K> keys = map.keySet().stream().sorted().collect(Collectors.toList());

		if (pageSize == 0 && page == 0) {
			int totalItems = keys.size();
			Map<K, V> allItems = keys.stream().collect(Collectors.toMap(k -> k, map::get));
			return new PaginationV2<>(0, 1, totalItems, totalItems, allItems);
		}

		int totalItems = keys.size();
		int totalPages = (int) Math.ceil((double) totalItems / pageSize);

		int fromIndex = page * pageSize;
		int toIndex = Math.min(fromIndex + pageSize, totalItems);

		List<K> pagedKeys = keys.subList(fromIndex, toIndex);
		Map<K, V> pagedMap = pagedKeys.stream().collect(Collectors.toMap(k -> k, map::get));

		return new PaginationV2<>(page, totalPages, totalItems, pageSize, pagedMap);
	}
}