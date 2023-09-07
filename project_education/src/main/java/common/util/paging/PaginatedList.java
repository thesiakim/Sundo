/*
 * Created on 2014. 06. 09.
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package common.util.paging;

import java.util.List;


@SuppressWarnings("all")
public interface PaginatedList extends List {
    /**
     * @return The maximum number of items per page.
     */
    public int getPageSize();

    /** Is the current page the first page?
     * @return True if the current page is the first page or if only
     * a single page exists.
     */
    public boolean isFirstPage();

    /** Is the current page a middle page (i.e. not first or last)?
     * @return True if the current page is not the first or last page,
     * and more than one page exists (always returns false if only a
     * single page exists).
     */
    public boolean isMiddlePage();

    /** Is the current page the last page?
     * @return True if the current page is the last page or if only
     * a single page exists.
     */
    public boolean isLastPage();

    /** Is a page available after the current page?
     * @return True if the next page is available
     */
    public boolean isNextPageAvailable();

    /** Is a page available before the current page?
     * @return True if the previous page is available
     */
    public boolean isPreviousPageAvailable();

    /**
     * Moves to the next page after the current page.  If the current
     * page is the last page, wrap to the first page.
     */
    public boolean nextPage();

    /**
     * Moves to the page before the current page.  If the current
     * page is the first page, wrap to the last page.
     */
    public boolean previousPage();

    /**
     * Moves to a specified page.  If the specified
     * page is beyond the last page, wrap to the first page.
     * If the specified page is before the first page, wrap
     * to the last page.
     */
    public void gotoPage(int pageNumber);

    /**
     * Returns the current page index, which is a zero based integer.
     * All paginated list implementations should know what index they are
     * on, even if they don't know the ultimate boundaries (min/max).
     */
    public int getPageIndex();

    /**
     * Returns the total number of items
     * @return
     */
    public int getTotalSize();
}
