package com.xiongus.bear.core.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** Page. */
public class PageDTO<E> implements Serializable {

  @Serial private static final long serialVersionUID = -8048577763828650574L;

  /** totalCount. */
  private int totalCount;

  /** pageNumber. */
  private int pageNumber;

  /** pagesAvailable. */
  private int pagesAvailable;

  /** pageItems. */
  private List<E> pageItems = new ArrayList<E>();

  public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
  }

  public void setPagesAvailable(int pagesAvailable) {
    this.pagesAvailable = pagesAvailable;
  }

  public void setPageItems(List<E> pageItems) {
    this.pageItems = pageItems;
  }

  public int getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(int totalCount) {
    this.totalCount = totalCount;
  }

  public int getPageNumber() {
    return pageNumber;
  }

  public int getPagesAvailable() {
    return pagesAvailable;
  }

  public List<E> getPageItems() {
    return pageItems;
  }

  public PageDTO() {
  }

  public PageDTO(int totalCount, int pageNumber, int pagesAvailable, List<E> pageItems) {
    this.totalCount = totalCount;
    this.pageNumber = pageNumber;
    this.pagesAvailable = pagesAvailable;
    this.pageItems = pageItems;
  }
}
