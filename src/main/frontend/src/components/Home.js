import React, { useState } from "react";
import { NumericFormat } from 'react-number-format';
import {
    MDBCard,
    MDBCardBody,
    MDBCardTitle,
    MDBCardText,
    MDBCardImage,
    MDBBtn,
    MDBContainer,
    MDBRow,
    MDBCol,
    MDBInput
} from 'mdb-react-ui-kit';

const Home = () => {

    const [books, setBooks] = useState([]);
    const [title, setTitle] = useState('');
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);



    const searchTitle = async () => {
        const trimmed = title.trim();
        if (!trimmed) {
            setError('제목을 입력하여 주십시오');
            return;
        }

        try {
            setLoading(true);
            setError(null);

            //한글/공백 안전 인코딩
            const q = encodeURIComponent(trimmed);

            const url = `http://localhost:8080/books/list?title=${q}`;

            const response = await fetch(url);

            if (!response.ok) {
                // 에러 응답이 JSON이 아닐 수도 있으니 text로 한번 받기
                const errText = await response.text();
                throw new Error(errText || `HTTP ${response.status}`);

            }

            const data = await response.json();
            setBooks(Array.isArray(data) ? data : []);


        } catch (e) {
            setBooks([]);
            setError(`검색 실패: ${(e instanceof Error ? e.message : String(e))}`);
        } finally {
            setLoading(false);
        }
    };

    const onKeyDown = (e) => {
        if (e.key === 'Enter') searchTitle();
    };


    return (
        <div className="container text-center">
            <h1 className="mb-3">It Book</h1>
            <div className="col-md-4 mx-auto mb-3">
                {/* MDBInput 사용으로 라벨 겹침 해결! */}
                <div className="input-group">
                    <MDBInput
                        id="title"
                        label="제목 검색"
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        onKeyDown={onKeyDown}
                    />
                    <MDBBtn onClick={searchTitle} disabled={loading}>
                        {loading ? '검색중...' : <i className="fas fa-search"></i>}
                    </MDBBtn>
                </div>
                {error && <div className="alert alert-danger">{error}</div>}
            </div>
            <MDBContainer>
                <MDBRow>
                    {books.map(book => (
                        <MDBCol md="4" key={book.id}>
                            <MDBCard>
                                <MDBCardImage 
                                src={`https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/${book.isbn}.jpg`} 
                                position='top' 
                                alt={book.title} 
                                style={{ height: "300px", objectFit: "contain" }}/>
                                <MDBCardBody>
                                    <MDBCardTitle>{book.title}</MDBCardTitle>
                                    <MDBCardText>
                                        <NumericFormat value={book.price} allowLeadingZeros thousandSeparator="," displayType="text" />
                                        원
                                    </MDBCardText>
                                    <MDBBtn href={`https://product.kyobobook.co.kr/detail/${book.sale_id}` } target="_blank" rel="noreferrer">구입하기</MDBBtn>
                                </MDBCardBody>
                            </MDBCard>
                        </MDBCol>
                    ))}


                </MDBRow>


            </MDBContainer>

        </div>
    )
}

export default Home;
