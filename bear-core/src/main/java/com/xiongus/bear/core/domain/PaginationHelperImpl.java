package com.xiongus.bear.core.domain;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * Pagination Utils
 *
 * @param <E> Generic class
 * @author xiongus
 */
public record PaginationHelperImpl<E>(
        JdbcTemplate jdbcTemplate) implements PaginationHelper<E> {

  private static final String PATTERN_STR = "*";

  @Override
  public Page<E> fetchPage(
          String sqlCountRows,
          String sqlFetchRows,
          Object[] args,
          int pageNo,
          int pageSize,
          RowMapper<E> rowMapper) {
    return fetchPage(sqlCountRows, sqlFetchRows, args, pageNo, pageSize, null, rowMapper);
  }

  @Override
  public Page<E> fetchPage(
          String sqlCountRows,
          String sqlFetchRows,
          Object[] args,
          int pageNo,
          int pageSize,
          Long lastMaxId,
          RowMapper<E> rowMapper) {
    if (pageNo <= 0 || pageSize <= 0) {
      throw new IllegalArgumentException("pageNo and pageSize must be greater than zero");
    }

    // Query the total number of current records
    Integer rowCountInt = jdbcTemplate.queryForObject(sqlCountRows, Integer.class, args);
    if (rowCountInt == null) {
      throw new IllegalArgumentException("fetchPageLimit error");
    }

    // Count pages
    int pageCount = rowCountInt / pageSize;
    if (rowCountInt > pageSize * pageCount) {
      pageCount++;
    }

    // Create Page object
    final Page<E> page = new Page<>();
    page.setPageNumber(pageNo);
    page.setPagesAvailable(pageCount);
    page.setTotalCount(rowCountInt);

    if (pageNo > pageCount) {
      return page;
    }

    final int startRow = (pageNo - 1) * pageSize;
    String selectSql;
    if (lastMaxId != null) {
      selectSql =
              sqlFetchRows
                      + " AND id > "
                      + lastMaxId
                      + " ORDER BY id ASC"
                      + " LIMIT "
                      + 0
                      + ","
                      + pageSize;
    } else {
      selectSql = sqlFetchRows + " LIMIT " + startRow + "," + pageSize;
    }

    List<E> result = jdbcTemplate.query(selectSql, rowMapper, args);
    for (E item : result) {
      page.getPageItems().add(item);
    }
    return page;
  }

  @Override
  public Page<E> fetchPageLimit(
          String sqlCountRows,
          String sqlFetchRows,
          Object[] args,
          int pageNo,
          int pageSize,
          RowMapper<E> rowMapper) {
    if (pageNo <= 0 || pageSize <= 0) {
      throw new IllegalArgumentException("pageNo and pageSize must be greater than zero");
    }
    // Query the total number of current records
    Integer rowCountInt = jdbcTemplate.queryForObject(sqlCountRows, Integer.class);
    if (rowCountInt == null) {
      throw new IllegalArgumentException("fetchPageLimit error");
    }

    // Count pages
    int pageCount = rowCountInt / pageSize;
    if (rowCountInt > pageSize * pageCount) {
      pageCount++;
    }

    // Create Page object
    final Page<E> page = new Page<>();
    page.setPageNumber(pageNo);
    page.setPagesAvailable(pageCount);
    page.setTotalCount(rowCountInt);

    if (pageNo > pageCount) {
      return page;
    }
    List<E> result = jdbcTemplate.query(sqlFetchRows, rowMapper, args);
    for (E item : result) {
      page.getPageItems().add(item);
    }
    return page;
  }

  @Override
  public Page<E> fetchPageLimit(
          String sqlCountRows,
          Object[] args1,
          String sqlFetchRows,
          Object[] args2,
          int pageNo,
          int pageSize,
          RowMapper<E> rowMapper) {
    if (pageNo <= 0 || pageSize <= 0) {
      throw new IllegalArgumentException("pageNo and pageSize must be greater than zero");
    }
    // Query the total number of current records
    Integer rowCountInt = jdbcTemplate.queryForObject(sqlCountRows, Integer.class, args1);
    if (rowCountInt == null) {
      throw new IllegalArgumentException("fetchPageLimit error");
    }

    // Count pages
    int pageCount = rowCountInt / pageSize;
    if (rowCountInt > pageSize * pageCount) {
      pageCount++;
    }

    // Create Page object
    final Page<E> page = new Page<>();
    page.setPageNumber(pageNo);
    page.setPagesAvailable(pageCount);
    page.setTotalCount(rowCountInt);

    if (pageNo > pageCount) {
      return page;
    }
    List<E> result = jdbcTemplate.query(sqlFetchRows, rowMapper, args2);
    for (E item : result) {
      page.getPageItems().add(item);
    }
    return page;
  }

  @Override
  public Page<E> fetchPageLimit(
          String sqlFetchRows, Object[] args, int pageNo, int pageSize, RowMapper<E> rowMapper) {
    if (pageNo <= 0 || pageSize <= 0) {
      throw new IllegalArgumentException("pageNo and pageSize must be greater than zero");
    }
    // Create Page object
    final Page<E> page = new Page<>();

    List<E> result = jdbcTemplate.query(sqlFetchRows, rowMapper, args);
    for (E item : result) {
      page.getPageItems().add(item);
    }
    return page;
  }

  @Override
  public void updateLimit(final String sql, final Object[] args) {
    jdbcTemplate.update(sql, args);
  }

  @Override
  public String generateLikeArgument(String s) {
    String fuzzySearchSign = "\\*";
    String sqlLikePercentSign = "%";
    if (s.contains(PATTERN_STR)) {
      return s.replaceAll(fuzzySearchSign, sqlLikePercentSign);
    } else {
      return s;
    }
  }

}
