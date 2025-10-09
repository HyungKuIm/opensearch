import React from "react";
import "./pagination.scss";
import ReactPaginate from "react-paginate";


/*
참고: https://tech.stmn.co.jp/entry/2020/10/28/141406
interface Props {
  userSize: number; //ページ数を計算するために必要な全ユーザーの数
  handleSearchUser: () => void;  // ユーザーを検索する関数
  setCurrentPageNumber: (page: number) => void;  //ページネーションの番号をセットする関数
}
 */
function Pagination({ totalPages, handleSearchUser, setCurrentPageNumber }) {

    //const ONE_PAGE_DISPLAY_USERS = 10;
    const LAST_DISPLAY_SIZE = 10;
    const AROUND_DISPLAY_PAGES = 5;

    const handlePaginate = selectedItem => {
        const page = selectedItem.selected + 1;
        setCurrentPageNumber(page);
        // APIを叩きに行く処理
        handleSearchUser();
    };


    const arrowIcon = iconName => {
        let icon;
        if (iconName === "left") {
            icon = <i class="fa-solid fa-chevron-left"></i>;
        } else {
            icon = <i class="fa-solid fa-chevron-right"></i>;
        }
        return icon;
    };

    // ページ数の計算
    // const calculatePageCount = () => {
    //     return Math.ceil(userSize / ONE_PAGE_DISPLAY_USERS)
    // };

    // ページネーションを表示
    return (
        <div className="page">
            <ReactPaginate
                pageCount={totalPages}
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