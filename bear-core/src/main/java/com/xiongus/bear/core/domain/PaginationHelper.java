package com.xiongus.bear.core.domain;

import org.springframework.jdbc.core.RowMapper;

/** Pagination Utils interface. */
public interface PaginationHelper<E> {

  Page<E> fetchPage(
      final String sqlCountRows,
      final String sqlFetchRows,
      final Object[] args,
      final int pageNo,
      final int pageSize,
      final RowMapper<E> rowMapper);

  Page<E> fetchPage(
      final String sqlCountRows,
      final String sqlFetchRows,
      final Object[] args,
      final int pageNo,
      final int pageSize,
      final Long lastMaxId,
      final RowMapper<E> rowMapper);

  Page<E> fetchPageLimit(
      final String sqlCountRows,
      final String sqlFetchRows,
      final Object[] args,
      final int pageNo,
      final int pageSize,
      final RowMapper<E> rowMapper);

  Page<E> fetchPageLimit(
      final String sqlCountRows,
      final Object[] args1,
      final String sqlFetchRows,
      final Object[] args2,
      final int pageNo,
      final int pageSize,
      final RowMapper<E> rowMapper);

  Page<E> fetchPageLimit(
      final String sqlFetchRows,
      final Object[] args,
      final int pageNo,
      final int pageSize,
      final RowMapper<E> rowMapper);

  void updateLimit(final String sql, final Object[] args);

   String generateLikeArgument(final String s);
}
