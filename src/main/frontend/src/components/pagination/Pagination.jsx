import React from "react";
import "./pagination.scss";
import ReactPaginate from "react-paginate";


/*
참고: https://tech.stmn.co.jp/entry/2020/10/28/141406
 */
function Pagination({ totalPages, currentPage, handleSearchUser, setCurrentPageNumber }) {

    //const ONE_PAGE_DISPLAY_USERS = 10;
    const LAST_DISPLAY_SIZE = 2;  // 양끝 표시 페이지 수 (보통 1~2)
    const AROUND_DISPLAY_PAGES = 5;   // 현재 페이지 주변 표시 개수

    const handlePaginate = selectedItem => {
        const page = selectedItem.selected + 1; // selected는 0-based
        setCurrentPageNumber(page);
        // API호출 처리
        handleSearchUser();
    };


    const arrowIcon = iconName => {
        let icon;
        if (iconName === "left") {
            icon = <i className="fa-solid fa-chevron-left"></i>;
        } else {
            icon = <i className="fa-solid fa-chevron-right"></i>;
        }
        return icon;
    };



    // 페이지네이션 표시
    return (
        <div className="page">
            <ReactPaginate
                pageCount={totalPages}
                // ★ 부모가 관리하는 현재 페이지로 강제 고정 (0-based)
                forcePage={(currentPage ?? 1) - 1}
                marginPagesDisplayed={LAST_DISPLAY_SIZE}
                pageRangeDisplayed={AROUND_DISPLAY_PAGES}
                onPageChange={handlePaginate}
                containerClassName="pagination"
                pageClassName="page-item"
                pageLinkClassName="page-link"
                activeClassName="active"
                activeLinkClassName="active"
                previousLinkClassName="previous-link"
                nextLinkClassName="next-link"
                previousLabel={arrowIcon('left')}
                nextLabel={arrowIcon('right')}
                disabledClassName="disabled-button"
            />
        </div>
    );
}

export default Pagination;